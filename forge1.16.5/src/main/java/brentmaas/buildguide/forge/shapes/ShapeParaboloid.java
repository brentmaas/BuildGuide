package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeParaboloid extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveInt propertyHalfwidth1 = new PropertyPositiveInt(1, 3, new TranslationTextComponent("property.buildguide.halfwidthdir", "Y"), () -> update());
	private PropertyPositiveInt propertyHalfwidth2 = new PropertyPositiveInt(2, 3, new TranslationTextComponent("property.buildguide.halfwidthdir", "Z"), () -> update());
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(3, 3, new TranslationTextComponent("property.buildguide.height"), () -> update());
	
	public ShapeParaboloid() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyHalfwidth1);
		properties.add(propertyHalfwidth2);
		properties.add(propertyHeight);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int hw1 = propertyHalfwidth1.value;
		int hw2 = propertyHalfwidth2.value;
		int h = propertyHeight.value;
		double fac1 = Math.abs((double) h) / hw1 / hw1;
		double fac2 = Math.abs((double) h) / hw2 / hw2;
		
		for(int a = -hw1;a <= hw1;++a) {
			for(int b = -hw2;b <= hw2;++b) {
				for(int c = 0;c < Math.abs(h);++c) {
					if(fac1 * a * a + fac2 * b * b >= c && (fac1 * (a - Math.signum(a)) * (a - Math.signum(a)) + fac2 * b * b < c || fac1 * a * a + fac2 * (b - Math.signum(b)) * (b - Math.signum(b)) < c || fac1 * a * a + fac2 * b * b < c + 1)) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(builder, (int) (c * Math.signum(h)), a, b);
							break;
						case Y:
							addShapeCube(builder, a, (int) (c * Math.signum(h)), b);
							break;
						case Z:
							addShapeCube(builder, a, b, (int) (c * Math.signum(h)));
							break;
						}
					}
				}
			}
		}
		
		switch(propertyDir.value) {
		case X:
			propertyHalfwidth1.setName(new TranslationTextComponent("property.buildguide.halfwidthdir", "Y"));
			propertyHalfwidth2.setName(new TranslationTextComponent("property.buildguide.halfwidthdir", "Z"));
			break;
		case Y:
			propertyHalfwidth1.setName(new TranslationTextComponent("property.buildguide.halfwidthdir", "X"));
			propertyHalfwidth2.setName(new TranslationTextComponent("property.buildguide.halfwidthdir", "Z"));
			break;
		case Z:
			propertyHalfwidth1.setName(new TranslationTextComponent("property.buildguide.halfwidthdir", "X"));
			propertyHalfwidth2.setName(new TranslationTextComponent("property.buildguide.halfwidthdir", "Y"));
			break;
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.paraboloid";
	}
}
