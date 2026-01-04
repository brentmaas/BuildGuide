package brentmaas.buildguide.forge.screen.widget;

import brentmaas.buildguide.common.screen.widget.ITextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

public class TextFieldImpl extends EditBox implements ITextField {
	public TextFieldImpl(int x, int y, int width, int height, String value) {
		super(Minecraft.getInstance().font, x, y, width, height, Component.literal(value));
	}
	
	public void setTextValue(String text) {
		setValue(text);
	}
	
	public void setTextColour(int colour) {
		setTextColor(ARGB.color((colour >> 16) & 0xFF, (colour >> 8) & 0xFF, colour & 0xFF));
	}
	
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}
	
	public void setYPosition(int y) {
		super.setY(y);
	}
	
	public String getTextValue() {
		return getValue();
	}
}