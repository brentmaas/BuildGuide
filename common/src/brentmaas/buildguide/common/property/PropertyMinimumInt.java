package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.ITextField;

public class PropertyMinimumInt extends Property<Integer> {
	private ITextField valueTextField;
	private int minValue;
	
	public PropertyMinimumInt(int value, String name, Runnable onPress, int minValue) {
		super(value, name);
		this.minValue = minValue;
		buttonList.add(BuildGuide.widgetHandler.createButton(x + 90, y, 20, height, "-", () -> {
			if(this.value > this.minValue) {
				--this.value;
				valueTextField.setTextValue("" + this.value);
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run(); 
			}
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(x + 190, y, 20, height, "+", () -> {
			++this.value;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(x + 160, y, 30, height, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
			try {
				int newVal = Integer.parseInt(valueTextField.getTextValue());
				if(newVal >= this.minValue) {
					this.value = newVal;
					valueTextField.setTextColour(0xFFFFFF);
					if(onPress != null) onPress.run();
				}else {
					valueTextField.setTextColour(0xFF0000);
				}
			}catch(NumberFormatException e) {
				valueTextField.setTextColour(0xFF0000);
			}
		}));
		valueTextField = BuildGuide.widgetHandler.createTextField(x + 110, y, 50, height, "");
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		textFieldList.add(valueTextField);
	}
	
	public void setValue(Integer value) {
		if(value >= minValue) {
			super.setValue(value);
			valueTextField.setTextValue("" + value);
			valueTextField.setTextColour(0xFFFFFF);
		}
	}
}
