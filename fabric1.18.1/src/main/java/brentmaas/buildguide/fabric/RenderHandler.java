package brentmaas.buildguide.fabric;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.fabric.shapes.Shape;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class RenderHandler {
	
	
	public static void register() {
		WorldRenderEvents.LAST.register(RenderHandler::onRenderBlock);
	}
	
	public static void onRenderBlock(WorldRenderContext context) {
		MinecraftClient.getInstance().getProfiler().push("buildguide");
		
		if(StateManager.getState().propertyEnable.value && StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos != null) {
			MatrixStack stack = context.matrixStack();
			Matrix4f projectionMatrix = context.projectionMatrix();
			if(StateManager.getState().propertyAdvancedMode.value) {
				for(Shape shape: StateManager.getState().advancedModeShapes) renderShape(stack, projectionMatrix, shape);
			}else {
				renderShape(stack, projectionMatrix, StateManager.getState().getCurrentShape());
			}
		}
	}
	
	private static void renderShape(MatrixStack stack, Matrix4f projectionMatrix, Shape s) {
		if(s.visible) {
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			
			stack.push();
			Vec3d projectedView = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
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
			RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE_MINUS_SRC_ALPHA);
			if(toggleBlend) RenderSystem.enableBlend();
			
			s.render(stack.peek().getPositionMatrix(), projectionMatrix);
			
			if(toggleBlend) RenderSystem.disableBlend();
			if(toggleDepthTest && hasDepthTest) RenderSystem.enableDepthTest();
			else if(toggleDepthTest) RenderSystem.disableDepthTest();
			if(toggleDepthMask) RenderSystem.depthMask(true);
			if(toggleTexture) RenderSystem.enableTexture();
			
			stack.pop();
		}
	}
}
