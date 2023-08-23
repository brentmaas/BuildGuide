package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;

public class ShapeCircle extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};

	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, BuildGuide.screenHandler.translate("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveFloat propertyRadius = new PropertyPositiveFloat(3, BuildGuide.screenHandler.translate("property.buildguide.radius"), () -> update());
	private PropertyNonzeroInt propertyDepth = new PropertyNonzeroInt(1, BuildGuide.screenHandler.translate("property.buildguide.depth"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.evenmode"), () -> update());
	
	public ShapeCircle() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyDepth);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float radius = propertyRadius.value;
		int depth = propertyDepth.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		setOriginOffset(propertyDir.value == direction.X ? 0 : offset, propertyDir.value == direction.Y ? 0 : offset, propertyDir.value == direction.Z ? 0 : offset);
		
		for(int x = (int) Math.floor(-radius + offset);x <= (int) Math.ceil(radius + offset);++x) {
			for(int y = (int) Math.floor(-radius + offset);y <= (int) Math.ceil(radius + offset);++y) {
				double r2 = (x - offset) * (x - offset) + (y - offset) * (y - offset);
				if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
					for(int z = (depth > 0 ? 0 : depth + 1);z < (depth > 0 ? depth : 1);++z) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(buffer, z, x, y);
							break;
						case Y:
							addShapeCube(buffer, x, z, y);
							break;
						case Z:
							addShapeCube(buffer, x, y, z);
							break;
						}
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.circle";
	}
}
