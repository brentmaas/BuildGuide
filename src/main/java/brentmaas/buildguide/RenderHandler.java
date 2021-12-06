package brentmaas.buildguide;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class RenderHandler {
	
	
	public static void register() {
		MinecraftForge.EVENT_BUS.register(new RenderHandler());
	}
	
	@SubscribeEvent
	public void onRenderBlock(RenderWorldLastEvent event) {
		Minecraft.getInstance().getProfiler().startSection("buildguide");
		
		if(StateManager.getState().propertyEnable.value && StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos != null) {
			MatrixStack stack = event.getMatrixStack();
			if(StateManager.getState().propertyAdvancedMode.value) {
				for(Shape shape: StateManager.getState().advancedModeShapes) renderShape(stack, shape);
			} else {
				renderShape(stack, StateManager.getState().getCurrentShape());
			}
		}
		
		Minecraft.getInstance().getProfiler().endSection();
	}
	
	private void renderShape(MatrixStack stack, Shape s) {
		if(s.visible) {
			stack.push();
			Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
			stack.translate(-projectedView.x + s.basePos.x, -projectedView.y + s.basePos.y, -projectedView.z + s.basePos.z);
			
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.getLast().getMatrix());
			
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
			
			s.render(stack.getLast().getMatrix());
			
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
