package brentmaas.buildguide;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

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
		
		if(BuildGuide.state.basePos != null) {
			//https://www.programcreek.com/java-api-examples/?code=MichaelHillcox%2FXRay-Mod%2FXRay-Mod-master%2Fsrc%2Fmain%2Fjava%2Fcom%2Fxray%2Fxray%2FRender.java
			MatrixStack stack = event.getMatrixStack();
			stack.push();
			Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
			stack.translate(-projectedView.x + BuildGuide.state.basePos.x, -projectedView.y + BuildGuide.state.basePos.y, -projectedView.z + BuildGuide.state.basePos.z);
			
			RenderSystem.pushMatrix();
			RenderSystem.multMatrix(stack.getLast().getMatrix());
			
			RenderSystem.disableTexture();
			if(!BuildGuide.state.propertyDepthTest.value) RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.polygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
			RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.enableBlend();
			
			State.getCurrentShape().render(stack.getLast().getMatrix());
			
			RenderSystem.disableBlend();
			if(!BuildGuide.state.propertyDepthTest.value) RenderSystem.enableDepthTest();
			RenderSystem.depthMask(true);
			RenderSystem.enableTexture();
			
			RenderSystem.popMatrix();
			
			stack.pop();
		}
		
		Minecraft.getInstance().getProfiler().endSection();
	}
}
