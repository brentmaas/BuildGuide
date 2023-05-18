package brentmaas.buildguide.common.shape;

import java.util.ArrayList;

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
		ArrayList<String> classIdentifiers = ShapeRegistry.getClassIdentifiers();
		shapes = new Shape[classIdentifiers.size()];
		index = startIndex;
		shapes[index] = initialiseShape(classIdentifiers.get(index));
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
			shapes[index] = initialiseShape(ShapeRegistry.getClassIdentifiers().get(index));
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
