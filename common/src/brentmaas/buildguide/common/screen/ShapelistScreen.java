package brentmaas.buildguide.common.screen;

import java.util.Random;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeRegistry;
import brentmaas.buildguide.common.shape.Shape.Basepos;

public class ShapelistScreen extends BaseScreen {
	private String titleNewShape = BuildGuide.screenHandler.translate("screen.buildguide.newshape");
	private String titleShapes = BuildGuide.screenHandler.translate("screen.buildguide.shapes");
	private String titleGlobalBasepos = BuildGuide.screenHandler.translate("screen.buildguide.globalbasepos");
	private String titleVisible = BuildGuide.screenHandler.translate("screen.buildguide.visible");
	private String titleNumberOfBlocks = BuildGuide.screenHandler.translate("screen.buildguide.numberofblocks");
	
	private IShapeList shapeList;
	
	private IButton buttonClose;
	private IButton buttonBack = BuildGuide.widgetHandler.createButton(0, 0, 20, 20, "<-", () -> BuildGuide.screenHandler.showScreen(new BuildGuideScreen()));
	private IButton buttonNewShapePrevious = BuildGuide.widgetHandler.createButton(0, 25, 20, 20, "<-", () -> updateNewShape(-1));
	private IButton buttonNewShapeNext = BuildGuide.widgetHandler.createButton(120, 25, 20, 20, "->", () -> updateNewShape(1));
	private IButton buttonAdd = BuildGuide.widgetHandler.createButton(0, 45, 140, 20, BuildGuide.screenHandler.translate("screen.buildguide.add"), () -> {
		Shape newShape = ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(BuildGuide.stateManager.getState().iAdvancedNew));
		newShape.resetBasepos();
		if(BuildGuide.stateManager.getState().propertyAdvancedModeRandomColours.value) {
			Random random = new Random();
			newShape.colourShapeR = random.nextFloat();
			newShape.colourShapeG = random.nextFloat();
			newShape.colourShapeB = random.nextFloat();
			newShape.colourBaseposR = random.nextFloat();
			newShape.colourBaseposG = random.nextFloat();
			newShape.colourBaseposB = random.nextFloat();
		}
		newShape.update();
		BuildGuide.stateManager.getState().advancedModeShapes.add(newShape);
		shapeList.addEntry(BuildGuide.stateManager.getState().advancedModeShapes.size() - 1);
		
		checkActive();
	});
	private ICheckboxRunnableButton buttonVisible = BuildGuide.widgetHandler.createCheckbox(120, 65, 20, 20, "", true, false, () -> setShapeVisibility());
	private IButton buttonDelete = BuildGuide.widgetHandler.createButton(0, 85, 140, 20, BuildGuide.screenHandler.translate("screen.buildguide.delete"), () -> {
		if(shapeList.getSelected() != null){
			BuildGuide.stateManager.getState().advancedModeShapes.remove(shapeList.getSelected().getShapeId());
			shapeList.removeEntry(shapeList.getSelected());
		}
		
		checkActive();
	});
	private IButton buttonGlobalBasepos = BuildGuide.widgetHandler.createButton(0, 125, 140, 20, BuildGuide.screenHandler.translate("screen.buildguide.setglobalbasepos"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) setGlobalBasepos();
	});
	//TODO World manager button
	private IButton buttonBaseposXDecrease = BuildGuide.widgetHandler.createButton(20, 145, 20, 20, "-", () -> shiftGlobalBasepos(-1, 0, 0));
	private IButton buttonBaseposXIncrease = BuildGuide.widgetHandler.createButton(120, 145, 20, 20, "+", () -> shiftGlobalBasepos(1, 0, 0));
	private IButton buttonBaseposYDecrease = BuildGuide.widgetHandler.createButton(20, 165, 20, 20, "-", () -> shiftGlobalBasepos(0, -1, 0));
	private IButton buttonBaseposYIncrease = BuildGuide.widgetHandler.createButton(120, 165, 20, 20, "+", () -> shiftGlobalBasepos(0, 1, 0));
	private IButton buttonBaseposZDecrease = BuildGuide.widgetHandler.createButton(20, 185, 20, 20, "-", () -> shiftGlobalBasepos(0, 0, -1));
	private IButton buttonBaseposZIncrease = BuildGuide.widgetHandler.createButton(120, 185, 20, 20, "+", () -> shiftGlobalBasepos(0, 0, 1));
	private ITextField textFieldX = BuildGuide.widgetHandler.createTextField(40, 145, 50, 20, "");
	private ITextField textFieldY = BuildGuide.widgetHandler.createTextField(40, 165, 50, 20, "");
	private ITextField textFieldZ = BuildGuide.widgetHandler.createTextField(40, 185, 50, 20, "");
	private IButton buttonSetX = BuildGuide.widgetHandler.createButton(90, 145, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldX.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShape().basepos.x;
			for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) {
				s.shiftBasepos(delta, 0, 0);
			}
			textFieldX.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetY = BuildGuide.widgetHandler.createButton(90, 165, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldY.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShape().basepos.y;
			for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, delta, 0);
			}
			textFieldY.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetZ = BuildGuide.widgetHandler.createButton(90, 185, 30, 20, BuildGuide.screenHandler.translate("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShape().basepos.z;
			for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, 0, delta);
			}
			textFieldZ.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColour(0xFF0000);
		}
	});
	
	public ShapelistScreen() {
		super(BuildGuide.screenHandler.translate("screen.buildguide.shapelist"));
	}
	
	public void init() {
		buttonClose = BuildGuide.widgetHandler.createButton(wrapper.getWidth() - 20, 0, 20, 20, "X", () -> BuildGuide.screenHandler.showScreen(null));
		
		checkActive();
		
		addButton(buttonClose);
		addButton(buttonBack);
		addButton(buttonNewShapePrevious);
		addButton(buttonNewShapeNext);
		addButton(buttonAdd);
		addCheckbox(buttonVisible);
		addButton(buttonDelete);
		addButton(buttonGlobalBasepos);
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
		
		shapeList = BuildGuide.widgetHandler.createShapelist(150, 300, 25, wrapper.getHeight(), 20, () -> {
			if(BuildGuide.stateManager.getState().isShapeAvailable()) {
				textFieldX.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.x);
				textFieldY.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.y);
				textFieldZ.setTextValue("" + BuildGuide.stateManager.getState().getCurrentShape().basepos.z);
			}else {
				textFieldX.setTextValue("-");
				textFieldY.setTextValue("-");
				textFieldZ.setTextValue("-");
			}
			textFieldX.setTextColour(0xFFFFFF);
			textFieldY.setTextColour(0xFFFFFF);
			textFieldZ.setTextColour(0xFFFFFF);
			if(BuildGuide.stateManager.getState().isShapeAvailable()) buttonVisible.setChecked(BuildGuide.stateManager.getState().getCurrentShape().visible);
		});
		
		addShapeList(shapeList);
	}
	
	public boolean isPauseScreen() {
		return false;
	}
	
	public void render() {
		drawShadowCentred(title, wrapper.getWidth() / 2, 5, 0xFFFFFF);
		drawShadowCentred(titleNewShape, 70, 15, 0xFFFFFF);
		drawShadowCentred(titleShapes, 220, 15, 0xFFFFFF);
		drawShadowCentred(titleGlobalBasepos, 70, 115, 0xFFFFFF);
		drawShadowCentred(titleNumberOfBlocks, 355, 15, 0xFFFFFF);
		
		drawShadowCentred(BuildGuide.screenHandler.translate(ShapeRegistry.getTranslationKeys().get(BuildGuide.stateManager.getState().iAdvancedNew)), 70, 30, 0xFFFFFF);
		
		drawShadow(titleVisible, 5, 70, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		drawShadow("X", 5, 150, 0xFFFFFF);
		drawShadow("Y", 5, 170, 0xFFFFFF);
		drawShadow("Z", 5, 190, 0xFFFFFF);
		
		int n = 0;
		for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) {
			if(s.visible) n += s.getNumberOfBlocks();
		}
		drawShadowCentred("" + n, 355, 30, 0xFFFFFF);
		drawShadowCentred("(" + (n / 64) + " x 64 + " + (n % 64) + ")", 355, 45, 0xFFFFFF);
	}
	
	private void updateNewShape(int di) {
		BuildGuide.stateManager.getState().iAdvancedNew = Math.floorMod(BuildGuide.stateManager.getState().iAdvancedNew + di, ShapeRegistry.getNumberOfShapes());
	}
	
	private void shiftGlobalBasepos(int dx, int dy, int dz) {
		for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) {
			s.shiftBasepos(dx, dy, dz);
		}
		if(BuildGuide.stateManager.getState().isShapeAvailable()) {
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
	
	private void setGlobalBasepos() {
		Basepos pos = BuildGuide.shapeHandler.getPlayerPosition();
		int deltaX = pos.x - BuildGuide.stateManager.getState().getCurrentShape().basepos.x;
		int deltaY = pos.y - BuildGuide.stateManager.getState().getCurrentShape().basepos.y;
		int deltaZ = pos.z - BuildGuide.stateManager.getState().getCurrentShape().basepos.z;
		shiftGlobalBasepos(deltaX, deltaY, deltaZ);
	}
	
	private void setShapeVisibility() {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) BuildGuide.stateManager.getState().getCurrentShape().visible = buttonVisible.isSelected();
	}
	
	private void checkActive() {
		if(!BuildGuide.stateManager.getState().isShapeAvailable()) {
			buttonVisible.setActive(false);
			buttonDelete.setActive(false);
			buttonGlobalBasepos.setActive(false);
			buttonBaseposXDecrease.setActive(false);
			buttonBaseposXIncrease.setActive(false);
			buttonBaseposYDecrease.setActive(false);
			buttonBaseposYIncrease.setActive(false);
			buttonBaseposZDecrease.setActive(false);
			buttonBaseposZIncrease.setActive(false);
			buttonSetX.setActive(false);
			buttonSetY.setActive(false);
			buttonSetZ.setActive(false);
		}else {
			buttonVisible.setActive(true);
			buttonDelete.setActive(true);
			buttonGlobalBasepos.setActive(true);
			buttonBaseposXDecrease.setActive(true);
			buttonBaseposXIncrease.setActive(true);
			buttonBaseposYDecrease.setActive(true);
			buttonBaseposYIncrease.setActive(true);
			buttonBaseposZDecrease.setActive(true);
			buttonBaseposZIncrease.setActive(true);
			buttonSetX.setActive(true);
			buttonSetY.setActive(true);
			buttonSetZ.setActive(true);
		}
	}
}
