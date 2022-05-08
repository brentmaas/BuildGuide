package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.shape.Shape.Basepos;

public interface IShapeHandler {
	
	
	public IShapeBuffer newBuffer();
	
	public Basepos getPlayerPosition();
}
