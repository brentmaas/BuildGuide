package brentmaas.buildguide.forge.screen.widget;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.TextComponent;

public class Slider extends AbstractSliderButton{
	private double min, max;
	private String prefix;
	
	public Slider(int x, int y, int width, int height, TextComponent name, double min, double max, double value) {
		super(x, y, width, height, new TextComponent(name.getString() + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
		this.min = min;
		this.max = max;
		prefix = name.getString() + ": ";
	}
	
	public void updateMessage() {
		setMessage(new TextComponent(prefix + Math.round(10.0 * getValue()) / 10.0));
	}
	
	protected void applyValue() {
		
	}
	
	public void setManualValue(double value) {
		this.value = (value - min) / (max - min);
	}
	
	public double getValue() {
		return value * (max - min) + min;
	}
}
