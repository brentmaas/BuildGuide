package brentmaas.buildguide.input;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.screen.VisualisationScreen;
import brentmaas.buildguide.screen.ShapelistScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputHandler {
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(Keybindings.openBuildGuide.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen());
		}
		
		if(Keybindings.openShapeList.isPressed() && StateManager.getState().propertyAdvancedMode.value) {
			Minecraft.getInstance().displayGuiScreen(new ShapelistScreen());
		}
		
		if(Keybindings.openVisualisation.isPressed()) {
			Minecraft.getInstance().displayGuiScreen(new VisualisationScreen());
		}
	}
}
