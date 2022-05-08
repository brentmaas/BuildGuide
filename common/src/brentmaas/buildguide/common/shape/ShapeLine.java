package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyInt;

public class ShapeLine extends Shape {
	private PropertyInt propertyDx = new PropertyInt(0, 3, BuildGuide.screenHandler.translate("property.buildguide.delta", "X"), () -> update());
	private PropertyInt propertyDy = new PropertyInt(1, 0, BuildGuide.screenHandler.translate("property.buildguide.delta", "Y"), () -> update());
	private PropertyInt propertyDz = new PropertyInt(2, 0, BuildGuide.screenHandler.translate("property.buildguide.delta", "Z"), () -> update());
	
	public ShapeLine() {
		super();
		
		properties.add(propertyDx);
		properties.add(propertyDy);
		properties.add(propertyDz);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int d = Math.max(Math.max(Math.abs(propertyDx.value), Math.abs(propertyDy.value)), Math.abs(propertyDz.value));
		double dx = ((double) propertyDx.value) / d;
		double dy = ((double) propertyDy.value) / d;
		double dz = ((double) propertyDz.value) / d;
		for(int i = 0;i <= d;++i) {
			addShapeCube(buffer, (int) (dx * i + 0.5 * Math.signum(propertyDx.value)), (int) (dy * i + 0.5 * Math.signum(propertyDy.value)), (int) (dz * i + 0.5 * Math.signum(propertyDz.value)));
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.line";
	}
}
