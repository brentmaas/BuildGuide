package brentmaas.buildguide;

import org.lwjgl.glfw.GLFW;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybindings {
	public static KeyBinding openBuildGuide;
	
	public static void register() {
		//TODO Name doesn't show up properly
		openBuildGuide = new KeyBinding("key.buildguide.openbuildguide", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.buildguide.category");
		//openBuildGuide = new KeyBinding(new TranslationTextComponent("key.buildguide.openbuildguide").getString(), GLFW.GLFW_KEY_B, new TranslationTextComponent("key.buildguide.category").getString());
		
		ClientRegistry.registerKeyBinding(openBuildGuide);
		
		MinecraftForge.EVENT_BUS.register(new InputHandler());
	}
}
