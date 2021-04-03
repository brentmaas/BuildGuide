package brentmaas.buildguide;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.shapes.Shape;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class BuildGuideGui extends Screen{
	private Button closeButton;
	private Button shapeButton = new Button(0, 20, 100, 20, new TranslationTextComponent(State.getCurrentShape().getTranslationKey()), button -> updateShape());
	private Button basePosButton = new Button(0, 40, 100, 20, new TranslationTextComponent("screen.buildguide.setbasepos"), button -> setBasePos());
	private Button depthTestButton = new Button(0, 60, 100, 20, new TranslationTextComponent("screen.buildguide.depthtest", State.depthTest), button -> {
		State.depthTest = !State.depthTest;
		button.setMessage(new TranslationTextComponent("screen.buildguide.depthtest", State.depthTest));
	});
	private Button basePosXDisplayButton = new Button(120, 20, 60, 20, new StringTextComponent("X: " + (State.basePos == null ? "" : (int) State.basePos.x)), null);
	private Button basePosXDecreaseButton = new Button(100, 20, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(-1, 0, 0));
	private Button basePosXIncreaseButton = new Button(180, 20, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(1, 0, 0));
	private Button basePosYDisplayButton = new Button(120, 40, 60, 20, new StringTextComponent("Y: " + (State.basePos == null ? "" : (int) State.basePos.y)), null);
	private Button basePosYDecreaseButton = new Button(100, 40, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, -1, 0));
	private Button basePosYIncreaseButton = new Button(180, 40, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 1, 0));
	private Button basePosZDisplayButton = new Button(120, 60, 60, 20, new StringTextComponent("Z: " + (State.basePos == null ? "" : (int) State.basePos.z)), null);
	private Button basePosZDecreaseButton = new Button(100, 60, 20, 20, new StringTextComponent("-"), button -> shiftBasePos(0, 0, -1));
	private Button basePosZIncreaseButton = new Button(180, 60, 20, 20, new StringTextComponent("+"), button -> shiftBasePos(0, 0, 1));
	
	public BuildGuideGui() {
		super(new TranslationTextComponent("screen.buildguide.title"));
	}
	
	@Override
	protected void init() {
		closeButton = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));
		addButton(closeButton);
		addButton(shapeButton);
		addButton(basePosButton);
		addButton(depthTestButton);
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
		
		for(Shape s: State.shapeStore) {
			for(Button b: s.buttonList) {
				addButton(b);
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
		basePosXDisplayButton.setMessage(new StringTextComponent("X: " + (int) State.basePos.x));
		basePosYDisplayButton.setMessage(new StringTextComponent("Y: " + (int) State.basePos.y));
		basePosZDisplayButton.setMessage(new StringTextComponent("Z: " + (int) State.basePos.z));
	}
	
	private void shiftBasePos(int dx, int dy, int dz) {
		State.basePos = new Vector3d(State.basePos.x + dx, State.basePos.y + dy, State.basePos.z + dz);
		for(Shape shape: State.shapeStore) shape.update();
		basePosXDisplayButton.setMessage(new StringTextComponent("X: " + (int) State.basePos.x));
		basePosYDisplayButton.setMessage(new StringTextComponent("Y: " + (int) State.basePos.y));
		basePosZDisplayButton.setMessage(new StringTextComponent("Z: " + (int) State.basePos.z));
	}
}
