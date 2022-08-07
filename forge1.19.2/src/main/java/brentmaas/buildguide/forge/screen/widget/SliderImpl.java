package brentmaas.buildguide.forge.screen.widget;

import brentmaas.buildguide.common.screen.widget.ISlider;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class SliderImpl extends AbstractSliderButton implements ISlider {
	private double min, max;
	private String prefix;
	
	public SliderImpl(int x, int y, int width, int height, String name, double min, double max, double value) {
		super(x, y, width, height, Component.literal(name + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
		this.min = min;
		this.max = max;
		prefix = name + ": ";
	}
	
	public void updateText() {
		setMessage(Component.literal(prefix + Math.round(10.0 *  getSliderValue()) / 10.0));
	}
	
	protected void applyValue() {
		
	}
	
	public void updateMessage() {
		updateText();
	}
	
	public void setSliderValue(double value) {
		this.value = (value - min) / (max - min);
	}
	
	public double getSliderValue() {
		return value * (max - min) + min;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
}
