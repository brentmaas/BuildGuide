package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;

public class PropertyBoolean extends Property<Boolean> {
	private ICheckboxRunnableButton button;
	
	public PropertyBoolean(Boolean value, Translatable name, Runnable onPress) {
		super(value, name);
		button = BuildGuide.widgetHandler.createCheckbox(x + 140, y, 20, height, new Translatable(""), value, false, () -> {
			this.value = button.isCheckboxSelected();
			if(onPress != null) onPress.run();
		});
		widgetList.add(button);
	}
	
	public void setValue(Boolean value) {
		super.setValue(value);
		button.setChecked(value);
	}
	
	public String getStringValue() {
		return value.toString();
	}
	
	public boolean setValueFromString(String value) {
		// No function for checking if a String is a valid boolean is provided by Java, this will have to do
		if(!(value.equalsIgnoreCase(Boolean.TRUE.toString()) || value.equalsIgnoreCase(Boolean.FALSE.toString()))) {
			return false;
		}
		setValue(Boolean.parseBoolean(value));
		return true;
	}
}
