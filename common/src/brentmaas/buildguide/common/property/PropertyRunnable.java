package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.PropertyScreen;

public class PropertyRunnable extends Property<Runnable> {
	
	
	public PropertyRunnable(Runnable value, String name) {
		super(value, name);
		buttonList.add(BuildGuide.widgetHandler.createButton(0, y, 210, height, name, () -> {
			this.value.run();
		}));
	}
	
	@Override
	public void render(PropertyScreen screen) {
		
	}
}
