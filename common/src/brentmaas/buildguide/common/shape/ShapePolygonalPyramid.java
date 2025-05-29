package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyMinimumInt;
import brentmaas.buildguide.common.property.PropertyNonzeroFloat;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapePolygonalPyramid extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	private enum rotation{
		ROT0,
		ROT90,
		ROT180,
		ROT270
	}

	private String[] directionNames = {"X", "Y", "Z"};
	private String[] rotationNames = {"0\u00B0", "90\u00B0", "180\u00B0", "270\u00B0"};
	
	//Constant arrays for rotation
	private static final int[] rotXX = {1, 0, -1, 0};
	private static final int[] rotXY = {0, -1, 0, 1};
	private static final int[] rotYX = {0, 1, 0, -1};
	
	private PropertyMinimumInt propertySides = new PropertyMinimumInt(4, new Translatable("property.buildguide.sides"), () -> update(), 3);
	private PropertyPositiveFloat propertyRadius = new PropertyPositiveFloat(3, new Translatable("property.buildguide.radius"), () -> update());
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.Y, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyEnum<rotation> propertyRot = new PropertyEnum<rotation>(rotation.ROT0, new Translatable("property.buildguide.rotation"), () -> update(), rotationNames);
	private PropertyNonzeroFloat propertyHeight = new PropertyNonzeroFloat(4, new Translatable("property.buildguide.height"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());
	
	public ShapePolygonalPyramid() {
		super();
		
		properties.add(propertySides);
		properties.add(propertyRadius);
		properties.add(propertyDir);
		properties.add(propertyRot);
		properties.add(propertyHeight);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int n = propertySides.value;
		float radius = propertyRadius.value;
		int rot = propertyRot.value.ordinal();
		float height = propertyHeight.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		switch(propertyDir.value) {
		case X:
			setOriginOffset(0.0, offset, offset);
			break;
		case Y:
			setOriginOffset(offset, 0.0, offset);
			break;
		case Z:
			setOriginOffset(offset, offset, 0.0);
			break;
		}
		for(int h = (int) Math.floor(height > 0 ? 0 : height + 1);h < Math.ceil(height > 0 ? height : 1);++h) {
			float r = (radius + 1) * Math.max(Math.abs(height) - Math.abs(h), 0) / Math.abs(height) - 1;
			float rNext = (radius + 1) * Math.max(Math.abs(height) - Math.abs(h) - 1, 0) / Math.abs(height) - 1;
			for(int i = 0;i < n;++i) {
				int minB = (int) Math.floor(Math.min(r * (-Math.cos(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n)), r * (-Math.cos(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n))) + offset);
				int minBNext = (int) Math.floor(Math.min(rNext * (-Math.cos(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n)), rNext * (-Math.cos(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n))) + offset);
				int maxA = (int) Math.ceil(Math.max(r * (Math.sin(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.cos(2 * Math.PI * i / n)), r * (Math.sin(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.cos(2 * Math.PI * i / n))) + offset);
				int maxB = (int) Math.ceil(Math.max(r * (-Math.cos(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n)), r * (-Math.cos(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n))) + offset);
				int maxBNext = (int) Math.ceil(Math.max(rNext * (-Math.cos(2 * Math.PI * i / n) - Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n)), rNext * (-Math.cos(2 * Math.PI * i / n) + Math.tan(Math.PI / n) * Math.sin(2 * Math.PI * i / n))) + offset);
				for(int a = 0;a <= maxA;++a) {
					for(int b = Math.min(minB, minBNext);b <= Math.max(maxB, maxBNext);++b) {
						double theta = Math.atan2(b - offset, a - offset) + Math.PI / 2;
						double ri = (a - offset) * Math.sin(2 * Math.PI * i / n) - (b - offset) * Math.cos(2 * Math.PI * i / n);
						if(theta < 0 && i > 0) theta += 2 * Math.PI;
						if((((ri - rNext) >= 0.5 && Math.abs(height) <= radius + 1) || (ri - r) >= -0.5 && Math.abs(height) >= radius + 1) && (ri - r) < 0.5 && theta >= (2 * i - 1) * Math.PI / n && theta < (2 * i + 1) * Math.PI / n) {
							switch(propertyDir.value) {
							case X:
								addShapeCube(buffer, h, b * rotXX[rot] + a * rotYX[rot], a * rotXX[rot] + b * rotXY[rot]);
								if(a != 0) addShapeCube(buffer, h, b * rotXX[rot] - a * rotYX[rot], -a * rotXX[rot] + b * rotXY[rot]);
								break;
							case Y:
								addShapeCube(buffer, b * rotXX[rot] + a * rotYX[rot], h, a * rotXX[rot] + b * rotXY[rot]);
								if(a != 0) addShapeCube(buffer, b * rotXX[rot] - a * rotYX[rot], h, -a * rotXX[rot] + b * rotXY[rot]);
								break;
							case Z:
								addShapeCube(buffer, a * rotXX[rot] + b * rotXY[rot], b * rotXX[rot] + a * rotYX[rot], h);
								if(a != 0) addShapeCube(buffer, -a * rotXX[rot] + b * rotXY[rot], b * rotXX[rot] - a * rotYX[rot], h);
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.polygonalpyramid";
	}
}
