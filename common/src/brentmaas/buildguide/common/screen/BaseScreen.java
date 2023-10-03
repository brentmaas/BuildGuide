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
import brentmaas.buildguide.common.screen.widget.IWidget;
import brentmaas.buildguide.common.shape.ShapeSet;

public abstract class BaseScreen {
	protected String title = BuildGuide.screenHandler.translate("screen.buildguide.title");
	protected String titleNumberOfBlocksShape = BuildGuide.screenHandler.translate("screen.buildguide.numberofblocksshape");
	protected String titleNumberOfBlocksTotal = BuildGuide.screenHandler.translate("screen.buildguide.numberofblockstotal");
	protected String textEnabled = BuildGuide.screenHandler.translate("screen.buildguide.enable");
	protected IScreenWrapper wrapper;
	protected ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private IButton buttonClose;
	private ICheckboxRunnableButton buttonEnabled;
	private IButton buttonBuildGuide = BuildGuide.widgetHandler.createButton(5, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.shape"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.BuildGuide)), !(this instanceof ShapeScreen));
	private IButton buttonVisualisation = BuildGuide.widgetHandler.createButton(130, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.visualisation"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.Visualisation)), !(this instanceof VisualisationScreen));
	private IButton buttonShapeList = BuildGuide.widgetHandler.createButton(255, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.shapelist"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.Shapelist)), !(this instanceof ShapelistScreen));
	private IButton buttonConfiguration = BuildGuide.widgetHandler.createButton(380, 30, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.configuration"), () -> BuildGuide.screenHandler.showScreen(BuildGuide.stateManager.getState().createNewScreen(ActiveScreen.Settings)), !(this instanceof ConfigurationScreen));
	
	public void init() {
		buttonClose = BuildGuide.widgetHandler.createButton(wrapper.getWidth() - 25, 5, 20, 20, "X", () -> BuildGuide.screenHandler.showScreen(null));
		buttonEnabled = BuildGuide.widgetHandler.createCheckbox(5, 5, 20, 20, "", BuildGuide.stateManager.getState().enabled, false, () -> {
			BuildGuide.stateManager.getState().enabled = buttonEnabled.isCheckboxSelected();
		});
		
		addWidget(buttonEnabled);
		addWidget(buttonClose);
		addWidget(buttonBuildGuide);
		addWidget(buttonVisualisation);
		addWidget(buttonShapeList);
		addWidget(buttonConfiguration);
	}
	
	public void render() {
		drawShadowCentred(title, wrapper.getWidth() / 2, 10, 0xFFFFFF);
		drawShadowLeft(textEnabled, 30, 10, 0xFFFFFF);
		
		int titlesMax = Math.max(wrapper.getTextWidth(titleNumberOfBlocksShape), wrapper.getTextWidth(titleNumberOfBlocksTotal));
		
		drawShadowCentred(titleNumberOfBlocksShape, wrapper.getWidth() / 2 - wrapper.getTextWidth(title) / 2 - titlesMax / 2 - 20, 5, 0xFFFFFF);
		int n = BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		drawShadowCentred(n + " (" + (n / 64) + " x 64 + " + (n % 64) + ")", wrapper.getWidth() / 2 - wrapper.getTextWidth(title) / 2 - titlesMax / 2 - 20, 20, 0xFFFFFF);
		
		drawShadowCentred(titleNumberOfBlocksTotal, wrapper.getWidth() / 2 + wrapper.getTextWidth(title) / 2 + titlesMax / 2 + 20, 5, 0xFFFFFF);
		int nTotal = 0;
		for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) {
			if(s.visible) nTotal += s.getShape().getNumberOfBlocks();
		}
		drawShadowCentred(nTotal + " (" + (nTotal / 64) + " x 64 + " + (nTotal % 64) + ")", wrapper.getWidth() / 2 + wrapper.getTextWidth(title) / 2 + titlesMax / 2 + 20, 20, 0xFFFFFF);
		
		for(Property<?> p: properties) {
			p.render(this);
		}
	}
	
	public void setWrapper(IScreenWrapper wrapper) {
		this.wrapper = wrapper;
	}
	
	public void addWidget(IWidget widget) {
		if(wrapper != null) {
			if(widget instanceof IButton) {
				wrapper.addButton((IButton) widget);
			} else if(widget instanceof ITextField) {
				wrapper.addTextField((ITextField) widget);
			} else if(widget instanceof ICheckboxRunnableButton) {
				wrapper.addCheckbox((ICheckboxRunnableButton) widget);
			} else if(widget instanceof ISlider) {
				wrapper.addSlider((ISlider) widget);
			} else if(widget instanceof IShapeList) {
				wrapper.addShapeList((IShapeList) widget);
			}
		}
	}
	
	public void drawShadowLeft(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x, y, colour);
	}
	
	public void drawShadowCentred(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x - wrapper.getTextWidth(text) / 2, y, colour);
	}
	
	public void drawShadowRight(String text, int x, int y, int colour) {
		if(wrapper != null) wrapper.drawShadow(text, x - wrapper.getTextWidth(text), y, colour);
	}
	
	protected void addProperty(Property<?> p) {
		properties.add(p);
		p.addToScreen(this);
	}
	
	public boolean isPauseScreen() {
		return false;
	}
}