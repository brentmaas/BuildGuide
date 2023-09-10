package brentmaas.buildguide.fabric.shape;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import brentmaas.buildguide.common.shape.IShapeHandler;
import brentmaas.buildguide.common.shape.ShapeSet.Origin;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class ShapeHandler implements IShapeHandler {
	public IShapeBuffer newBuffer() {
		return new ShapeBuffer();
	}
	
	public Origin getPlayerPosition() {
		Vec3d pos = MinecraftClient.getInstance().player.getPos();
		return new Origin((int) Math.floor(pos.x), (int) Math.floor(pos.y), (int) Math.floor(pos.z));
	}
}
