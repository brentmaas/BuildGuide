package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IWidget;

public class PropertyBoolean extends Property<Boolean> {
	private ICheckboxRunnableButton button;
	private Runnable onPress;
	
	public PropertyBoolean(Boolean value, Translatable name, Runnable onPress) {
		super(value, name);
		this.onPress = onPress;
	}
	
	protected void initWidgets(ArrayList<IWidget> widgetList) {
		button = BuildGuide.widgetHandler.createCheckbox(x + 140, y, new Translatable(""), value, false, () -> {
			this.value = button.isCheckboxSelected();
			if(onPress != null) onPress.run();
		});
		widgetList.add(button);
	}
	
	public void setValue(Boolean value) {
		super.setValue(value);
		getWidgetList(); // Initialise `button` if still null
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
