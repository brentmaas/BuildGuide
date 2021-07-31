package brentmaas.buildguide.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3d;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.State;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec3;

public class BuildGuideScreen extends Screen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private Button buttonClose;
	//It's better off as custom buttons instead of PropertyEnum
	private Button buttonShapePrevious = new Button(60, 40, 20, 20, new TextComponent("<-"), button -> updateShape(-1));
	private Button buttonShapeNext = new Button(140, 40, 20, 20, new TextComponent("->"), button -> updateShape(1));
	private Button buttonBasepos = new Button(0, 60, 160, 20, new TranslatableComponent("screen.buildguide.setbasepos"), button -> setBasePos());
	private Button buttonColours = new Button(0, 100, 160, 20, new TranslatableComponent("screen.buildguide.colours"), button -> {
		Minecraft.getInstance().setScreen(new ColoursScreen());
	});
	//It's better off as custom buttons instead of PropertyInt
	private Button buttonBaseposXDecrease = new Button(200, 40, 20, 20, new TextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(300, 40, 20, 20, new TextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(200, 60, 20, 20, new TextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(300, 60, 20, 20, new TextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(200, 80, 20, 20, new TextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(300, 80, 20, 20, new TextComponent("+"), button -> shiftBasePos(0, 0, 1));
	private EditBox textFieldX;
	private EditBox textFieldY;
	private EditBox textFieldZ;
	private Button buttonSetX = new Button(270, 40, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getValue());
			BuildGuide.state.basePos = new Vector3d(newval, BuildGuide.state.basePos.y, BuildGuide.state.basePos.z);
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(270, 60, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getValue());
			BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x, newval, BuildGuide.state.basePos.z);
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(270, 80, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getValue());
			BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x, BuildGuide.state.basePos.y, newval);
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
		
		if(BuildGuide.state.basePos == null) { //Very likely the first time opening, so basepos and shapes haven't been properly set up yet
			setBasePos();
			for(Shape shape: BuildGuide.state.shapeStore) shape.update();
		}
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new TextComponent("X"), button -> Minecraft.getInstance().setScreen(null));
		
		addRenderableWidget(buttonClose);
		addRenderableWidget(buttonShapePrevious);
		addRenderableWidget(buttonShapeNext);
		addRenderableWidget(buttonBasepos);
		addRenderableWidget(buttonColours);
		addRenderableWidget(buttonBaseposXDecrease);
		addRenderableWidget(buttonBaseposXIncrease);
		addRenderableWidget(buttonBaseposYDecrease);
		addRenderableWidget(buttonBaseposYIncrease);
		addRenderableWidget(buttonBaseposZDecrease);
		addRenderableWidget(buttonBaseposZIncrease);
		
		textFieldX = new EditBox(font, 220, 40, 50, 20, new TextComponent(""));
		textFieldX.setValue("" + (int) BuildGuide.state.basePos.x);
		textFieldX.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldX);
		textFieldY = new EditBox(font, 220, 60, 50, 20, new TextComponent(""));
		textFieldY.setValue("" + (int) BuildGuide.state.basePos.y);
		textFieldY.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldY);
		textFieldZ = new EditBox(font, 220, 80, 50, 20, new TextComponent(""));
		textFieldZ.setValue("" + (int) BuildGuide.state.basePos.z);
		textFieldZ.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldZ);
		
		addRenderableWidget(buttonSetX);
		addRenderableWidget(buttonSetY);
		addRenderableWidget(buttonSetZ);
		
		properties.add(BuildGuide.state.propertyDepthTest);
		
		for(Property<?> p: properties) {
			p.addToBuildGuideScreen(this);
		}
		for(Shape s: BuildGuide.state.shapeStore) {
			for(Property<?> p: s.properties) {
				if(p.mightNeedTextFields()) p.addTextFields(font);
				p.addToBuildGuideScreen(this);
			}
			s.onDeselectedInGUI(); //TODO: Test?
		}
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawShadow(matrixStack, title.getString(), (width - font.width(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawShadow(matrixStack, titleGlobalProperties, (160 - font.width(titleGlobalProperties)) / 2, 25, 0xFFFFFF);
		font.drawShadow(matrixStack, titleShapeProperties, (160 - font.width(titleShapeProperties)) / 2, 130, 0xFFFFFF);
		font.drawShadow(matrixStack, titleBasepos, 160 + (160 - font.width(titleBasepos)) / 2, 25, 0xFFFFFF);
		font.drawShadow(matrixStack, titleNumberOfBlocks, 340 + (100 - font.width(titleNumberOfBlocks)) / 2, 25, 0xFFFFFF);
		String numberOfBlocks = "" + State.getCurrentShape().getNumberOfBlocks();
		String numberOfStacks = "(" + (State.getCurrentShape().getNumberOfBlocks() / 64) + " x 64 + " + (State.getCurrentShape().getNumberOfBlocks() % 64) + ")";
		font.drawShadow(matrixStack, numberOfBlocks, 340 + (100 - font.width(numberOfBlocks)) / 2, 45, 0xFFFFFF);
		font.drawShadow(matrixStack, numberOfStacks, 340 + (100 - font.width(numberOfStacks)) / 2, 65, 0xFFFFFF);
		
		font.drawShadow(matrixStack, textShape, 5, 45, 0xFFFFFF);
		font.drawShadow(matrixStack, State.getCurrentShape().getTranslatedName(), 80 + (60 - font.width(State.getCurrentShape().getTranslatedName())) / 2, 45, 0xFFFFFF);
		
		font.drawShadow(matrixStack, "X", 185, 45, 0xFFFFFF);
		font.drawShadow(matrixStack, "Y", 185, 65, 0xFFFFFF);
		font.drawShadow(matrixStack, "Z", 185, 85, 0xFFFFFF);
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
		Vec3 pos = Minecraft.getInstance().player.position();
		BuildGuide.state.basePos = new Vector3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
		if(textFieldX != null) {
			textFieldX.setValue("" + (int) BuildGuide.state.basePos.x);
			textFieldX.setTextColor(0xFFFFFF);
		}
		if(textFieldY != null) {
			textFieldY.setValue("" + (int) BuildGuide.state.basePos.y);
			textFieldY.setTextColor(0xFFFFFF);
		}
		if(textFieldZ != null) {
			textFieldZ.setValue("" + (int) BuildGuide.state.basePos.z);
			textFieldZ.setTextColor(0xFFFFFF);
		}
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		BuildGuide.state.basePos = new Vector3d(BuildGuide.state.basePos.x + dx, BuildGuide.state.basePos.y + dy, BuildGuide.state.basePos.z + dz);
		textFieldX.setValue("" + (int) BuildGuide.state.basePos.x);
		textFieldY.setValue("" + (int) BuildGuide.state.basePos.y);
		textFieldZ.setValue("" + (int) BuildGuide.state.basePos.z);
		textFieldX.setTextColor(0xFFFFFF);
		textFieldY.setTextColor(0xFFFFFF);
		textFieldZ.setTextColor(0xFFFFFF);
	}
	
	public void addButtonExternal(AbstractButton button) {
		addRenderableWidget(button);
	}
	
	public void addEditBoxExternal(EditBox eb) {
		addRenderableWidget(eb);
	}
}
