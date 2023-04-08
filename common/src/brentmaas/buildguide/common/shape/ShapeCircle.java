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
		float dx = propertyRadius.value, dy = propertyRadius.value, dz = propertyRadius.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
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
		setOriginOffset(dx == 0 ? 0 : offset, dy == 0 ? 0 : offset, dz == 0 ? 0 : offset);
		
		for(int x = (int) Math.floor(-dx + originOffsetX); x <= (int) Math.ceil(dx + originOffsetX);++x) {
			for(int y = (int) Math.floor(-dy + originOffsetY); y <= (int) Math.ceil(dy + originOffsetY);++y) {
				for(int z = (int) Math.floor(-dz + originOffsetZ); z <= (int) Math.ceil(dz + originOffsetZ);++z) {
					double r2 = (x - originOffsetX) * (x - originOffsetX) + (y - originOffsetY) * (y - originOffsetY) + (z - originOffsetZ) * (z - originOffsetZ);
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
