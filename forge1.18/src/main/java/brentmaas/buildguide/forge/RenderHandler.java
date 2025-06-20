package brentmaas.buildguide.forge;

import org.lwjgl.opengl.GL32;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import brentmaas.buildguide.common.AbstractLegacyRenderHandler;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.forge.shape.ShapeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler extends AbstractLegacyRenderHandler {
	private PoseStack poseStackInstance;
	private Matrix4f projectionMatrixInstance;
	
	public void register() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onRenderBlock(RenderLevelLastEvent event) {
		poseStackInstance = event.getPoseStack();
		projectionMatrixInstance = event.getProjectionMatrix();
		
		render();
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(poseStackInstance.last().pose(), projectionMatrixInstance);
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		
		poseStackInstance.pushPose();
		Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		poseStackInstance.translate(-projectedView.x + shapeSet.origin.x, -projectedView.y + shapeSet.origin.y, -projectedView.z + shapeSet.origin.z);
	}
	
	protected void endRenderingShapeSet() {
		poseStackInstance.popPose();
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
		if(enabled) RenderSystem.enableTexture();
		else RenderSystem.disableTexture();
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
		RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
	}
	
	protected void pushProfiler(String key) {
		Minecraft.getInstance().getProfiler().push(key);
	}
	
	protected void popProfiler() {
		Minecraft.getInstance().getProfiler().pop();
	}
}
