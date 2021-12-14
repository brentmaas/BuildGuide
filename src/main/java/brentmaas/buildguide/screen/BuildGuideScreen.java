package brentmaas.buildguide.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class BuildGuideScreen extends PropertyScreen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private Button buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private Button buttonShapePrevious = new Button(60, 25, 20, 20, new TextComponent("<-"), button -> updateShape(-1));
	private Button buttonShapeNext = new Button(140, 25, 20, 20, new TextComponent("->"), button -> updateShape(1));
	private Button buttonShapelist = new Button(140, 25, 20, 20, new TextComponent("..."), button -> Minecraft.getInstance().setScreen(new ShapelistScreen()));
	private Button buttonBasepos = new Button(185, 25, 120, 20, new TranslatableComponent("screen.buildguide.setbasepos"), button -> StateManager.getState().resetBasepos());
	private Button buttonVisualisation = new Button(0, 65, 160, 20, new TranslatableComponent("screen.buildguide.visualisation"), button -> Minecraft.getInstance().setScreen(new VisualisationScreen()));
	//It's better off as custom buttons instead of PropertyInt
	private Button buttonBaseposXDecrease = new Button(185, 45, 20, 20, new TextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(285, 45, 20, 20, new TextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(185, 65, 20, 20, new TextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(285, 65, 20, 20, new TextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(185, 85, 20, 20, new TextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(285, 85, 20, 20, new TextComponent("+"), button -> shiftBasePos(0, 0, 1));
	private EditBox textFieldX;
	private EditBox textFieldY;
	private EditBox textFieldZ;
	private Button buttonSetX = new Button(255, 45, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getValue());
			StateManager.getState().setBaseposX(newval);
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(255, 65, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getValue());
			StateManager.getState().setBaseposY(newval);
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(255, 85, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getValue());
			StateManager.getState().setBaseposZ(newval);
			textFieldZ.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColor(0xFF0000);
		}
	});
	
	public BuildGuideScreen() {
		super(new TranslatableComponent("screen.buildguide.title"));
	}
	
	@Override
	protected void init() {
		titleGlobalProperties = new TranslatableComponent("screen.buildguide.globalproperties").getString();
		titleShapeProperties = new TranslatableComponent("screen.buildguide.shapeproperties").getString();
		titleBasepos = new TranslatableComponent("screen.buildguide.basepos").getString();
		titleNumberOfBlocks = new TranslatableComponent("screen.buildguide.numberofblocks").getString();
		textShape = new TranslatableComponent("screen.buildguide.shape").getString();
		
		if(StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos == null) { //Very likely the first time opening, so basepos and shapes haven't been properly set up yet
			StateManager.getState().resetBasepos();
			for(Shape shape: StateManager.getState().simpleModeShapes) {
				shape.update();
			}
			//Advanced mode shapes should be empty
		}
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new TextComponent("X"), button -> Minecraft.getInstance().setScreen(null));
		
		if(!StateManager.getState().isShapeAvailable()) {
			buttonBasepos.active = false;
			buttonBaseposXDecrease.active = false;
			buttonBaseposXIncrease.active = false;
			buttonBaseposYDecrease.active = false;
			buttonBaseposYIncrease.active = false;
			buttonBaseposZDecrease.active = false;
			buttonBaseposZIncrease.active = false;
			buttonSetX.active = false;
			buttonSetY.active = false;
			buttonSetZ.active = false;
		}
		
		addRenderableWidget(buttonClose);
		if(!StateManager.getState().propertyAdvancedMode.value) {
			addRenderableWidget(buttonShapePrevious);
			addRenderableWidget(buttonShapeNext);
		}else {
			addRenderableWidget(buttonShapelist);
		}
		addRenderableWidget(buttonBasepos);
		addRenderableWidget(buttonVisualisation);
		addRenderableWidget(buttonBaseposXDecrease);
		addRenderableWidget(buttonBaseposXIncrease);
		addRenderableWidget(buttonBaseposYDecrease);
		addRenderableWidget(buttonBaseposYIncrease);
		addRenderableWidget(buttonBaseposZDecrease);
		addRenderableWidget(buttonBaseposZIncrease);
		addRenderableWidget(buttonSetX);
		addRenderableWidget(buttonSetY);
		addRenderableWidget(buttonSetZ);
		
		textFieldX = new EditBox(font, 205, 45, 50, 20, new TextComponent(""));
		textFieldX.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldX);
		textFieldY = new EditBox(font, 205, 65, 50, 20, new TextComponent(""));
		textFieldY.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldY);
		textFieldZ = new EditBox(font, 205, 85, 50, 20, new TextComponent(""));
		textFieldZ.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldZ);
		
		addProperty(StateManager.getState().propertyEnable);
		addProperty(StateManager.getState().propertyAdvancedMode);
		
		if(StateManager.getState().propertyAdvancedMode.value) {
			for(Shape s: StateManager.getState().advancedModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}else {
			for(Shape s: StateManager.getState().simpleModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}
		
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawShadow(matrixStack, title.getString(), (width - font.width(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawShadow(matrixStack, titleGlobalProperties, (160 - font.width(titleGlobalProperties)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleShapeProperties, (160 - font.width(titleShapeProperties)) / 2, 115, 0xFFFFFF);
		font.drawShadow(matrixStack, titleBasepos, 185 + (120 - font.width(titleBasepos)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleNumberOfBlocks, 305 + (100 - font.width(titleNumberOfBlocks)) / 2, 15, 0xFFFFFF);
		
		int n = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		String numberOfBlocks = "" + n;
		String numberOfStacks = "(" + (n / 64) + " x 64 + " + (n % 64) + ")";
		font.drawShadow(matrixStack, numberOfBlocks, 305 + (100 - font.width(numberOfBlocks)) / 2, 30, 0xFFFFFF);
		font.drawShadow(matrixStack, numberOfStacks, 305 + (100 - font.width(numberOfStacks)) / 2, 45, 0xFFFFFF);
		
		font.drawShadow(matrixStack, textShape, 5, 30, 0xFFFFFF);
		String shapeName = (StateManager.getState().isShapeAvailable() && !StateManager.getState().getCurrentShape().visible ? "\247m" : "") + (StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getTranslatedName() : new TranslatableComponent("shape.buildguide.none").getString());
		font.drawShadow(matrixStack, shapeName, 80 + (60 - font.width(shapeName)) / 2, 30, 0xFFFFFF);
		
		font.drawShadow(matrixStack, "X", 170, 50, 0xFFFFFF);
		font.drawShadow(matrixStack, "Y", 170, 70, 0xFFFFFF);
		font.drawShadow(matrixStack, "Z", 170, 90, 0xFFFFFF);
		
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void updateShape(int di) {
		StateManager.getState().getCurrentShape().onDeselectedInGUI();
		
		StateManager.getState().iSimple = Math.floorMod(StateManager.getState().iSimple + di, StateManager.getState().simpleModeShapes.length);
		
		StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		StateManager.getState().shiftBasepos(dx, dy, dz);
		textFieldX.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.x);
		textFieldY.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.y);
		textFieldZ.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.z);
		textFieldX.setTextColor(0xFFFFFF);
		textFieldY.setTextColor(0xFFFFFF);
		textFieldZ.setTextColor(0xFFFFFF);
	}
}
