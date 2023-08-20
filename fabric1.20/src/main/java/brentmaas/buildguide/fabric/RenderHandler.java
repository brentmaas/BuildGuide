package brentmaas.buildguide.fabric;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class RenderHandler extends AbstractRenderHandler {
	private MatrixStack matrixStackInstance;
	private Matrix4f projectionMatrixInstance;
	
	public void register() {
		WorldRenderEvents.LAST.register(this::onRenderBlock);
	}
	
	public void onRenderBlock(WorldRenderContext context) {
		matrixStackInstance = context.matrixStack();
		projectionMatrixInstance = context.projectionMatrix();
		
		render();
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(matrixStackInstance.peek().getPositionMatrix(), projectionMatrixInstance);
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		matrixStackInstance.push();
		Vec3d projectedView = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
		matrixStackInstance.translate(-projectedView.x + shapeSet.origin.x, -projectedView.y + shapeSet.origin.y, -projectedView.z + shapeSet.origin.z);
	}
	
	protected void endRenderingShape() {
		matrixStackInstance.pop();
	}
	
	protected boolean isCompatibilityProfile() {
		return GL32.glGetInteger(GL32.GL_CONTEXT_PROFILE_MASK) == GL32.GL_CONTEXT_COMPATIBILITY_PROFILE_BIT;
	}
	
	protected boolean textureEnabled() {
		return GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
	}
	
	protected boolean depthTestEnabled() {
		return GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
	}
	
	protected boolean depthMaskEnabled() {
		return GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
	}
	
	protected boolean blendEnabled() {
		return GL11.glIsEnabled(GL11.GL_BLEND);
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
		RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
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
