package brentmaas.buildguide.shapes;

import com.mojang.blaze3d.vertex.BufferBuilder;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import net.minecraft.network.chat.TranslatableComponent;

public class ShapeLine extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private final String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslatableComponent("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyNonzeroInt propertyLength = new PropertyNonzeroInt(1, 5, new TranslatableComponent("property.buildguide.length"), () -> update());
	
	public ShapeLine() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyLength);
	}
	
	protected void updateShape(BufferBuilder builder) {
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
			addShapeCube(builder, dx * i, dy * i, dz * i);
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.line";
	}
}
