package brentmaas.buildguide.fabric.screen;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ScreenHandler extends AbstractScreenHandler {
	public void showNone() {
		Minecraft.getInstance().setScreen(null);
	}
	
	public IScreenWrapper createWrapper(Translatable title) {
		return new ScreenWrapper(Component.translatable(title.getTranslationKey(), title.getValues()));
	}
	
	public String translate(String translationKey) {
		return Component.translatable(translationKey).getString();
	}
	
	public String translate(String translationKey, Object... values) {
		return Component.translatable(translationKey, values).getString();
	}
}
