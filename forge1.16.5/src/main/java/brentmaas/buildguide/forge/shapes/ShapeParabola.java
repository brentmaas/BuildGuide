package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeParabola extends Shape{
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
	private String[] rotationNames = {"0°", "90°", "180°", "270°"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyEnum<rotation> propertyRot = new PropertyEnum<rotation>(1, rotation.ROT0, new TranslationTextComponent("property.buildguide.rotation"), () -> update(), rotationNames);
	private PropertyPositiveInt propertyHalfwidth = new PropertyPositiveInt(2, 3, new TranslationTextComponent("property.buildguide.halfwidth"), () -> update());
	private PropertyPositiveInt propertyHeight = new PropertyPositiveInt(3, 3, new TranslationTextComponent("property.buildguide.height"), () -> update());
	private PropertyNonzeroInt propertyDepth = new PropertyNonzeroInt(4, 1, new TranslationTextComponent("property.buildguide.depth"), () -> update());
	
	public ShapeParabola() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRot);
		properties.add(propertyHalfwidth);
		properties.add(propertyHeight);
		properties.add(propertyDepth);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int hw = propertyHalfwidth.value;
		int h = propertyHeight.value;
		double fac = ((double) h) / hw / hw;
		int rot = propertyRot.value.ordinal();
		
		for(int a = -hw;a <= hw;++a) {
			for(int b = 0;b < h;++b) {
				if(fac * a * a >= b && (fac * (a - Math.signum(a)) * (a - Math.signum(a)) < b || fac * a * a < b + 1)) {
					for(int s = (propertyDepth.value > 0 ? 0 : propertyDepth.value + 1);s < (propertyDepth.value > 0 ? propertyDepth.value : 1);++s) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(builder, s, b * rotXX[rot] + a * rotYX[rot], a * rotXX[rot] + b * rotXY[rot]);
							break;
						case Y:
							addShapeCube(builder, b * rotXX[rot] + a * rotYX[rot], s, a * rotXX[rot] + b * rotXY[rot]);
							break;
						case Z:
							addShapeCube(builder, a * rotXX[rot] + b * rotXY[rot], b * rotXX[rot] + a * rotYX[rot], s);
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
