package brentmaas.buildguide.common.screen.widget;

public interface ISlider extends IWidget {
	//TODO Move implementation more towards here
	
	public void updateText();
	
	public void setSliderValue(double value);
	
	public double getSliderValue();
	
	public void setActive(boolean active);
}
