package brentmaas.buildguide;

import org.lwjgl.glfw.GLFW;

import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.screen.VisualisationScreen;
import brentmaas.buildguide.screen.ShapelistScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InputHandler {
	public static KeyBinding openBuildGuide;
	public static KeyBinding openShapeList;
	public static KeyBinding openVisualisation;
	
	public static void register() {
		openBuildGuide = new KeyBinding("key.buildguide.openbuildguide", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.buildguide.category");
		openShapeList = new KeyBinding("key.buildguide.openshapelist", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.buildguide.category");
		openVisualisation = new KeyBinding("key.buildguide.openvisualisation", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key.buildguide.category");
		
		ClientRegistry.registerKeyBinding(openBuildGuide);
		ClientRegistry.registerKeyBinding(openShapeList);
		ClientRegistry.registerKeyBinding(openVisualisation);
		
		MinecraftForge.EVENT_BUS.register(new InputHandler());
	}
	
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(openBuildGuide.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen());
		}
		
		if(openShapeList.isPressed() && StateManager.getState().propertyAdvancedMode.value) {
			Minecraft.getInstance().displayGuiScreen(new ShapelistScreen());
		}
		
		if(openVisualisation.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new VisualisationScreen());
		}
	}
}
