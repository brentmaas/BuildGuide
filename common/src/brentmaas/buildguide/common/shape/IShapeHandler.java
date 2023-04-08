package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.shape.Shape.Origin;

public interface IShapeHandler {
	
	
	public IShapeBuffer newBuffer();
	
	public Origin getPlayerPosition();
}
