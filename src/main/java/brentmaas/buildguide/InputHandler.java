package brentmaas.buildguide;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputHandler {
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(Keybindings.openBuildGuide.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new BuildGuideGui());
		}
	}
}
