package brentmaas.buildguide.forge.shape;

import brentmaas.buildguide.common.shapes.IShapeBuffer;
import brentmaas.buildguide.common.shapes.IShapeHandler;
import brentmaas.buildguide.common.shapes.Shape.Basepos;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;

public class ShapeHandler implements IShapeHandler {
	
	
	public IShapeBuffer newBuffer() {
		return new ShapeBuffer();
	}
	
	public Basepos getPlayerPosition() {
		Vector3d pos = Minecraft.getInstance().player.position();
		return new Basepos((int) Math.floor(pos.x), (int) Math.floor(pos.y), (int) Math.floor(pos.z));
	}
}
