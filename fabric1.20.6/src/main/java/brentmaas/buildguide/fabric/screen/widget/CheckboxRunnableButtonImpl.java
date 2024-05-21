package brentmaas.buildguide.fabric.screen.widget;

import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class CheckboxRunnableButtonImpl implements ICheckboxRunnableButton {
	protected final ICheckboxRunnableButton.IPressable onPress;
	private int x, y;
	private String title;
	private boolean checked;
	public CheckboxWidget checkbox;
	
	public CheckboxRunnableButtonImpl(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		this.x = x;
		this.y = y;
		this.title = title;
		this.checked = checked;
		this.onPress = onPress;
	}
	
	public void initCheckboxIfNull() {
		if(checkbox == null) {
			checkbox = CheckboxWidget.builder(Text.literal(title), MinecraftClient.getInstance().textRenderer).pos(x, y).callback((CheckboxWidget checkbox, boolean selected) -> onPress()).checked(checked).build();
			//checkbox.setWidth(width);
			//checkbox.setHeight(height);
		}
	}
	
	public void onPress() {
		onPress.onPress();
	}
	
	public void setChecked(boolean checked) {
		initCheckboxIfNull();
		if(checkbox.isChecked() != checked) onPress();
	}
	
	public boolean isCheckboxSelected() {
		initCheckboxIfNull();
		return checkbox.isChecked();
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
