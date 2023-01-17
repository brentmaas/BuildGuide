package brentmaas.buildguide.common.screen.widget;

public interface IButton {
	
	
	public void setVisible(boolean visible);
	
	public void setActive(boolean active);
	
	public void setYPosition(int y);
	
	public interface IPressable{
		public void onPress();
	}
}