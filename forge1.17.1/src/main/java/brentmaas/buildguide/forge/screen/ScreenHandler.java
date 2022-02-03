package brentmaas.buildguide.forge.screen;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ScreenHandler extends AbstractScreenHandler {
	
	
	public void showNone() {
		Minecraft.getInstance().setScreen(null);
	}
	
	public IScreenWrapper createWrapper(String title) {
		return new ScreenWrapper(new TextComponent(title));
	}
	
	public String translate(String translationKey) {
		return new TranslatableComponent(translationKey).getString();
	}
	
	public String translate(String translationKey, Object... values) {
		return new TranslatableComponent(translationKey, values).getString();
	}
}
