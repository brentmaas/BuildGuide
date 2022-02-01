package brentmaas.buildguide.common.screen;

public interface IScreenHandler {
	
	
	public IScreenWrapper wrapScreen(BaseScreen screen);
	
	public void showScreen(BaseScreen screen);
	
	public String translate(String translationKey);
	
	public String translate(String translationKey, Object... values);
}