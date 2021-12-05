package brentmaas.buildguide.screen.widget;

import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CheckboxRunnableButton extends Checkbox{
	protected final CheckboxRunnableButton.IPressable onPress;
	
	public CheckboxRunnableButton(int x, int y, int width, int height, TextComponent title, boolean checked, boolean drawTitle, CheckboxRunnableButton.IPressable pressedAction) {
		super(x, y, width, height, title, checked, drawTitle);
		this.onPress = pressedAction;
	}
	
	public void onPress() {
		super.onPress();
		onPress.onPress(this);
	}
	
	public void setChecked(boolean checked) {
		//Why did selected have to be private ffs... At least I didn't have to do reflection by using this hack in order to get basic functionality
		if(selected() != checked) onPress();
		//this.selected = checked;
	}
	
	@OnlyIn(Dist.CLIENT)
	public interface IPressable{
		void onPress(CheckboxRunnableButton button);
	}
}
