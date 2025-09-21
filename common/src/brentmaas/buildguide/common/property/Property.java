package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.ShapeScreen;
import brentmaas.buildguide.common.screen.widget.IWidget;

public abstract class Property<T> {
	protected int x;
	protected int y;
	public T value;
	protected Translatable name;
	private ArrayList<IWidget> widgetList = null;
	protected boolean visible = true;
	
	public Property(T value, Translatable name) {
		x = ShapeScreen.basePropertiesX;
		y = ShapeScreen.basePropertiesY;
		this.value = value;
		this.name = name;
	}
	
	protected abstract void initWidgets(ArrayList<IWidget> widgetList);
	
	public ArrayList<IWidget> getWidgetList() {
		if(widgetList == null) {
			widgetList = new ArrayList<IWidget>();
			initWidgets(widgetList);
		}
		return widgetList;
	}
	
	public void addToScreen(BaseScreen screen) {
		for(IWidget widget: getWidgetList()) {
			screen.addWidget(widget);
		}
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public abstract String getStringValue();
	
	public abstract boolean setValueFromString(String value);
	
	public void setName(Translatable name) {
		this.name = name;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
		for(IWidget widget: getWidgetList()) {
			widget.setYPosition(y);
		}
	}
	
	public void setVisibility(boolean visible) {
		for(IWidget widget: getWidgetList()) {
			widget.setVisibility(visible);
		}
		this.visible = visible;
	}
	
	public void render(BaseScreen screen) {
		drawString(screen, name.toString(), x + 5, y + 5, 0xFFFFFF);
	}
	
	public void drawString(BaseScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadowLeft(text, x, y, 0xFFFFFF);
	}
	
	public void drawStringCentred(BaseScreen screen, String text, int x, int y, int colour) {
		if(visible) screen.drawShadowCentred(text, x, y, 0xFFFFFF);
	}
}
