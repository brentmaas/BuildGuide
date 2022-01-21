package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.text.TranslatableText;

public class ShapeCircle extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslatableText("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(1, 3, new TranslatableText("property.buildguide.radius"), () -> update());
	private PropertyNonzeroInt propertyDepth = new PropertyNonzeroInt(2, 1, new TranslatableText("property.buildguide.depth"), () -> update());
	
	public ShapeCircle() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyDepth);
	}
	
	protected void updateShape(BufferBuilder builder) {
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
							addShapeCube(builder, x + (propertyDir.value == direction.X ? s : 0), y + (propertyDir.value == direction.Y ? s : 0), z + (propertyDir.value == direction.Z ? s : 0));
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
