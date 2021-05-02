package brentmaas.buildguide.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class BuildGuideScreen extends Screen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	private String textShape;
	
	private ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private Button buttonClose;
	//Too much effort to use PropertyEnum, so still normal buttons and drawing strings in render()
	private Button buttonShapePrevious = new Button(60, 40, 20, 20, new StringTextComponent("<-"), button -> updateShape(-1));
	private Button buttonShapeNext = new Button(140, 40, 20, 20, new StringTextComponent("->"), button -> updateShape(1));
	private Button buttonBasepos = new Button(0, 60, 160, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> setBasePos());
	private Button buttonColours = new Button(0, 100, 160, 20, new TranslationTextComponent("screen.buildguide.colours"), button -> {
		Minecraft.getInstance().displayGuiScreen(new ColoursScreen());
	});
	//Too much effort to use PropertyInt, so still normal buttons and drawing strings in render()
	private Button buttonBaseposXDecrease = new Button(200, 40, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(280, 40, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(200, 60, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(280, 60, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(200, 80, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(280, 80, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	
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
		
		if(State.basePos == null) setBasePos();
		
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
		
		properties.add(State.propertyDepthTest);
		
		for(Property<?> p: properties) {
			p.addToBuildGuideScreen(this);
		}
		for(Shape s: State.shapeStore) {
			for(Property<?> p: s.properties) {
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
		font.drawStringWithShadow(matrixStack, titleNumberOfBlocks, 320 + (100 - font.getStringWidth(titleNumberOfBlocks)) / 2, 25, 0xFFFFFF);
		String numberOfBlocks = "" + State.getCurrentShape().getNumberOfBlocks();
		font.drawStringWithShadow(matrixStack, numberOfBlocks, 320 + (100 - font.getStringWidth(numberOfBlocks)) / 2, 45, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, textShape, 5, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, State.getCurrentShape().getTranslatedName(), 80 + (60 - font.getStringWidth(State.getCurrentShape().getTranslatedName())) / 2, 45, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, "X", 185, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Y", 185, 65, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Z", 185, 85, 0xFFFFFF);
		String x = "" + (int) State.basePos.x;
		String y = "" + (int) State.basePos.y;
		String z = "" + (int) State.basePos.z;
		font.drawStringWithShadow(matrixStack, x, 220 + (60 - font.getStringWidth(x)) / 2, 45, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, y, 220 + (60 - font.getStringWidth(y)) / 2, 65, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, z, 220 + (60 - font.getStringWidth(z)) / 2, 85, 0xFFFFFF);
		
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
		for(Property<?> p: State.getCurrentShape().properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
	}
	
	private void updateShape(int di) {
		State.getCurrentShape().onDeselectedInGUI();
		
		if(State.basePos == null) setBasePos();
		
		State.i_shape = Math.floorMod(State.i_shape + di, State.shapeStore.length);
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	private void setBasePos() {
		Vector3d pos = Minecraft.getInstance().player.getPositionVec();
		State.basePos = new Vector3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
		for(Shape shape: State.shapeStore) shape.update();
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		State.basePos = new Vector3d(State.basePos.x + dx, State.basePos.y + dy, State.basePos.z + dz);
	}
	
	public void addButtonExternal(AbstractButton button) {
		addButton(button);
	}
}
