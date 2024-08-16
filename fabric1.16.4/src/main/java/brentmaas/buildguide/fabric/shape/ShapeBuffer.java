package brentmaas.buildguide.fabric.shape;

import org.lwjgl.opengl.GL32;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;

import brentmaas.buildguide.common.shape.IShapeBuffer;

public class ShapeBuffer implements IShapeBuffer {
	private BufferBuilder builder;
	private VertexBuffer buffer;
	
	public ShapeBuffer() {
		builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(GL32.GL_QUADS, DefaultVertexFormat.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		builder.defaultColor(r, g, b, a);
	}
	
	public void pushVertex(double x, double y, double z) {
		builder.vertex(x, y, z).endVertex();
	}
	
	public void end() {
		builder.end();
		buffer = new VertexBuffer(DefaultVertexFormat.POSITION_COLOR);
		buffer.upload(builder);
	}
	
	public void close() {
		if(buffer != null) buffer.close();
	}
	
	public void render(Matrix4f model) {
		this.buffer.bind();
		DefaultVertexFormat.POSITION_COLOR.setupBufferState(0);
		this.buffer.draw(model, GL32.GL_QUADS);
		VertexBuffer.unbind();
		DefaultVertexFormat.POSITION_COLOR.clearBufferState();
	}
}
