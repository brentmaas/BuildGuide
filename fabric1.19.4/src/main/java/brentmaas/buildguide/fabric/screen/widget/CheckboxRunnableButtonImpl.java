package brentmaas.buildguide.fabric.screen.widget;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;

public class CheckboxRunnableButtonImpl extends Checkbox implements ICheckboxRunnableButton {
	protected final ICheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButtonImpl(int x, int y, int width, int height, Translatable title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		super(x, y, width, height, Component.translatable(title.getTranslationKey(), title.getValues()), checked, drawTitle);
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
		super.setY(y);
	}
	
	public void setVisibility(boolean visible) {
		this.visible = visible;
	}
}
