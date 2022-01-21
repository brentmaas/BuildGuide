package brentmaas.buildguide.forge.property;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PropertyInt extends Property<Integer>{
	private TextFieldWidget valueWidget;
	
	public PropertyInt(int slot, int value, TextComponent name, Runnable onUpdate) {
		super(slot, value, name, onUpdate);
		buttonList.add(new Button(90, y, 20, 20, new StringTextComponent("-"), button -> {
			--this.value;
			valueWidget.setValue("" + this.value);
			valueWidget.setTextColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(190, y, 20, 20, new StringTextComponent("+"), button -> {
			++this.value;
			valueWidget.setValue("" + this.value);
			valueWidget.setTextColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(160, y, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			try {
				int newval = Integer.parseInt(valueWidget.getValue());
				this.value = newval;
				valueWidget.setTextColor(0xFFFFFF);
				if(onUpdate != null) onUpdate.run();
			}catch(NumberFormatException e) {
				valueWidget.setTextColor(0xFF0000);
			}
		}));
	}
	
	public void setValue(Integer value) {
		super.setValue(value);
		valueWidget.setValue("" + value);
		valueWidget.setTextColor(0xFFFFFF);
	}
	
	public void addTextFields(FontRenderer fr) {
		valueWidget = new TextFieldWidget(fr, 110, y, 50, 20, new StringTextComponent(""));
		valueWidget.setValue("" + value);
		valueWidget.setTextColor(0xFFFFFF);
		textFieldList.add(valueWidget);
	}
}
