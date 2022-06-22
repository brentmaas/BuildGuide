package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyPositiveInt;

public class ShapeTorus extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, BuildGuide.screenHandler.translate("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveInt propertyOuterRadius = new PropertyPositiveInt(5, BuildGuide.screenHandler.translate("property.buildguide.outerradius"), () -> updateOuter());
	private PropertyPositiveInt propertyInnerRadius = new PropertyPositiveInt(3, BuildGuide.screenHandler.translate("property.buildguide.innerradius"), () -> updateInner());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.evenmode"), () -> update());
	
	public ShapeTorus() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyOuterRadius);
		properties.add(propertyInnerRadius);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		switch(propertyDir.value) {
		case X:
			setBaseposOffset(0.0, offset, offset);
			break;
		case Y:
			setBaseposOffset(offset, 0.0, offset);
			break;
		case Z:
			setBaseposOffset(offset, offset, 0.0);
			break;
		}
		for(int a = (int) Math.floor(-propertyOuterRadius.value - propertyInnerRadius.value - offset);a < (int) Math.ceil(propertyOuterRadius.value + propertyInnerRadius.value + 1 + offset);++a) {
			for(int b = (int) Math.floor(-propertyOuterRadius.value - propertyInnerRadius.value - offset);b < (int) Math.ceil(propertyOuterRadius.value + propertyInnerRadius.value + 1 + offset);++b) {
				double theta = Math.atan2(b - offset, a - offset);
				double a_circ = propertyOuterRadius.value * Math.cos(theta) + offset;
				double b_circ = propertyOuterRadius.value * Math.sin(theta) + offset;
				for(int c = -propertyInnerRadius.value;c < propertyInnerRadius.value + 1;++c) {
					double r2 = (a - a_circ) * (a - a_circ) + (b - b_circ) * (b - b_circ) + c * c;
					if(r2 >= (propertyInnerRadius.value - 0.5) * (propertyInnerRadius.value - 0.5) && r2 < (propertyInnerRadius.value + 0.5) * (propertyInnerRadius.value + 0.5)) {
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
		if(propertyOuterRadius.value < propertyInnerRadius.value) propertyInnerRadius.setValue(propertyOuterRadius.value);;
		
		update();
	}
	
	private void updateInner() {
		if(propertyOuterRadius.value < propertyInnerRadius.value) propertyOuterRadius.setValue(propertyInnerRadius.value);
		
		update();
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.torus";
	}
}
