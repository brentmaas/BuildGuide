package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeLine extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private final String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyNonzeroInt propertyLength = new PropertyNonzeroInt(1, 5, new TranslationTextComponent("property.buildguide.length"), () -> {this.update();});
	
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
			addCube(builder, dx * i + 0.2, dy * i + 0.2, dz * i + 0.2, 0.6);
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.line";
	}
}
