package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.ITextField;

public class PropertyNonzeroInt extends Property<Integer> {
	private ITextField valueTextField;
	
	public PropertyNonzeroInt(int value, String name, Runnable onPress) {
		super(value, name);
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, 20, height, "-", () -> {
			--this.value;
			if(this.value == 0) this.value = -1;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
		valueTextField = BuildGuide.widgetHandler.createTextField(x + 110, y, 50, height, "");
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		widgetList.add(valueTextField);
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 160, y, 30, height, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
			try {
				int newval = Integer.parseInt(valueTextField.getTextValue());
				this.value = newval;
				if(this.value == 0) {
					this.value = 1;
					valueTextField.setTextValue("" + this.value);
				}
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run();
			}catch(NumberFormatException e) {
				valueTextField.setTextColour(0xFF0000);
			}
		}));
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, 20, height, "+", () -> {
			++this.value;
			if(this.value == 0) this.value = 1;
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
}
