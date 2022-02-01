package brentmaas.buildguide.common.screen;

public abstract class AbstractScreenHandler {
	
	
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