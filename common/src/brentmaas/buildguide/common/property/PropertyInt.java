package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.screen.widget.ITextField;

public class PropertyInt extends Property<Integer> {
	private ITextField valueTextField;
	
	public PropertyInt(int value, Translatable name, Runnable onPress) {
		super(value, name);
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, new Translatable("-"), () -> {
			--this.value;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
		valueTextField = BuildGuide.widgetHandler.createTextField(x + 110, y, 50, AbstractWidgetHandler.defaultSize, "");
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		widgetList.add(valueTextField);
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 160, y, 30, AbstractWidgetHandler.defaultSize, new Translatable("screen.buildguide.set"), () -> {
			try {
				int newval = Integer.parseInt(valueTextField.getTextValue());
				this.value = newval;
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run();
			}catch(NumberFormatException e) {
				valueTextField.setTextColour(0xFF0000);
			}
		}));
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, new Translatable("+"), () -> {
			++this.value;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
	}
	
	public void setValue(Integer value) {
		super.setValue(value);
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
	}
	
	public String getStringValue() {
		return value.toString();
	}
	
	public boolean setValueFromString(String value) {
		try {
			setValue(Integer.parseInt(value));
			return true;
		}catch(NumberFormatException e) {}
		return false;
	}
}
