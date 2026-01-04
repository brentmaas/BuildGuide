package brentmaas.buildguide.neoforge.shape;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.joml.Matrix4f;

import com.mojang.blaze3d.buffers.BufferType;
import com.mojang.blaze3d.buffers.BufferUsage;
import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.MeshData;
import com.mojang.blaze3d.vertex.VertexFormat;

import brentmaas.buildguide.common.shape.IShapeBuffer;
import brentmaas.buildguide.neoforge.RenderHandler;
import net.minecraft.client.Minecraft;

public class ShapeBuffer implements IShapeBuffer {
	private ByteBufferBuilder byteBufferBuilder;
	private BufferBuilder bufferBuilder;
	private GpuBuffer vertexBuffer, indexBuffer;
	private int indexCount;
	private int defaultR = 255, defaultG  = 255, defaultB = 255, defaultA = 255;
	
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
		MeshData meshData = bufferBuilder.build();
		vertexBuffer = RenderSystem.getDevice().createBuffer(() -> "Build Guide vertices", BufferType.VERTICES, BufferUsage.STATIC_WRITE, meshData.vertexBuffer());
		indexCount = meshData.drawState().indexCount();
		indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(indexCount);
	}
	
	public void close() {
		if(vertexBuffer != null) vertexBuffer.close();
		// Don't also close indexBuffer, it is a reference to a global buffer used everywhere
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		RenderTarget renderTarget = Minecraft.getInstance().getMainRenderTarget();
		GpuTexture colourTexture = renderTarget.getColorTexture();
		GpuTexture depthTexture = renderTarget.getDepthTexture();

		RenderSystem.backupProjectionMatrix();
		RenderSystem.setProjectionMatrix(new Matrix4f(projection).mul(model), RenderSystem.getProjectionType());
		try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(colourTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderHandler.getRenderPipeline());
			if(indexBuffer.isClosed()) {
				indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(indexCount);
			}
			renderPass.setIndexBuffer(indexBuffer, RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).type());
			renderPass.setVertexBuffer(0, vertexBuffer);
			renderPass.drawIndexed(0, indexCount);
		}
		RenderSystem.restoreProjectionMatrix();
	}
}
