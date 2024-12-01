package brentmaas.buildguide.common.screen.widget;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public abstract class AbstractWidgetHandler {
	public abstract IButton createButton(int x, int y, int width, int height, Translatable text, IButton.IPressable onPress);
	
	public IButton createButton(int x, int y, int width, int height, Translatable text, IButton.IPressable onPress, boolean active) {
		IButton button = createButton(x, y, width, height, text, onPress);
		button.setActive(active);
		return button;
	}
	
	public abstract ITextField createTextField(int x, int y, int width, int height, String value);
	
	public abstract ICheckboxRunnableButton createCheckbox(int x, int y, int width, int height, Translatable title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress);
	
	public abstract ISlider createSlider(int x, int y, int width, int height, Translatable name, double min, double max, double value);
	
	public abstract IShapeList createShapelist(int left, int right, int top, int bottom, int slotHeight, Runnable update);
}
