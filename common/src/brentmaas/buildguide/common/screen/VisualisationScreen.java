package brentmaas.buildguide.common.screen;

import java.util.Random;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ISlider;

public class VisualisationScreen extends PropertyScreen {
	private String titleColours = BuildGuide.screenHandler.translate("screen.buildguide.colours");
	private String titleShape = BuildGuide.screenHandler.translate("screen.buildguide.shape");
	private String titleBasepos = BuildGuide.screenHandler.translate("screen.buildguide.basepos");
	private String titleRendering = BuildGuide.screenHandler.translate("screen.buildguide.rendering");
	
	private IButton buttonClose;
	private IButton buttonBack = BuildGuide.widgetHandler.createButton(0, 0, 20, 20, "<-", () -> BuildGuide.screenHandler.showScreen(new BuildGuideScreen()));
	private ISlider sliderShapeR = BuildGuide.widgetHandler.createSlider(0, 45, 100, 20, "R", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeR : 1.0);
	private ISlider sliderShapeG = BuildGuide.widgetHandler.createSlider(0, 65, 100, 20, "G", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeG : 1.0);
	private ISlider sliderShapeB = BuildGuide.widgetHandler.createSlider(0, 85, 100, 20, "B", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeB : 1.0);
	private ISlider sliderShapeA = BuildGuide.widgetHandler.createSlider(0, 105, 100, 20, "A", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourShapeA : 0.5);
	private ISlider sliderBaseposR = BuildGuide.widgetHandler.createSlider(110, 45, 100, 20, "R", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposR : 1.0);
	private ISlider sliderBaseposG = BuildGuide.widgetHandler.createSlider(110, 65, 100, 20, "G", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposG : 1.0);
	private ISlider sliderBaseposB = BuildGuide.widgetHandler.createSlider(110, 85, 100, 20, "B", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposB : 1.0);
	private ISlider sliderBaseposA = BuildGuide.widgetHandler.createSlider(110, 105, 100, 20, "A", 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().colourBaseposA : 0.5);
	private IButton buttonSetShape = BuildGuide.widgetHandler.createButton(0, 125, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setShapeColour((float) sliderShapeR.getSliderValue(), (float) sliderShapeG.getSliderValue(), (float) sliderShapeB.getSliderValue(), (float) sliderShapeA.getSliderValue());
		}
	});
	private IButton buttonSetBasepos = BuildGuide.widgetHandler.createButton(110, 125, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setBaseposColour((float) sliderBaseposR.getSliderValue(), (float) sliderBaseposG.getSliderValue(), (float) sliderBaseposB.getSliderValue(), (float) sliderBaseposA.getSliderValue());
		}
	});
	private IButton buttonSetShapeRandom = BuildGuide.widgetHandler.createButton(0, 145, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.setrandom"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			Random random = new Random();
			sliderShapeR.setSliderValue(random.nextDouble());
			sliderShapeG.setSliderValue(random.nextDouble());
			sliderShapeB.setSliderValue(random.nextDouble());
			BuildGuide.stateManager.getState().setShapeColour((float) sliderShapeR.getSliderValue(), (float) sliderShapeG.getSliderValue(), (float) sliderShapeB.getSliderValue(), (float) sliderShapeA.getSliderValue());
		}
	});
	private IButton buttonSetBaseposRandom = BuildGuide.widgetHandler.createButton(110, 145, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.setrandom"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			Random random = new Random();
			sliderBaseposR.setSliderValue(random.nextDouble());
			sliderBaseposG.setSliderValue(random.nextDouble());
			sliderBaseposB.setSliderValue(random.nextDouble());
			BuildGuide.stateManager.getState().setBaseposColour((float) sliderBaseposR.getSliderValue(), (float) sliderBaseposG.getSliderValue(), (float) sliderBaseposB.getSliderValue(), (float) sliderBaseposA.getSliderValue());
		}
	});
	private IButton buttonDefaultShape = BuildGuide.widgetHandler.createButton(0, 165, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
		sliderShapeR.setSliderValue(1.0);
		sliderShapeG.setSliderValue(1.0);
		sliderShapeB.setSliderValue(1.0);
		sliderShapeA.setSliderValue(0.5);
		sliderShapeR.updateText();
		sliderShapeG.updateText();
		sliderShapeB.updateText();
		sliderShapeA.updateText();
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setShapeColour(1.0f, 1.0f, 1.0f, 0.5f);
		}
	});
	private IButton buttonDefaultBasepos = BuildGuide.widgetHandler.createButton(110, 165, 100, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
		sliderBaseposR.setSliderValue(1.0);
		sliderBaseposG.setSliderValue(0.0);
		sliderBaseposB.setSliderValue(0.0);
		sliderBaseposA.setSliderValue(0.5);
		sliderBaseposR.updateText();
		sliderBaseposG.updateText();
		sliderBaseposB.updateText();
		sliderBaseposA.updateText();
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
		addButton(buttonSetShapeRandom);
		addButton(buttonSetBaseposRandom);
		addButton(buttonSetBasepos);
		addButton(buttonDefaultBasepos);
		
		addProperty(BuildGuide.stateManager.getState().propertyDepthTest);
	}
	
	public void render() {
		super.render();
		drawShadowCentred(title, wrapper.getWidth() / 2, 5, 0xFFFFFF);
		drawShadowCentred(titleColours, 105, 15, 0xFFFFFF);
		drawShadowCentred(titleShape, 50, 35, 0xFFFFFF);
		drawShadowCentred(titleBasepos, 160, 35, 0xFFFFFF);
		drawShadowCentred(titleRendering, 80, 195, 0xFFFFFF);
	}
}
