package brentmaas.buildguide.input;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class Keybindings {
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
}
