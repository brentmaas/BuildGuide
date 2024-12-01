package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeParaboloid extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveFloat propertyHalfwidth1 = new PropertyPositiveFloat(3, new Translatable("property.buildguide.halfwidthdir", "Y"), () -> update());
	private PropertyPositiveFloat propertyHalfwidth2 = new PropertyPositiveFloat(3, new Translatable("property.buildguide.halfwidthdir", "Z"), () -> update());
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(3, new Translatable("property.buildguide.height"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());
	
	public ShapeParaboloid() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyHalfwidth1);
		properties.add(propertyHalfwidth2);
		properties.add(propertyHeight);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float hw1 = propertyHalfwidth1.value, hw2 = propertyHalfwidth2.value;
		int h = propertyHeight.value;
		double fac1 = Math.abs((double) h) / hw1 / hw1;
		double fac2 = Math.abs((double) h) / hw2 / hw2;
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
		
		for(int a = (int) Math.floor(-hw1 + offset);a <= (int) Math.ceil(hw1 + offset);++a) {
			for(int b = (int) Math.floor(-hw2 + offset);b <= (int) Math.ceil(hw2 + offset);++b) {
				for(int c = 0;c < Math.abs(h);++c) {
					if(fac1 * (a - offset) * (a - offset) + fac2 * (b - offset) * (b - offset) >= c && (fac1 * (a - offset - Math.signum(a - offset)) * (a - offset - Math.signum(a - offset)) + fac2 * (b - offset) * (b - offset) < c || fac1 * (a - offset) * (a - offset) + fac2 * (b - offset - Math.signum(b - offset)) * (b - offset - Math.signum(b - offset)) < c || fac1 * (a - offset) * (a - offset) + fac2 * (b - offset) * (b - offset) < c + 1)) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(buffer, (int) (c * Math.signum(h)), a, b);
							break;
						case Y:
							addShapeCube(buffer, a, (int) (c * Math.signum(h)), b);
							break;
						case Z:
							addShapeCube(buffer, a, b, (int) (c * Math.signum(h)));
							break;
						}
					}
				}
			}
		}
		
		switch(propertyDir.value) {
		case X:
			propertyHalfwidth1.setName(new Translatable("property.buildguide.halfwidthdir", "Y"));
			propertyHalfwidth2.setName(new Translatable("property.buildguide.halfwidthdir", "Z"));
			break;
		case Y:
			propertyHalfwidth1.setName(new Translatable("property.buildguide.halfwidthdir", "X"));
			propertyHalfwidth2.setName(new Translatable("property.buildguide.halfwidthdir", "Z"));
			break;
		case Z:
			propertyHalfwidth1.setName(new Translatable("property.buildguide.halfwidthdir", "X"));
			propertyHalfwidth2.setName(new Translatable("property.buildguide.halfwidthdir", "Y"));
			break;
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.paraboloid";
	}
}
