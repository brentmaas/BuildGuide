package brentmaas.buildguide.property;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.screen.BuildGuideScreen;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.TextComponent;

public abstract class Property<T> {
	protected int x, y;
	public T value;
	protected TextComponent name;
	public ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
	
	public Property(int x, int y, T value, TextComponent name, Runnable onUpdate){
		this.x = x;
		this.y = y;
		this.value = value;
		this.name = name;
	}
	
	public void onSelectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = true;
		}
	}
	
	public void onDeselectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = false;
		}
	}
	
	public void addToBuildGuideScreen(BuildGuideScreen screen) {
		for(AbstractButton b: buttonList) {
			screen.addButtonExternal(b);
		}
	}
	
	public void setName(TextComponent name) {
		this.name = name;
	}
	
	public abstract void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font);
}
