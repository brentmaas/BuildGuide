package brentmaas.buildguide.fabric.screen.widget;

import brentmaas.buildguide.common.screen.widget.IButton;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ButtonImpl extends ButtonWidget implements IButton {
	
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public ButtonImpl(int x, int y, int width, int height, String text, IButton.IPressable onPress) {
		super(x, y, width, height, Text.literal(text), button -> onPress.onPress());
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
