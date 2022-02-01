package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.ITextField;

public class PropertyMinimumInt extends Property<Integer> {
	private ITextField valueTextField;
	private int minInt;
	
	public PropertyMinimumInt(int slot, int value, String name, Runnable onPress, int minInt) {
		super(slot, value, name);
		this.minInt = minInt;
		buttonList.add(BuildGuide.widgetHandler.createButton(90, y, 20, 20, "-", () -> {
			if(this.value > this.minInt) --this.value; 
			valueTextField.setText("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(190, y, 20, 20, "+", () -> {
			++this.value;
			valueTextField.setText("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(160, y, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
			try {
				int newval = Integer.parseInt(valueTextField.getText());
				this.value = newval;
				if(this.value < this.minInt) {
					this.value = this.minInt;
					valueTextField.setText("" + this.value);
				}
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run();
			}catch(NumberFormatException e) {
				valueTextField.setTextColour(0xFF0000);
			}
		}));
		valueTextField = BuildGuide.widgetHandler.createTextField(110, y, 50, 20, "");
		valueTextField.setText("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		textFieldList.add(valueTextField);
	}
	
	public void setValue(Integer value) {
		super.setValue(value);
		valueTextField.setText("" + value);
		valueTextField.setTextColour(0xFFFFFF);
	}
}
