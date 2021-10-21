package brentmaas.buildguide.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BuildGuideScreen extends Screen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private Button buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private Button buttonShapePrevious = new Button(60, 40, 20, 20, new StringTextComponent("<-"), button -> updateShape(-1));
	private Button buttonShapeNext = new Button(140, 40, 20, 20, new StringTextComponent("->"), button -> updateShape(1));
	private Button buttonShapelist = new Button(140, 40, 20, 20, new StringTextComponent("..."), button -> Minecraft.getInstance().displayGuiScreen(new ShapelistScreen()));
	private Button buttonBasepos = new Button(200, 40, 120, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> StateManager.getState().resetBasepos());
	private Button buttonColours = new Button(0, 80, 160, 20, new TranslationTextComponent("screen.buildguide.colours"), button -> {
		Minecraft.getInstance().displayGuiScreen(new ColoursScreen());
	});
	//It's better off as custom buttons instead of PropertyInt
	private Button buttonBaseposXDecrease = new Button(200, 60, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(300, 60, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(200, 80, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(300, 80, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(200, 100, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(300, 100, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private Button buttonSetX = new Button(270, 60, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			StateManager.getState().setBaseposX(newval);
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(270, 80, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			StateManager.getState().setBaseposY(newval);
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(270, 100, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
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
		
		textFieldX = new TextFieldWidget(font, 220, 60, 50, 20, new StringTextComponent(""));
		textFieldX.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setTextColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(font, 220, 80, 50, 20, new StringTextComponent(""));
		textFieldY.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setTextColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(font, 220, 100, 50, 20, new StringTextComponent(""));
		textFieldZ.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setTextColor(0xFFFFFF);
		children.add(textFieldZ);
		
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		properties.add(StateManager.getState().propertyRender);
		properties.add(StateManager.getState().propertyDepthTest);
		properties.add(StateManager.getState().propertyAdvancedMode);
		
		for(Property<?> p: properties) {
			p.addToBuildGuideScreen(this);
		}
		if(StateManager.getState().propertyAdvancedMode.value) {
			for(Shape s: StateManager.getState().advancedModeShapes) {
				for(Property<?> p: s.properties) {
					if(p.mightNeedTextFields()) p.addTextFields(font);
					p.addToBuildGuideScreen(this);
				}
				s.onDeselectedInGUI();
			}
		}else {
			for(Shape s: StateManager.getState().basicModeShapes) {
				for(Property<?> p: s.properties) {
					if(p.mightNeedTextFields()) p.addTextFields(font);
					p.addToBuildGuideScreen(this);
				}
				s.onDeselectedInGUI();
			}
		}
		
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().onSelectedInGUI();
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleGlobalProperties, (160 - font.getStringWidth(titleGlobalProperties)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleShapeProperties, (160 - font.getStringWidth(titleShapeProperties)) / 2, 150, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleBasepos, 160 + (160 - font.getStringWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleNumberOfBlocks, 340 + (100 - font.getStringWidth(titleNumberOfBlocks)) / 2, 25, 0xFFFFFF);
		int n = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getNumberOfBlocks() : 0;
		String numberOfBlocks = "" + n;
		String numberOfStacks = "(" + (n / 64) + " x 64 + " + (n % 64) + ")";
		font.drawStringWithShadow(matrixStack, numberOfBlocks, 340 + (100 - font.getStringWidth(numberOfBlocks)) / 2, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, numberOfStacks, 340 + (100 - font.getStringWidth(numberOfStacks)) / 2, 65, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, textShape, 5, 45, 0xFFFFFF);
		String shapeName = StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().getTranslatedName() : new TranslationTextComponent("shape.buildguide.none").getString();
		font.drawStringWithShadow(matrixStack, shapeName, 80 + (60 - font.getStringWidth(shapeName)) / 2, 45, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, "X", 185, 65, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Y", 185, 85, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Z", 185, 105, 0xFFFFFF);
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
		
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
		if(StateManager.getState().isShapeAvailable()) {
			for(Property<?> p: StateManager.getState().getCurrentShape().properties) {
				p.render(matrixStack, mouseX, mouseY, partialTicks, font);
			}
		}
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
	
	public void addButtonExternal(AbstractButton button) {
		addButton(button);
	}
	
	public void addTextFieldExternal(TextFieldWidget tfw) {
		children.add(tfw);
	}
}
