package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
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
	
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 3, BuildGuide.screenHandler.translate("property.buildguide.radius"), () -> update());
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(1, dome.NO, BuildGuide.screenHandler.translate("property.buildguide.dome"), () -> update(), domeNames);
	
	public ShapeSphere() {
		super();
		
		properties.add(propertyRadius);
		properties.add(propertyDome);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int radius = propertyRadius.value;
		
		for(int x = propertyDome.value == dome.POSITIVE_X ? 0 : -radius; x <= (propertyDome.value == dome.NEGATIVE_X ? 0 : radius);++x) {
			for(int y = propertyDome.value == dome.POSITIVE_Y ? 0 : -radius; y <= (propertyDome.value == dome.NEGATIVE_Y ? 0 : radius);++y) {
				for(int z = propertyDome.value == dome.POSITIVE_Z ? 0 : -radius; z <= (propertyDome.value == dome.NEGATIVE_Z ? 0 : radius);++z) {
					int r2 = x * x + y * y + z * z;
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
