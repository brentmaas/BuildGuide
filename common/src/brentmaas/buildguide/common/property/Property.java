package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.ITextField;

public abstract class Property<T> {
	protected final static int baseX = 180;
	protected final static int baseY = 70;
	protected final static int height = 20;
	
	protected int x;
	protected int y;
	public T value;
	protected String name;
	public ArrayList<IButton> buttonList = new ArrayList<IButton>();
	public ArrayList<ITextField> textFieldList = new ArrayList<ITextField>();
	public ArrayList<ICheckboxRunnableButton> checkboxList = new ArrayList<ICheckboxRunnableButton>();
	protected boolean visible = true;
	
	public Property(T value, String name) {
		x = baseX;
		y = baseY;
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
	
	public void addToScreen(BaseScreen screen) {
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
	
	public void setSlot(int slot) {
		x = baseX;
		y = baseY + slot * height;
		for(IButton button: buttonList) {
			button.setYPosition(y);
		}
		for(ITextField textField: textFieldList) {
			textField.setYPosition(y);
		}
		for(ICheckboxRunnableButton checkbox: checkboxList) {
			checkbox.setYPosition(y);
		}
	}
	
	public void render(BaseScreen screen) {
		drawString(screen, name, x + 5, y + 5, 0xFFFFFF);
	}
	
	public void drawString(BaseScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadow(text, x, y, 0xFFFFFF);
	}
	
	public void drawStringCentred(BaseScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadowCentred(text, x, y, 0xFFFFFF);
	}
}
