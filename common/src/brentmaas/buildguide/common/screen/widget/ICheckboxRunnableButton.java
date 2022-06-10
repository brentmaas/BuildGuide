package brentmaas.buildguide.common.screen.widget;

public interface ICheckboxRunnableButton {
	
	
	public void onPress();
	
	public void setChecked(boolean checked);
	
	public boolean isSelected();
	
	public void setVisible(boolean visible);
	
	public void setActive(boolean active);
	
	public void setY(int y);
	
	public interface IPressable{
		void onPress();
	}
}
