package brentmaas.buildguide.common;

import brentmaas.buildguide.common.screen.IScreenHandler;

public class BuildGuide {
	public static final String modid = "buildguide";
	
	public static IInputHandler keyBindHandler;
	public static IScreenHandler screenHandler;
	
	public BuildGuide(IInputHandler keyBindHandler, IScreenHandler screenHandler) {
		BuildGuide.keyBindHandler = keyBindHandler;
		BuildGuide.screenHandler = screenHandler;
		
		keyBindHandler.register();
		keyBindHandler.registerOnKeyInput();
	}
}
