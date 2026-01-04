package brentmaas.buildguide.neoforge.shape;

import java.util.OptionalDouble;
import java.util.OptionalInt;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTextureView;
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
		vertexBuffer = RenderSystem.getDevice().createBuffer(() -> "Build Guide vertices", GpuBuffer.USAGE_VERTEX, meshData.vertexBuffer());
		indexCount = meshData.drawState().indexCount();
		indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(indexCount);
	}
	
	public void close() {
		if(vertexBuffer != null) vertexBuffer.close();
		// Don't also close indexBuffer, it is a reference to a global buffer used everywhere
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		RenderTarget renderTarget = Minecraft.getInstance().getMainRenderTarget();
		GpuTextureView colourTexture = renderTarget.getColorTextureView();
		GpuTextureView depthTexture = renderTarget.getDepthTextureView();

		RenderSystem.backupProjectionMatrix();
		RenderSystem.setProjectionMatrix(RenderHandler.projectionMatrixBuffer.getBuffer(new Matrix4f(projection).mul(model)), RenderSystem.getProjectionType());
		GpuBufferSlice dynamicTransforms = RenderSystem.getDynamicUniforms().writeTransform(RenderSystem.getModelViewMatrix(), new Vector4f(1.0f), new Vector3f(), new Matrix4f(), 0.0f);
		try (RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> "Build Guide", colourTexture, OptionalInt.empty(), depthTexture, OptionalDouble.empty())) {
			renderPass.setPipeline(RenderHandler.getRenderPipeline());
			RenderSystem.bindDefaultUniforms(renderPass);
			renderPass.setUniform("DynamicTransforms", dynamicTransforms);
			if(indexBuffer.isClosed()) {
				indexBuffer = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).getBuffer(indexCount);
			}
			renderPass.setIndexBuffer(indexBuffer, RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS).type());
			renderPass.setVertexBuffer(0, vertexBuffer);
			renderPass.drawIndexed(0, 0, indexCount, 1);
		}
		RenderSystem.restoreProjectionMatrix();
	}
}
