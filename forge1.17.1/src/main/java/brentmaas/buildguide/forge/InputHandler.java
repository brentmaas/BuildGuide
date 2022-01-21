package brentmaas.buildguide.forge;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import brentmaas.buildguide.forge.screen.BuildGuideScreen;
import brentmaas.buildguide.forge.screen.ShapelistScreen;
import brentmaas.buildguide.forge.screen.VisualisationScreen;
import brentmaas.buildguide.forge.shapes.Shape;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class InputHandler {
	private static final String keyCategory = "key.buildguide.category";
	
	private static KeyMapping openBuildGuide;
	private static KeyMapping openShapeList;
	private static KeyMapping openVisualisation;
	private static KeyMapping toggleEnable;
	private static KeyMapping setBasepos;
	private static KeyMapping setGlobalBasepos;
	
	public static void register() {
		openBuildGuide = new KeyMapping("key.buildguide.openbuildguide", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, keyCategory);
		openShapeList = new KeyMapping("key.buildguide.openshapelist", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
		openVisualisation = new KeyMapping("key.buildguide.openvisualisation", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
		toggleEnable = new KeyMapping("key.buildguide.toggleenable", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
		setBasepos = new KeyMapping("key.buildguide.setbasepos", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
		setGlobalBasepos = new KeyMapping("key.buildguide.setglobalbasepos", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, keyCategory);
		
		ClientRegistry.registerKeyBinding(openBuildGuide);
		ClientRegistry.registerKeyBinding(openShapeList);
		ClientRegistry.registerKeyBinding(openVisualisation);
		ClientRegistry.registerKeyBinding(toggleEnable);
		ClientRegistry.registerKeyBinding(setBasepos);
		ClientRegistry.registerKeyBinding(setGlobalBasepos);
		
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
		
		if(toggleEnable.isDown()) {
			StateManager.getState().initCheck();
			StateManager.getState().propertyEnable.setValue(!StateManager.getState().propertyEnable.value);
		}
		
		if(setBasepos.isDown() && StateManager.getState().isShapeAvailable()) {
			StateManager.getState().resetBasepos();
		}
		
		if(setGlobalBasepos.isDown() && StateManager.getState().propertyAdvancedMode.value && StateManager.getState().isShapeAvailable()) {
			Vec3 pos = Minecraft.getInstance().player.position();
			int deltaX = (int) (Math.floor(pos.x) - StateManager.getState().getCurrentShape().basePos.x);
			int deltaY = (int) (Math.floor(pos.y) - StateManager.getState().getCurrentShape().basePos.y);
			int deltaZ = (int) (Math.floor(pos.z) - StateManager.getState().getCurrentShape().basePos.z);
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(deltaX, deltaY, deltaZ);
			}
		}
	}
}
