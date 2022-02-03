package brentmaas.buildguide.fabric.shape;

import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.shapes.IShapeBuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.math.Matrix4f;

public class ShapeBuffer implements IShapeBuffer {
	private BufferBuilder builder;
	private VertexBuffer buffer;
	
	public ShapeBuffer() {
		builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		builder.fixedColor(r, g, b, a);
	}
	
	public void pushVertex(double x, double y, double z) {
		builder.vertex(x, y, z).next();
	}
	
	public void end() {
		builder.end();
		buffer = new VertexBuffer();
		buffer.upload(builder);
	}
	
	public void close() {
		buffer.close();
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		buffer.setShader(model, projection, RenderSystem.getShader());
	}
}
