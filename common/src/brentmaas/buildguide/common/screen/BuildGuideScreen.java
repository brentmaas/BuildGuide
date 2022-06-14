package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.Property;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.shape.Shape;

public class BuildGuideScreen extends PropertyScreen{
	private String titleGlobalProperties = BuildGuide.screenHandler.translate("screen.buildguide.globalproperties");
	private String titleShapeProperties = BuildGuide.screenHandler.translate("screen.buildguide.shapeproperties");
	private String titleBasepos = BuildGuide.screenHandler.translate("screen.buildguide.basepos");
	private String titleNumberOfBlocks = BuildGuide.screenHandler.translate("screen.buildguide.numberofblocks");
	private String textShape = BuildGuide.screenHandler.translate("screen.buildguide.shape");
	
	private static final String[] progressIndicator = {"|", "/", "-" , "\\"};
	
	private IButton buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private IButton buttonShapePrevious = BuildGuide.widgetHandler.createButton(60, 25, 20, 20, "<-", () -> updateShape(-1));
	private IButton buttonShapeNext = BuildGuide.widgetHandler.createButton(140, 25, 20, 20, "->", () -> updateShape(1));
	private IButton buttonShapeList = BuildGuide.widgetHandler.createButton(140, 25, 20, 20, "...", () -> BuildGuide.screenHandler.showScreen(new ShapelistScreen()));
	private IButton buttonBasepos = BuildGuide.widgetHandler.createButton(185, 25, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.setbasepos"), () -> setBasepos());;
	private IButton buttonVisualisation = BuildGuide.widgetHandler.createButton(0, 65, 160, 20, BuildGuide.screenHandler.translate("screen.buildguide.visualisation"), () -> BuildGuide.screenHandler.showScreen(new VisualisationScreen()));
	//It's better off as custom buttons instead of PropertyInt
	private IButton buttonBaseposXDecrease = BuildGuide.widgetHandler.createButton(185, 45, 20, 20, "-", () -> shiftBasepos(-1, 0, 0));
	private IButton buttonBaseposXIncrease = BuildGuide.widgetHandler.createButton(285, 45, 20, 20, "+", () -> shiftBasepos(1, 0, 0));
	private IButton buttonBaseposYDecrease = BuildGuide.widgetHandler.createButton(185, 65, 20, 20, "-", () -> shiftBasepos(0, -1, 0));
	private IButton buttonBaseposYIncrease = BuildGuide.widgetHandler.createButton(285, 65, 20, 20, "+", () -> shiftBasepos(0, 1, 0));
	private IButton buttonBaseposZDecrease = BuildGuide.widgetHandler.createButton(185, 85, 20, 20, "-", () -> shiftBasepos(0, 0, -1));
	private IButton buttonBaseposZIncrease = BuildGuide.widgetHandler.createButton(285, 85, 20, 20, "+", () -> shiftBasepos(0, 0, 1));
	private ITextField textFieldX = BuildGuide.widgetHandler.createTextField(205, 45, 50, 20, "");;
	private ITextField textFieldY = BuildGuide.widgetHandler.createTextField(205, 65, 50, 20, "");;
	private ITextField textFieldZ = BuildGuide.widgetHandler.createTextField(205, 85, 50, 20, "");;
	private IButton buttonSetX = BuildGuide.widgetHandler.createButton(255, 45, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldX.getTextValue());
			BuildGuide.stateManager.getState().setBaseposX(newval);
		}catch(NumberFormatException e) {
			textFieldX.setTextColour(0xFF0000);
		}
	});;
	private IButton buttonSetY = BuildGuide.widgetHandler.createButton(255, 65, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldY.getTextValue());
			BuildGuide.stateManager.getState().setBaseposY(newval);
		}catch(NumberFormatException e) {
			textFieldY.setTextColour(0xFF0000);
		}
	});;
	private IButton buttonSetZ = BuildGuide.widgetHandler.createButton(255, 85, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getTextValue());
			BuildGuide.stateManager.getState().setBaseposZ(newval);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColour(0xFF0000);
		}
	});;
	
	public BuildGuideScreen() {
		super("screen.buildguide.title");
	}
	
	public void init() {
		super.init();
		
		BuildGuide.stateManager.getState().initCheck();
		
		buttonClose = BuildGuide.widgetHandler.createButton(wrapper.getWidth() - 20, 0, 20, 20, "X", () -> BuildGuide.screenHandler.showScreen(null));
		
		if(!BuildGuide.stateManager.getState().isShapeAvailable()) {
			buttonBasepos.setActive(false);
			buttonBaseposXDecrease.setActive(false);
			buttonBaseposXIncrease.setActive(false);
			buttonBaseposYDecrease.setActive(false);
			buttonBaseposYIncrease.setActive(false);
			buttonBaseposZDecrease.setActive(false);
			buttonBaseposZIncrease.setActive(false);
			buttonSetX.setActive(false);
			buttonSetY.setActive(false);
			buttonSetZ.setActive(false);
		}
		
		addButton(buttonClose);
		if(!BuildGuide.stateManager.getState().propertyAdvancedMode.value) {
			addButton(buttonShapePrevious);
			addButton(buttonShapeNext);
		}else {
			addButton(buttonShapeList);
		}
		addButton(buttonBasepos);
		addButton(buttonVisualisation);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		textFieldX.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShape().basepos.x : "-");
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShape().basepos.y : "-");
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShape().basepos.z : "-");
		textFieldZ.setTextColour(0xFFFFFF);
		
		addTextField(textFieldX);
		addTextField(textFieldY);
		addTextField(textFieldZ);
		
		BuildGuide.stateManager.getState().propertyEnable.setSlot(-4);
		addProperty(BuildGuide.stateManager.getState().propertyEnable);
		BuildGuide.stateManager.getState().propertyAdvancedMode.setSlot(-2);
		addProperty(BuildGuide.stateManager.getState().propertyAdvancedMode);
		
		if(BuildGuide.stateManager.getState().propertyAdvancedMode.value) {
			for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}else {
			for(Shape s: BuildGuide.stateManager.getState().simpleModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}
		
		if(BuildGuide.stateManager.getState().isShapeAvailable()) BuildGuide.stateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	public void render() {
		super.render();
		drawShadowCentred(title, wrapper.getWidth() / 2, 5, 0xFFFFFF);
		drawShadowCentred(titleGlobalProperties, 80, 15, 0xFFFFFF);
		drawShadowCentred(titleShapeProperties, 80, 115, 0xFFFFFF);
		drawShadowCentred(titleBasepos, 245, 15, 0xFFFFFF);
		drawShadowCentred(titleNumberOfBlocks, 355, 15, 0xFFFFFF);
		
		int n = BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		drawShadowCentred("" + n, 355, 30, 0xFFFFFF);
		drawShadowCentred("(" + (n / 64) + " x 64 + " + (n % 64) + ")", 355, 45, 0xFFFFFF);
		
		int colourFraction = (int) Math.max(Math.min(BuildGuide.stateManager.getState().getCurrentShape().getHowLongAgoCompletedMillis() * 0xFF / 1000, 0xFF), 0);
		String progressIndicatorPart = "";
		if(!BuildGuide.stateManager.getState().getCurrentShape().ready) {
			System.out.println("not ready");
			long time = System.currentTimeMillis();
			progressIndicatorPart = " " + progressIndicator[(int) ((time / 100) % progressIndicator.length)];
		}
		drawShadow(textShape, 5, 30, 0xFFFFFF);
		String shapeName = (BuildGuide.stateManager.getState().isShapeAvailable() && !BuildGuide.stateManager.getState().getCurrentShape().visible ? "\247m" : "") + (BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().getTranslatedName() : BuildGuide.screenHandler.translate("shape.buildguide.none")) + progressIndicatorPart;
		drawShadowCentred(shapeName, 110, 30, BuildGuide.stateManager.getState().getCurrentShape().error ? 0xFF0000 : (0x00FF00 + colourFraction * 0x010001));
		
		drawShadow("X", 170, 50, 0xFFFFFF);
		drawShadow("Y", 170, 70, 0xFFFFFF);
		drawShadow("Z", 170, 90, 0xFFFFFF);
	}
	
	private void updateShape(int di) {
		BuildGuide.stateManager.getState().getCurrentShape().onDeselectedInGUI();
		
		BuildGuide.stateManager.getState().iSimple = Math.floorMod(BuildGuide.stateManager.getState().iSimple + di, BuildGuide.stateManager.getState().simpleModeShapes.length);
		
		BuildGuide.stateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	private void setBasepos() {
		BuildGuide.stateManager.getState().resetBasepos();
		textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.x);
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.y);
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.z);
		textFieldZ.setTextColour(0xFFFFFF);
	}
	
	private void shiftBasepos(int dx, int dy, int dz) {
		BuildGuide.stateManager.getState().shiftBasepos(dx, dy, dz);
		if(dx != 0) {
			textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.x);
			textFieldX.setTextColour(0xFFFFFF);
		}
		if(dy != 0) {
			textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.y);
			textFieldY.setTextColour(0xFFFFFF);
		}
		if(dz != 0) {
			textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.z);
			textFieldZ.setTextColour(0xFFFFFF);
		}
	}
}
