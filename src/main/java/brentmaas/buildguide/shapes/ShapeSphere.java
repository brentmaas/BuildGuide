package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

//TODO Odd sphere + even sphere
public class ShapeSphere extends Shape{
	private enum dome {
		NO,
		NEGATIVE_X,
		NEGATIVE_Y,
		NEGATIVE_Z,
		POSITIVE_X,
		POSITIVE_Y,
		POSITIVE_Z
	}
	
	private String[] domeNames = {"-", "-X", "-Y", "-Z", "+X", "+Y", "+Z"};
	
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 145, 3, new TranslationTextComponent("property.buildguide.radius").getString(), this);
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(0, 165, dome.NO, new TranslationTextComponent("property.buildguide.dome").getString(), this, domeNames);
	
	public ShapeSphere() {
		super();
		
		properties.add(propertyRadius);
		properties.add(propertyDome);
		
		onDeselectedInGUI();
	}
	
	protected void updateShape(BufferBuilder builder) {
		if(State.basePos == null) return;
		
		int radius = propertyRadius.value;
		
		for(int x = propertyDome.value == dome.POSITIVE_X ? 0 : -radius; x <= (propertyDome.value == dome.NEGATIVE_X ? 0 : radius);++x) {
			for(int y = propertyDome.value == dome.POSITIVE_Y ? 0 : -radius; y <= (propertyDome.value == dome.NEGATIVE_Y ? 0 : radius);++y) {
				for(int z = propertyDome.value == dome.POSITIVE_Z ? 0 : -radius; z <= (propertyDome.value == dome.NEGATIVE_Z ? 0 : radius);++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
						addCube(builder, State.basePos.x + x + 0.2, State.basePos.y + y + 0.2, State.basePos.z + z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.sphere";
	}
}
