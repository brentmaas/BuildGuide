package brentmaas.buildguide.fabric.screen;

import brentmaas.buildguide.fabric.StateManager;
import brentmaas.buildguide.fabric.screen.widget.CheckboxRunnableButton;
import brentmaas.buildguide.fabric.screen.widget.ShapeList;
import brentmaas.buildguide.fabric.shapes.Shape;
import brentmaas.buildguide.fabric.shapes.ShapeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;

public class ShapelistScreen extends Screen{
	private String titleNewShape;
	private String titleShapes;
	private String titleGlobalBasepos;
	private String titleVisible;
	private String titleNumberOfBlocks;
	
	private ShapeList shapeList;
	
	private int newShapeId = 0;
	
	private ButtonWidget buttonClose;
	private ButtonWidget buttonBack = new ButtonWidget(0, 0, 20, 20, new LiteralText("<-"), button -> MinecraftClient.getInstance().openScreen(new BuildGuideScreen()));
	private ButtonWidget buttonNewShapePrevious = new ButtonWidget(0, 25, 20, 20, new LiteralText("<-"), button -> updateNewShape(-1));
	private ButtonWidget buttonNewShapeNext = new ButtonWidget(120, 25, 20, 20, new LiteralText("->"), button -> updateNewShape(1));
	private ButtonWidget buttonAdd = new ButtonWidget(0, 45, 140, 20, new TranslatableText("screen.buildguide.add"), button -> {
		StateManager.getState().advancedModeShapes.add(ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(newShapeId)));
		StateManager.getState().resetBasepos(StateManager.getState().advancedModeShapes.size() - 1);
		StateManager.getState().advancedModeShapes.get(StateManager.getState().advancedModeShapes.size() - 1).update();
		shapeList.addEntryExternal(StateManager.getState().advancedModeShapes.size() - 1);
		
		checkActive();
	});
	private CheckboxRunnableButton buttonVisible = new CheckboxRunnableButton(120, 65, 20, 20, new LiteralText(""), true, false, button -> setShapeVisibility());
	private ButtonWidget buttonDelete = new ButtonWidget(0, 85, 140, 20, new TranslatableText("screen.buildguide.delete"), button -> {
		if(shapeList.getSelected() != null) {
			StateManager.getState().advancedModeShapes.remove(shapeList.getSelected().getShapeId());
			shapeList.removeEntry(shapeList.getSelected());
		}
		
		checkActive();
	});
	private ButtonWidget buttonGlobalBasepos = new ButtonWidget(0, 125, 140, 20, new TranslatableText("screen.buildguide.setglobalbasepos"), button -> {
		if(StateManager.getState().isShapeAvailable()) setGlobalBasePos();
	});
	//TODO: World manager button
	private ButtonWidget buttonBaseposXDecrease = new ButtonWidget(20, 145, 20, 20, new LiteralText("-"), button -> shiftGlobalBasePos(-1, 0, 0));
	private ButtonWidget buttonBaseposXIncrease = new ButtonWidget(120, 145, 20, 20, new LiteralText("+"), button -> shiftGlobalBasePos(1, 0, 0));
	private ButtonWidget buttonBaseposYDecrease = new ButtonWidget(20, 165, 20, 20, new LiteralText("-"), button -> shiftGlobalBasePos(0, -1, 0));
	private ButtonWidget buttonBaseposYIncrease = new ButtonWidget(120, 165, 20, 20, new LiteralText("+"), button -> shiftGlobalBasePos(0, 1, 0));
	private ButtonWidget buttonBaseposZDecrease = new ButtonWidget(20, 185, 20, 20, new LiteralText("-"), button -> shiftGlobalBasePos(0, 0, -1));
	private ButtonWidget buttonBaseposZIncrease = new ButtonWidget(120, 185, 20, 20, new LiteralText("+"), button -> shiftGlobalBasePos(0, 0, 1));
	private TextFieldWidget textFieldX;
	private TextFieldWidget textFieldY;
	private TextFieldWidget textFieldZ;
	private ButtonWidget buttonSetX = new ButtonWidget(90, 145, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldX.getText());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.x;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(delta, 0, 0);
			}
			textFieldX.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldX.setEditableColor(0xFF0000);
		}
	});
	private ButtonWidget buttonSetY = new ButtonWidget(90, 165, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldY.getText());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.y;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, delta, 0);
			}
			textFieldY.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldY.setEditableColor(0xFF0000);
		}
	});
	private ButtonWidget buttonSetZ = new ButtonWidget(90, 185, 30, 20, new TranslatableText("screen.buildguide.set"), button -> {
		try {
			int newval = Integer.parseInt(textFieldZ.getText());
			int delta = newval - (int) StateManager.getState().getCurrentShape().basePos.z;
			for(Shape s: StateManager.getState().advancedModeShapes) {
				s.shiftBasepos(0, 0, delta);
			}
			textFieldZ.setEditableColor(0xFFFFFF);
		}catch(NumberFormatException e) {
			textFieldZ.setEditableColor(0xFF0000);
		}
	});
	
	public ShapelistScreen() {
		super(new TranslatableText("screen.buildguide.shapelist"));
	}
	
	@Override
	protected void init() {
		titleNewShape = new TranslatableText("screen.buildguide.newshape").getString();
		titleShapes = new TranslatableText("screen.buildguide.shapes").getString();
		titleGlobalBasepos = new TranslatableText("screen.buildguide.globalbasepos").getString();
		titleVisible = new TranslatableText("screen.buildguide.visible").getString();
		titleNumberOfBlocks = new TranslatableText("screen.buildguide.numberofblocks").getString();
		
		buttonClose = new ButtonWidget(this.width - 20, 0, 20, 20, new LiteralText("X"), button -> MinecraftClient.getInstance().openScreen(null));
		
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
		
		textFieldX = new TextFieldWidget(textRenderer, 40, 145, 50, 20, new LiteralText(""));
		textFieldX.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setEditableColor(0xFFFFFF);
		children.add(textFieldX);
		textFieldY = new TextFieldWidget(textRenderer, 40, 165, 50, 20, new LiteralText(""));
		textFieldY.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setEditableColor(0xFFFFFF);
		children.add(textFieldY);
		textFieldZ = new TextFieldWidget(textRenderer, 40, 185, 50, 20, new LiteralText(""));
		textFieldZ.setText(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setEditableColor(0xFFFFFF);
		children.add(textFieldZ);
		
		shapeList = new ShapeList(client, 150, 300, 25, height, 20, () -> {
			if(StateManager.getState().isShapeAvailable()) {
				textFieldX.setText("" + (int) StateManager.getState().getCurrentShape().basePos.x);
				textFieldY.setText("" + (int) StateManager.getState().getCurrentShape().basePos.y);
				textFieldZ.setText("" + (int) StateManager.getState().getCurrentShape().basePos.z);
			} else {
				textFieldX.setText("-");
				textFieldY.setText("-");
				textFieldZ.setText("-");
			}
			textFieldX.setEditableColor(0xFFFFFF);
			textFieldY.setEditableColor(0xFFFFFF);
			textFieldZ.setEditableColor(0xFFFFFF);
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
		textRenderer.drawWithShadow(matrixStack, title.getString(), (width - textRenderer.getWidth(title.getString())) / 2, 5, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleNewShape, (140 - textRenderer.getWidth(titleNewShape)) / 2, 15, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleShapes, 150 + (150 - textRenderer.getWidth(titleShapes)) / 2, 15, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleGlobalBasepos, (140 - textRenderer.getWidth(titleGlobalBasepos)) / 2, 115, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleNumberOfBlocks, 305 + (100 - textRenderer.getWidth(titleNumberOfBlocks)) / 2, 15, 0xFFFFFF);
		
		String newShapeName = new TranslatableText(ShapeRegistry.getTranslationKeys().get(newShapeId)).getString();
		textRenderer.drawWithShadow(matrixStack, newShapeName, 20 + (100 - textRenderer.getWidth(newShapeName)) / 2, 30, 0xFFFFFF);
		
		textRenderer.drawWithShadow(matrixStack, titleVisible, 5, 70, StateManager.getState().isShapeAvailable() ? 0xFFFFFF : 0x444444);
		
		textRenderer.drawWithShadow(matrixStack, "X", 5, 150, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, "Y", 5, 170, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, "Z", 5, 190, 0xFFFFFF);
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
		textRenderer.drawWithShadow(matrixStack, numberOfBlocks, 305 + (100 - textRenderer.getWidth(numberOfBlocks)) / 2, 30, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, numberOfStacks, 305 + (100 - textRenderer.getWidth(numberOfStacks)) / 2, 45, 0xFFFFFF);
		
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
				textFieldX.setText("" + (int) StateManager.getState().getCurrentShape().basePos.x);
				textFieldX.setEditableColor(0xFFFFFF);
			}
			if(dy != 0) {
				textFieldY.setText("" + (int) StateManager.getState().getCurrentShape().basePos.y);
				textFieldY.setEditableColor(0xFFFFFF);
			}
			if(dz != 0) {
				textFieldZ.setText("" + (int) StateManager.getState().getCurrentShape().basePos.z);
				textFieldZ.setEditableColor(0xFFFFFF);
			}
		} else {
			if(dx != 0) {
				textFieldX.setText("-");
				textFieldX.setEditableColor(0xFFFFFF);
			}
			if(dy != 0) {
				textFieldY.setText("-");
				textFieldY.setEditableColor(0xFFFFFF);
			}
			if(dz != 0) {
				textFieldZ.setText("-");
				textFieldZ.setEditableColor(0xFFFFFF);
			}
		}
	}
	
	private void setGlobalBasePos() {
		Vec3d pos = MinecraftClient.getInstance().player.getPos();
		int deltaX = (int) (Math.floor(pos.x) - StateManager.getState().getCurrentShape().basePos.x);
		int deltaY = (int) (Math.floor(pos.y) - StateManager.getState().getCurrentShape().basePos.y);
		int deltaZ = (int) (Math.floor(pos.z) - StateManager.getState().getCurrentShape().basePos.z);
		shiftGlobalBasePos(deltaX, deltaY, deltaZ);
	}
	
	private void setShapeVisibility() {
		if(StateManager.getState().isShapeAvailable()) StateManager.getState().getCurrentShape().visible = buttonVisible.isChecked();
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
	
	public void addButtonExternal(ClickableWidget button) {
		addButton(button);
	}
	
	public void addTextFieldExternal(TextFieldWidget tfw) {
		children.add(tfw);
	}
}
