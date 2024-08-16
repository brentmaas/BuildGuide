package brentmaas.buildguide.fabric.screen.widget;

import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.TextComponent;

public class CheckboxRunnableButtonImpl extends Checkbox implements ICheckboxRunnableButton {
	protected final ICheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButtonImpl(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		super(x, y, width, height, new TextComponent(title), checked, drawTitle);
		this.onPress = onPress;
	}
	
	@Override
	public void onPress() {
		super.onPress();
		onPress.onPress();
	}
	
	public void setChecked(boolean checked) {
		if(selected() != checked) onPress();
	}
	
	public boolean isCheckboxSelected() {
		return selected();
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public void setYPosition(int y) {
		this.y = y;
	}
	
	public void setVisibility(boolean visible) {
		this.visible = visible;
	}
}
