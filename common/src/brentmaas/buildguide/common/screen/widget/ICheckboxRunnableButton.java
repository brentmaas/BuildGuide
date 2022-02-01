package brentmaas.buildguide.common.screen.widget;

public interface ICheckboxRunnableButton {
	
	
	public void onPress();
	
	public void setChecked(boolean checked);
	
	public boolean isChecked();
	
	public void setVisible(boolean visible);
	
	public void setActive(boolean active);
	
	public interface IPressable{
		void onPress();
	}
}
