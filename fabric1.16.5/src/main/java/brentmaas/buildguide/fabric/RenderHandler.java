package brentmaas.buildguide;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.shapes.Shape;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;

public class RenderHandler {
	
	
	public static void register() {
		WorldRenderEvents.LAST.register(RenderHandler::onRenderBlock);
	}
	
	public static void onRenderBlock(WorldRenderContext context) {
		MinecraftClient.getInstance().getProfiler().push("buildguide");
		
		if(StateManager.getState().propertyEnable.value && StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos != null) {
			MatrixStack stack = context.matrixStack();
			if(StateManager.getState().propertyAdvancedMode.value) {
				for(Shape shape: StateManager.getState().advancedModeShapes) renderShape(stack, shape);
			}else {
				renderShape(stack, StateManager.getState().getCurrentShape());
			}
		}
		
		MinecraftClient.getInstance().getProfiler().pop();
	}
	
	private static void renderShape(MatrixStack stack, Shape s) {
		if(s.visible) {
			stack.push();
			Vec3d projectedView = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
			stack.translate(-projectedView.x + s.basePos.x, -projectedView.y + s.basePos.y, -projectedView.z + s.basePos.z);
			
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.peek().getModel());
			
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
			RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
			if(toggleBlend) RenderSystem.enableBlend();
			
			s.render(stack.peek().getModel());
			
			if(toggleBlend) RenderSystem.disableBlend();
			if(toggleDepthTest && hasDepthTest) RenderSystem.enableDepthTest();
			else if(toggleDepthTest) RenderSystem.disableDepthTest();
			if(toggleDepthMask) RenderSystem.depthMask(true);
			if(toggleTexture) RenderSystem.enableTexture();
			
			RenderSystem.popMatrix();
			
			stack.pop();
		}
	}
}
