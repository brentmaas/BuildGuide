package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeRectangle extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyNonzeroInt propertyLength = new PropertyNonzeroInt(0, 165, 3, new TranslationTextComponent("property.buildguide.length"), () -> {this.update();});
	private PropertyNonzeroInt propertyWidth = new PropertyNonzeroInt(0, 185, 3, new TranslationTextComponent("property.buildguide.width"), () -> {this.update();});
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(0, 205, 1, new TranslationTextComponent("property.buildguide.height"), () -> {this.update();});
	
	public ShapeRectangle() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyLength);
		properties.add(propertyWidth);
		properties.add(propertyHeight);
		
		onDeselectedInGUI();
	}
	
	protected void updateShape(BufferBuilder builder) {
		for(int i = (propertyLength.value > 0 ? 0 : propertyLength.value + 1);i < (propertyLength.value > 0 ? propertyLength.value : 1);++i) {
			for(int j = (propertyHeight.value > 0 ? 0 : propertyHeight.value + 1);j < (propertyHeight.value > 0 ? propertyHeight.value : 1);++j) {
				switch(propertyDir.value) {
				case X:
					addCube(builder, j + 0.2, i + 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					addCube(builder, j + 0.2, i + 0.2, propertyWidth.value - 0.8, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					break;
				case Y:
					addCube(builder, 0.2, j + 0.2, i + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					addCube(builder, propertyWidth.value - 0.8, j + 0.2, i + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					break;
				case Z:
					addCube(builder, i + 0.2, 0.2, j + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					addCube(builder, i + 0.2, propertyWidth.value - 0.8, j + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					break;
				}
			}
		}
		for(int i = (propertyWidth.value > 0 ? 1 : propertyWidth.value);i < (propertyWidth.value > 0 ? propertyWidth.value - 1 : 0);++i) {
			for(int j = (propertyHeight.value > 0 ? 0 : propertyHeight.value + 1);j < (propertyHeight.value > 0 ? propertyHeight.value : 1);++j) {
				switch(propertyDir.value) {
				case X:
					addCube(builder, j + 0.2, 0.2, i + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					addCube(builder, j + 0.2, propertyLength.value - 0.8, i + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					break;
				case Y:
					addCube(builder, i + 0.2, j + 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					addCube(builder, i + 0.2, j + 0.2, propertyLength.value - 0.8, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					break;
				case Z:
					addCube(builder, 0.2, i + 0.2, j + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					addCube(builder, propertyLength.value - 0.8, i + 0.2, j + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					break;
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.rectangle";
	}
}
