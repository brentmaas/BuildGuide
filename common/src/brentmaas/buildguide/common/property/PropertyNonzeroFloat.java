package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.screen.widget.IWidget;

public class PropertyNonzeroFloat extends Property<Float> {
	private ITextField valueTextField;
	private Runnable onPress;
	
	public PropertyNonzeroFloat(float value, Translatable name, Runnable onPress) {
		super(value, name);
		this.onPress = onPress;
	}
	
	protected void initWidgets(ArrayList<IWidget> widgetList) {
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, new Translatable("-"), () -> {
			--this.value;
			if(this.value == 0) this.value = -1.0f;
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
				float newval = Float.parseFloat(valueTextField.getTextValue());
				this.value = newval;
				if(this.value == 0) {
					this.value = 1.0f;
					valueTextField.setTextValue("" + this.value);
				}
				valueTextField.setTextColour(0xFFFFFF);
				if(onPress != null) onPress.run();
			}catch(NumberFormatException e) {
				valueTextField.setTextColour(0xFF0000);
			}
		}));
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, new Translatable("+"), () -> {
			++this.value;
			if(this.value == 0) this.value = 1.0f;
			valueTextField.setTextValue("" + this.value);
			valueTextField.setTextColour(0xFFFFFF);
			if(onPress != null) onPress.run();
		}));
	}
	
	public void setValue(Float value) {
		super.setValue(value);
		getWidgetList(); // Initialise `valueTextField` if still null
		valueTextField.setTextValue("" + value);
		valueTextField.setTextColour(0xFFFFFF);
	}
	
	public String getStringValue() {
		return value.toString();
	}
	
	public boolean setValueFromString(String value) {
		try {
			float parsedValue = Float.parseFloat(value);
			if(parsedValue != 0) {
				setValue(parsedValue);
				return true;
			}
		}catch(NumberFormatException e) {}
		return false;
	}
}
