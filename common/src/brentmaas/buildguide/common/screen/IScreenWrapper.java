package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;

public interface IScreenWrapper {
	
	
	public void attachScreen(BaseScreen screen);
	
	public void show();
	
	public void addButton(IButton button);
	
	public void addTextField(ITextField textField);
	
	public void addCheckbox(ICheckboxRunnableButton checkbox);
	
	public void addSlider(ISlider slider);
	
	public void addShapeList(IShapeList shapeList);
	
	public void drawShadow(String text, int x, int y, int colour);
	
	public int getTextWidth(String text);
	
	public int getWidth();
	
	public int getHeight();
}
