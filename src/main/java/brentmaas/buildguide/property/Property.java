package brentmaas.buildguide.property;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.screen.PropertyScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.BaseComponent;

public abstract class Property<T> {
	protected final static int baseY = 125;
	protected final static int height = 20;
	
	protected int y;
	public T value;
	protected BaseComponent name;
	public ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
	public ArrayList<EditBox> editBoxList = new ArrayList<EditBox>();
	protected boolean visible;
	
	public Property(int slot, T value, BaseComponent name, Runnable onUpdate){
		y = baseY + slot * height;
		this.value = value;
		this.name = name;
		visible = true;
	}
	
	public abstract void addTextFields(Font fr);
	
	public void onSelectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = true;
		}
		for(EditBox eb: editBoxList) {
			eb.visible = true;
		}
		visible = true;
	}
	
	public void onDeselectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = false;
		}
		for(EditBox eb: editBoxList) {
			eb.visible = false;
		}
		visible = false;
	}
	
	public void addToPropertyScreen(PropertyScreen screen) {
		for(AbstractButton b: buttonList) {
			screen.addWidgetExternal(b);
		}
		for(EditBox eb: editBoxList) {
			screen.addWidgetExternal(eb);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setName(BaseComponent name) {
		this.name = name;
	}
	
	public boolean mightNeedTextFields() {
		return editBoxList.size() == 0;
	}
	
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks, Font font) {
		for(EditBox eb: editBoxList) {
			eb.render(matrixStack, mouseX, mouseY, partialTicks);
		}
		drawString(matrixStack, name.getString(), 5, y + 5, 0xFFFFFF, font);
	}
	
	protected void drawString(PoseStack matrixStack, String text, float x, float y, int colour, Font font) {
		if(visible) {
			font.drawShadow(matrixStack, text, x, y, colour);
		}
	}
}
