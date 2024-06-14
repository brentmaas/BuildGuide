package brentmaas.buildguide.fabric.shape;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.BufferAllocator;

public class ShapeBuffer implements IShapeBuffer {
	private BufferAllocator byteBuilder;
	private BufferBuilder builder;
	private VertexBuffer buffer;
	private int defaultR = 255, defaultG = 255, defaultB = 255, defaultA = 255;
	
	public ShapeBuffer() {
		byteBuilder = new BufferAllocator(28); //28 is lowest working (4 bytes * XYZ+RGBA). Number of blocks isn't always known, so it'll have to grow on its own
		builder = new BufferBuilder(byteBuilder, DrawMode.QUADS, VertexFormats.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		defaultR = r;
		defaultG = g;
		defaultB = b;
		defaultA = a;
	}
	
	public void pushVertex(double x, double y, double z) {
		builder.vertex((float) x, (float) y, (float) z).color(defaultR, defaultG, defaultB, defaultA);
	}
	
	public void end() {
		buffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		buffer.bind();
		buffer.upload(builder.endNullable());
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
