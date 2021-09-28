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
		this.onPress.onPress(this);
	}
	
	@OnlyIn(Dist.CLIENT)
	public interface IPressable{
		void onPress(CheckboxRunnableButton button);
	}
}
