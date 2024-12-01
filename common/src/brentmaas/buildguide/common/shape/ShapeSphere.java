package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeSphere extends Shape {
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
	
	private PropertyPositiveFloat propertyRadius = new PropertyPositiveFloat(3, new Translatable("property.buildguide.radius"), () -> update());
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(dome.NO, new Translatable("property.buildguide.dome"), () -> update(), domeNames);
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());
	
	public ShapeSphere() {
		super();
		
		properties.add(propertyRadius);
		properties.add(propertyDome);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float radius = propertyRadius.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		setOriginOffset(offset, offset, offset);
		
		for(int x = (int) Math.floor((propertyDome.value == dome.POSITIVE_X ? 0.5 : -radius) + offset); x <= Math.ceil((propertyDome.value == dome.NEGATIVE_X ? -0.5 : radius) + offset);++x) {
			for(int y = (int) Math.floor((propertyDome.value == dome.POSITIVE_Y ? 0.5 : -radius) + offset); y <= Math.ceil((propertyDome.value == dome.NEGATIVE_Y ? -0.5 : radius) + offset);++y) {
				for(int z = (int) Math.floor((propertyDome.value == dome.POSITIVE_Z ? 0.5 : -radius) + offset); z <= Math.ceil((propertyDome.value == dome.NEGATIVE_Z ? -0.5 : radius) + offset);++z) {
					double r2 = (x - offset) * (x - offset) + (y - offset) * (y - offset) + (z - offset) * (z - offset);
					if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
						addShapeCube(buffer, x, y, z);
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.sphere";
	}
}
