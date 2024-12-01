package brentmaas.buildguide.neoforge.screen.widget;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.ISlider;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

public class SliderImpl extends AbstractSliderButton implements ISlider {
	private double min, max;
	private Translatable name;
	
	public SliderImpl(int x, int y, int width, int height, Translatable name, double min, double max, double value) {
		super(x, y, width, height, Component.literal(name + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
		this.name = name;
		this.min = min;
		this.max = max;
	}
	 
	public void setYPosition(int y) { 
		this.setY(y); 
	} 
	 
	public void setVisibility(boolean visible) { 
		this.visible = visible; 
	} 
	
	public void updateText() {
		setMessage(Component.literal(name + ": " + Math.round(10.0 * getSliderValue()) / 10.0));
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
