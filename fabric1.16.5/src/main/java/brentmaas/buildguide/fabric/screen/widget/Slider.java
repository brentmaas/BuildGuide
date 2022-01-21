package brentmaas.buildguide.fabric.screen.widget;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class Slider extends SliderWidget{
	private double min, max;
	private String prefix;
	
	public Slider(int x, int y, int width, int height, Text name, double min, double max, double value) {
		super(x, y, width, height, new LiteralText(name.getString() + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
		this.min = min;
		this.max = max;
		prefix = name.getString() + ": ";
	}
	
	protected void updateMessage() {
		setMessage(new LiteralText(prefix + Math.round(10.0 * getValue()) / 10.0));
	}
	
	protected void applyValue() {
		
	}
	
	public void updateSlider() {
		updateMessage();
	}
	
	public void setManualValue(double value) {
		this.value = (value - min) / (max - min);
	}
	
	public double getValue() {
		return value * (max - min) + min;
	}
}
