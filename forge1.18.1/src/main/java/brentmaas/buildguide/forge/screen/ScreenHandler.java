package brentmaas.buildguide.forge.screen;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.IScreenHandler;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class ScreenHandler implements IScreenHandler {
	
	
	public void showScreen(BaseScreen screen) {
		if(screen != null) wrapScreen(screen).show();
		else Minecraft.getInstance().setScreen(null);
	}
	
	public IScreenWrapper wrapScreen(BaseScreen screen) {
		IScreenWrapper wrapper = new ScreenWrapper(new TextComponent(screen.title));
		wrapper.attachScreen(screen);
		return wrapper;
	}
	
	public String translate(String string) {
		return new TranslatableComponent(string).getString();
	}
	
	public String translate(String translationKey, Object... values) {
		return new TranslatableComponent(translationKey, values).getString();
	}
}
