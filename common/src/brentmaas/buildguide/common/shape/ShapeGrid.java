package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveInt;

public class ShapeGrid extends Shape {
	private PropertyPositiveInt propertyStepsX = new PropertyPositiveInt(3, "Steps X", () -> update());
	private PropertyPositiveInt propertyStepsY = new PropertyPositiveInt(1, "Steps Y", () -> update());
	private PropertyPositiveInt propertyStepsZ = new PropertyPositiveInt(3, "Steps Z", () -> update());
	private PropertyNonzeroInt propertyOffsetX = new PropertyNonzeroInt(3, "Offset X", () -> update());
	private PropertyNonzeroInt propertyOffsetY = new PropertyNonzeroInt(3, "Offset Y", () -> update());
	private PropertyNonzeroInt propertyOffsetZ = new PropertyNonzeroInt(3, "Offset Z", () -> update());
	
	public ShapeGrid() {
		super();
		
		properties.add(propertyStepsX);
		properties.add(propertyStepsY);
		properties.add(propertyStepsZ);
		properties.add(propertyOffsetX);
		properties.add(propertyOffsetY);
		properties.add(propertyOffsetZ);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int nx = propertyStepsX.value;
		int ny = propertyStepsY.value;
		int nz = propertyStepsZ.value;
		int dx = propertyOffsetX.value;
		int dy = propertyOffsetY.value;
		int dz = propertyOffsetZ.value;
		
		for(int x = 0;x < nx;++x) {
			for(int y = 0;y < ny;++y) {
				for(int z = 0;z < nz;++z) {
					addShapeCube(buffer, x * dx, y * dy, z * dz);
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.grid";
	}
}
