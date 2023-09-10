package brentmaas.buildguide.forge.shape;

import org.lwjgl.opengl.GL32;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.math.vector.Matrix4f;

public class ShapeBuffer implements IShapeBuffer{
	private BufferBuilder builder;
	private VertexBuffer buffer;
	
	public ShapeBuffer() {
		builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(GL32.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		builder.defaultColor(r, g, b, a);
	}
	
	public void pushVertex(double x, double y, double z) {
		builder.vertex(x, y, z).endVertex();
	}
	
	public void end() {
		builder.end();
		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_COLOR);
		buffer.upload(builder);
	}
	
	public void close() {
		if(buffer != null) buffer.close();
	}
	
	public void render(Matrix4f model) {
		buffer.bind();
		DefaultVertexFormats.POSITION_COLOR.setupBufferState(0);
		buffer.draw(model, GL32.GL_QUADS);
		VertexBuffer.unbind();
		DefaultVertexFormats.POSITION_COLOR.clearBufferState();
	}
}
