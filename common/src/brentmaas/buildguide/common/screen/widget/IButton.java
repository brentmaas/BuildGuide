package brentmaas.buildguide.common.screen.widget;

public class IButton {
	public final int x, y, width, height;
	public final String text;
	public final IButton.IPressable onPress;
	
	public IButton(int x, int y, int width, int height, String text, IButton.IPressable onPress) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.text = text;
		this.onPress = onPress;
	}
	
	public void onPress() {
		onPress.onPress(this);
	}
	
	public interface IPressable {
		public void onPress(IButton button);
	}
}