package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.Property;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.shape.Shape;

public class BuildGuideScreen extends PropertyScreen{
	private String titleGlobalProperties = BuildGuide.screenHandler.translate("screen.buildguide.globalproperties");
	private String titleShapeProperties = BuildGuide.screenHandler.translate("screen.buildguide.shapeproperties");
	private String titleOrigin = BuildGuide.screenHandler.translate("screen.buildguide.origin");
	private String titleNumberOfBlocks = BuildGuide.screenHandler.translate("screen.buildguide.numberofblocks");
	private String textShape = BuildGuide.screenHandler.translate("screen.buildguide.shape");
	
	private static final String[] progressIndicator = {"|", "/", "-" , "\\"};
	
	private IButton buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private IButton buttonShapePrevious = BuildGuide.widgetHandler.createButton(60, 25, 20, 20, "<-", () -> updateShape(-1));
	private IButton buttonShapeNext = BuildGuide.widgetHandler.createButton(140, 25, 20, 20, "->", () -> updateShape(1));
	private IButton buttonShapeList = BuildGuide.widgetHandler.createButton(140, 25, 20, 20, "...", () -> BuildGuide.screenHandler.showScreen(new ShapelistScreen()));
	private IButton buttonOrigin = BuildGuide.widgetHandler.createButton(185, 25, 120, 20, BuildGuide.screenHandler.translate("screen.buildguide.setorigin"), () -> setOrigin());;
	private IButton buttonVisualisation = BuildGuide.widgetHandler.createButton(0, 65, 160, 20, BuildGuide.screenHandler.translate("screen.buildguide.visualisation"), () -> BuildGuide.screenHandler.showScreen(new VisualisationScreen()));
	//It's better off as custom buttons instead of PropertyInt
	private IButton buttonOriginXDecrease = BuildGuide.widgetHandler.createButton(185, 45, 20, 20, "-", () -> shiftOrigin(-1, 0, 0));
	private IButton buttonOriginXIncrease = BuildGuide.widgetHandler.createButton(285, 45, 20, 20, "+", () -> shiftOrigin(1, 0, 0));
	private IButton buttonOriginYDecrease = BuildGuide.widgetHandler.createButton(185, 65, 20, 20, "-", () -> shiftOrigin(0, -1, 0));
	private IButton buttonOriginYIncrease = BuildGuide.widgetHandler.createButton(285, 65, 20, 20, "+", () -> shiftOrigin(0, 1, 0));
	private IButton buttonOriginZDecrease = BuildGuide.widgetHandler.createButton(185, 85, 20, 20, "-", () -> shiftOrigin(0, 0, -1));
	private IButton buttonOriginZIncrease = BuildGuide.widgetHandler.createButton(285, 85, 20, 20, "+", () -> shiftOrigin(0, 0, 1));
	private ITextField textFieldX = BuildGuide.widgetHandler.createTextField(205, 45, 50, 20, "");;
	private ITextField textFieldY = BuildGuide.widgetHandler.createTextField(205, 65, 50, 20, "");;
	private ITextField textFieldZ = BuildGuide.widgetHandler.createTextField(205, 85, 50, 20, "");;
	private IButton buttonSetX = BuildGuide.widgetHandler.createButton(255, 45, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldX.getTextValue());
			BuildGuide.stateManager.getState().setOriginX(newval);
		}catch(NumberFormatException e) {
			textFieldX.setTextColour(0xFF0000);
		}
	});;
	private IButton buttonSetY = BuildGuide.widgetHandler.createButton(255, 65, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldY.getTextValue());
			BuildGuide.stateManager.getState().setOriginY(newval);
		}catch(NumberFormatException e) {
			textFieldY.setTextColour(0xFF0000);
		}
	});;
	private IButton buttonSetZ = BuildGuide.widgetHandler.createButton(255, 85, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getTextValue());
			BuildGuide.stateManager.getState().setOriginZ(newval);
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
			buttonOrigin.setActive(false);
			buttonOriginXDecrease.setActive(false);
			buttonOriginXIncrease.setActive(false);
			buttonOriginYDecrease.setActive(false);
			buttonOriginYIncrease.setActive(false);
			buttonOriginZDecrease.setActive(false);
			buttonOriginZIncrease.setActive(false);
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
		addButton(buttonOrigin);
		addButton(buttonVisualisation);
		addButton(buttonOriginXDecrease);
		addButton(buttonOriginXIncrease);
		addButton(buttonOriginYDecrease);
		addButton(buttonOriginYIncrease);
		addButton(buttonOriginZDecrease);
		addButton(buttonOriginZIncrease);
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		textFieldX.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShape().origin.x : "-");
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShape().origin.y : "-");
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShape().origin.z : "-");
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
		drawShadowCentred(titleOrigin, 245, 15, 0xFFFFFF);
		drawShadowCentred(titleNumberOfBlocks, 355, 15, 0xFFFFFF);
		
		int n = BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		drawShadowCentred("" + n, 355, 30, 0xFFFFFF);
		drawShadowCentred("(" + (n / 64) + " x 64 + " + (n % 64) + ")", 355, 45, 0xFFFFFF);
		
		int colourFraction = (int) Math.max(Math.min((BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().getHowLongAgoCompletedMillis() : 2000) * 0xFF / 1000, 0xFF), 0);
		String progressIndicatorPart = "";
		if(BuildGuide.stateManager.getState().isShapeAvailable() && !BuildGuide.stateManager.getState().getCurrentShape().ready) {
			long time = System.currentTimeMillis();
			progressIndicatorPart = " " + progressIndicator[(int) ((time / 100) % progressIndicator.length)];
		}
		drawShadow(textShape, 5, 30, 0xFFFFFF);
		String shapeName = (BuildGuide.stateManager.getState().isShapeAvailable() && !BuildGuide.stateManager.getState().getCurrentShape().visible ? "\247m" : "") + (BuildGuide.stateManager.getState().isShapeAvailable() ? BuildGuide.stateManager.getState().getCurrentShape().getTranslatedName() : BuildGuide.screenHandler.translate("shape.buildguide.none")) + progressIndicatorPart;
		drawShadowCentred(shapeName, 110, 30, BuildGuide.stateManager.getState().isShapeAvailable() && BuildGuide.stateManager.getState().getCurrentShape().error ? 0xFF0000 : (0x00FF00 + colourFraction * 0x010001));
		
		drawShadow("X", 170, 50, 0xFFFFFF);
		drawShadow("Y", 170, 70, 0xFFFFFF);
		drawShadow("Z", 170, 90, 0xFFFFFF);
	}
	
	private void updateShape(int di) {
		BuildGuide.stateManager.getState().getCurrentShape().onDeselectedInGUI();
		
		BuildGuide.stateManager.getState().iSimple = Math.floorMod(BuildGuide.stateManager.getState().iSimple + di, BuildGuide.stateManager.getState().simpleModeShapes.length);
		
		BuildGuide.stateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	private void setOrigin() {
		BuildGuide.stateManager.getState().resetOrigin();
		textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().origin.x);
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().origin.y);
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().origin.z);
		textFieldZ.setTextColour(0xFFFFFF);
	}
	
	private void shiftOrigin(int dx, int dy, int dz) {
		BuildGuide.stateManager.getState().shiftOrigin(dx, dy, dz);
		if(dx != 0) {
			textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().origin.x);
			textFieldX.setTextColour(0xFFFFFF);
		}
		if(dy != 0) {
			textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().origin.y);
			textFieldY.setTextColour(0xFFFFFF);
		}
		if(dz != 0) {
			textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().origin.z);
			textFieldZ.setTextColour(0xFFFFFF);
		}
	}
}
