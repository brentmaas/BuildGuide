package brentmaas.buildguide.property;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.BaseComponent;
import net.minecraft.network.chat.TextComponent;

public class PropertyBoolean extends Property<Boolean>{
	private Checkbox button;
	
	public PropertyBoolean(int x, int y, Boolean value, BaseComponent name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate);
		button = new Checkbox(x + 140, y, 20, 20, new TextComponent(""), value, false); //Definitely not this value so the UI lines up nicely
		buttonList.add(button);
	}
	
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, Font font) {
		super.render(matrixStack, mouseX, mouseY, partialTicks, font);
		value = button.selected();
		font.drawShadow(matrixStack, name.getString(), x + 5, y + 5, 0xFFFFFF);
	}
	
	public void addTextFields(Font fr) {
		
	}
}
