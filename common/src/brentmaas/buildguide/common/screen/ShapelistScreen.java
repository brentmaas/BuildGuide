package brentmaas.buildguide.common.screen;

import java.util.Random;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.shape.ShapeRegistry;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.common.shape.ShapeSet.Origin;

public class ShapelistScreen extends BaseScreen {
	private String titleNewShape = BuildGuide.screenHandler.translate("screen.buildguide.newshape");
	private String titleSelectedShape = BuildGuide.screenHandler.translate("screen.buildguide.selectedshape");
	private String textVisible = BuildGuide.screenHandler.translate("screen.buildguide.visible");
	private String titleNumberOfBlocks = BuildGuide.screenHandler.translate("screen.buildguide.numberofblocks");
	private String titleGlobalOrigin = BuildGuide.screenHandler.translate("screen.buildguide.globalorigin");
	private String titleShapes = BuildGuide.screenHandler.translate("screen.buildguide.shapes");
	
	private IShapeList shapeList;
	
	private IButton buttonNewShapePrevious = BuildGuide.widgetHandler.createButton(5, 70, 20, 20, "<-", () -> updateNewShape(-1));
	private IButton buttonNewShapeNext = BuildGuide.widgetHandler.createButton(145, 70, 20, 20, "->", () -> updateNewShape(1));
	private IButton buttonAdd = BuildGuide.widgetHandler.createButton(5, 90, 160, 20, BuildGuide.screenHandler.translate("screen.buildguide.add"), () -> {
		ShapeSet newShapeSet = new ShapeSet(BuildGuide.stateManager.getState().iShapeNew);
		newShapeSet.resetOrigin();
		if(BuildGuide.stateManager.getState().propertyAdvancedModeRandomColours.value) {
			Random random = new Random();
			newShapeSet.colourShapeR = random.nextFloat();
			newShapeSet.colourShapeG = random.nextFloat();
			newShapeSet.colourShapeB = random.nextFloat();
			newShapeSet.colourOriginR = random.nextFloat();
			newShapeSet.colourOriginG = random.nextFloat();
			newShapeSet.colourOriginB = random.nextFloat();
		}
		newShapeSet.updateAllShapes();
		BuildGuide.stateManager.getState().shapeSets.add(newShapeSet);
		shapeList.addEntry(BuildGuide.stateManager.getState().shapeSets.size() - 1);
		
		checkActive();
	});
	private ICheckboxRunnableButton buttonVisible = BuildGuide.widgetHandler.createCheckbox(5, 135, 20, 20, "", true, false, () -> setShapeVisibility());
	private IButton buttonDelete = BuildGuide.widgetHandler.createButton(5, 155, 160, 20, BuildGuide.screenHandler.translate("screen.buildguide.delete"), () -> {
		if(shapeList.getSelected() != null){
			BuildGuide.stateManager.getState().shapeSets.remove(shapeList.getSelected().getShapeSetId());
			shapeList.removeEntry(shapeList.getSelected());
		}
		
		checkActive();
	});
	private IButton buttonGlobalOrigin = BuildGuide.widgetHandler.createButton(5, 200, 160, 20, BuildGuide.screenHandler.translate("screen.buildguide.setglobalorigin"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) setGlobalOrigin();
	});
	private IButton buttonOriginXDecrease = BuildGuide.widgetHandler.createButton(25, 220, 20, 20, "-", () -> shiftGlobalOrigin(-1, 0, 0));
	private IButton buttonOriginXIncrease = BuildGuide.widgetHandler.createButton(145, 220, 20, 20, "+", () -> shiftGlobalOrigin(1, 0, 0));
	private IButton buttonOriginYDecrease = BuildGuide.widgetHandler.createButton(25, 240, 20, 20, "-", () -> shiftGlobalOrigin(0, -1, 0));
	private IButton buttonOriginYIncrease = BuildGuide.widgetHandler.createButton(145, 240, 20, 20, "+", () -> shiftGlobalOrigin(0, 1, 0));
	private IButton buttonOriginZDecrease = BuildGuide.widgetHandler.createButton(25, 260, 20, 20, "-", () -> shiftGlobalOrigin(0, 0, -1));
	private IButton buttonOriginZIncrease = BuildGuide.widgetHandler.createButton(145, 260, 20, 20, "+", () -> shiftGlobalOrigin(0, 0, 1));
	private ITextField textFieldX = BuildGuide.widgetHandler.createTextField(45, 220, 70, 20, "");
	private ITextField textFieldY = BuildGuide.widgetHandler.createTextField(45, 240, 70, 20, "");
	private ITextField textFieldZ = BuildGuide.widgetHandler.createTextField(45, 260, 70, 20, "");
	private IButton buttonSetX = BuildGuide.widgetHandler.createButton(115, 220, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldX.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x;
			for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) {
				s.shiftOrigin(delta, 0, 0);
			}
			textFieldX.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetY = BuildGuide.widgetHandler.createButton(115, 240, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldY.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y;
			for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) {
				s.shiftOrigin(0, delta, 0);
			}
			textFieldY.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetZ = BuildGuide.widgetHandler.createButton(115, 260, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z;
			for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) {
				s.shiftOrigin(0, 0, delta);
			}
			textFieldZ.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColour(0xFF0000);
		}
	});
	
	public void init() {
		super.init();
		
		checkActive();
		
		textFieldX.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x : "-");
		textFieldX.setTextColour(0xFFFFFF);
		textFieldY.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y : "-");
		textFieldY.setTextColour(0xFFFFFF);
		textFieldZ.setTextValue(BuildGuide.stateManager.getState().isShapeAvailable() ? "" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z : "-");
		textFieldZ.setTextColour(0xFFFFFF);
		
		addWidget(buttonNewShapePrevious);
		addWidget(buttonNewShapeNext);
		addWidget(buttonAdd);
		addWidget(buttonVisible);
		addWidget(buttonDelete);
		addWidget(buttonGlobalOrigin);
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
		
		shapeList = BuildGuide.widgetHandler.createShapelist(180, 325, 70, wrapper.getHeight(), 20, () -> {
			if(BuildGuide.stateManager.getState().isShapeAvailable()) {
				textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x);
				textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y);
				textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z);
			}else {
				textFieldX.setTextValue("-");
				textFieldY.setTextValue("-");
				textFieldZ.setTextValue("-");
			}
			textFieldX.setTextColour(0xFFFFFF);
			textFieldY.setTextColour(0xFFFFFF);
			textFieldZ.setTextColour(0xFFFFFF);
			if(BuildGuide.stateManager.getState().isShapeAvailable()) buttonVisible.setChecked(BuildGuide.stateManager.getState().getCurrentShapeSet().visible);
		});
		
		addWidget(shapeList);
	}
	
	public void render() {
		super.render();
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleNewShape, 85, 55, 0xFFFFFF);
		drawShadowCentred(BuildGuide.screenHandler.translate(ShapeRegistry.getTranslationKeys().get(BuildGuide.stateManager.getState().iShapeNew)), 85, 75, 0xFFFFFF);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleSelectedShape, 85, 120, 0xFFFFFF);
		drawShadow(textVisible, 30, 140, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleNumberOfBlocks, 85, 290, 0xFFFFFF);
		int n = 0;
		for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) {
			if(s.visible) n += s.getShape().getNumberOfBlocks();
		}
		drawShadowCentred(n + " (" + (n / 64) + " x 64 + " + (n % 64) + ")", 85, 305, 0xFFFFFF);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleGlobalOrigin, 85, 185, 0xFFFFFF);
		drawShadow("X", 10, 225, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		drawShadow("Y", 10, 245, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		drawShadow("Z", 10, 265, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleShapes, 250, 55, 0xFFFFFF);
	}
	
	private void updateNewShape(int di) {
		BuildGuide.stateManager.getState().iShapeNew = Math.floorMod(BuildGuide.stateManager.getState().iShapeNew + di, ShapeRegistry.getNumberOfShapes());
	}
	
	private void shiftGlobalOrigin(int dx, int dy, int dz) {
		for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) {
			s.shiftOrigin(dx, dy, dz);
		}
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
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
		} else {
			if(dx != 0) {
				textFieldX.setTextValue("-");
				textFieldX.setTextColour(0xFFFFFF);
			}
			if(dy != 0) {
				textFieldY.setTextValue("-");
				textFieldY.setTextColour(0xFFFFFF);
			}
			if(dz != 0) {
				textFieldZ.setTextValue("-");
				textFieldZ.setTextColour(0xFFFFFF);
			}
		}
	}
	
	private void setGlobalOrigin() {
		Origin pos = BuildGuide.shapeHandler.getPlayerPosition();
		int deltaX = pos.x - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x;
		int deltaY = pos.y - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y;
		int deltaZ = pos.z - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z;
		shiftGlobalOrigin(deltaX, deltaY, deltaZ);
	}
	
	private void setShapeVisibility() {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) BuildGuide.stateManager.getState().getCurrentShapeSet().visible = buttonVisible.isCheckboxSelected();
	}
	
	private void checkActive() {
		if(!BuildGuide.stateManager.getState().isShapeAvailable()) {
			buttonVisible.setActive(false);
			buttonDelete.setActive(false);
			buttonGlobalOrigin.setActive(false);
			buttonOriginXDecrease.setActive(false);
			buttonOriginXIncrease.setActive(false);
			buttonOriginYDecrease.setActive(false);
			buttonOriginYIncrease.setActive(false);
			buttonOriginZDecrease.setActive(false);
			buttonOriginZIncrease.setActive(false);
			buttonSetX.setActive(false);
			buttonSetY.setActive(false);
			buttonSetZ.setActive(false);
		}else {
			buttonVisible.setActive(true);
			buttonDelete.setActive(true);
			buttonGlobalOrigin.setActive(true);
			buttonOriginXDecrease.setActive(true);
			buttonOriginXIncrease.setActive(true);
			buttonOriginYDecrease.setActive(true);
			buttonOriginYIncrease.setActive(true);
			buttonOriginZDecrease.setActive(true);
			buttonOriginZIncrease.setActive(true);
			buttonSetX.setActive(true);
			buttonSetY.setActive(true);
			buttonSetZ.setActive(true);
		}
	}
}
