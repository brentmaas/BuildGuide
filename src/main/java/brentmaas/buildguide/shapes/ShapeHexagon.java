package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeHexagon extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	private enum rotation{
		ROT0,
		ROT90
	}

	private String[] directionNames = {"X", "Y", "Z"};
	private String[] rotationNames = {"0°", "90°"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyEnum<rotation> propertyRot = new PropertyEnum<rotation>(0, 165, rotation.ROT0, new TranslationTextComponent("property.buildguide.rotation"), () -> {this.update();}, rotationNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 185, 3, new TranslationTextComponent("property.buildguide.radius"), () -> {this.update();});
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(0, 205, 1, new TranslationTextComponent("property.buildguide.height"), () -> {this.update();});
	
	public ShapeHexagon() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyRot);
		properties.add(propertyRadius);
		properties.add(propertyHeight);
	}
	
	protected void updateShape(BufferBuilder builder) {
		//TODO: Hexagon updateShape
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.hexagon";
	}
}
