package brentmaas.buildguide.shapes;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeLine extends Shape{
	private enum direction{
		POSITIVE_X,
		POSITIVE_Y,
		POSITIVE_Z,
		NEGATIVE_X,
		NEGATIVE_Y,
		NEGATIVE_Z
	}
	
	private final String[] directionNames = {"+X", "+Y", "+Z", "-X", "-Y", "-Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.POSITIVE_X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyPositiveInt propertyLength = new PropertyPositiveInt(0, 165, 5, new TranslationTextComponent("property.buildguide.length"), () -> {this.update();});
	
	public ShapeLine() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyLength);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int dx = 0, dy = 0, dz = 0;
		switch(propertyDir.value) {
		case NEGATIVE_X:
			dx = -1;
			break;
		case NEGATIVE_Y:
			dy = -1;
			break;
		case NEGATIVE_Z:
			dz = -1;
			break;
		case POSITIVE_X:
			dx = 1;
			break;
		case POSITIVE_Y:
			dy = 1;
			break;
		case POSITIVE_Z:
			dz = 1;
			break;
		}
		
		for(int i = 0;i < propertyLength.value;++i) {
			addCube(builder, dx * i + 0.2, dy * i + 0.2, dz * i + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.line";
	}
}
