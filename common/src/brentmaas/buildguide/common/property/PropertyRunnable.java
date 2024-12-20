package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.BaseScreen;

public class PropertyRunnable extends Property<Runnable> {
	public PropertyRunnable(Runnable value, Translatable name) {
		super(value, name);
		widgetList.add(BuildGuide.widgetHandler.createButton(x, y, 210, height, name, () -> {
			this.value.run();
		}));
	}
	
	@Override
	public void render(BaseScreen screen) {
		
	}
	
	public String getStringValue() {
		return "Runnable";
	}
	
	public boolean setValueFromString(String value) {
		return true;
	}
}
