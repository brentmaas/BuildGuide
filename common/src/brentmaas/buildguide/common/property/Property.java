package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.screen.PropertyScreen;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.ITextField;

public abstract class Property<T> {
	protected final static int baseY = 125;
	protected final static int height = 20;
	
	protected int y;
	public T value;
	protected String name;
	public ArrayList<IButton> buttonList = new ArrayList<IButton>();
	public ArrayList<ITextField> textFieldList = new ArrayList<ITextField>();
	public ArrayList<ICheckboxRunnableButton> checkboxList = new ArrayList<ICheckboxRunnableButton>();
	protected boolean visible = true;
	
	public Property(int slot, T value, String name) {
		y = baseY + slot * height;
		this.value = value;
		this.name = name;
	}
	
	public void onSelectedInGUI() {
		for(IButton b: buttonList) {
			b.setVisible(true);
		}
		for(ITextField tf: textFieldList) {
			tf.setVisibility(true);
		}
		for(ICheckboxRunnableButton cb: checkboxList) {
			cb.setVisible(true);
		}
		visible = true;
	}
	
	public void onDeselectedInGUI() {
		for(IButton b: buttonList) {
			b.setVisible(false);
		}
		for(ITextField tf: textFieldList) {
			tf.setVisibility(false);
		}
		for(ICheckboxRunnableButton cb: checkboxList) {
			cb.setVisible(false);
		}
		visible = false;
	}
	
	public void addToPropertyScreen(PropertyScreen screen) {
		for(IButton b: buttonList) {
			screen.addButton(b);
		}
		for(ITextField tf: textFieldList) {
			screen.addTextField(tf);
		}
		for(ICheckboxRunnableButton cb: checkboxList) {
			screen.addCheckbox(cb);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void render(PropertyScreen screen) {
		drawString(screen, name, 5, y + 5, 0xFFFFFF);
	}
	
	public void drawString(PropertyScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadow(text, x, y, 0xFFFFFF);
	}
	
	public void drawStringCentred(PropertyScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadowCentred(text, x, y, 0xFFFFFF);
	}
}
