package brentmaas.buildguide.forge.screen;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ScreenHandler extends AbstractScreenHandler {
	
	
	public void showNone() {
		Minecraft.getInstance().setScreen(null);
	}
	
	public IScreenWrapper createWrapper(String title) {
		return new ScreenWrapper(new StringTextComponent(title));
	}
	
	public String translate(String translationKey) {
		return new TranslationTextComponent(translationKey).getString();
	}
	
	public String translate(String translationKey, Object... values) {
		return new TranslationTextComponent(translationKey, values).getString();
	}
}
