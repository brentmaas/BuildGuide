package brentmaas.buildguide.fabric.screen.widget;

import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class CheckboxRunnableButtonImpl extends CheckboxWidget implements ICheckboxRunnableButton {
	protected ICheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButtonImpl(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		super(x, y, width, height, Text.literal(title), checked, drawTitle);
		this.onPress = onPress;
	}
	
	public void onPress() {
		super.onPress();
		onPress.onPress();
	}
	
	public void setChecked(boolean checked) {
		if(isCheckboxSelected() != checked) onPress();
	}
	
	public boolean isCheckboxSelected() {
		return isChecked();
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setYPosition(int y) {
		super.setY(y);
	}
}
