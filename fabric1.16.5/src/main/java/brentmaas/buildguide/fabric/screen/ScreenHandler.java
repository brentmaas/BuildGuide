package brentmaas.buildguide.fabric.screen;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class ScreenHandler extends AbstractScreenHandler {
	
	
	public void showNone() {
		MinecraftClient.getInstance().openScreen(null);
	}
	
	public IScreenWrapper createWrapper(String title) {
		return new ScreenWrapper(new LiteralText(title));
	}
	
	public String translate(String translationKey) {
		return new TranslatableText(translationKey).getString();
	}
	
	public String translate(String translationKey, Object... values) {
		return new TranslatableText(translationKey, values).getString();
	}
}
