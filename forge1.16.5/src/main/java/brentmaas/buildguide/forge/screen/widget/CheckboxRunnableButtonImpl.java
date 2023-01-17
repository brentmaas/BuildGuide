package brentmaas.buildguide.forge.screen.widget;

import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.StringTextComponent;

public class CheckboxRunnableButtonImpl extends CheckboxButton implements ICheckboxRunnableButton {
	protected final ICheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButtonImpl(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		super(x, y, width, height, new StringTextComponent(title), checked, drawTitle);
		this.onPress = onPress;
	}
	
	public void onPress() {
		super.onPress();
		onPress.onPress();
	}
	
	public void setChecked(boolean checked) {
		if(selected() != checked) onPress();
	}
	
	public boolean isSelected() {
		return selected();
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setYPosition(int y) {
		this.y = y;
	}
}
