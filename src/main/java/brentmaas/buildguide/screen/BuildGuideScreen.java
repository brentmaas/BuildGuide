package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.State;
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
	
	private Button buttonClose = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));;
	private Button buttonShape = new Button(0, 40, 100, 20, new TranslationTextComponent(State.getCurrentShape().getTranslationKey()), button -> updateShape());
	private Button buttonBasepos = new Button(0, 60, 100, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> setBasePos());
	private Button buttonDepthTest = new Button(0, 80, 100, 20, new TranslationTextComponent("screen.buildguide.depthtest", State.depthTest), button -> {
		State.depthTest = !State.depthTest;
		button.setMessage(new TranslationTextComponent("screen.buildguide.depthtest", State.depthTest));
	});
	private Button buttonColours = new Button(0, 100, 100, 20, new TranslationTextComponent("screen.buildguide.colours"), button -> {
		Minecraft.getInstance().displayGuiScreen(new ColoursScreen());
	});
	private Button buttonBaseposXDisplay;
	private Button buttonBaseposXDecrease = new Button(160, 40, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(240, 40, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button buttonBaseposYDisplay;
	private Button buttonBaseposYDecrease = new Button(160, 60, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(240, 60, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button buttonBaseposZDisplay;
	private Button buttonBaseposZDecrease = new Button(160, 80, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(240, 80, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	
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
		
		buttonBaseposXDisplay = new Button(180, 40, 60, 20, new StringTextComponent("X: " + (int) State.basePos.x), null);
		buttonBaseposYDisplay = new Button(180, 60, 60, 20, new StringTextComponent("Y: " + (int) State.basePos.y), null);
		buttonBaseposZDisplay = new Button(180, 80, 60, 20, new StringTextComponent("Z: " + (int) State.basePos.z), null);
		
		addButton(buttonClose);
		addButton(buttonShape);
		addButton(buttonBasepos);
		addButton(buttonDepthTest);
		addButton(buttonColours);
		buttonBaseposXDisplay.active = false;
		addButton(buttonBaseposXDisplay);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		buttonBaseposYDisplay.active = false;
		addButton(buttonBaseposYDisplay);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		buttonBaseposZDisplay.active = false;
		addButton(buttonBaseposZDisplay);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		
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
		buttonShape.setMessage(new TranslationTextComponent(State.getCurrentShape().getTranslationKey()));
		
		State.getCurrentShape().onSelectedInGUI();
	}
	
	private void setBasePos() {
		Vector3d pos = Minecraft.getInstance().player.getPositionVec();
		State.basePos = new Vector3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
		for(Shape shape: State.shapeStore) shape.update();
		if(buttonBaseposXDisplay != null) buttonBaseposXDisplay.setMessage(new StringTextComponent("X: " + (int) State.basePos.x));
		if(buttonBaseposYDisplay != null) buttonBaseposYDisplay.setMessage(new StringTextComponent("Y: " + (int) State.basePos.y));
		if(buttonBaseposZDisplay != null) buttonBaseposZDisplay.setMessage(new StringTextComponent("Z: " + (int) State.basePos.z));
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		State.basePos = new Vector3d(State.basePos.x + dx, State.basePos.y + dy, State.basePos.z + dz);
		for(Shape shape: State.shapeStore) shape.update();
		buttonBaseposXDisplay.setMessage(new StringTextComponent("X: " + (int) State.basePos.x));
		buttonBaseposYDisplay.setMessage(new StringTextComponent("Y: " + (int) State.basePos.y));
		buttonBaseposZDisplay.setMessage(new StringTextComponent("Z: " + (int) State.basePos.z));
	}
	
	public void addButtonExternal(Button button) {
		addButton(button);
	}
}
