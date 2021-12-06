package brentmaas.buildguide;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.screen.ShapelistScreen;
import brentmaas.buildguide.screen.VisualisationScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class InputHandler {
	public static KeyMapping openBuildGuide;
	public static KeyMapping openShapeList;
	public static KeyMapping openVisualisation;
	
	public static void register() {
		openBuildGuide = new KeyMapping("key.buildguide.openbuildguide", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.buildguide.category");
		openShapeList = new KeyMapping("key.buildguide.openshapelist", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.buildguide.category");
		openVisualisation = new KeyMapping("key.buildguide.openvisualisation", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.buildguide.category");
		
		ClientRegistry.registerKeyBinding(openBuildGuide);
		ClientRegistry.registerKeyBinding(openShapeList);
		ClientRegistry.registerKeyBinding(openVisualisation);
		
		MinecraftForge.EVENT_BUS.register(new InputHandler());
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(openBuildGuide.isDown()) {
			Minecraft.getInstance().setScreen(new BuildGuideScreen());
		}
		
		if(openShapeList.isDown() && StateManager.getState().propertyAdvancedMode.value) {
			Minecraft.getInstance().setScreen(new ShapelistScreen());
		}
		
		if(openVisualisation.isDown()) {
			Minecraft.getInstance().setScreen(new VisualisationScreen());
		}
	}
}
