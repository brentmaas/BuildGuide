package brentmaas.buildguide.screen.widget;

import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheckboxRunnableButton extends CheckboxButton {
	protected final CheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButton(int x, int y, int width, int height, ITextComponent title, boolean checked, boolean drawTitle, CheckboxRunnableButton.IPressable pressedAction) {
		super(x, y, width, height, title, checked, drawTitle);
		this.onPress = pressedAction;
	}
	
	public void onPress() {
		super.onPress();
		onPress.onPress(this);
	}
	
	public void setChecked(boolean checked) {
		//Why did checked have to be private ffs... At least I didn't have to do reflection by using this hack in order to get basic functionality
		if(isChecked() != checked) onPress();
		//this.checked = checked;
	}
	
	@OnlyIn(Dist.CLIENT)
	public interface IPressable{
		void onPress(CheckboxRunnableButton button);
	}
}
