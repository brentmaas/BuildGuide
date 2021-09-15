package brentmaas.buildguide.shapes;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeSphere extends Shape{
	private enum dome {
		NO,
		POSITIVE_X,
		POSITIVE_Y,
		POSITIVE_Z,
		NEGATIVE_X,
		NEGATIVE_Y,
		NEGATIVE_Z
	}
	
	private String[] domeNames = {"-", "+X", "+Y", "+Z", "-X", "-Y", "-Z"};
	
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 145, 3, new TranslationTextComponent("property.buildguide.radius"), () -> {this.update();});
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(0, 165, dome.NO, new TranslationTextComponent("property.buildguide.dome"), () -> {this.update();}, domeNames);
	
	public ShapeSphere() {
		super();
		
		properties.add(propertyRadius);
		properties.add(propertyDome);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int radius = propertyRadius.value;
		
		for(int x = propertyDome.value == dome.POSITIVE_X ? 0 : -radius; x <= (propertyDome.value == dome.NEGATIVE_X ? 0 : radius);++x) {
			for(int y = propertyDome.value == dome.POSITIVE_Y ? 0 : -radius; y <= (propertyDome.value == dome.NEGATIVE_Y ? 0 : radius);++y) {
				for(int z = propertyDome.value == dome.POSITIVE_Z ? 0 : -radius; z <= (propertyDome.value == dome.NEGATIVE_Z ? 0 : radius);++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
						addCube(builder, x + 0.2, y + 0.2, z + 0.2, 0.6, StateManager.getState().colourShapeR, StateManager.getState().colourShapeG, StateManager.getState().colourShapeB, StateManager.getState().colourShapeA);
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.sphere";
	}
}
