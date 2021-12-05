package brentmaas.buildguide.input;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.screen.ShapelistScreen;
import brentmaas.buildguide.screen.VisualisationScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputHandler {
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		if(Keybindings.openBuildGuide.isDown()) {
			Minecraft.getInstance().setScreen(new BuildGuideScreen());
		}
		
		if(Keybindings.openShapeList.isDown() && StateManager.getState().propertyAdvancedMode.value) {
			Minecraft.getInstance().setScreen(new ShapelistScreen());
		}
		
		if(Keybindings.openVisualisation.isDown()) {
			Minecraft.getInstance().setScreen(new VisualisationScreen());
		}
	}
}
