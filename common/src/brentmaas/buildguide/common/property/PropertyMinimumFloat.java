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
		buttonList.add(BuildGuide.widgetHandler.createButton(90, y, 20, height, "-", () -> {
			if(this.value - 1 > this.minValue || (this.inclusive && this.value - 1 == this.minValue)) {
				--this.value;
				valueTextField.setTextValue("" + this.value);
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run();
			}
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(190, y, 20, height, "+", () -> {
			++this.value;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(160, y, 30, height, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
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
		valueTextField = BuildGuide.widgetHandler.createTextField(110, y, 50, height, "");
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		textFieldList.add(valueTextField);
	}
	
	public void setValue(float value) {
		if(value > minValue || (inclusive && value == minValue)) {
			super.setValue(value);
			valueTextField.setTextValue("" + value);
			valueTextField.setTextColour(0xFFFFFF);
		}
	}
}
