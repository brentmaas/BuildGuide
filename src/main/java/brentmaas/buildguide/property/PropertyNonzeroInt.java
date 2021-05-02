package brentmaas.buildguide.property;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class PropertyNonzeroInt extends Property<Integer>{
	
	
	public PropertyNonzeroInt(int x, int y, int value, TextComponent name, Shape parentShape) {
		super(x, y, value, name, parentShape);
		buttonList.add(new Button(x + 100, y, 20, 20, new StringTextComponent("-"), button -> {
			--this.value;
			if(this.value == 0) --this.value;
			if(parentShape != null) parentShape.update();
		}));
		buttonList.add(new Button(x + 140, y, 20, 20, new StringTextComponent("+"), button -> {
			++this.value;
			if(this.value == 0) ++this.value;
			if(parentShape != null) parentShape.update();
		}));
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font) {
		font.drawStringWithShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, value.toString(), x + 120 + (20 - font.getStringWidth(value.toString())) / 2, y + 5, 0xFFFFFF);
	}
}
