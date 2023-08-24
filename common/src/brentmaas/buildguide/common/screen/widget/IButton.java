package brentmaas.buildguide.common.screen.widget;

public interface IButton extends IWidget {
	public void setActive(boolean active);
	
	public interface IPressable{
		public void onPress();
	}
}