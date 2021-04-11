package brentmaas.buildguide.property;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;

public class PropertyBoolean extends Property<Boolean>{
	
	
	public PropertyBoolean(int x, int y, Boolean value, String name, Shape parentShape) {
		super(x, y, value, name, parentShape);
		//TODO Look at CheckboxButton
		buttonList.add(new Button(x + 80, y, 40, 20, new TranslationTextComponent("screen.buildguide.toggle"), button -> {
			this.value = !this.value;
			if(parentShape != null) parentShape.update();
		}));
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font) {
		font.drawStringWithShadow(matrixStack, name, x + 5, y + 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, value.toString(), x + 120 + (20 - font.getStringWidth(value.toString())) / 2, y + 5, 0xFFFFFF);
	}
}
