package brentmaas.buildguide.fabric.property;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class PropertyEnum<T extends Enum<T>> extends Property<T> {
	private String[] names;
	
	public PropertyEnum(int slot, T value, Text name, Runnable onUpdate, String[] names) {
		super(slot, value, name, onUpdate);
		this.names = names;
		buttonList.add(new ButtonWidget(90, y, 20, 20, new LiteralText("<-"), button -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() - 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new ButtonWidget(190, y, 20, 20, new LiteralText("->"), button -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() + 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onUpdate != null) onUpdate.run();
		}));
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, TextRenderer font) {
		super.render(matrixStack, mouseX, mouseY, partialTicks, font);
		drawString(matrixStack, names[value.ordinal()], 110 + (80 - font.getWidth(names[value.ordinal()])) / 2, y + 5, 0xFFFFFF, font);
	}
	
	public void addTextFields(TextRenderer fr) {
		
	}
}
