package brentmaas.buildguide.property;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class PropertyMinimumInt extends Property<Integer>{
	private EditBox valueWidget;
	private int minInt;
	
	public PropertyMinimumInt(int x, int y, int value, BaseComponent name, Runnable onUpdate, int minInt) {
		super(x, y, value, name, onUpdate);
		this.minInt = minInt;
		buttonList.add(new Button(x + 90, y, 20, 20, new TextComponent("-"), button -> {
			if(this.value > this.minInt) --this.value;
			valueWidget.setValue("" + this.value);
			valueWidget.setTextColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(x + 190, y, 20, 20, new TextComponent("+"), button -> {
			++this.value;
			valueWidget.setValue("" + this.value);
			valueWidget.setTextColor(0xFFFFFF);
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(x + 160, y, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
			try {
				int newval = Integer.parseInt(valueWidget.getValue());
				this.value = newval;
				if(this.value < minInt) {
					this.value = minInt;
				}
				valueWidget.setValue("" + this.value);
				valueWidget.setTextColor(0xFFFFFF);
				if(onUpdate != null) onUpdate.run();
			}catch(NumberFormatException e) {
				valueWidget.setTextColor(0xFF0000);
			}
		}));
	}
	
	public void render(PoseStack PoseStack, int mouseX, int mouseY, float partialTicks, Font font) {
		super.render(PoseStack, mouseX, mouseY, partialTicks, font);
		font.drawShadow(PoseStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
	}
	
	public void setValue(Integer value) {
		super.setValue(value);
		valueWidget.setValue("" + value);
		valueWidget.setTextColor(0xFFFFFF);
	}
	
	public void addTextFields(Font fr) {
		valueWidget = new EditBox(fr, x + 110, y, 50, 20, new TextComponent(""));
		valueWidget.setValue("" + value);
		valueWidget.setTextColor(0xFFFFFF);
		editBoxList.add(valueWidget);
	}
}
