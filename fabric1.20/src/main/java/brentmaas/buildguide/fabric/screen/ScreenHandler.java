package brentmaas.buildguide.fabric.screen;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class ScreenHandler extends AbstractScreenHandler {
	public void showNone() {
		MinecraftClient.getInstance().setScreen(null);
	}
	
	public IScreenWrapper createWrapper(String title) {
		return new ScreenWrapper(Text.literal(title));
	}
	
	public String translate(String translationKey) {
		return Text.translatable(translationKey).getString();
	}
	
	public String translate(String translationKey, Object... values) {
		return Text.translatable(translationKey, values).getString();
	}
}
