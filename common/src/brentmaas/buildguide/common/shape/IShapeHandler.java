package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.shape.ShapeSet.Origin;

public interface IShapeHandler {
	public IShapeBuffer newBuffer();
	
	public Origin getPlayerPosition();
}
