package brentmaas.buildguide.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.State;
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
	private Button buttonBasepos = new Button(0, 60, 160, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> setBasePos());
	private Button buttonColours = new Button(0, 100, 160, 20, new TranslationTextComponent("screen.buildguide.colours"), button -> {
		Minecraft.getInstance().displayGuiScreen(new ColoursScreen());
	});
	//It's better off as custom buttons instead of PropertyInt
	private Button buttonBaseposXDecrease = new Button(200, 40, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(300, 40, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(200, 60, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(300, 60, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(200, 80, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(300, 80, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private Button buttonSetX = new Button(270, 40, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			BuildGuide.state.basePos = new Vector3d(newval, BuildGuide.state.basePos.y, BuildGuide.state.basePos.z);
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(270, 60, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x, newval, BuildGuide.state.basePos.z);
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(270, 80, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getText());
			BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x, BuildGuide.state.basePos.y, newval);
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
		
		if(BuildGuide.state.basePos == null) { //Very likely the first time opening, so basepos and shapes haven't been properly set up yet
			setBasePos();
			for(Shape shape: BuildGuide.state.shapeStore) shape.update();
		}
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));
		
		addButton(buttonClose);
		addButton(buttonShapePrevious);
		addButton(buttonShapeNext);
		addButton(buttonBasepos);
		addButton(buttonColours);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		
		textFieldX = new TextFieldWidget(font, 220, 40, 50, 20, new StringTextComponent(""));
		textFieldX.setText("" + (int) BuildGuide.state.basePos.x);
		textFieldX.setTextColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(font, 220, 60, 50, 20, new StringTextComponent(""));
		textFieldY.setText("" + (int) BuildGuide.state.basePos.y);
		textFieldY.setTextColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(font, 220, 80, 50, 20, new StringTextComponent(""));
		textFieldZ.setText("" + (int) BuildGuide.state.basePos.z);
		textFieldZ.setTextColor(0xFFFFFF);
		children.add(textFieldZ);
		
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		properties.add(BuildGuide.state.propertyDepthTest);
		
		for(Property<?> p: properties) {
			p.addToBuildGuideScreen(this);
		}
		for(Shape s: BuildGuide.state.shapeStore) {
			s.onDeselectedInGUI();
			for(Property<?> p: s.properties) {
				if(p.mightNeedTextFields()) p.addTextFields(font);
				p.addToBuildGuideScreen(this);
			}
		}
		
		State.getCurrentShape().onSelectedInGUI();
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
		font.drawStringWithShadow(matrixStack, titleShapeProperties, (160 - font.getStringWidth(titleShapeProperties)) / 2, 130, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleBasepos, 160 + (160 - font.getStringWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleNumberOfBlocks, 340 + (100 - font.getStringWidth(titleNumberOfBlocks)) / 2, 25, 0xFFFFFF);
		String numberOfBlocks = "" + State.getCurrentShape().getNumberOfBlocks();
		String numberOfStacks = "(" + (State.getCurrentShape().getNumberOfBlocks() / 64) + " x 64 + " + (State.getCurrentShape().getNumberOfBlocks() % 64) + ")";
		font.drawStringWithShadow(matrixStack, numberOfBlocks, 340 + (100 - font.getStringWidth(numberOfBlocks)) / 2, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, numberOfStacks, 340 + (100 - font.getStringWidth(numberOfStacks)) / 2, 65, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, textShape, 5, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, State.getCurrentShape().getTranslatedName(), 80 + (60 - font.getStringWidth(State.getCurrentShape().getTranslatedName())) / 2, 45, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, "X", 185, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Y", 185, 65, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Z", 185, 85, 0xFFFFFF);
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
		
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
		for(Property<?> p: State.getCurrentShape().properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
	}
	
	private void updateShape(int di) {
		State.getCurrentShape().onDeselectedInGUI();
		
		if(BuildGuide.state.basePos == null) setBasePos();
		
		BuildGuide.state.i_shape = Math.floorMod(BuildGuide.state.i_shape + di, BuildGuide.state.shapeStore.length);
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	private void setBasePos() {
		Vector3d pos = Minecraft.getInstance().player.getPositionVec();
		BuildGuide.state.basePos = new Vector3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
		if(textFieldX != null) {
			textFieldX.setText("" + (int) BuildGuide.state.basePos.x);
			textFieldX.setTextColor(0xFFFFFF);
		}
		if(textFieldY != null) {
			textFieldY.setText("" + (int) BuildGuide.state.basePos.y);
			textFieldY.setTextColor(0xFFFFFF);
		}
		if(textFieldZ != null) {
			textFieldZ.setText("" + (int) BuildGuide.state.basePos.z);
			textFieldZ.setTextColor(0xFFFFFF);
		}
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x + dx, BuildGuide.state.basePos.y + dy, BuildGuide.state.basePos.z + dz);
		textFieldX.setText("" + (int) BuildGuide.state.basePos.x);
		textFieldY.setText("" + (int) BuildGuide.state.basePos.y);
		textFieldZ.setText("" + (int) BuildGuide.state.basePos.z);
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
