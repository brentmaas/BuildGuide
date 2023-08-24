package brentmaas.buildguide.common.screen.widget;

public interface ICheckboxRunnableButton extends IWidget {
	public void onPress();
	
	public void setChecked(boolean checked);
	
	public boolean isCheckboxSelected();
	
	public void setActive(boolean active);
	
	public interface IPressable{
		void onPress();
	}
}
