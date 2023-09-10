package brentmaas.buildguide.forge.screen.widget;

import brentmaas.buildguide.common.screen.widget.ITextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.util.text.StringTextComponent;

public class TextFieldImpl extends TextFieldWidget implements ITextField {
	public TextFieldImpl(int x, int y, int width, int height, String value) {
		super(Minecraft.getInstance().font, x, y, width, height, new StringTextComponent(value));
	}
	
	public void setTextValue(String text) {
		setValue(text);
	}
	
	public void setTextColour(int colour) {
		setTextColor(colour);
	}
	
	public void setVisibility(boolean visible) {
		setVisible(visible);
	}
	
	public void setYPosition(int y) {
		this.y = y;
	}
	
	public String getTextValue() {
		return getValue();
	}
}
