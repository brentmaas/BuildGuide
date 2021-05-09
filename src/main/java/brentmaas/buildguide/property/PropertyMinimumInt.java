package brentmaas.buildguide.property;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class PropertyMinimumInt extends Property<Integer>{
	private int minInt;
	
	public PropertyMinimumInt(int x, int y, int value, TextComponent name, Runnable onUpdate, int minInt) {
		super(x, y, value, name, onUpdate);
		this.minInt = minInt;
		buttonList.add(new Button(x + 100, y, 20, 20, new StringTextComponent("-"), button -> {
			if(this.value > this.minInt) --this.value;
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(x + 140, y, 20, 20, new StringTextComponent("+"), button -> {
			++this.value;
			if(onUpdate != null) onUpdate.run();
		}));
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font) {
		font.drawStringWithShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, value.toString(), x + 120 + (20 - font.getStringWidth(value.toString())) / 2, y + 5, 0xFFFFFF);
	}
}
