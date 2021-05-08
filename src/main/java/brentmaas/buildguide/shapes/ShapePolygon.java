package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapePolygon extends Shape{
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
	private String[] rotationNames = {"0°", "90°", "180°", "270°"};
	
	//TODO propertySides
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyEnum<rotation> propertyRot = new PropertyEnum<rotation>(0, 165, rotation.ROT0, new TranslationTextComponent("property.buildguide.rotation"), () -> {this.update();}, rotationNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 185, 3, new TranslationTextComponent("property.buildguide.radius"), () -> {this.update();});
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(0, 205, 1, new TranslationTextComponent("property.buildguide.height"), () -> {this.update();});
	
	public ShapePolygon() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRot);
		properties.add(propertyRadius);
		properties.add(propertyHeight);
	}
	
	protected void updateShape(BufferBuilder builder) {
		//TODO: Polygon updateShape; currently asymmetric and has duplicates
		int r = propertyRadius.value;
		for(int i = 0;i < 3;++i) {
			int minA = (int) Math.floor(Math.min(r * (Math.sin(2 * Math.PI * i / 3) - Math.tan(Math.PI / 3) * Math.cos(2 * Math.PI * i / 3)), r * (Math.sin(2 * Math.PI * i / 3) + Math.tan(Math.PI / 3) * Math.cos(2 * Math.PI * i / 3))));
			int minB = (int) Math.floor(Math.min(r * (-Math.cos(2 * Math.PI * i / 3) - Math.tan(Math.PI / 3) * Math.sin(2 * Math.PI * i / 3)), r * (-Math.cos(2 * Math.PI * i / 3) + Math.tan(Math.PI / 3) * Math.sin(2 * Math.PI * i / 3))));
			int maxA = (int) Math.floor(Math.max(r * (Math.sin(2 * Math.PI * i / 3) - Math.tan(Math.PI / 3) * Math.cos(2 * Math.PI * i / 3)), r * (Math.sin(2 * Math.PI * i / 3) + Math.tan(Math.PI / 3) * Math.cos(2 * Math.PI * i / 3))));
			int maxB = (int) Math.floor(Math.max(r * (-Math.cos(2 * Math.PI * i / 3) - Math.tan(Math.PI / 3) * Math.sin(2 * Math.PI * i / 3)), r * (-Math.cos(2 * Math.PI * i / 3) + Math.tan(Math.PI / 3) * Math.sin(2 * Math.PI * i / 3))));
			for(int a = minA;a < maxA + 1;++a) {
				for(int b = minB;b < maxB + 1;++b) {
					double adx = (a - r * Math.sin(2 * Math.PI * i / 3)) * Math.cos(2 * Math.PI * i / 3) + (b + r * Math.cos(2 * Math.PI * i / 3)) * Math.sin(2 * Math.PI * i / 3);
					double d2 = (a - r * Math.sin(2 * Math.PI * i / 3) - adx * Math.cos(2 * Math.PI * i / 3)) * (a - r * Math.sin(2 * Math.PI * i / 3) - adx * Math.cos(2 * Math.PI * i / 3)) + (b + r * Math.cos(2 * Math.PI * i / 3) - adx * Math.sin(2 * Math.PI * i / 3)) * (b + r * Math.cos(2 * Math.PI * i / 3) - adx * Math.sin(2 * Math.PI * i / 3));
					if(d2 <= 0.25) {
						System.out.println("h");
						for(int h = 0;h < propertyHeight.value;++h) {
							addCube(builder, a + 0.2, h + 0.2, b + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
						}
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.polygon";
	}
}
