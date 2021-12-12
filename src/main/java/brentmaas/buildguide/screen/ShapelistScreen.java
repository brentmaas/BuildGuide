package brentmaas.buildguide.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.CheckboxRunnableButton;
import brentmaas.buildguide.screen.widget.ShapeList;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec3;

public class ShapelistScreen extends Screen{
	private String titleNewShape;
	private String titleShapes;
	private String titleGlobalBasepos;
	private String titleVisible;
	private String titleNumberOfBlocks;
	
	private ShapeList shapeList;
	
	private int newShapeId = 0;
	
	private Button buttonClose;
	private Button buttonBack = new Button(0, 0, 20, 20, new TextComponent("<-"), button -> Minecraft.getInstance().setScreen(new BuildGuideScreen()));
	private Button buttonNewShapePrevious = new Button(0, 25, 20, 20, new TextComponent("<-"), button -> updateNewShape(-1));
	private Button buttonNewShapeNext = new Button(120, 25, 20, 20, new TextComponent("->"), button -> updateNewShape(1));
	private Button buttonAdd = new Button(0, 45, 140, 20, new TranslatableComponent("screen.buildguide.add"), button -> {
		StateManager.getState().advancedModeShapes.add(ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(newShapeId)));
		StateManager.getState().resetBasepos(StateManager.getState().advancedModeShapes.size() - 1);
		StateManager.getState().advancedModeShapes.get(StateManager.getState().advancedModeShapes.size() - 1).update();
		shapeList.addEntryExternal(StateManager.getState().advancedModeShapes.size() - 1);
		
		checkActive();
	});
	private CheckboxRunnableButton buttonVisible = new CheckboxRunnableButton(120, 65, 20, 20, new TextComponent(""), true, false, button -> setShapeVisibility());
	private Button buttonDelete = new Button(0, 85, 140, 20, new TranslatableComponent("screen.buildguide.delete"), button -> {
		if(shapeList.getSelected() != null) {
			StateManager.getState().advancedModeShapes.remove(shapeList.getSelected().getShapeId());
			shapeList.removeEntry(shapeList.getSelected());
		}
		
		checkActive();
	});
	private Button buttonGlobalBasepos = new Button(0, 125, 140, 20, new TranslatableComponent("screen.buildguide.setglobalbasepos"), button -> {
		if(StateManager.getState().isShapeAvailable()) setGlobalBasePos();
	});
	//TODO: World manager button
	private Button buttonBaseposXDecrease = new Button(20, 145, 20, 20, new TextComponent("-"), button -> shiftGlobalBasePos(-1, 0, 0));
	private Button buttonBaseposXIncrease = new Button(120, 145, 20, 20, new TextComponent("+"), button -> shiftGlobalBasePos(1, 0, 0));
	private Button buttonBaseposYDecrease = new Button(20, 165, 20, 20, new TextComponent("-"), button -> shiftGlobalBasePos(0, -1, 0));
	private Button buttonBaseposYIncrease = new Button(120, 165, 20, 20, new TextComponent("+"), button -> shiftGlobalBasePos(0, 1, 0));
	private Button buttonBaseposZDecrease = new Button(20, 185, 20, 20, new TextComponent("-"), button -> shiftGlobalBasePos(0, 0, -1));
	private Button buttonBaseposZIncrease = new Button(120, 185, 20, 20, new TextComponent("+"), button -> shiftGlobalBasePos(0, 0, 1));
	private EditBox textFieldX;
	private EditBox textFieldY;
	private EditBox textFieldZ;
	private Button buttonSetX = new Button(90, 145, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
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
	private Button buttonSetY = new Button(90, 165, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
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
	private Button buttonSetZ = new Button(90, 185, 30, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
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
		super(new TranslatableComponent("screen.buildguide.shapelist"));
	}
	
	@Override
	public void init() {
		titleNewShape = new TranslatableComponent("screen.buildguide.newshape").getString();
		titleShapes = new TranslatableComponent("screen.buildguide.shapes").getString();
		titleGlobalBasepos = new TranslatableComponent("screen.buildguide.globalbasepos").getString();
		titleVisible = new TranslatableComponent("screen.buildguide.visible").getString();
		titleNumberOfBlocks = new TranslatableComponent("screen.buildguide.numberofblocks").getString();
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new TextComponent("X"), button -> Minecraft.getInstance().setScreen(null));
		
		checkActive();
		
		addRenderableWidget(buttonClose);
		addRenderableWidget(buttonBack);
		addRenderableWidget(buttonNewShapePrevious);
		addRenderableWidget(buttonNewShapeNext);
		addRenderableWidget(buttonAdd);
		addRenderableWidget(buttonVisible);
		addRenderableWidget(buttonDelete);
		addRenderableWidget(buttonGlobalBasepos);
		addRenderableWidget(buttonBaseposXDecrease);
		addRenderableWidget(buttonBaseposXIncrease);
		addRenderableWidget(buttonBaseposYDecrease);
		addRenderableWidget(buttonBaseposYIncrease);
		addRenderableWidget(buttonBaseposZDecrease);
		addRenderableWidget(buttonBaseposZIncrease);
		addRenderableWidget(buttonSetX);
		addRenderableWidget(buttonSetY);
		addRenderableWidget(buttonSetZ);
		
		textFieldX = new EditBox(font, 40, 145, 50, 20, new TextComponent(""));
		textFieldX.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.x : "-");
		textFieldX.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldX);
		textFieldY = new EditBox(font, 40, 165, 50, 20, new TextComponent(""));
		textFieldY.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.y : "-");
		textFieldY.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldY);
		textFieldZ = new EditBox(font, 40, 185, 50, 20, new TextComponent(""));
		textFieldZ.setValue(StateManager.getState().isShapeAvailable() ? "" + (int) StateManager.getState().getCurrentShape().basePos.z : "-");
		textFieldZ.setTextColor(0xFFFFFF);
		addRenderableWidget(textFieldZ);
		
		shapeList = new ShapeList(minecraft, 150, 300, 25, height, 20, () -> {
			updateGlobalBasepos();
			if(StateManager.getState().isShapeAvailable()) buttonVisible.setChecked(StateManager.getState().getCurrentShape().visible);
		});
		
		addWidget(shapeList);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawShadow(matrixStack, title.getString(), (width - font.width(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawShadow(matrixStack, titleNewShape, (140 - font.width(titleNewShape)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleShapes, 150 + (150 - font.width(titleShapes)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleGlobalBasepos, (140 - font.width(titleGlobalBasepos)) / 2, 115, 0xFFFFFF);
		font.drawShadow(matrixStack, titleNumberOfBlocks, 305 + (100 - font.width(titleNumberOfBlocks)) / 2, 15, 0xFFFFFF);
		
		String newShapeName = new TranslatableComponent(ShapeRegistry.getTranslationKeys().get(newShapeId)).getString();
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
	
	private void updateGlobalBasepos() {
		if(StateManager.getState().isShapeAvailable()) {
			textFieldX.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.x);
			textFieldY.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.y);
			textFieldZ.setValue("" + (int) StateManager.getState().getCurrentShape().basePos.z);
		}else {
			textFieldX.setValue("-");
			textFieldY.setValue("-");
			textFieldZ.setValue("-");
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
		Vec3 pos = Minecraft.getInstance().player.position();
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
	
	public void addWidgetExternal(AbstractWidget widget) {
		addRenderableWidget(widget);
	}
}
