package brentmaas.buildguide.property;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.screen.PropertyScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.TextComponent;

public abstract class Property<T> {
	protected final static int baseY = 125;
	protected final static int height = 20;
	
	protected int y;
	public T value;
	protected TextComponent name;
	public ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
	public ArrayList<TextFieldWidget> textFieldList = new ArrayList<TextFieldWidget>();
	protected boolean visible;
	
	public Property(int slot, T value, TextComponent name, Runnable onUpdate) {
		y = baseY + slot * height;
		this.value = value;
		this.name = name;
		visible = true;
	}
	
	public abstract void addTextFields(FontRenderer fr);
	
	public void onSelectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = true;
		}
		for(TextFieldWidget tfw: textFieldList) {
			tfw.visible = true;
		}
		visible = true;
	}
	
	public void onDeselectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = false;
		}
		for(TextFieldWidget tfw: textFieldList) {
			tfw.visible = false;
		}
		visible = false;
	}
	
	public void addToPropertyScreen(PropertyScreen screen) {
		for(AbstractButton b: buttonList) {
			screen.addButtonExternal(b);
		}
		for(TextFieldWidget tfw: textFieldList) {
			screen.addTextFieldExternal(tfw);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setName(TextComponent name) {
		this.name = name;
	}
	
	public boolean mightNeedTextFields() {
		return textFieldList.size() == 0;
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font) {
		for(TextFieldWidget tfw: textFieldList) {
			tfw.render(matrixStack, mouseX, mouseY, partialTicks);
		}
		drawString(matrixStack, name.getString(), 5, y + 5, 0xFFFFFF, font);
	}
	
	protected void drawString(MatrixStack matrixStack, String text, float x, float y, int colour, FontRenderer font) {
		if(visible) {
			font.drawStringWithShadow(matrixStack, text, x, y, colour);
		}
	}
}
