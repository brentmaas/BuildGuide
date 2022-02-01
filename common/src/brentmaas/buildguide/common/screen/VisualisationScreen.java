package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ISlider;

public class VisualisationScreen extends PropertyScreen {
	private String titleColours = BuildGuide.screenHandler.translate("screen.buildguide.colours");;
	private String titleShape = BuildGuide.screenHandler.translate("screen.buildguide.shape");;
	private String titleBasepos = BuildGuide.screenHandler.translate("screen.buildguide.basepos");;
	
	private IButton buttonClose;
	private IButton buttonBack = BuildGuide.widgetHandler.createButton(0, 0, 20, 20, "<-", () -> BuildGuide.screenHandler.showScreen(new BuildGuideScreen()));
	private ISlider sliderShapeR = BuildGuide.widgetHandler.createSlider(0, 35, 100, 20, "R", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeR : 1.0);
	private ISlider sliderShapeG = BuildGuide.widgetHandler.createSlider(0, 55, 100, 20, "G", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeG : 1.0);
	private ISlider sliderShapeB = BuildGuide.widgetHandler.createSlider(0, 75, 100, 20, "B", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeB : 1.0);
	private ISlider sliderShapeA = BuildGuide.widgetHandler.createSlider(0, 95, 100, 20, "A", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeA : 0.5);
	private ISlider sliderBaseposR = BuildGuide.widgetHandler.createSlider(110, 35, 100, 20, "R", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposR : 1.0);
	private ISlider sliderBaseposG = BuildGuide.widgetHandler.createSlider(110, 55, 100, 20, "G", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposG : 1.0);
	private ISlider sliderBaseposB = BuildGuide.widgetHandler.createSlider(110, 75, 100, 20, "B", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposB : 1.0);
	private ISlider sliderBaseposA = BuildGuide.widgetHandler.createSlider(110, 95, 100, 20, "A", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposA : 0.5);
	private IButton buttonSetShape = BuildGuide.widgetHandler.createButton(0, 115, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setShapeColour((float) sliderShapeR.getSliderValue(), (float) sliderShapeG.getSliderValue(), (float) sliderShapeB.getSliderValue(), (float) sliderShapeA.getSliderValue());
		}
	});
	private IButton buttonSetBasepos = BuildGuide.widgetHandler.createButton(110, 115, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setBaseposColour((float) sliderBaseposR.getSliderValue(), (float) sliderBaseposG.getSliderValue(), (float) sliderBaseposB.getSliderValue(), (float) sliderBaseposA.getSliderValue());
		}
	});
	private IButton buttonDefaultShape = BuildGuide.widgetHandler.createButton(0, 135, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
		sliderShapeR.setSliderValue(1.0);
		sliderShapeG.setSliderValue(1.0);
		sliderShapeB.setSliderValue(1.0);
		sliderShapeA.setSliderValue(0.5);
		sliderShapeR.updateMessage();
		sliderShapeG.updateMessage();
		sliderShapeB.updateMessage();
		sliderShapeA.updateMessage();
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setShapeColour(1.0f, 1.0f, 1.0f, 0.5f);
		}
	});
	private IButton buttonDefaultBasepos = BuildGuide.widgetHandler.createButton(110, 135, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
		sliderBaseposR.setSliderValue(1.0);
		sliderBaseposG.setSliderValue(0.0);
		sliderBaseposB.setSliderValue(0.0);
		sliderBaseposA.setSliderValue(0.5);
		sliderBaseposR.updateMessage();
		sliderBaseposG.updateMessage();
		sliderBaseposB.updateMessage();
		sliderBaseposA.updateMessage();
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setBaseposColour(1.0f, 0.0f, 0.0f, 0.5f);
		}
	});
	
	public VisualisationScreen() {
		super(BuildGuide.screenHandler.translate("screen.buildguide.visualisation"));
	}
	
	public void init() {
		super.init();
		
		buttonClose = BuildGuide.widgetHandler.createButton(wrapper.getWidth() - 20, 0, 20, 20, "X", () -> BuildGuide.screenHandler.showScreen(null));
		
		if(!BuildGuide.stateManager.getState().isShapeAvailable()) {
			sliderShapeR.setActive(false);
			sliderShapeG.setActive(false);
			sliderShapeB.setActive(false);
			sliderShapeA.setActive(false);
			sliderBaseposR.setActive(false);
			sliderBaseposG.setActive(false);
			sliderBaseposB.setActive(false);
			sliderBaseposA.setActive(false);
			buttonSetShape.setActive(false);
			buttonDefaultShape.setActive(false);
			buttonSetBasepos.setActive(false);
			buttonDefaultBasepos.setActive(false);
		}
		
		addButton(buttonClose);
		addButton(buttonBack);
		addSlider(sliderShapeR);
		addSlider(sliderShapeG);
		addSlider(sliderShapeB);
		addSlider(sliderShapeA);
		addSlider(sliderBaseposR);
		addSlider(sliderBaseposG);
		addSlider(sliderBaseposB);
		addSlider(sliderBaseposA);
		addButton(buttonSetShape);
		addButton(buttonDefaultShape);
		addButton(buttonSetBasepos);
		addButton(buttonDefaultBasepos);
		
		addProperty(BuildGuide.stateManager.getState().propertyDepthTest);
	}
	
	public void render() {
		super.render();
		drawShadowCentred(title, wrapper.getWidth() / 2, 5, 0xFFFFFF);
		drawShadowCentred(titleColours, 105, 15, 0xFFFFFF);
		drawShadowCentred(titleShape, 50, 25, 0xFFFFFF);
		drawShadowCentred(titleBasepos, 160, 25, 0xFFFFFF);
	}
}
