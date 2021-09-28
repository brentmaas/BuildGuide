package brentmaas.buildguide.property;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.screen.widget.CheckboxRunnableButton;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class PropertyBoolean extends Property<Boolean>{
	private CheckboxRunnableButton button;
	
	public PropertyBoolean(int x, int y, Boolean value, TextComponent name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate);
		button = new CheckboxRunnableButton(x + 140, y, 20, 20, new StringTextComponent(""), value, false, button -> {
			this.value = button.isChecked();
			if(onUpdate != null) onUpdate.run();
		});
		buttonList.add(button);
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font) {
		super.render(matrixStack, mouseX, mouseY, partialTicks, font);
		font.drawStringWithShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
	}
	
	public void addTextFields(FontRenderer fr) {
		
	}
}
