package brentmaas.buildguide.common.shapes;

import brentmaas.buildguide.common.shapes.Shape.Basepos;

public interface IShapeHandler {
	
	
	public IShapeBuffer newBuffer();
	
	public Basepos getPlayerPosition();
}
