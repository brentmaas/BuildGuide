package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.Property;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.shape.Shape;

public class ShapeScreen extends BaseScreen{
	private String titleShapeProperties = BuildGuide.screenHandler.translate("screen.buildguide.shapeproperties");
	private String titleOrigin = BuildGuide.screenHandler.translate("screen.buildguide.origin");
	private String titleShape = BuildGuide.screenHandler.translate("screen.buildguide.shape");
	
	//It's better off as custom buttons instead of PropertyEnum
	private IButton buttonShapePrevious = BuildGuide.widgetHandler.createButton(5, 70, 20, 20, "<-", () -> updateShape(-1));
	private IButton buttonShapeNext = BuildGuide.widgetHandler.createButton(145, 70, 20, 20, "->", () -> updateShape(1));
	private IButton buttonOrigin = BuildGuide.widgetHandler.createButton(5, 115, 160, 20, BuildGuide.screenHandler.translate("screen.buildguide.setorigin"), () -> setOrigin());
	//It's better off as custom buttons instead of PropertyInt
	private IButton buttonOriginXDecrease = BuildGuide.widgetHandler.createButton(25, 135, 20, 20, "-", () -> shiftOrigin(-1, 0, 0));
	private IButton buttonOriginXIncrease = BuildGuide.widgetHandler.createButton(145, 135, 20, 20, "+", () -> shiftOrigin(1, 0, 0));
	private IButton buttonOriginYDecrease = BuildGuide.widgetHandler.createButton(25, 155, 20, 20, "-", () -> shiftOrigin(0, -1, 0));
	private IButton buttonOriginYIncrease = BuildGuide.widgetHandler.createButton(145, 155, 20, 20, "+", () -> shiftOrigin(0, 1, 0));
	private IButton buttonOriginZDecrease = BuildGuide.widgetHandler.createButton(25, 175, 20, 20, "-", () -> shiftOrigin(0, 0, -1));
	private IButton buttonOriginZIncrease = BuildGuide.widgetHandler.createButton(145, 175, 20, 20, "+", () -> shiftOrigin(0, 0, 1));
	private ITextField textFieldX = BuildGuide.widgetHandler.createTextField(45, 135, 70, 20, "");
	private ITextField textFieldY = BuildGuide.widgetHandler.createTextField(45, 155, 70, 20, "");
	private ITextField textFieldZ = BuildGuide.widgetHandler.createTextField(45, 175, 70, 20, "");
	private IButton buttonSetX = BuildGuide.widgetHandler.createButton(115, 135, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldX.getTextValue());
			BuildGuide.stateManager.getState().setOriginX(newval);
		}catch(NumberFormatException e) {
			textFieldX.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetY = BuildGuide.widgetHandler.createButton(115, 155, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldY.getTextValue());
			BuildGuide.stateManager.getState().setOriginY(newval);
		}catch(NumberFormatException e) {
			textFieldY.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetZ = BuildGuide.widgetHandler.createButton(115, 175, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getTextValue());
			BuildGuide.stateManager.getState().setOriginZ(newval);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColour(0xFF0000);
		}
	});
	
	public void init() {
		super.init();
		
		if(!BuildGuide.stateManager.getState().isShapeAvailable()) {
			buttonShapePrevious.setActive(false);
			buttonShapeNext.setActive(false);
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
		
		textFieldX.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x : "-");
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y : "-");
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z : "-");
		textFieldZ.setTextColour(0xFFFFFF);
		
		addWidget(buttonShapePrevious);
		addWidget(buttonShapeNext);
		addWidget(buttonOrigin);
		addWidget(buttonOriginXDecrease);
		addWidget(textFieldX);
		addWidget(buttonSetX);
		addWidget(buttonOriginXIncrease);
		addWidget(buttonOriginYDecrease);
		addWidget(textFieldY);
		addWidget(buttonSetY);
		addWidget(buttonOriginYIncrease);
		addWidget(buttonOriginZDecrease);
		addWidget(textFieldZ);
		addWidget(buttonSetZ);
		addWidget(buttonOriginZIncrease);
		
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
			for(Shape shape: BuildGuide.stateManager.getState().getCurrentShapeSet().shapes) {
				if(shape != null) {
					shape.onDeselectedInGUI();
					addShapeProperties(shape);
				}
			}
			BuildGuide.stateManager.getState().getCurrentShape().onSelectedInGUI();
		}
	}
	
	public void render() {
		super.render();
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleShape, 85, 55, 0xFFFFFF);
		drawShadowCentred(BuildGuide.screenHandler.getFormattedShapeName(BuildGuide.stateManager.getState().getCurrentShapeSet()), 85, 75, BuildGuide.screenHandler.getShapeProgressColour(BuildGuide.stateManager.getState().getCurrentShape()));
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleOrigin, 85, 100, 0xFFFFFF);
		drawShadowLeft("X", 10, 140, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		drawShadowLeft("Y", 10, 160, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		drawShadowLeft("Z", 10, 180, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleShapeProperties, 285, 55, 0xFFFFFF);
	}
	
	private void addShapeProperties(Shape shape) {
		for(Property<?> p: shape.properties) {
			addProperty(p);
		}
	}
	
	private void updateShape(int di) {
		BuildGuide.stateManager.getState().getCurrentShape().onDeselectedInGUI();
		
		BuildGuide.stateManager.getState().shiftShape(di);
		if(!BuildGuide.stateManager.getState().getCurrentShapeSet().isShapeAvailable()) {
			addShapeProperties(BuildGuide.stateManager.getState().getCurrentShape());
		}
		
		BuildGuide.stateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	private void setOrigin() {
		BuildGuide.stateManager.getState().resetOrigin();
		BaseScreen.shouldUpdatePersistence = true;
		textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x);
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y);
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z);
		textFieldZ.setTextColour(0xFFFFFF);
	}
	
	private void shiftOrigin(int dx, int dy, int dz) {
		BuildGuide.stateManager.getState().shiftOrigin(dx, dy, dz);
		if(dx != 0) {
			textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x);
			textFieldX.setTextColour(0xFFFFFF);
		}
		if(dy != 0) {
			textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y);
			textFieldY.setTextColour(0xFFFFFF);
		}
		if(dz != 0) {
			textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z);
			textFieldZ.setTextColour(0xFFFFFF);
		}
	}
}
