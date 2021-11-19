package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BuildGuideScreen extends PropertyScreen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private Button buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private Button buttonShapePrevious = new Button(60, 25, 20, 20, new StringTextComponent("<-"), button -> updateShape(-1));
	private Button buttonShapeNext = new Button(140, 25, 20, 20, new StringTextComponent("->"), button -> updateShape(1));
	private Button buttonShapelist = new Button(140, 25, 20, 20, new StringTextComponent("..."), button -> Minecraft.getInstance().displayGuiScreen(new ShapelistScreen()));
	private Button buttonBasepos = new Button(185, 25, 120, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> StateManager.getState().resetBasepos());
	private Button buttonColours = new Button(0, 65, 160, 20, new TranslationTextComponent("screen.buildguide.visualisation"), button -> {
		Minecraft.getInstance().displayGuiScreen(new VisualisationScreen());
	});
	//It's better off as custom buttons instead of PropertyInt
	private Button buttonBaseposXDecrease = new Button(185, 45, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(285, 45, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(185, 65, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(285, 65, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(185, 85, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(285, 85, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private Button buttonSetX = new Button(255, 45, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			StateManager.getState().setBaseposX(newval);
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(255, 65, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			StateManager.getState().setBaseposY(newval);
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(255, 85, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getText());
			StateManager.getState().setBaseposZ(newval);
			textFieldZ.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColor(0xFF0000);
		}
	});
	
	public BuildGuideScreen() {
		super(new TranslationTextComponent("screen.buildguide.title"));
	}
	
	@Override
	protected void init() {
		titleGlobalProperties = new TranslationTextComponent("screen.buildguide.globalproperties").getString();
		titleShapeProperties = new TranslationTextComponent("screen.buildguide.shapeproperties").getString();
		titleBasepos = new TranslationTextComponent("screen.buildguide.basepos").getString();
		titleNumberOfBlocks = new TranslationTextComponent("screen.buildguide.numberofblocks").getString();
		textShape = new TranslationTextComponent("screen.buildguide.shape").getString();
		
		if(StateManager.getState().isShapeAvailable() && StateManager.getState().getCurrentShape().basePos == null) { //Very likely the first time opening, so basepos and shapes haven't been properly set up yet
			StateManager.getState().resetBasepos();
			for(Shape shape: StateManager.getState().basicModeShapes) {
				shape.update();
			}
			//Advanced mode shapes should be empty
		}
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));
		
		if(!StateManager.getState().isShapeAvailable()) {
			buttonBasepos.active = false;
			buttonColours.active = false;
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
		
		addButton(buttonClose);
		if(!StateManager.getState().propertyAdvancedMode.value) {
			addButton(buttonShapePrevious);
			addButton(buttonShapeNext);
		}else {
			addButton(buttonShapelist);
		}
		addButton(buttonBasepos);
		addButton(buttonColours);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		textFieldX = new TextFieldWidget(font, 205, 45, 50, 20, new StringTextComponent(""));
		textFieldX.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setTextColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(font, 205, 65, 50, 20, new StringTextComponent(""));
		textFieldY.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setTextColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(font, 205, 85, 50, 20, new StringTextComponent(""));
		textFieldZ.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setTextColor(0xFFFFFF);
		children.add(textFieldZ);
		
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
			for(Shape s: StateManager.getState().basicModeShapes) {
				for(Property<?> p: s.properties) {
					addProperty(p);
				}
				s.onDeselectedInGUI();
			}
		}
		
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleGlobalProperties, (160 - font.getStringWidth(titleGlobalProperties)) / 2, 15, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleShapeProperties, (160 - font.getStringWidth(titleShapeProperties)) / 2, 115, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleBasepos, 185 + (120 - font.getStringWidth(titleBasepos)) / 2, 15, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleNumberOfBlocks, 305 + (100 - font.getStringWidth(titleNumberOfBlocks)) / 2, 15, 0xFFFFFF);
		int n = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		String numberOfBlocks = "" + n;
		String numberOfStacks = "(" + (n / 64) + " x 64 + " + (n % 64) + ")";
		font.drawStringWithShadow(matrixStack, numberOfBlocks, 305 + (100 - font.getStringWidth(numberOfBlocks)) / 2, 35, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, numberOfStacks, 305 + (100 - font.getStringWidth(numberOfStacks)) / 2, 55, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, textShape, 5, 30, 0xFFFFFF);
		String shapeName = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getTranslatedName() : new TranslationTextComponent("shape.buildguide.none").getString();
		font.drawStringWithShadow(matrixStack, shapeName, 80 + (60 - font.getStringWidth(shapeName)) / 2, 30, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, "X", 170, 50, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Y", 170, 70, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Z", 170, 90, 0xFFFFFF);
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void updateShape(int di) {
		StateManager.getState().getCurrentShape().onDeselectedInGUI();
		
		StateManager.getState().iBasic = Math.floorMod(StateManager.getState().iBasic + di, StateManager.getState().basicModeShapes.length);
		
		StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		StateManager.getState().shiftBasepos(dx, dy, dz);
		textFieldX.setText("" + (int) StateManager.getState().getCurrentShape().basePos.x);
		textFieldY.setText("" + (int) StateManager.getState().getCurrentShape().basePos.y);
		textFieldZ.setText("" + (int) StateManager.getState().getCurrentShape().basePos.z);
		textFieldX.setTextColor(0xFFFFFF);
		textFieldY.setTextColor(0xFFFFFF);
		textFieldZ.setTextColor(0xFFFFFF);
	}
}
