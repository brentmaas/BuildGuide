package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeTorus extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveFloat propertyOuterRadius = new PropertyPositiveFloat(5, new Translatable("property.buildguide.outerradius"), () -> updateOuter());
	private PropertyPositiveFloat propertyInnerRadius = new PropertyPositiveFloat(3, new Translatable("property.buildguide.innerradius"), () -> updateInner());
	private PropertyBoolean propertyOuterEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.outerevenmode"), () -> update());
	private PropertyBoolean propertyInnerEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.innerevenmode"), () -> update());
	
	public ShapeTorus() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyOuterRadius);
		properties.add(propertyInnerRadius);
		properties.add(propertyOuterEvenMode);
		properties.add(propertyInnerEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float or = propertyOuterRadius.value, ir = propertyInnerRadius.value;
		double offsetOuter = propertyOuterEvenMode.value ? 0.5 : 0.0;
		double offsetInner = propertyInnerEvenMode.value ? 0.5 : 0.0;
		switch(propertyDir.value) {
		case X:
			setOriginOffset(offsetInner, offsetOuter, offsetOuter);
			break;
		case Y:
			setOriginOffset(offsetOuter, offsetInner, offsetOuter);
			break;
		case Z:
			setOriginOffset(offsetOuter, offsetOuter, offsetInner);
			break;
		}
		for(int a = (int) Math.floor(-or - ir - offsetOuter);a < (int) Math.ceil(or + ir + 1 + offsetOuter);++a) {
			for(int b = (int) Math.floor(-or - ir - offsetOuter);b < (int) Math.ceil(or + ir + 1 + offsetOuter);++b) {
				double theta = Math.atan2(b - offsetOuter, a - offsetOuter);
				double a_circ = or * Math.cos(theta) + offsetOuter;
				double b_circ = or * Math.sin(theta) + offsetOuter;
				for(int c = (int) Math.floor(-ir + offsetInner);c < (int) Math.ceil(ir + 1 + offsetInner);++c) {
					double r2 = (a - a_circ) * (a - a_circ) + (b - b_circ) * (b - b_circ) + (c - offsetInner) * (c - offsetInner);
					if(r2 >= (ir - 0.5) * (ir - 0.5) && r2 < (ir + 0.5) * (ir + 0.5)) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(buffer, c, b, a);
							break;
						case Y:
							addShapeCube(buffer, a, c, b);
							break;
						case Z:
							addShapeCube(buffer, b, a, c);
							break;
						}
					}
				}
			}
		}
	}
	
	private void updateOuter() {
		if(propertyOuterRadius.value < propertyInnerRadius.value) propertyInnerRadius.setValue(propertyOuterRadius.value);
		
		update();
	}
	
	private void updateInner() {
		if(propertyOuterRadius.value < propertyInnerRadius.value) propertyOuterRadius.setValue(propertyInnerRadius.value);
		
		update();
	}
}
