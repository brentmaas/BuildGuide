package brentmaas.buildguide.common.screen.widget;

public interface ISlider {
	//TODO Move implementation more towards here
	
	public void updateMessage();
	
	public void setSliderValue(double value);
	
	public double getSliderValue();
	
	public void setActive(boolean active);
}
