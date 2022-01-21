package brentmaas.buildguide.forge;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;

import brentmaas.buildguide.forge.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
	
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(new RenderHandler());
	}
	
	@SubscribeEvent
	public void onRenderBlock(RenderWorldLastEvent event) {
		Minecraft.getInstance().getProfiler().push("buildguide");
		
		if(StateManager.getState().propertyEnable.value && StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos != null) {
			PoseStack stack = event.getMatrixStack();
			Matrix4f projectionMatrix = event.getProjectionMatrix();
			if(StateManager.getState().propertyAdvancedMode.value) {
				for(Shape shape: StateManager.getState().advancedModeShapes) renderShape(stack, projectionMatrix, shape);
			}else {
				renderShape(stack, projectionMatrix, StateManager.getState().getCurrentShape());
			}
		}
		
		Minecraft.getInstance().getProfiler().pop();
	}
	
	private void renderShape(PoseStack stack, Matrix4f projectionMatrix, Shape s) {
		if(s.visible) {
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			
			stack.pushPose();
			Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
			stack.translate(-projectedView.x + s.basePos.x, -projectedView.y + s.basePos.y, -projectedView.z + s.basePos.z);
			
			boolean toggleTexture = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
			
			boolean hasDepthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
			boolean toggleDepthTest = StateManager.getState().propertyDepthTest.value ^ hasDepthTest;
			
			boolean toggleDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
			
			boolean toggleBlend = !GL11.glIsEnabled(GL11.GL_BLEND);
			
			if(toggleTexture) RenderSystem.disableTexture();
			if(toggleDepthTest && hasDepthTest) RenderSystem.disableDepthTest();
			else if(toggleDepthTest) RenderSystem.enableDepthTest();
			if(toggleDepthMask) RenderSystem.depthMask(false);
			RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			if(toggleBlend) RenderSystem.enableBlend();
			
			s.render(stack.last().pose(), projectionMatrix);
			
			if(toggleBlend) RenderSystem.disableBlend();
			if(toggleDepthTest && hasDepthTest) RenderSystem.enableDepthTest();
			else if(toggleDepthTest) RenderSystem.disableDepthTest();
			if(toggleDepthMask) RenderSystem.depthMask(true);
			if(toggleTexture) RenderSystem.enableTexture();

			stack.popPose();
		}
	}
}
