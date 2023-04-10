package brentmaas.buildguide.common.screen;

public abstract class AbstractScreenHandler {
	public final String TEXT_MODIFIER_OBFUSCATED = "\247k";
	public final String TEXT_MODIFIER_BOLD = "\247l";
	public final String TEXT_MODIFIER_STRIKETHROUGH = "\247m";
	public final String TEXT_MODIFIER_UNDERLINE = "\247n";
	public final String TEXT_MODIFIER_ITALIC = "\247o";
	public final String TEXT_MODIFIER_RESET = "\247r";
	
	public void showScreen(BaseScreen screen) {
		if(screen != null) wrapScreen(screen).show();
		else showNone();
	}
	
	public IScreenWrapper wrapScreen(BaseScreen screen) {
		IScreenWrapper wrapper = createWrapper(screen.title);
		wrapper.attachScreen(screen);
		return wrapper;
	}
	
	public abstract IScreenWrapper createWrapper(String title);
	
	public abstract void showNone();
	
	public abstract String translate(String translationKey);
	
	public abstract String translate(String translationKey, Object... values);
}