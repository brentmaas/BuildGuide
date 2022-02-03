package brentmaas.buildguide.forge.screen.widget;

import brentmaas.buildguide.common.screen.widget.ITextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;

public class TextFieldImpl extends EditBox implements ITextField {
	
	
	public TextFieldImpl(int x, int y, int width, int height, String value) {
		super(Minecraft.getInstance().font, x, y, width, height, new TextComponent(value));
	}
	
	public void setText(String text) {
		setValue(text);
	}
	
	public void setTextColour(int colour) {
		setTextColor(colour);
	}
	
	public String getText() {
		return getValue();
	}
}