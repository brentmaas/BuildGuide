package brentmaas.buildguide.fabric;

import org.lwjgl.glfw.GLFW;

import brentmaas.buildguide.fabric.screen.BuildGuideScreen;
import brentmaas.buildguide.fabric.screen.ShapelistScreen;
import brentmaas.buildguide.fabric.screen.VisualisationScreen;
import brentmaas.buildguide.fabric.shapes.Shape;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.Vec3d;

public class InputHandler {
	private static final String keyCategory = "key.buildguide.category";
	
	private static KeyBinding openBuildGuide;
	private static KeyBinding openShapelist;
	private static KeyBinding openVisualisation;
	private static KeyBinding toggleEnable;
	private static KeyBinding setBasepos;
	private static KeyBinding setGlobalBasepos;
	
	public static void register() {
		openBuildGuide = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openbuildguide", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, keyCategory));
		openShapelist = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openshapelist", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory));
		openVisualisation = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.openvisualisation", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory));
		toggleEnable = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.toggleenable", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory));
		setBasepos = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.setbasepos", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory));
		setGlobalBasepos = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.buildguide.setglobalbasepos", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory));
		
		ClientTickEvents.END_CLIENT_TICK.register(InputHandler::onKeyInput);
	}
	
	public static void onKeyInput(MinecraftClient client) {
		if(openBuildGuide.wasPressed()) {
			MinecraftClient.getInstance().openScreen(new BuildGuideScreen());
		}
		
		if(openShapelist.wasPressed() && StateManager.getState().propertyAdvancedMode.value) {
			MinecraftClient.getInstance().openScreen(new ShapelistScreen());
		}
		
		if(openVisualisation.wasPressed()) {
			MinecraftClient.getInstance().openScreen(new VisualisationScreen());
		}
		
		if(toggleEnable.wasPressed()) {
			StateManager.getState().initCheck();
			StateManager.getState().propertyEnable.setValue(!StateManager.getState().propertyEnable.value);
		}
		
		if(setBasepos.wasPressed() && StateManager.getState().isShapeAvailable()) {
			StateManager.getState().resetBasepos();
		}
		
		if(setGlobalBasepos.wasPressed() && StateManager.getState().propertyAdvancedMode.value && StateManager.getState().isShapeAvailable()) {
			Vec3d pos = MinecraftClient.getInstance().player.getPos();
			int deltaX = (int) (Math.floor(pos.x)- StateManager.getState().getCurrentShape().basePos.x);
			int deltaY = (int) (Math.floor(pos.y)- StateManager.getState().getCurrentShape().basePos.y);
			int deltaZ = (int) (Math.floor(pos.z)- StateManager.getState().getCurrentShape().basePos.z);
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(deltaX, deltaY, deltaZ);
			}
		}
	}
}
