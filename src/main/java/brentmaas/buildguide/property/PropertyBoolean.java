package brentmaas.buildguide.property;

import brentmaas.buildguide.screen.widget.CheckboxRunnableButton;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;

public class PropertyBoolean extends Property<Boolean>{
	private CheckboxRunnableButton button;
	
	public PropertyBoolean(int slot, Boolean value, BaseComponent name, Runnable onUpdate) {
		super(slot, value, name, onUpdate);
		button = new CheckboxRunnableButton(140, y, 20, 20, new TextComponent(""), value, false, button -> {
			this.value = button.selected();
			if(onUpdate != null) onUpdate.run();
		});
		buttonList.add(button);
	}
	
	public void addTextFields(Font fr) {
		
	}
}
