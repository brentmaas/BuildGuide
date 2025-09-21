package brentmaas.buildguide.common.property;

import java.util.ArrayList;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.screen.widget.IWidget;

public class PropertyRunnable extends Property<Runnable> {
	public PropertyRunnable(Runnable value, Translatable name) {
		super(value, name);
	}
	
	protected void initWidgets(ArrayList<IWidget> widgetList) {
		widgetList.add(BuildGuide.widgetHandler.createButton(x, y, 210, AbstractWidgetHandler.defaultSize, name, () -> {
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
