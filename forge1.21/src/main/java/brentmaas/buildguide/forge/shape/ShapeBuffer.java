package brentmaas.buildguide.forge.shape;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import net.minecraft.client.renderer.GameRenderer;

public class ShapeBuffer implements IShapeBuffer{
	private ByteBufferBuilder byteBufferBuilder;
	private BufferBuilder bufferBuilder;
	private VertexBuffer buffer;
	private int defaultR = 255, defaultG = 255, defaultB = 255, defaultA = 255;
	
	public ShapeBuffer() {
		byteBufferBuilder = new ByteBufferBuilder(28); //28 is lowest working (4 bytes * XYZ+RGBA). Number of blocks isn't always known, so it'll have to grow on its own
		bufferBuilder = new BufferBuilder(byteBufferBuilder, VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		defaultR = r;
		defaultG = g;
		defaultB = b;
		defaultA = a;
	}
	
	public void pushVertex(double x, double y, double z) {
		bufferBuilder.addVertex((float) x, (float) y, (float) z).setColor(defaultR, defaultG, defaultB, defaultA);
	}
	
	public void end() {
		buffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		buffer.bind();
		buffer.upload(bufferBuilder.build());
		VertexBuffer.unbind();
	}
	
	public void close() {
		if(buffer != null) buffer.close();
	}
	
	public void render() {
		buffer.bind();
		buffer.drawWithShader(RenderSystem.getModelViewStack(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionColorShader());
		VertexBuffer.unbind();
	}
}
