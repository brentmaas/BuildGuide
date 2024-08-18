package brentmaas.buildguide.neoforge.screen.widget;

import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;

public class CheckboxRunnableButtonImpl implements ICheckboxRunnableButton {
	protected final ICheckboxRunnableButton.IPressable onPress;
	private int x, y;
	private String title;
	private boolean checked;
	public Checkbox checkbox;
	
	public CheckboxRunnableButtonImpl(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		this.x = x;
		this.y = y;
		this.title = title;
		this.checked = checked;
		this.onPress = onPress;
	}
	
	public void initCheckboxIfNull() {
		if(checkbox == null) {
			checkbox = Checkbox.builder(Component.literal(title), Minecraft.getInstance().font).pos(x, y).onValueChange((Checkbox cb, boolean selected) -> onPress()).selected(checked).build();
			//checkbox.setWidth(width);
			//checkbox.setHeight(height);
		}
	}
	
	public void onPress() {
		onPress.onPress();
	}
	
	public void setChecked(boolean checked) {
		initCheckboxIfNull();
		if(checkbox.selected() != checked) checkbox.onPress();
	}
	
	public boolean isCheckboxSelected() {
		initCheckboxIfNull();
		return checkbox.selected();
	}
	
	public void setActive(boolean active) {
		initCheckboxIfNull();
		checkbox.active = active;
	}
	
	public void setYPosition(int y) {
		initCheckboxIfNull();
		checkbox.setY(y);
	}
	
	public void setVisibility(boolean visible) {
		initCheckboxIfNull();
		checkbox.visible = visible;
	}
}
