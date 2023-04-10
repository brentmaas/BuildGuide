package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;

public class PropertyBoolean extends Property<Boolean> {
	private ICheckboxRunnableButton button;
	
	public PropertyBoolean(Boolean value, String name, Runnable onPress) {
		super(value, name);
		button = BuildGuide.widgetHandler.createCheckbox(x + 140, y, 20, height, "", value, false, () -> {
			this.value = button.isCheckboxSelected();
			if(onPress != null) onPress.run();
		});
		checkboxList.add(button);
	}
	
	public void setValue(Boolean value) {
		super.setValue(value);
		button.setChecked(value);
	}
}
