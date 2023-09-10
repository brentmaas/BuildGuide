package brentmaas.buildguide.fabric.shape;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;

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
		buffer = new VertexBuffer();
		buffer.bind();
		buffer.upload(builder.end());
		VertexBuffer.unbind();
	}
	
	public void close() {
		if(buffer != null) buffer.close();
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		buffer.bind();
		buffer.draw(model, RenderSystem.getProjectionMatrix(), GameRenderer.getPositionColorProgram());
		VertexBuffer.unbind();
	}
}
