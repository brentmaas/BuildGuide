package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.widget.IWidget;

public abstract class Property<T> {
	protected final static int baseX = 180;
	protected final static int baseY = 70;
	protected final static int height = 20;
	
	protected int x;
	protected int y;
	public T value;
	protected String name;
	public ArrayList<IWidget> widgetList = new ArrayList<IWidget>();
	protected boolean visible = true;
	
	public Property(T value, String name) {
		x = baseX;
		y = baseY;
		this.value = value;
		this.name = name;
	}
	
	public void onSelectedInGUI() {
		for(IWidget widget: widgetList) {
			widget.setVisibility(true);
		}
		visible = true;
	}
	
	public void onDeselectedInGUI() {
		for(IWidget widget: widgetList) {
			widget.setVisibility(false);
		}
		visible = false;
	}
	
	public void addToScreen(BaseScreen screen) {
		for(IWidget widget: widgetList) {
			screen.addWidget(widget);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setSlot(int slot) {
		x = baseX;
		y = baseY + slot * height;
		for(IWidget widget: widgetList) {
			widget.setYPosition(y);
		}
	}
	
	public void render(BaseScreen screen) {
		drawString(screen, name, x + 5, y + 5, 0xFFFFFF);
	}
	
	public void drawString(BaseScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadow(text, x, y, 0xFFFFFF);
	}
	
	public void drawStringCentred(BaseScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadowCentred(text, x, y, 0xFFFFFF);
	}
}
