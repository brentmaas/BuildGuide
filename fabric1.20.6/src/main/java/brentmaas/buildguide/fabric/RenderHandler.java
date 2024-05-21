package brentmaas.buildguide.fabric;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL32;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.AbstractRenderHandler;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.fabric.shape.ShapeBuffer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class RenderHandler extends AbstractRenderHandler {
	private Camera cameraInstance;
	private MatrixStack poseStackInstance;
	private Matrix4f projectionMatrixInstance;
	
	public void register() {
		WorldRenderEvents.LAST.register(this::onRenderBlock);
	}
	
	public void onRenderBlock(WorldRenderContext context) {
		poseStackInstance = context.matrixStack();
		poseStackInstance.push();
		Matrix4f rotationMatrix = new Matrix4f();
		cameraInstance = MinecraftClient.getInstance().gameRenderer.getCamera();
		rotationMatrix.rotate((float) (cameraInstance.getPitch() * Math.PI / 180), new Vector3f(1, 0, 0));
		rotationMatrix.rotate((float) ((cameraInstance.getYaw() - 180) * Math.PI / 180), new Vector3f(0, 1, 0));
		poseStackInstance.multiplyPositionMatrix(rotationMatrix);
		projectionMatrixInstance = context.projectionMatrix();
		
		render();
		
		poseStackInstance.pop();
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(poseStackInstance.peek().getPositionMatrix(), projectionMatrixInstance);
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		poseStackInstance.push();
		Vec3d projectedView = cameraInstance.getPos();
		poseStackInstance.translate(-projectedView.x + shapeSet.origin.x, -projectedView.y + shapeSet.origin.y, -projectedView.z + shapeSet.origin.z);
	}
	
	protected void endRenderingShape() {
		poseStackInstance.pop();
	}
	
	protected boolean isCompatibilityProfile() {
		return GL32.glGetInteger(GL32.GL_CONTEXT_PROFILE_MASK) == GL32.GL_CONTEXT_COMPATIBILITY_PROFILE_BIT;
	}
	
	protected boolean textureEnabled() {
		return GL32.glIsEnabled(GL32.GL_TEXTURE_2D);
	}
	
	protected boolean depthTestEnabled() {
		return GL32.glIsEnabled(GL32.GL_DEPTH_TEST);
	}
	
	protected boolean depthMaskEnabled() {
		return GL32.glGetBoolean(GL32.GL_DEPTH_WRITEMASK);
	}
	
	protected boolean blendEnabled() {
		return GL32.glIsEnabled(GL32.GL_BLEND);
	}
	
	protected void setTexture(boolean enabled) {
		//Appears to not be needed/available in 1.19.4
	}
	
	protected void setDepthTest(boolean enabled) {
		if(enabled) RenderSystem.enableDepthTest();
		else RenderSystem.disableDepthTest();
	}
	
	protected void setDepthMask(boolean enabled) {
		RenderSystem.depthMask(enabled);
	}
	
	protected void setBlend(boolean enabled) {
		if(enabled) RenderSystem.enableBlend();
		else RenderSystem.disableBlend();
	}
	
	protected void setupNotCulling() {
		RenderSystem.polygonMode(GL32.GL_FRONT_AND_BACK, GL32.GL_FILL);
	}
	
	protected void setupBlendFunc() {
		RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
	}
	
	protected void pushProfiler(String key) {
		MinecraftClient.getInstance().getProfiler().push(key);
	}
	
	protected void popProfiler() {
		MinecraftClient.getInstance().getProfiler().pop();
	}
}
