package brentmaas.buildguide.forge.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.forge.StateManager;
import brentmaas.buildguide.forge.screen.widget.CheckboxRunnableButton;
import brentmaas.buildguide.forge.screen.widget.ShapeList;
import brentmaas.buildguide.forge.shapes.Shape;
import brentmaas.buildguide.forge.shapes.ShapeRegistry;
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
	private String titleNumberOfBlocks;
	
	private ShapeList shapeList;
	
	private int newShapeId = 0;
	
	private Button buttonClose;
	private Button buttonBack = new Button(0, 0, 20, 20, new StringTextComponent("<-"), button -> Minecraft.getInstance().setScreen(new BuildGuideScreen()));
	private Button buttonNewShapePrevious = new Button(0, 25, 20, 20, new StringTextComponent("<-"), button -> updateNewShape(-1));
	private Button buttonNewShapeNext = new Button(120, 25, 20, 20, new StringTextComponent("->"), button -> updateNewShape(1));
	private Button buttonAdd = new Button(0, 45, 140, 20, new TranslationTextComponent("screen.buildguide.add"), button -> {
		StateManager.getState().advancedModeShapes.add(ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(newShapeId)));
		StateManager.getState().resetBasepos(StateManager.getState().advancedModeShapes.size() - 1);
		StateManager.getState().advancedModeShapes.get(StateManager.getState().advancedModeShapes.size() - 1).update();
		shapeList.addEntryExternal(StateManager.getState().advancedModeShapes.size() - 1);
		
		checkActive();
	});
	private CheckboxRunnableButton buttonVisible = new CheckboxRunnableButton(120, 65, 20, 20, new StringTextComponent(""), true, false, button -> setShapeVisibility());
	private Button buttonDelete = new Button(0, 85, 140, 20, new TranslationTextComponent("screen.buildguide.delete"), button -> {
		if(shapeList.getSelected() != null) {
			StateManager.getState().advancedModeShapes.remove(shapeList.getSelected().getShapeId());
			shapeList.removeEntry(shapeList.getSelected());
		}
		
		checkActive();
	});
	private Button buttonGlobalBasepos = new Button(0, 125, 140, 20, new TranslationTextComponent("screen.buildguide.setglobalbasepos"), button -> {
		if(StateManager.getState().isShapeAvailable()) setGlobalBasePos();
	});
	//TODO: World manager button
	private Button buttonBaseposXDecrease = new Button(20, 145, 20, 20, new StringTextComponent("-"), button -> shiftGlobalBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(120, 145, 20, 20, new StringTextComponent("+"), button -> shiftGlobalBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(20, 165, 20, 20, new StringTextComponent("-"), button -> shiftGlobalBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(120, 165, 20, 20, new StringTextComponent("+"), button -> shiftGlobalBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(20, 185, 20, 20, new StringTextComponent("-"), button -> shiftGlobalBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(120, 185, 20, 20, new StringTextComponent("+"), button -> shiftGlobalBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private Button buttonSetX = new Button(90, 145, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getValue());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.x;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(delta, 0, 0);
			}
			textFieldX.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetY = new Button(90, 165, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getValue());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.y;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, delta, 0);
			}
			textFieldY.setTextColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setTextColor(0xFF0000);
		}
	});
	private Button buttonSetZ = new Button(90, 185, 30, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getValue());
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
	}
	
	@Override
	protected void init() {
		titleNewShape = new TranslationTextComponent("screen.buildguide.newshape").getString();
		titleShapes = new TranslationTextComponent("screen.buildguide.shapes").getString();
		titleGlobalBasepos = new TranslationTextComponent("screen.buildguide.globalbasepos").getString();
		titleVisible = new TranslationTextComponent("screen.buildguide.visible").getString();
		titleNumberOfBlocks = new TranslationTextComponent("screen.buildguide.numberofblocks").getString();
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().setScreen(null));
		
		checkActive();
		
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
		addButton(buttonSetX);
		addButton(buttonSetY);
		addButton(buttonSetZ);
		
		textFieldX = new TextFieldWidget(font, 40, 145, 50, 20, new StringTextComponent(""));
		textFieldX.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setTextColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(font, 40, 165, 50, 20, new StringTextComponent(""));
		textFieldY.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setTextColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(font, 40, 185, 50, 20, new StringTextComponent(""));
		textFieldZ.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setTextColor(0xFFFFFF);
		children.add(textFieldZ);
		
		shapeList = new ShapeList(minecraft, 150, 300, 25, height, 20, () -> {
			if(StateManager.getState().isShapeAvailable()) {
				textFieldX.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.x);
				textFieldY.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.y);
				textFieldZ.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.z);
			} else {
				textFieldX.setValue("-");
				textFieldY.setValue("-");
				textFieldZ.setValue("-");
			}
			textFieldX.setTextColor(0xFFFFFF);
			textFieldY.setTextColor(0xFFFFFF);
			textFieldZ.setTextColor(0xFFFFFF);
			if(StateManager.getState().isShapeAvailable()) buttonVisible.setChecked(StateManager.getState().getCurrentShape().visible);
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
		font.drawShadow(matrixStack, title.getString(), (width - font.width(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawShadow(matrixStack, titleNewShape, (140 - font.width(titleNewShape)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleShapes, 150 + (150 - font.width(titleShapes)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleGlobalBasepos, (140 - font.width(titleGlobalBasepos)) / 2, 115, 0xFFFFFF);
		font.drawShadow(matrixStack, titleNumberOfBlocks, 305 + (100 - font.width(titleNumberOfBlocks)) / 2, 15, 0xFFFFFF);
		
		String newShapeName = new TranslationTextComponent(ShapeRegistry.getTranslationKeys().get(newShapeId)).getString();
		font.drawShadow(matrixStack, newShapeName, 20 + (100 - font.width(newShapeName)) / 2, 30, 0xFFFFFF);
		
		font.drawShadow(matrixStack, titleVisible, 5, 70, StateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		font.drawShadow(matrixStack, "X", 5, 150, 0xFFFFFF);
		font.drawShadow(matrixStack, "Y", 5, 170, 0xFFFFFF);
		font.drawShadow(matrixStack, "Z", 5, 190, 0xFFFFFF);
		textFieldX.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldY.render(matrixStack, mouseX, mouseY, partialTicks);
		textFieldZ.render(matrixStack, mouseX, mouseY, partialTicks);
		
		int n = 0;
		for(Shape s: StateManager.getState().advancedModeShapes) {
			if(s.visible) {
				n += s.getNumberOfBlocks();
			}
		}
		String numberOfBlocks = "" + n;
		String numberOfStacks = "(" + (n / 64) + " x 64 + " + (n % 64) + ")";
		font.drawShadow(matrixStack, numberOfBlocks, 305 + (100 - font.width(numberOfBlocks)) / 2, 30, 0xFFFFFF);
		font.drawShadow(matrixStack, numberOfStacks, 305 + (100 - font.width(numberOfStacks)) / 2, 45, 0xFFFFFF);
		
		shapeList.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void updateNewShape(int di) {
		newShapeId = Math.floorMod(newShapeId + di, ShapeRegistry.getNumberOfShapes());
	}
	
	private void shiftGlobalBasePos(int dx, int dy, int dz) {
		for(Shape s: StateManager.getState().advancedModeShapes) {
			s.shiftBasepos(dx, dy, dz);
		}
		if(StateManager.getState().isShapeAvailable()) {
			if(dx != 0) {
				textFieldX.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.x);
				textFieldX.setTextColor(0xFFFFFF);
			}
			if(dy != 0) {
				textFieldY.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.y);
				textFieldY.setTextColor(0xFFFFFF);
			}
			if(dz != 0) {
				textFieldZ.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.z);
				textFieldZ.setTextColor(0xFFFFFF);
			}
		}else {
			if(dx != 0) {
				textFieldX.setValue("-");
				textFieldX.setTextColor(0xFFFFFF);
			}
			if(dy != 0) {
				textFieldY.setValue("-");
				textFieldY.setTextColor(0xFFFFFF);
			}
			if(dz != 0) {
				textFieldZ.setValue("-");
				textFieldZ.setTextColor(0xFFFFFF);
			}
		}
	}
	
	private void setGlobalBasePos() {
		Vector3d pos = Minecraft.getInstance().player.position();
		int deltaX = (int) (Math.floor(pos.x) - StateManager.getState().getCurrentShape().basePos.x);
		int deltaY = (int) (Math.floor(pos.y) - StateManager.getState().getCurrentShape().basePos.y);
		int deltaZ = (int) (Math.floor(pos.z) - StateManager.getState().getCurrentShape().basePos.z);
		shiftGlobalBasePos(deltaX, deltaY, deltaZ);
	}
	
	private void setShapeVisibility() {
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().visible = buttonVisible.selected();
	}
	
	private void checkActive() {
		if(!StateManager.getState().isShapeAvailable()) {
			buttonVisible.active = false;
			buttonDelete.active = false;
			buttonGlobalBasepos.active = false;
			buttonBaseposXDecrease.active = false;
			buttonBaseposXIncrease.active = false;
			buttonBaseposYDecrease.active = false;
			buttonBaseposYIncrease.active = false;
			buttonBaseposZDecrease.active = false;
			buttonBaseposZIncrease.active = false;
			buttonSetX.active = false;
			buttonSetY.active = false;
			buttonSetZ.active = false;
		}else {
			buttonVisible.active = true;
			buttonDelete.active = true;
			buttonGlobalBasepos.active = true;
			buttonBaseposXDecrease.active = true;
			buttonBaseposXIncrease.active = true;
			buttonBaseposYDecrease.active = true;
			buttonBaseposYIncrease.active = true;
			buttonBaseposZDecrease.active = true;
			buttonBaseposZIncrease.active = true;
			buttonSetX.active = true;
			buttonSetY.active = true;
			buttonSetZ.active = true;
		}
	}
	
	public void addButtonExternal(AbstractButton button) {
		addButton(button);
	}
	
	public void addTextFieldExternal(TextFieldWidget tfw) {
		children.add(tfw);
	}
}
