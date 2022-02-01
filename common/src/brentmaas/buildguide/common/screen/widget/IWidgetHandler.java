package brentmaas.buildguide.common.screen.widget;

public interface IWidgetHandler {
	
	
	public IButton createButton(int x, int y, int width, int height, String text, IButton.IPressable onPress);
	
	public ITextField createTextField(int x, int y, int width, int height, String value);
	
	public ICheckboxRunnableButton createCheckbox(int x, int y, int width, int height, String title, boolean checked, boolean drawTitle, ICheckboxRunnableButton.IPressable onPress);
	
	public ISlider createSlider(int x, int y, int width, int height, String name, double min, double max, double value);
	
	public IShapeList createShapelist(int left, int right, int top, int bottom, int slotHeight, Runnable update);
}
