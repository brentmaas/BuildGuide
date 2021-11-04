package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.CheckboxRunnableButton;
import brentmaas.buildguide.screen.widget.ShapeList;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapelistScreen extends Screen{
	private String titleNewShape;
	private String titleShapes;
	private String titleGlobalBasepos;
	private String titleVisible;
	
	private ShapeList shapeList;
	
	private int newShapeId = 0;
	
	private Button buttonClose;
	private Button buttonBack = new Button(0, 0, 20, 20, new StringTextComponent("<-"), button -> Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen()));
	private Button buttonNewShapePrevious = new Button(0, 40, 20, 20, new StringTextComponent("<-"), button -> updateNewShape(-1));
	private Button buttonNewShapeNext = new Button(120, 40, 20, 20, new StringTextComponent("->"), button -> updateNewShape(1));
	private Button buttonAdd = new Button(0, 60, 140, 20, new TranslationTextComponent("screen.buildguide.add"), button -> {
		StateManager.getState().advancedModeShapes.add(ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(newShapeId)));
		StateManager.getState().resetBasepos(StateManager.getState().advancedModeShapes.size() - 1);
		StateManager.getState().advancedModeShapes.get(StateManager.getState().advancedModeShapes.size() - 1).update();
		shapeList.addEntryExternal(StateManager.getState().advancedModeShapes.size() - 1);
	});
	private CheckboxRunnableButton buttonVisible = new CheckboxRunnableButton(120, 80, 20, 20, new StringTextComponent(""), true, false, button -> setShapeVisibility());
	private Button buttonDelete = new Button(0, 100, 140, 20, new TranslationTextComponent("screen.buildguide.delete"), button -> {
		if(shapeList.getSelected() != null) {
			StateManager.getState().advancedModeShapes.remove(shapeList.getSelected().getShapeId());
			shapeList.removeEntry(shapeList.getSelected());
			StateManager.getState().iAdvanced = 0;
		}
	});
	private Button buttonGlobalBasepos = new Button(0, 150, 140, 20, new TranslationTextComponent("screen.buildguide.setglobalbasepos"), button -> setGlobalBasePos());
	//TODO: World manager button
	private Button buttonBaseposXDecrease = new Button(20, 170, 20, 20, new StringTextComponent("-"), button -> shiftGlobalBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(120, 170, 20, 20, new StringTextComponent("+"), button -> shiftGlobalBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(20, 190, 20, 20, new StringTextComponent("-"), button -> shiftGlobalBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(120, 190, 20, 20, new StringTextComponent("+"), button -> shiftGlobalBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(20, 210, 20, 20, new StringTextComponent("-"), button -> shiftGlobalBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(120, 210, 20, 20, new StringTextComponent("+"), button -> shiftGlobalBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private Button buttonSetX = new Button(90, 170, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.x;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(delta, 0, 0);
			}
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(90, 190, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.y;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, delta, 0);
			}
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(90, 210, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getText());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.z;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, 0, delta);
			}
			textFieldZ.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setTextColor(0xFF0000);
		}
	});
	
	public ShapelistScreen() {
		super(new TranslationTextComponent("screen.buildguide.shapelist"));
		
		titleNewShape = new TranslationTextComponent("screen.buildguide.newshape").getString();
		titleShapes = new TranslationTextComponent("screen.buildguide.shapes").getString();
		titleGlobalBasepos = new TranslationTextComponent("screen.buildguide.globalbasepos").getString();
		titleVisible = new TranslationTextComponent("screen.buildguide.visible").getString();
	}
	
	@Override
	protected void init() {
		buttonClose = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));
		
		addButton(buttonClose);
		addButton(buttonBack);
		addButton(buttonNewShapePrevious);
		addButton(buttonNewShapeNext);
		addButton(buttonAdd);
		addButton(buttonVisible);
		addButton(buttonDelete);
		addButton(buttonGlobalBasepos);
		addButton(buttonBaseposXDecrease);
		addButton(buttonBaseposXIncrease);
		addButton(buttonBaseposYDecrease);
		addButton(buttonBaseposYIncrease);
		addButton(buttonBaseposZDecrease);
		addButton(buttonBaseposZIncrease);
		
		textFieldX = new TextFieldWidget(font, 40, 170, 50, 20, new StringTextComponent(""));
		textFieldX.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setTextColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(font, 40, 190, 50, 20, new StringTextComponent(""));
		textFieldY.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setTextColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(font, 40, 210, 50, 20, new StringTextComponent(""));
		textFieldZ.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setTextColor(0xFFFFFF);
		children.add(textFieldZ);
		
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		shapeList = new ShapeList(minecraft, width, height - 60, 60, height, 20, () -> {
			updateGlobalBasepos();
			buttonVisible.setChecked(StateManager.getState().getCurrentShape().visible);
		});
		children.add(shapeList);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleNewShape, (140 - font.getStringWidth(titleNewShape)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleShapes, (width - font.getStringWidth(titleShapes)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleGlobalBasepos, (140 - font.getStringWidth(titleGlobalBasepos)) / 2, 135, 0xFFFFFF);
		
		String newShapeName = new TranslationTextComponent(ShapeRegistry.getTranslationKeys().get(newShapeId)).getString();
		font.drawStringWithShadow(matrixStack, newShapeName, 20 + (100 - font.getStringWidth(newShapeName)) / 2, 45, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, titleVisible, (120 - font.getStringWidth(titleVisible)) / 2, 85, 0xFFFFFF);
		
		font.drawStringWithShadow(matrixStack, "X", 5, 175, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Y", 5, 195, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, "Z", 5, 215, 0xFFFFFF);
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
		
		shapeList.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void updateGlobalBasepos() {
		if(StateManager.getState().isShapeAvailable()) {
			textFieldX.setText("" + (int) StateManager.getState().getCurrentShape().basePos.x);
			textFieldY.setText("" + (int) StateManager.getState().getCurrentShape().basePos.y);
			textFieldZ.setText("" + (int) StateManager.getState().getCurrentShape().basePos.z);
		} else {
			textFieldX.setText("-");
			textFieldY.setText("-");
			textFieldZ.setText("-");
		}
		textFieldX.setTextColor(0xFFFFFF);
		textFieldY.setTextColor(0xFFFFFF);
		textFieldZ.setTextColor(0xFFFFFF);
	}
	
	private void updateNewShape(int di) {
		newShapeId = Math.floorMod(newShapeId + di, ShapeRegistry.getNumberOfShapes());
	}
	
	private void shiftGlobalBasePos(int dx, int dy, int dz) {
		for(Shape s: StateManager.getState().advancedModeShapes) {
			s.shiftBasepos(dx, dy, dz);
		}
		updateGlobalBasepos();
	}
	
	private void setGlobalBasePos() {
		Vector3d pos = Minecraft.getInstance().player.getPositionVec();
		int deltaX = (int) (Math.floor(pos.x) - StateManager.getState().getCurrentShape().basePos.x);
		int deltaY = (int) (Math.floor(pos.y) - StateManager.getState().getCurrentShape().basePos.y);
		int deltaZ = (int) (Math.floor(pos.z) - StateManager.getState().getCurrentShape().basePos.z);
		shiftGlobalBasePos(deltaX, deltaY, deltaZ);
	}
	
	private void setShapeVisibility() {
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().visible = buttonVisible.isChecked();
	}
	
	public void addButtonExternal(AbstractButton button) {
		addButton(button);
	}
	
	public void addTextFieldExternal(TextFieldWidget tfw) {
		children.add(tfw);
	}
}
