package brentmaas.buildguide.common.screen;

import java.util.Random;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.ISlider;

public class VisualisationScreen extends BaseScreen {
	private Translatable titleShapeColour = new Translatable("screen.buildguide.shapecolour");
	private Translatable titleOriginColour = new Translatable("screen.buildguide.origincolour");
	private Translatable titleRendering = new Translatable("screen.buildguide.rendering");
	private Translatable textDepthTest = new Translatable("screen.buildguide.depthtest");
	private Translatable titleCubeSize = new Translatable("screen.buildguide.cubesize");
	
	private ISlider sliderShapeR = BuildGuide.widgetHandler.createSlider(5, 70, 120, 20, new Translatable("R"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourShapeR : 1.0);
	private ISlider sliderShapeG = BuildGuide.widgetHandler.createSlider(5, 90, 120, 20, new Translatable("G"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourShapeG : 1.0);
	private ISlider sliderShapeB = BuildGuide.widgetHandler.createSlider(5, 110, 120, 20, new Translatable("B"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourShapeB : 1.0);
	private ISlider sliderShapeA = BuildGuide.widgetHandler.createSlider(5, 130, 120, 20, new Translatable("A"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourShapeA : 0.5);
	private ISlider sliderOriginR = BuildGuide.widgetHandler.createSlider(140, 70, 120, 20, new Translatable("R"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourOriginR : 1.0);
	private ISlider sliderOriginG = BuildGuide.widgetHandler.createSlider(140, 90, 120, 20, new Translatable("G"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourOriginG : 1.0);
	private ISlider sliderOriginB = BuildGuide.widgetHandler.createSlider(140, 110, 120, 20, new Translatable("B"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourOriginB : 1.0);
	private ISlider sliderOriginA = BuildGuide.widgetHandler.createSlider(140, 130, 120, 20, new Translatable("A"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().colourOriginA : 0.5);
	private ISlider sliderShapeCubeSize = BuildGuide.widgetHandler.createSlider(140, 235, 120, 20, new Translatable("screen.buildguide.shape"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().shapeCubeSize : 0.6);
	private ISlider sliderOriginCubeSize = BuildGuide.widgetHandler.createSlider(140, 255, 120, 20, new Translatable("screen.buildguide.origin"), 0.0, 1.0, BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShapeSet().originCubeSize : 0.2);
	
	private IButton buttonSetShape = BuildGuide.widgetHandler.createButton(5, 150, 120, 20, new Translatable("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setShapeColour((float) sliderShapeR.getSliderValue(), (float) sliderShapeG.getSliderValue(), (float) sliderShapeB.getSliderValue(), (float) sliderShapeA.getSliderValue());
		}
	});
	private IButton buttonSetOrigin = BuildGuide.widgetHandler.createButton(140, 150, 120, 20, new Translatable("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setOriginColour((float) sliderOriginR.getSliderValue(), (float) sliderOriginG.getSliderValue(), (float) sliderOriginB.getSliderValue(), (float) sliderOriginA.getSliderValue());
		}
	});
	private IButton buttonSetShapeRandom = BuildGuide.widgetHandler.createButton(5, 170, 120, 20, new Translatable("screen.buildguide.setrandom"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			Random random = new Random();
			sliderShapeR.setSliderValue(random.nextDouble());
			sliderShapeG.setSliderValue(random.nextDouble());
			sliderShapeB.setSliderValue(random.nextDouble());
			sliderShapeR.updateText();
			sliderShapeG.updateText();
			sliderShapeB.updateText();
			BuildGuide.stateManager.getState().setShapeColour((float) sliderShapeR.getSliderValue(), (float) sliderShapeG.getSliderValue(), (float) sliderShapeB.getSliderValue(), (float) sliderShapeA.getSliderValue());
		}
	});
	private IButton buttonSetOriginRandom = BuildGuide.widgetHandler.createButton(140, 170, 120, 20, new Translatable("screen.buildguide.setrandom"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			Random random = new Random();
			sliderOriginR.setSliderValue(random.nextDouble());
			sliderOriginG.setSliderValue(random.nextDouble());
			sliderOriginB.setSliderValue(random.nextDouble());
			sliderOriginR.updateText();
			sliderOriginG.updateText();
			sliderOriginB.updateText();
			BuildGuide.stateManager.getState().setOriginColour((float) sliderOriginR.getSliderValue(), (float) sliderOriginG.getSliderValue(), (float) sliderOriginB.getSliderValue(), (float) sliderOriginA.getSliderValue());
		}
	});
	private IButton buttonDefaultShape = BuildGuide.widgetHandler.createButton(5, 190, 120, 20, new Translatable("screen.buildguide.default"), () -> {
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
	private IButton buttonDefaultOrigin = BuildGuide.widgetHandler.createButton(140, 190, 120, 20, new Translatable("screen.buildguide.default"), () -> {
		sliderOriginR.setSliderValue(1.0);
		sliderOriginG.setSliderValue(0.0);
		sliderOriginB.setSliderValue(0.0);
		sliderOriginA.setSliderValue(0.5);
		sliderOriginR.updateText();
		sliderOriginG.updateText();
		sliderOriginB.updateText();
		sliderOriginA.updateText();
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setOriginColour(1.0f, 0.0f, 0.0f, 0.5f);
		}
	});
	private ICheckboxRunnableButton buttonDepthTest;
	private IButton buttonSetCubeSize = BuildGuide.widgetHandler.createButton(140, 275, 120, 20, new Translatable("screen.buildguide.set"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setCubeSize(sliderShapeCubeSize.getSliderValue(), sliderOriginCubeSize.getSliderValue());
		}
	});
	private IButton buttonDefaultCubeSize = BuildGuide.widgetHandler.createButton(140, 295, 120, 20, new Translatable("screen.buildguide.default"), () -> {
		sliderShapeCubeSize.setSliderValue(0.6);
		sliderOriginCubeSize.setSliderValue(0.2);
		sliderShapeCubeSize.updateText();
		sliderOriginCubeSize.updateText();
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			BuildGuide.stateManager.getState().setCubeSize(0.6, 0.2);
		}
	});
	
	public void init() {
		super.init();
		
		if(!BuildGuide.stateManager.getState().isShapeAvailable()) {
			sliderShapeR.setActive(false);
			sliderShapeG.setActive(false);
			sliderShapeB.setActive(false);
			sliderShapeA.setActive(false);
			sliderOriginR.setActive(false);
			sliderOriginG.setActive(false);
			sliderOriginB.setActive(false);
			sliderOriginA.setActive(false);
			buttonSetShape.setActive(false);
			buttonSetShapeRandom.setActive(false);
			buttonDefaultShape.setActive(false);
			buttonSetOrigin.setActive(false);
			buttonSetOriginRandom.setActive(false);
			buttonDefaultOrigin.setActive(false);
		}
		
		buttonDepthTest = BuildGuide.widgetHandler.createCheckbox(5, 235, 20, 20, new Translatable(""), BuildGuide.stateManager.getState().depthTest, false, () -> {
			BuildGuide.stateManager.getState().depthTest = buttonDepthTest.isCheckboxSelected();
			BaseScreen.shouldUpdatePersistence = true;
		});
		
		addWidget(sliderShapeR);
		addWidget(sliderShapeG);
		addWidget(sliderShapeB);
		addWidget(sliderShapeA);
		addWidget(buttonSetShape);
		addWidget(buttonSetShapeRandom);
		addWidget(buttonDefaultShape);
		addWidget(sliderOriginR);
		addWidget(sliderOriginG);
		addWidget(sliderOriginB);
		addWidget(sliderOriginA);
		addWidget(buttonSetOrigin);
		addWidget(buttonSetOriginRandom);
		addWidget(buttonDefaultOrigin);
		addWidget(buttonDepthTest);
		addWidget(sliderShapeCubeSize);
		addWidget(sliderOriginCubeSize);
		addWidget(buttonSetCubeSize);
		addWidget(buttonDefaultCubeSize);
	}
	
	public void render() {
		super.render();
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleShapeColour, 65, 55, 0xFFFFFF);
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleOriginColour, 200, 55, 0xFFFFFF);
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleRendering, 65, 220, 0xFFFFFF);
		drawShadowLeft(textDepthTest.toString(), 30, 240, 0xFFFFFF);
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleCubeSize, 200, 220, 0xFFFFFF);
	}
}
