package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeLine extends Shape{
	private enum direction{
		NEGATIVE_X,
		NEGATIVE_Y,
		NEGATIVE_Z,
		POSITIVE_X,
		POSITIVE_Y,
		POSITIVE_Z
	}
	
	private final String[] directionNames = {"-X", "-Y", "-Z", "+X", "+Y", "+Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.NEGATIVE_X, new TranslationTextComponent("property.buildguide.direction").getString(), this, directionNames);
	private PropertyPositiveInt propertyLength = new PropertyPositiveInt(0, 165, 5, new TranslationTextComponent("property.buildguide.length").getString(), this);
	
	public ShapeLine() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyLength);
		
		onDeselectedInGUI();
	}
	
	protected void updateShape(BufferBuilder builder) {
		if(State.basePos == null) return;
		
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
			addCube(builder, State.basePos.x + dx * i + 0.2, State.basePos.y + dy * i + 0.2, State.basePos.z + dz * i + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.line";
	}
}
