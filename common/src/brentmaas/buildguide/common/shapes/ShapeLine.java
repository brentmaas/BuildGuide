package brentmaas.buildguide.common.shapes;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;

public class ShapeLine extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private final String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, BuildGuide.screenHandler.translate("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyNonzeroInt propertyLength = new PropertyNonzeroInt(1, 5, BuildGuide.screenHandler.translate("property.buildguide.length"), () -> {update();});
	
	public ShapeLine() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyLength);
	}
	
	protected void updateShape(IShapeBuffer buffer) {
		int dx = 0, dy = 0, dz = 0;
		switch(propertyDir.value) {
		case X:
			dx = (int) Math.signum(propertyLength.value);
			break;
		case Y:
			dy = (int) Math.signum(propertyLength.value);
			break;
		case Z:
			dz = (int) Math.signum(propertyLength.value);
			break;
		}
		
		for(int i = 0;i < Math.abs(propertyLength.value);++i) {
			addShapeCube(buffer, dx * i, dy * i, dz * i);
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.line";
	}
}
