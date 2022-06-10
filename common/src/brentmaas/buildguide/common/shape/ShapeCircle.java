package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveInt;

public class ShapeCircle extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};

	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, BuildGuide.screenHandler.translate("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(3, BuildGuide.screenHandler.translate("property.buildguide.radius"), () -> update());
	private PropertyNonzeroInt propertyDepth = new PropertyNonzeroInt(1, BuildGuide.screenHandler.translate("property.buildguide.depth"), () -> update());
	
	public ShapeCircle() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyDepth);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int dx = propertyRadius.value, dy = propertyRadius.value, dz = propertyRadius.value;
		switch(propertyDir.value) {
		case X:
			dx = 0;
			break;
		case Y:
			dy = 0;
			break;
		case Z:
			dz = 0;
			break;
		}
		
		for(int x = -dx; x <= dx;++x) {
			for(int y = -dy; y <= dy;++y) {
				for(int z = -dz; z <= dz;++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (propertyRadius.value - 0.5) * (propertyRadius.value - 0.5) && r2 <= (propertyRadius.value + 0.5) * (propertyRadius.value + 0.5)) {
						for(int s = (propertyDepth.value > 0 ? 0 : propertyDepth.value + 1);s < (propertyDepth.value > 0 ? propertyDepth.value : 1);++s) {
							addShapeCube(buffer, x + (propertyDir.value == direction.X ? s : 0), y + (propertyDir.value == direction.Y ? s : 0), z + (propertyDir.value == direction.Z ? s : 0));
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
