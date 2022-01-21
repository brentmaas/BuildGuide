package brentmaas.buildguide.property;

import brentmaas.buildguide.screen.widget.CheckboxRunnableButton;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class PropertyBoolean extends Property<Boolean>{
	private CheckboxRunnableButton button;
	
	public PropertyBoolean(int slot, Boolean value, Text name, Runnable onUpdate) {
		super(slot, value, name, onUpdate);
		button = new CheckboxRunnableButton(140, y, 20, 20, new LiteralText(""), value, false, button -> {
			this.value = button.isChecked();
			if(onUpdate != null) onUpdate.run();
		});
		buttonList.add(button);
	}
	
	public void addTextFields(TextRenderer fr) {
		
	}
	
	@Override
	public void setValue(Boolean value) {
		super.setValue(value);
		button.setChecked(value);
	}
}
