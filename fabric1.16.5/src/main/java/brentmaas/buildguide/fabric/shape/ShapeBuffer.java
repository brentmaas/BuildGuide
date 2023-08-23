package brentmaas.buildguide.fabric.shape;

import org.lwjgl.opengl.GL32;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.math.Matrix4f;

public class ShapeBuffer implements IShapeBuffer {
	private BufferBuilder builder;
	private VertexBuffer buffer;
	
	public ShapeBuffer() {
		builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(GL32.GL_QUADS, VertexFormats.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		builder.fixedColor(r, g, b, a);
	}
	
	public void pushVertex(double x, double y, double z) {
		builder.vertex(x, y, z).next();
	}
	
	public void end() {
		builder.end();
		buffer = new VertexBuffer(VertexFormats.POSITION_COLOR);
		buffer.upload(builder);
	}
	
	public void close() {
		if(buffer != null) buffer.close();
	}
	
	public void render(Matrix4f model) {
		this.buffer.bind();
		VertexFormats.POSITION_COLOR.startDrawing(0);
		this.buffer.draw(model, GL32.GL_QUADS);
		VertexBuffer.unbind();
		VertexFormats.POSITION_COLOR.endDrawing();
	}
}
