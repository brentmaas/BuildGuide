package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;

public abstract class BaseScreen {
	public String title;
	protected IScreenWrapper wrapper;
	
	public BaseScreen(String title) {
		this.title = BuildGuide.screenHandler.translate(title);
	}
	
	public void setWrapper(IScreenWrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public void addButton(IButton button) {
		if(wrapper != null) wrapper.addButton(button);
	}
	
	public void addTextField(ITextField textField) {
		if(wrapper != null) wrapper.addTextField(textField);
	}
	
	public void addCheckbox(ICheckboxRunnableButton checkbox) {
		if(wrapper != null) wrapper.addCheckbox(checkbox);
	}
	
	public void addSlider(ISlider slider){
		if(wrapper != null) wrapper.addSlider(slider);
	}
	
	public void addShapeList(IShapeList shapeList) {
		if(wrapper != null) wrapper.addShapeList(shapeList);
	}
	
	public void drawShadow(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x, y, colour);
	}
	
	public void drawShadowCentred(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x - wrapper.getTextWidth(text) / 2, y, colour);
	}
	
	public abstract void init();
	
	public abstract void render();
	
	public abstract boolean isPauseScreen();
}