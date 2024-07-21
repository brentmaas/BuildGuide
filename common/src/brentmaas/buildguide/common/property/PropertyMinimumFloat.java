package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.ITextField;

public class PropertyMinimumFloat extends Property<Float> {
	private ITextField valueTextField;
	private float minValue;
	private boolean inclusive;
	
	public PropertyMinimumFloat(float value, String name, Runnable onPress, float minValue, boolean inclusive) {
		super(value, name);
		this.minValue = minValue;
		this.inclusive = inclusive;
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, 20, height, "-", () -> {
			if(this.value - 1 > this.minValue || (this.inclusive && this.value - 1 == this.minValue)) {
				--this.value;
				valueTextField.setTextValue("" + this.value);
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run();
			}
		}));
		valueTextField = BuildGuide.widgetHandler.createTextField(x + 110, y, 50, height, "");
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		widgetList.add(valueTextField);
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 160, y, 30, height, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
			try {
				float newVal = Float.parseFloat(valueTextField.getTextValue());
				if(newVal > this.minValue || (this.inclusive && newVal == this.minValue)) {
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
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, 20, height, "+", () -> {
			++this.value;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
	}
	
	public void setValue(Float value) {
		if(value > minValue || (inclusive && value == minValue)) {
			super.setValue(value);
			valueTextField.setTextValue("" + value);
			valueTextField.setTextColour(0xFFFFFF);
		}
	}
	
	public String getStringValue() {
		return value.toString();
	}
	
	public boolean setValueFromString(String value) {
		try {
			float parsedValue = Float.parseFloat(value);
			if(parsedValue > minValue) {
				setValue(parsedValue);
				return true;
			}
		}catch(NumberFormatException e) {}
		return false;
	}
}
