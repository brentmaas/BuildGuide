package brentmaas.buildguide.fabric.screen.widget;

import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;
import net.minecraft.client.MinecraftClient;

public class WidgetHandler extends AbstractWidgetHandler {
	public IButton createButton(int x, int y, int width, int height, String text, IButton.IPressable onPress) {
		return new ButtonImpl(x, y, width, height, text, onPress);
	}
	
	public ITextField createTextField(int x, int y, int width, int height, String value) {
		return new TextFieldImpl(x, y, width, height, value);
	}
	
	public ICheckboxRunnableButton createCheckbox(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress) {
		return new CheckboxRunnableButtonImpl(x, y, width, height, title, checked, drawTitle, onPress);
	}
	
	public ISlider createSlider(int x, int y, int width, int height, String name, double min, double max, double value) {
		return new SliderImpl(x, y, width, height, name, min, max, value);
	}
	
	public IShapeList createShapelist(int left, int right, int top, int bottom, int slotHeight, Runnable update) {
		return new ShapeListImpl(MinecraftClient.getInstance(), left, right, top, bottom, slotHeight, update);
	}
}
