package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;

public class PropertyBoolean extends Property<Boolean> {
	private ICheckboxRunnableButton button;
	
	public PropertyBoolean(int slot, Boolean value, String name, Runnable onPress) {
		super(slot, value, name);
		button = BuildGuide.widgetHandler.createCheckbox(140, y, 20, 20, "", value, false, () -> {
			this.value = button.isSelected();
			if(onPress != null) onPress.run();
		});
		checkboxList.add(button);
	}
	
	public void setValue(Boolean value) {
		super.setValue(value);
		button.setChecked(value);
	}
}
