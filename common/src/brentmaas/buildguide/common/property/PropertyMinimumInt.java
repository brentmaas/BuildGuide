package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.screen.widget.IWidget;

public class PropertyMinimumInt extends Property<Integer> {
	private ITextField valueTextField;
	private Runnable onPress;
	private int minValue;
	
	public PropertyMinimumInt(int value, Translatable name, Runnable onPress, int minValue) {
		super(value, name);
		this.onPress = onPress;
		this.minValue = minValue;
	}
	
	protected void initWidgets(ArrayList<IWidget> widgetList) {
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, new Translatable("-"), () -> {
			if(this.value > this.minValue) {
				--this.value;
				valueTextField.setTextValue("" + this.value);
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run(); 
			}
		}));
		valueTextField = BuildGuide.widgetHandler.createTextField(x + 110, y, 50, AbstractWidgetHandler.defaultSize, "");
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
		widgetList.add(valueTextField);
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 160, y, 30, AbstractWidgetHandler.defaultSize, new Translatable("screen.buildguide.set"), () -> {
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
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, new Translatable("+"), () -> {
			++this.value;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
	}
	
	public void setValue(Integer value) {
		if(value >= minValue) {
			super.setValue(value);
			getWidgetList(); // Initialise `valueTextField` if still null
			valueTextField.setTextValue("" + value);
			valueTextField.setTextColour(0xFFFFFF);
		}
	}
	
	public String getStringValue() {
		return value.toString();
	}
	
	public boolean setValueFromString(String value) {
		try {
			int parsedValue = Integer.parseInt(value);
			if(parsedValue > minValue) {
				setValue(parsedValue);
				return true;
			}
		}catch(NumberFormatException e) {}
		return false;
	}
}
