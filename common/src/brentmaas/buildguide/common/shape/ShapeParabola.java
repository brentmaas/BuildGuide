package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;

public class ShapeParabola extends Shape {
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
	
	//Constant arrays for rotation
	private static final int[] rotXX = {1, 0, -1, 0};
	private static final int[] rotXY = {0, -1, 0, 1};
	private static final int[] rotYX = {0, 1, 0, -1};
	
	private String[] directionNames = {"X", "Y", "Z"};
	private String[] rotationNames = {"0\u00B0", "90\u00B0", "180\u00B0", "270\u00B0"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, BuildGuide.screenHandler.translate("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyEnum<rotation> propertyRot = new PropertyEnum<rotation>(rotation.ROT0, BuildGuide.screenHandler.translate("property.buildguide.rotation"), () -> update(), rotationNames);
	private PropertyPositiveFloat propertyHalfwidth = new PropertyPositiveFloat(3, BuildGuide.screenHandler.translate("property.buildguide.halfwidth"), () -> update());
	private PropertyPositiveFloat propertyHeight = new PropertyPositiveFloat(3, BuildGuide.screenHandler.translate("property.buildguide.height"), () -> update());
	private PropertyNonzeroInt propertyDepth = new PropertyNonzeroInt(1, BuildGuide.screenHandler.translate("property.buildguide.depth"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.evenmode"), () -> update());
	
	public ShapeParabola() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRot);
		properties.add(propertyHalfwidth);
		properties.add(propertyHeight);
		properties.add(propertyDepth);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float hw = propertyHalfwidth.value, h = propertyHeight.value;
		double fac = ((double) h) / hw / hw;
		int rot = propertyRot.value.ordinal();
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		
		switch(propertyDir.value) {
		case X:
			setBaseposOffset(0.0, offset * rotYX[rot], offset * rotXX[rot]);
			break;
		case Y:
			setBaseposOffset(offset * rotYX[rot], 0.0, offset * rotXX[rot]);
			break;
		case Z:
			setBaseposOffset(offset * rotXX[rot], offset * rotYX[rot], 0.0);
			break;
		}
		
		for(int a = (int) Math.floor(-hw+offset);a <= (int) Math.ceil(hw+offset);++a) {
			for(int b = 0;b < h;++b) {
				if(fac * (a - offset) * (a - offset) >= b && (fac * (a - offset - Math.signum(a - offset)) * (a - offset - Math.signum(a - offset)) < b || fac * (a - offset) * (a - offset) < b + 1)) {
					for(int s = (propertyDepth.value > 0 ? 0 : propertyDepth.value + 1);s < (propertyDepth.value > 0 ? propertyDepth.value : 1);++s) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(buffer, s, b * rotXX[rot] + a * rotYX[rot], a * rotXX[rot] + b * rotXY[rot]);
							break;
						case Y:
							addShapeCube(buffer, b * rotXX[rot] + a * rotYX[rot], s, a * rotXX[rot] + b * rotXY[rot]);
							break;
						case Z:
							addShapeCube(buffer, a * rotXX[rot] + b * rotXY[rot], b * rotXX[rot] + a * rotYX[rot], s);
							break;
						}
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.parabola";
	}
}
