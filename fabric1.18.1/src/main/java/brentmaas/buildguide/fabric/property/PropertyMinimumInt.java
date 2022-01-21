package brentmaas.buildguide.property;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class PropertyMinimumInt extends Property<Integer>{
	private TextFieldWidget valueWidget;
	private int minInt;
	
	public PropertyMinimumInt(int slot, int value, Text name, Runnable onUpdate, int minInt) {
		super(slot, value, name, onUpdate);
		this.minInt = minInt;
		buttonList.add(new ButtonWidget(90, y, 20, 20, new LiteralText("-"), button -> {
			if(this.value > this.minInt) --this.value;
			valueWidget.setText("" + this.value);
			valueWidget.setEditableColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new ButtonWidget(190, y, 20, 20, new LiteralText("+"), button -> {
			++this.value;
			valueWidget.setText("" + this.value);
			valueWidget.setEditableColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new ButtonWidget(160, y, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
			try {
				int newval = Integer.parseInt(valueWidget.getText());
				this.value = newval;
				if(this.value < minInt) {
					this.value = minInt;
				}
				valueWidget.setText("" + this.value);
				valueWidget.setEditableColor(0xFFFFFF);
				if(onUpdate != null) onUpdate.run();
			}catch(NumberFormatException e) {
				valueWidget.setEditableColor(0xFF0000);
			}
		}));
	}
	
	public void setValue(Integer value) {
		super.setValue(value);
		valueWidget.setText("" + value);
		valueWidget.setEditableColor(0xFFFFFF);
	}
	
	public void addTextFields(TextRenderer fr) {
		valueWidget = new TextFieldWidget(fr, 110, y, 50, 20, new LiteralText(""));
		valueWidget.setText("" + value);
		valueWidget.setEditableColor(0xFFFFFF);
		textFieldList.add(valueWidget);
	}
}
