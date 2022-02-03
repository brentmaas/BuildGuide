package brentmaas.buildguide.forge;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.AbstractRenderHandler;
import brentmaas.buildguide.common.shapes.Shape;
import brentmaas.buildguide.forge.shape.ShapeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler extends AbstractRenderHandler {
	private MatrixStack matrixStackInstance;
	
	public void register() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onRenderBlock(RenderWorldLastEvent event) {
		matrixStackInstance = event.getMatrixStack();
		
		render();
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(matrixStackInstance.last().pose());
	}
	
	protected void setupRenderingShape(Shape shape) {
		matrixStackInstance.pushPose();
		Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		matrixStackInstance.translate(-projectedView.x + shape.basepos.x, -projectedView.y + shape.basepos.y, -projectedView.z + shape.basepos.z);
		
		//TODO Shader?
		RenderSystem.pushMatrix();
		RenderSystem.multMatrix(matrixStackInstance.last().pose());
	}
	
	protected void endRenderingShape() {
		RenderSystem.popMatrix();
		
		matrixStackInstance.popPose();
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
		RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
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
