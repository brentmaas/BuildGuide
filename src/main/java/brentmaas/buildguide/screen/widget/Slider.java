package brentmaas.buildguide.screen.widget;

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class Slider extends AbstractSlider{
	private double min, max;
	private String prefix;
	
	public Slider(int x, int y, int width, int height, TextComponent name, double min, double max, double value) {
		super(x, y, width, height, new StringTextComponent(name.getString() + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
		this.min = min;
		this.max = max;
		prefix = name.getString() + ": ";
	}
	
	protected void func_230979_b_() { //updateMessage
		setMessage(new StringTextComponent(prefix + Math.round(10.0 * getValue()) / 10.0));
	}
	
	protected void func_230972_a_() { //applyValue
		
	}
	
	public void updateSlider() {
		func_230979_b_();
	}
	
	public void setManualValue(double value) {
		sliderValue = (value - min) / (max - min);
	}
	
	public double getValue() {
		return sliderValue * (max - min) + min;
	}
}
