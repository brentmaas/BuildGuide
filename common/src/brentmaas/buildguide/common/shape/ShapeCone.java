package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyFloat;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeCone extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveFloat propertyRadius = new PropertyPositiveFloat(3, new Translatable("property.buildguide.radius"), () -> update());
	private PropertyFloat propertyHeight = new PropertyFloat(3.0f, new Translatable("property.buildguide.height"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());
	
	public ShapeCone() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyHeight);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		switch(propertyDir.value) {
		case X:
			setOriginOffset(0, offset, offset);
			break;
		case Y:
			setOriginOffset(offset, 0, offset);
			break;
		case Z:
			setOriginOffset(offset, offset, 0);
			break;
		}
		
		float radius = propertyRadius.value;
		float height = propertyHeight.value;
		for(int x = (int) Math.floor(-radius + offset);x <= (int) Math.ceil(radius + offset);++x) {
			for(int y = (int) Math.floor(-radius + offset);y <= (int) Math.ceil(radius + offset);++y) {
				for(int z = height < 0 ? (int) Math.floor(height) : 0;z <= (height > 0 ? (int) Math.ceil(height) : 0);++z) {
					double r = Math.sqrt((x - offset) * (x - offset) + (y - offset) * (y - offset));
					if(r <= radius + 0.5 && ((Math.abs(height) * (1.0 - r / radius) >= Math.abs(z) - 0.5 && Math.abs(height) * (1.0 - r / radius) < Math.abs(z) + 0.5) || (Math.abs(height) * (1.0 - (r - 0.5) / radius) >= Math.abs(z) && Math.abs(height) * (1.0 - (r + 0.5) / radius) < Math.abs(z)))) {
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
}
