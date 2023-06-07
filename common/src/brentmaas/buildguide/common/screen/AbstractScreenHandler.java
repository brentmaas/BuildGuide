package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;

public abstract class AbstractScreenHandler {
	public final String TEXT_MODIFIER_OBFUSCATED = "\247k";
	public final String TEXT_MODIFIER_BOLD = "\247l";
	public final String TEXT_MODIFIER_STRIKETHROUGH = "\247m";
	public final String TEXT_MODIFIER_UNDERLINE = "\247n";
	public final String TEXT_MODIFIER_ITALIC = "\247o";
	public final String TEXT_MODIFIER_RESET = "\247r";
	
	private static final String[] progressIndicator = {"|", "/", "-" , "\\"};
	
	public void showScreen(BaseScreen screen) {
		if(screen != null) wrapScreen(screen).show();
		else showNone();
	}
	
	public IScreenWrapper wrapScreen(BaseScreen screen) {
		IScreenWrapper wrapper = createWrapper(screen.title);
		wrapper.attachScreen(screen);
		return wrapper;
	}
	
	public String getFormattedShapeName(ShapeSet shapeSet) {
		String progressIndicatorPart = "";
		if(shapeSet != null && shapeSet.isShapeAvailable() && !shapeSet.getShape().ready) {
			long time = System.currentTimeMillis();
			progressIndicatorPart = " " + progressIndicator[(int) ((time / 100) % progressIndicator.length)];
		}
		return (shapeSet != null && shapeSet.isShapeAvailable() && !shapeSet.visible ? BuildGuide.screenHandler.TEXT_MODIFIER_STRIKETHROUGH : "") + (shapeSet != null && shapeSet.isShapeAvailable() ? shapeSet.getShape().getTranslatedName() : BuildGuide.screenHandler.translate("shape.buildguide.none")) + progressIndicatorPart;
	}
	
	public int getShapeProgressColour(Shape shape) {
		int colourFraction = (int) Math.max(Math.min((shape != null ? shape.getHowLongAgoCompletedMillis() : 2000) * 0xFF / 1000, 0xFF), 0);
		return shape != null && shape.error ? 0xFF0000 : (0x00FF00 + colourFraction * 0x010001);
	}
	
	public abstract IScreenWrapper createWrapper(String title);
	
	public abstract void showNone();
	
	public abstract String translate(String translationKey);
	
	public abstract String translate(String translationKey, Object... values);
}