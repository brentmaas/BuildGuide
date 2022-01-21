package brentmaas.buildguide.forge.property;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class PropertyNonzeroInt extends Property<Integer>{
	private EditBox valueWidget;
	
	public PropertyNonzeroInt(int slot, int value, BaseComponent name, Runnable onUpdate) {
		super(slot, value, name, onUpdate);
		buttonList.add(new Button(90, y, 20, 20, new TextComponent("-"), button -> {
			--this.value;
			if(this.value == 0) --this.value;
			valueWidget.setValue("" + this.value);
			valueWidget.setTextColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(190, y, 20, 20, new TextComponent("+"), button -> {
			++this.value;
			if(this.value == 0) ++this.value;
			valueWidget.setValue("" + this.value);
			valueWidget.setTextColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(160, y, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
			try {
				int newval = Integer.parseInt(valueWidget.getValue());
				this.value = newval;
				if(this.value == 0) {
					this.value = 1;
				}
				valueWidget.setValue("" + this.value);
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
	
	public void addTextFields(Font fr) {
		valueWidget = new EditBox(fr, 110, y, 50, 20, new TextComponent(""));
		valueWidget.setValue("" + value);
		valueWidget.setTextColor(0xFFFFFF);
		editBoxList.add(valueWidget);
	}
}
