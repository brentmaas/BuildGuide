package brentmaas.buildguide.forge.shape;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import brentmaas.buildguide.common.shape.IShapeHandler;
import brentmaas.buildguide.common.shape.Shape.Basepos;
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
