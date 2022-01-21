package brentmaas.buildguide.fabric.screen.widget;

import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.text.Text;

public class CheckboxRunnableButton extends CheckboxWidget{
	protected final CheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButton(int x, int y, int width, int height, Text title, boolean checked, boolean drawTitle, CheckboxRunnableButton.IPressable pressedAction) {
		super(x, y, width, height, title, checked, drawTitle);
		onPress = pressedAction;
	}
	
	public void onPress() {
		super.onPress();
		onPress.onPress(this);
	}
	
	public void setChecked(boolean checked) {
		//Why did selected have to be private ffs... At least I didn't have to do reflection by using this hack in order to get basic functionality
		if(isChecked() != checked) onPress();
		//this.checked = checked;
	}
	
	public interface IPressable {
		void onPress(CheckboxRunnableButton button);
	}
}
