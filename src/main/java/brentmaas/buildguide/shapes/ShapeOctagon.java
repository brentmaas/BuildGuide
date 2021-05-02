package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeOctagon extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}

	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 165, 3, new TranslationTextComponent("property.buildguide.radius"), () -> {this.update();});
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(0, 185, 1, new TranslationTextComponent("property.buildguide.height"), () -> {this.update();});
	
	public ShapeOctagon() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyHeight);
		
		onDeselectedInGUI();
	}
	
	protected void updateShape(BufferBuilder builder) {
		//TODO: Octagon updateShape
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.octagon";
	}
}
