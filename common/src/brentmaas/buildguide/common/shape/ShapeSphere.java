package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyPositiveInt;

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
	
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(3, BuildGuide.screenHandler.translate("property.buildguide.radius"), () -> update());
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(dome.NO, BuildGuide.screenHandler.translate("property.buildguide.dome"), () -> update(), domeNames);
	private PropertyBoolean property2x2x2 = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.basepos2x2x2"), () -> update());
	
	public ShapeSphere() {
		super();
		
		properties.add(propertyRadius);
		properties.add(propertyDome);
		properties.add(property2x2x2);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int radius = propertyRadius.value;
		double offset = property2x2x2.value ? 0.5 : 0.0;
		setBaseposOffset(offset, offset, offset);
		
		for(int x = (int) Math.floor((propertyDome.value == dome.POSITIVE_X ? 0 : -radius) + offset); x <= Math.ceil((propertyDome.value == dome.NEGATIVE_X ? 0 : radius) + offset);++x) {
			for(int y = (int) Math.floor((propertyDome.value == dome.POSITIVE_Y ? 0 : -radius) + offset); y <= Math.ceil((propertyDome.value == dome.NEGATIVE_Y ? 0 : radius) + offset);++y) {
				for(int z = (int) Math.floor((propertyDome.value == dome.POSITIVE_Z ? 0 : -radius) + offset); z <= Math.ceil((propertyDome.value == dome.NEGATIVE_Z ? 0 : radius) + offset);++z) {
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
