package brentmaas.buildguide.common.screen;

import java.util.ArrayList;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.State.ActiveScreen;
import brentmaas.buildguide.common.property.Property;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;

public abstract class BaseScreen {
	protected String title = BuildGuide.screenHandler.translate("screen.buildguide.title");
	protected String textEnabled = BuildGuide.screenHandler.translate("screen.buildguide.enable");
	protected IScreenWrapper wrapper;
	protected ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private IButton buttonClose;
	private ICheckboxRunnableButton buttonEnabled;
	private IButton buttonBuildGuide = BuildGuide.widgetHandler.createButton(5, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.title"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.BuildGuide)), !(this instanceof BuildGuideScreen));
	private IButton buttonVisualisation = BuildGuide.widgetHandler.createButton(130, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.visualisation"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.Visualisation)), !(this instanceof VisualisationScreen));
	private IButton buttonShapeList = BuildGuide.widgetHandler.createButton(255, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.shapelist"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.Shapelist)), !(this instanceof ShapelistScreen));
	private IButton buttonSettings = BuildGuide.widgetHandler.createButton(380, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.settings"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.Settings)), !(this instanceof SettingsScreen));
	
	public void init() {
		buttonClose = BuildGuide.widgetHandler.createButton(wrapper.getWidth() - 25, 5, 20, 20, "X", () -> BuildGuide.screenHandler.showScreen(null));
		buttonEnabled = BuildGuide.widgetHandler.createCheckbox(5, 5, 20, 20, "", BuildGuide.stateManager.getState().enabled, false, () -> {
			BuildGuide.stateManager.getState().enabled = buttonEnabled.isCheckboxSelected();
		});
		
		addButton(buttonClose);
		addCheckbox(buttonEnabled);
		addButton(buttonBuildGuide);
		addButton(buttonVisualisation);
		addButton(buttonShapeList);
		addButton(buttonSettings);
	}
	
	public void render() {
		drawShadowCentred(title, wrapper.getWidth() / 2, 10, 0xFFFFFF);
		drawShadow(textEnabled, 30, 10, 0xFFFFFF);
		
		for(Property<?> p: properties) {
			p.render(this);
		}
	}
	
	public void setWrapper(IScreenWrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public void addButton(IButton button) {
		if(wrapper != null) wrapper.addButton(button);
	}
	
	public void addTextField(ITextField textField) {
		if(wrapper != null) wrapper.addTextField(textField);
	}
	
	public void addCheckbox(ICheckboxRunnableButton checkbox) {
		if(wrapper != null) wrapper.addCheckbox(checkbox);
	}
	
	public void addSlider(ISlider slider){
		if(wrapper != null) wrapper.addSlider(slider);
	}
	
	public void addShapeList(IShapeList shapeList) {
		if(wrapper != null) wrapper.addShapeList(shapeList);
	}
	
	public void drawShadow(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x, y, colour);
	}
	
	public void drawShadowCentred(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x - wrapper.getTextWidth(text) / 2, y, colour);
	}
	
	protected void addProperty(Property<?> p) {
		properties.add(p);
		p.addToScreen(this);
	}
	
	public boolean isPauseScreen() {
		return false;
	}
}