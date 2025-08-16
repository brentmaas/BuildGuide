package brentmaas.buildguide.common.screen.widget;

import java.util.List;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.ISelectorList.ISelectorListCallback;

public abstract class AbstractWidgetHandler {
	public static final int defaultSize = 20;
	public static final int defaultTextFieldWidth = 70;
	public static final int defaultSliderWidth = 120;
	
	public abstract IButton createButton(int x, int y, int width, int height, Translatable text, IButton.IPressable onPress);
	
	public IButton createButton(int x, int y, Translatable text, IButton.IPressable onPress) {
		return createButton(x, y, defaultSize, defaultSize, text, onPress);
	}
	
	public IButton createButton(int x, int y, int width, int height, Translatable text, IButton.IPressable onPress, boolean active) {
		IButton button = createButton(x, y, width, height, text, onPress);
		button.setActive(active);
		return button;
	}
	
	public IButton createButton(int x, int y, Translatable text, IButton.IPressable onPress, boolean active) {
		return createButton(x, y, defaultSize, defaultSize, text, onPress, active);
	}
	
	public abstract ITextField createTextField(int x, int y, int width, int height, String value);
	
	public ITextField createTextField(int x, int y, String value) {
		return createTextField(x, y, defaultTextFieldWidth, defaultSize, value);
	}
	
	public abstract ICheckboxRunnableButton createCheckbox(int x, int y, int width, int height, Translatable title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress);
	
	public ICheckboxRunnableButton createCheckbox(int x, int y, Translatable title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		return createCheckbox(x, y, defaultSize, defaultSize, title, checked, drawTitle, onPress);
	}
	
	public abstract ISlider createSlider(int x, int y, int width, int height, Translatable name, double min, double max, double value);
	
	public ISlider createSlider(int x, int y, Translatable name, double min, double max, double value) {
		return createSlider(x, y, defaultSliderWidth, defaultSize, name, min, max, value);
	}
	
	public abstract IShapeList createShapelist(int left, int right, int top, int bottom, int slotHeight, Runnable update);
	
	public IShapeList createShapelist(int left, int right, int top, int bottom, Runnable update) {
		return createShapelist(left, right, top, bottom, defaultSize, update);
	}
	
	public abstract ISelectorList createSelectorList(int left, int right, int top, int bottom, int slotHeight, List<Translatable> titles, int current, ISelectorListCallback callback);
	
	public ISelectorList createSelectorList(int left, int right, int top, int bottom, List<Translatable> titles, int current, ISelectorListCallback callback) {
		return createSelectorList(left, right, top, bottom, defaultSize, titles, current, callback);
	}
}
