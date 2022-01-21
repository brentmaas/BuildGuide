package brentmaas.buildguide.forge.property;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;

public class PropertyEnum<T extends Enum<T>> extends Property<T> {
	private String[] names;
	
	public PropertyEnum(int slot, T value, BaseComponent name, Runnable onUpdate, String[] names) {
		super(slot, value, name, onUpdate);
		this.names = names;
		buttonList.add(new Button(90, y, 20, 20, new TextComponent("<-"), button -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() - 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onUpdate != null) onUpdate.run();
		}));
		buttonList.add(new Button(190, y, 20, 20, new TextComponent("->"), button -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() + 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onUpdate != null) onUpdate.run();
		}));
	}
	
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, Font font) {
		super.render(matrixStack, mouseX, mouseY, partialTicks, font);
		drawString(matrixStack, names[value.ordinal()], 110 + (80 - font.width(names[value.ordinal()])) / 2, y + 5, 0xFFFFFF, font);
	}
	
	public void addTextFields(Font fr) {
		
	}
}
