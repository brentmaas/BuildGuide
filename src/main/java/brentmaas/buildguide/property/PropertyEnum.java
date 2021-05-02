package brentmaas.buildguide.property;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class PropertyEnum<T extends Enum<T>> extends Property<T> {
	private String[] names;
	
	public PropertyEnum(int x, int y, T value, TextComponent name, Runnable onUpdate, String[] names) {
		super(x, y, value, name, onUpdate);
		this.names = names;
		buttonList.add(new Button(x + 100, y, 20, 20, new StringTextComponent("<-"), button -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() - 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(x + 140, y, 20, 20, new StringTextComponent("->"), button -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() + 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onUpdate != null) onUpdate.run();
		}));
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font) {
		font.drawStringWithShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, names[value.ordinal()], x + 120 + (20 - font.getStringWidth(names[value.ordinal()])) / 2, y + 5, 0xFFFFFF);
	}
}
