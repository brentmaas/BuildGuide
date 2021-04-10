package brentmaas.buildguide;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.property.Property;
import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class BuildGuideScreen extends Screen{
	private String titleGlobalProperties;
	private String titleShapeProperties;
	private String titleBasepos;
	private String titleNumberOfBlocks;
	
	//TODO: All buttons to this
	//private ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	private Button closeButton = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));;
	private Button shapeButton = new Button(0, 40, 100, 20, new TranslationTextComponent(State.getCurrentShape().getTranslationKey()), button -> updateShape());
	private Button basePosButton = new Button(0, 60, 100, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> setBasePos());
	private Button depthTestButton = new Button(0, 80, 100, 20, new TranslationTextComponent("screen.buildguide.depthtest", State.depthTest), button -> {
		State.depthTest = !State.depthTest;
		button.setMessage(new TranslationTextComponent("screen.buildguide.depthtest", State.depthTest));
	});
	//TODO
	private Button coloursButton = new Button(0, 100, 100, 20, new TranslationTextComponent("screen.buildguide.colours"), button -> {
		System.out.println("TODO");
	});
	private Button basePosXDisplayButton;
	private Button basePosXDecreaseButton = new Button(160, 40, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button basePosXIncreaseButton = new Button(240, 40, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button basePosYDisplayButton;
	private Button basePosYDecreaseButton = new Button(160, 60, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button basePosYIncreaseButton = new Button(240, 60, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button basePosZDisplayButton;
	private Button basePosZDecreaseButton = new Button(160, 80, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button basePosZIncreaseButton = new Button(240, 80, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	
	public BuildGuideScreen() {
		super(new TranslationTextComponent("screen.buildguide.title"));
	}
	
	@Override
	protected void init() {
		titleGlobalProperties = new TranslationTextComponent("screen.buildguide.globalproperties").getString();
		titleShapeProperties = new TranslationTextComponent("screen.buildguide.shapeproperties").getString();
		titleBasepos = new TranslationTextComponent("screen.buildguide.basepos").getString();
		titleNumberOfBlocks = new TranslationTextComponent("screen.buildguide.numberofblocks").getString();
		
		if(State.basePos == null) setBasePos();
		
		basePosXDisplayButton = new Button(180, 40, 60, 20, new StringTextComponent("X: " + (int) State.basePos.x), null);
		basePosYDisplayButton = new Button(180, 60, 60, 20, new StringTextComponent("Y: " + (int) State.basePos.y), null);
		basePosZDisplayButton = new Button(180, 80, 60, 20, new StringTextComponent("Z: " + (int) State.basePos.z), null);
		
		addButton(closeButton);
		addButton(shapeButton);
		addButton(basePosButton);
		addButton(depthTestButton);
		addButton(coloursButton);
		basePosXDisplayButton.active = false;
		addButton(basePosXDisplayButton);
		addButton(basePosXDecreaseButton);
		addButton(basePosXIncreaseButton);
		basePosYDisplayButton.active = false;
		addButton(basePosYDisplayButton);
		addButton(basePosYDecreaseButton);
		addButton(basePosYIncreaseButton);
		basePosZDisplayButton.active = false;
		addButton(basePosZDisplayButton);
		addButton(basePosZDecreaseButton);
		addButton(basePosZIncreaseButton);
		
		/*for(Property<?> p: properties) {
			p.addToBuildGuideScreen(this);
		}*/
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
		/*for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}*/
		for(Property<?> p: State.getCurrentShape().properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
	}
	
	private void updateShape() {
		State.getCurrentShape().onDeselectedInGUI();
		
		if(State.basePos == null) setBasePos();
		
		State.i_shape = (State.i_shape + 1) % State.shapeStore.length;
		shapeButton.setMessage(new TranslationTextComponent(State.getCurrentShape().getTranslationKey()));
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	private void setBasePos() {
		Vector3d pos = Minecraft.getInstance().player.getPositionVec();
		State.basePos = new Vector3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
		for(Shape shape: State.shapeStore) shape.update();
		if(basePosXDisplayButton != null) basePosXDisplayButton.setMessage(new StringTextComponent("X: " + (int) State.basePos.x));
		if(basePosYDisplayButton != null) basePosYDisplayButton.setMessage(new StringTextComponent("Y: " + (int) State.basePos.y));
		if(basePosZDisplayButton != null) basePosZDisplayButton.setMessage(new StringTextComponent("Z: " + (int) State.basePos.z));
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		State.basePos = new Vector3d(State.basePos.x + dx, State.basePos.y + dy, State.basePos.z + dz);
		for(Shape shape: State.shapeStore) shape.update();
		basePosXDisplayButton.setMessage(new StringTextComponent("X: " + (int) State.basePos.x));
		basePosYDisplayButton.setMessage(new StringTextComponent("Y: " + (int) State.basePos.y));
		basePosZDisplayButton.setMessage(new StringTextComponent("Z: " + (int) State.basePos.z));
	}
	
	public void addButtonExternal(Button button) {
		addButton(button);
	}
}
