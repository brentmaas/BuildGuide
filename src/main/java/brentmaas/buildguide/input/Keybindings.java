package brentmaas.buildguide.input;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class Keybindings {
	public static KeyMapping openBuildGuide;
	
	public static void register() {
		openBuildGuide = new KeyMapping("key.buildguide.openbuildguide", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.buildguide.category");
		
		ClientRegistry.registerKeyBinding(openBuildGuide);
		
		MinecraftForge.EVENT_BUS.register(new InputHandler());
	}
}
