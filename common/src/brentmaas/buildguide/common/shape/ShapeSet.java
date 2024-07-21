package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;

public class ShapeSet {
	public Shape[] shapes;
	private int index;
	
	public Origin origin;
	
	public boolean visible = true;
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourOriginR = 1.0f;
	public float colourOriginG = 0.0f;
	public float colourOriginB = 0.0f;
	public float colourOriginA = 0.5f;
	
	public ShapeSet(int startIndex) {
		shapes = new Shape[ShapeRegistry.getNumberOfShapes()];
		index = startIndex;
		shapes[index] = initialiseShape(ShapeRegistry.getClassIdentifier(index));
	}
	
	private Shape initialiseShape(String shapeId) {
		Shape newShape = ShapeRegistry.getNewInstance(shapeId);
		newShape.shapeSet = this;
		newShape.update();
		return newShape;
	}
	
	public void resetOrigin() {
		origin = BuildGuide.shapeHandler.getPlayerPosition();
	}
	
	public void updateShape() {
		if(shapes[index] != null) {
			shapes[index].update();
		}
	}
	
	public void updateAllShapes() {
		for(Shape s: shapes) {
			if(s != null) {
				s.update();
			}
		}
	}
	
	public void setOriginX(int x) {
		origin.x = x;
	}
	
	public void setOriginY(int y) {
		origin.y = y;
	}
	
	public void setOriginZ(int z) {
		origin.z = z;
	}
	
	public void setOrigin(int x, int y, int z) {
		origin.x = x;
		origin.y = y;
		origin.z = z;
	}
	
	public void shiftOrigin(int dx, int dy, int dz) {
		origin.x += dx;
		origin.y += dy;
		origin.z += dz;
	}
	
	public boolean isShapeAvailable() {
		return isShapeAvailable(index);
	}
	
	public boolean isShapeAvailable(int index) {
		return shapes[index] != null;
	}
	
	public Shape getShape() {
		if(shapes[index] == null) {
			shapes[index] = initialiseShape(ShapeRegistry.getClassIdentifier(index));
		}
		
		return shapes[index];
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public static class Origin {
		public int x, y, z;
		
		public Origin(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
