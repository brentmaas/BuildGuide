package brentmaas.buildguide.input;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybindings {
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
}
