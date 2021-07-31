package brentmaas.buildguide.property;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.screen.BuildGuideScreen;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.BaseComponent;

public abstract class Property<T> {
	protected int x, y;
	public T value;
	protected BaseComponent name;
	public ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
	public ArrayList<EditBox> editBoxList = new ArrayList<EditBox>();
	
	public Property(int x, int y, T value, BaseComponent name, Runnable onUpdate){
		this.x = x;
		this.y = y;
		this.value = value;
		this.name = name;
	}
	
	public void onSelectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = true;
		}
		for(EditBox eb: editBoxList) {
			eb.visible = true;
		}
	}
	
	public void onDeselectedInGUI() {
		for(AbstractButton b: buttonList) {
			b.visible = false;
		}
		for(EditBox eb: editBoxList) {
			eb.visible = false;
		}
	}
	
	public void addToBuildGuideScreen(BuildGuideScreen screen) {
		for(AbstractButton b: buttonList) {
			screen.addButtonExternal(b);
		}
		for(EditBox eb: editBoxList) {
			screen.addEditBoxExternal(eb);
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
	}
	
	public abstract void addTextFields(Font fr);
}
