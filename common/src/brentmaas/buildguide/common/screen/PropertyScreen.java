package brentmaas.buildguide.common.screen;

import java.util.ArrayList;

import brentmaas.buildguide.common.property.Property;

public class PropertyScreen extends BaseScreen {
	protected ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	public PropertyScreen(String title) {
		super(title);
	}
	
	protected void addProperty(Property<?> p) {
		properties.add(p);
		p.addToPropertyScreen(this);
	}
	
	public void init() {
		
	}
	
	public void render() {
		for(Property<?> p: properties) {
			p.render(this);
		}
	}
	
	public boolean isPauseScreen() {
		return false;
	}
}
