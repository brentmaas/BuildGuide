package brentmaas.buildguide.fabric.property;

import java.util.ArrayList;

import brentmaas.buildguide.fabric.screen.PropertyScreen;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class Property<T> {
	protected final static int baseY = 125;
	protected final static int height = 20;
	
	protected int y;
	public T value;
	protected Text name;
	public ArrayList<ClickableWidget> buttonList = new ArrayList<ClickableWidget>();
	public ArrayList<TextFieldWidget> textFieldList = new ArrayList<TextFieldWidget>();
	protected boolean visible = true;
	
	public Property(int slot, T value, Text name, Runnable onUpdate){
		y = baseY + slot * height;
		this.value = value;
		this.name = name;
	}
	
	public abstract void addTextFields(TextRenderer fr);
	
	public void onSelectedInGUI() {
		for(ClickableWidget b: buttonList) {
			b.visible = true;
		}
		for(TextFieldWidget tfw: textFieldList) {
			tfw.visible = true;
		}
		visible = true;
	}
	
	public void onDeselectedInGUI() {
		for(ClickableWidget b: buttonList) {
			b.visible = false;
		}
		for(TextFieldWidget tfw: textFieldList) {
			tfw.visible = false;
		}
		visible = false;
	}
	
	public void addToPropertyScreen(PropertyScreen screen) {
		for(ClickableWidget b: buttonList) {
			screen.addWidgetExternal(b);
		}
		for(TextFieldWidget tfw: textFieldList) {
			screen.addWidgetExternal(tfw);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setName(Text name) {
		this.name = name;
	}
	
	public boolean mightNeedTextFields() {
		return textFieldList.size() == 0;
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, TextRenderer font) {
		for(TextFieldWidget tfw: textFieldList) {
			tfw.render(matrixStack, mouseX, mouseY, partialTicks);
		}
		drawString(matrixStack, name.getString(), 5, y + 5, 0xFFFFFF, font);
	}
	
	protected void drawString(MatrixStack matrixStack, String text, float x, float y, int colour, TextRenderer font) {
		if(visible) {
			font.drawWithShadow(matrixStack, text, x, y, colour);
		}
	}
}
