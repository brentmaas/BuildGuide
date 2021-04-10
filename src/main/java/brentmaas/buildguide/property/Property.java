package brentmaas.buildguide.property;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.BuildGuideScreen;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;

public abstract class Property<T> {
	protected int x, y;
	public T value;
	protected String name;
	public ArrayList<Button> buttonList = new ArrayList<Button>();
	
	public Property(int x, int y, T value, String name, Shape parentShape){
		this.x = x;
		this.y = y;
		this.value = value;
		this.name = name;
	}
	
	public void onSelectedInGUI() {
		for(Button b: buttonList) {
			b.visible = true;
		}
	}
	
	public void onDeselectedInGUI() {
		for(Button b: buttonList) {
			b.visible = false;
		}
	}
	
	public void addToBuildGuideScreen(BuildGuideScreen screen) {
		for(Button b: buttonList) {
			screen.addButtonExternal(b);
		}
	}
	
	public abstract void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks, FontRenderer font);
}
