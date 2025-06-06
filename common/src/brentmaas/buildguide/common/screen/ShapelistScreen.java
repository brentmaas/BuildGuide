package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.common.shape.ShapeRegistry;
import brentmaas.buildguide.common.shape.ShapeSet.Origin;

public class ShapelistScreen extends BaseScreen {
	private Translatable titleNewShape = new Translatable("screen.buildguide.newshape");
	private Translatable titleSelectedShape = new Translatable("screen.buildguide.selectedshape");
	private Translatable textVisible = new Translatable("screen.buildguide.visible");
	private Translatable titleGlobalOrigin = new Translatable("screen.buildguide.globalorigin");
	private Translatable titleShapes = new Translatable("screen.buildguide.shapes");
	
	private IShapeList shapeList;
	
	private DropdownOverlayScreen dropdownOverlayScreenNewShapeSelect = new DropdownOverlayScreen(this, 5, 70, 160, 20, ShapeRegistry.getTranslatables(), BuildGuide.stateManager.getState().iShapeNew, (int selected) -> setNewShape(selected));
	private IButton buttonAdd = BuildGuide.widgetHandler.createButton(5, 90, 160, 20, new Translatable("screen.buildguide.add"), () -> {
		BuildGuide.stateManager.getState().pushNewShapeSet();
		BuildGuide.stateManager.getState().getShapeSet(BuildGuide.stateManager.getState().getNumberOfShapeSets() - 1).updateAllShapes();
		shapeList.addEntry(BuildGuide.stateManager.getState().getNumberOfShapeSets() - 1);
		
		checkActive();
	});
	private ICheckboxRunnableButton buttonVisible = BuildGuide.widgetHandler.createCheckbox(5, 135, 20, 20, new Translatable(""), true, false, () -> setShapeVisibility());
	private IButton buttonDelete = BuildGuide.widgetHandler.createButton(5, 155, 160, 20, new Translatable("screen.buildguide.delete"), () -> {
		if(shapeList.getSelected() != null){
			BuildGuide.stateManager.getState().removeShapeSet(shapeList.getSelected().getShapeSetId());
			shapeList.removeEntry(shapeList.getSelected());
		}
		
		checkActive();
	});
	private IButton buttonGlobalOrigin = BuildGuide.widgetHandler.createButton(5, 200, 160, 20, new Translatable("screen.buildguide.setglobalorigin"), () -> {
		if(BuildGuide.stateManager.getState().isShapeAvailable()) setGlobalOrigin();
	});
	private IButton buttonOriginXDecrease = BuildGuide.widgetHandler.createButton(25, 220, 20, 20, new Translatable("-"), () -> shiftGlobalOrigin(-1, 0, 0));
	private IButton buttonOriginXIncrease = BuildGuide.widgetHandler.createButton(145, 220, 20, 20, new Translatable("+"), () -> shiftGlobalOrigin(1, 0, 0));
	private IButton buttonOriginYDecrease = BuildGuide.widgetHandler.createButton(25, 240, 20, 20, new Translatable("-"), () -> shiftGlobalOrigin(0, -1, 0));
	private IButton buttonOriginYIncrease = BuildGuide.widgetHandler.createButton(145, 240, 20, 20, new Translatable("+"), () -> shiftGlobalOrigin(0, 1, 0));
	private IButton buttonOriginZDecrease = BuildGuide.widgetHandler.createButton(25, 260, 20, 20, new Translatable("-"), () -> shiftGlobalOrigin(0, 0, -1));
	private IButton buttonOriginZIncrease = BuildGuide.widgetHandler.createButton(145, 260, 20, 20, new Translatable("+"), () -> shiftGlobalOrigin(0, 0, 1));
	private ITextField textFieldX = BuildGuide.widgetHandler.createTextField(45, 220, 70, 20, "");
	private ITextField textFieldY = BuildGuide.widgetHandler.createTextField(45, 240, 70, 20, "");
	private ITextField textFieldZ = BuildGuide.widgetHandler.createTextField(45, 260, 70, 20, "");
	private IButton buttonSetX = BuildGuide.widgetHandler.createButton(115, 220, 30, 20, new Translatable("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldX.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.x;
			BuildGuide.stateManager.getState().shiftOrigins(delta, 0, 0);
			textFieldX.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetY = BuildGuide.widgetHandler.createButton(115, 240, 30, 20, new Translatable("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldY.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.y;
			BuildGuide.stateManager.getState().shiftOrigins(0, delta, 0);
			textFieldY.setTextColour(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColour(0xFF0000);
		}
	});
	private IButton buttonSetZ = BuildGuide.widgetHandler.createButton(115, 260, 30, 20, new Translatable("screen.buildguide.set"), () -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getTextValue());
			int delta = newval - BuildGuide.stateManager.getState().getCurrentShapeSet().origin.z;
			BuildGuide.stateManager.getState().shiftOrigins(0, 0, delta);
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
		
		addDropdownOverlayScreen(dropdownOverlayScreenNewShapeSelect);
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
			BaseScreen.shouldUpdatePersistence = true;
		});
		
		addWidget(shapeList);
	}
	
	public void render() {
		super.render();
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleNewShape, 85, 55, 0xFFFFFF);
		drawShadowCentred(new Translatable(ShapeRegistry.getTranslationKey(BuildGuide.stateManager.getState().iShapeNew)).toString(), 85, 75, 0xFFFFFF);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleSelectedShape, 85, 120, 0xFFFFFF);
		drawShadowLeft(textVisible.toString(), 30, 140, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleGlobalOrigin, 85, 185, 0xFFFFFF);
		drawShadowLeft("X", 10, 225, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		drawShadowLeft("Y", 10, 245, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		drawShadowLeft("Z", 10, 265, BuildGuide.stateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		drawShadowCentred(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + titleShapes, 250, 55, 0xFFFFFF);
	}
	
	private void setNewShape(int i) {
		BuildGuide.stateManager.getState().iShapeNew = i;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	private void shiftGlobalOrigin(int dx, int dy, int dz) {
		BuildGuide.stateManager.getState().shiftOrigins(dx, dy, dz);
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
