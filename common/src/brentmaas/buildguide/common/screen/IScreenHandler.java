package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.screen.widget.IButton;

public abstract class IScreenHandler {
	
	
	public abstract void show(IScreen iScreen);
	
	public static abstract class IScreen {
		public IScreenWrapper wrapper = null;
		
		public void setWrapper(IScreenWrapper wrapper) {
			this.wrapper = wrapper;
		}
		
		public abstract void init();
		
		public abstract void render();
		
		public abstract boolean isPauseScreen();
		
		public abstract String getTitle();
		
		public void addButton(IButton button) {
			if(wrapper != null) wrapper.addButton(button);
		}
		
		public void drawShadowCentred(String string, int x, int y, int colour) {
			if(wrapper != null) wrapper.drawShadowCentred(string, x, y, colour);
		}
		
		public int getWidth() {
			if(wrapper != null) return wrapper.getWidth();
			return 0;
		}
		
		public int getHeight() {
			if(wrapper != null) return wrapper.getHeight();
			return 0;
		}
	}
	
	public abstract class IScreenWrapper {
		public IScreen iScreen;
		
		public IScreenWrapper(IScreen iScreen) {
			this.iScreen = iScreen;
			iScreen.setWrapper(this);
		}
		
		public abstract void show();
		
		public abstract void addButton(IButton button);
		
		public abstract void drawShadowCentred(String string, int x, int y, int colour);
		
		public abstract int getWidth();
		
		public abstract int getHeight();
	}
}