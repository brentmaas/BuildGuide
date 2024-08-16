package brentmaas.buildguide.fabric.shape;

import org.joml.Matrix4f;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import net.minecraft.client.renderer.GameRenderer;

public class ShapeBuffer implements IShapeBuffer {
	private BufferBuilder builder;
	private VertexBuffer buffer;
	
	public ShapeBuffer() {
		builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
	}
	
	public void setColour(int r, int g, int b, int a) {
		builder.defaultColor(r, g, b, a);
	}
	
	public void pushVertex(double x, double y, double z) {
		builder.vertex(x, y, z).endVertex();
	}
	
	public void end() {
		buffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
		buffer.bind();
		buffer.upload(builder.end());
		VertexBuffer.unbind();
	}
	
	public void close() {
		if(buffer != null) buffer.close();
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		buffer.bind();
		buffer.drawWithShader(model, RenderSystem.getProjectionMatrix(), GameRenderer.getPositionColorShader());
		VertexBuffer.unbind();
	}
}
