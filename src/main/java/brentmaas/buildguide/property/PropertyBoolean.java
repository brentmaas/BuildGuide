package brentmaas.buildguide.property;

import brentmaas.buildguide.screen.widget.CheckboxRunnableButton;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class PropertyBoolean extends Property<Boolean>{
	private CheckboxRunnableButton button;
	
	public PropertyBoolean(int slot, Boolean value, TextComponent name, Runnable onUpdate) {
		super(slot, value, name, onUpdate);
		button = new CheckboxRunnableButton(140, y, 20, 20, new StringTextComponent(""), value, false, button -> {
			this.value = button.isChecked();
			if(onUpdate != null) onUpdate.run();
		});
		buttonList.add(button);
	}
	
	public void addTextFields(FontRenderer fr) {
		
	}
}
