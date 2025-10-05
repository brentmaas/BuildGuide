package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyInt;
import brentmaas.buildguide.common.property.PropertyRunnable;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.shape.ShapeSet.Origin;

public class ShapeLine extends Shape {
	private PropertyInt propertyDx = new PropertyInt(3, new Translatable("property.buildguide.delta", "X"), () -> update());
	private PropertyInt propertyDy = new PropertyInt(0, new Translatable("property.buildguide.delta", "Y"), () -> update());
	private PropertyInt propertyDz = new PropertyInt(0, new Translatable("property.buildguide.delta", "Z"), () -> update());
	private PropertyRunnable propertySetEndpoint = new PropertyRunnable(() -> {
		Origin pos = BuildGuide.shapeHandler.getPlayerPosition();
		propertyDx.setValue(pos.x - shapeSet.getOriginX());
		propertyDy.setValue(pos.y - shapeSet.getOriginY());
		propertyDz.setValue(pos.z - shapeSet.getOriginZ());
		update();
	}, new Translatable("property.buildguide.setendpoint"));
	
	public ShapeLine() {
		super();
		
		properties.add(propertyDx);
		properties.add(propertyDy);
		properties.add(propertyDz);
		properties.add(propertySetEndpoint);
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
}
