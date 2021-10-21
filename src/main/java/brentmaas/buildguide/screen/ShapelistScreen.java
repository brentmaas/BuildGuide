package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.ShapeList;
import brentmaas.buildguide.shapes.ShapeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapelistScreen extends Screen{
	private String titleNewShape;
	private String titleShapes;
	
	private ShapeList shapeList;
	
	private int newShapeId = 0;
	
	private Button buttonBack = new Button(0, 0, 20, 20, new StringTextComponent("<-"), button -> Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen()));
	private Button buttonNewShapePrevious = new Button(0, 50, 20, 20, new StringTextComponent("<-"), button -> updateNewShape(-1));
	private Button buttonNewShapeNext = new Button(120, 50, 20, 20, new StringTextComponent("->"), button -> updateNewShape(1));
	private Button buttonAdd = new Button(0, 70, 140, 20, new TranslationTextComponent("screen.buildguide.add"), button -> {
		StateManager.getState().advancedModeShapes.add(ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(newShapeId)));
		StateManager.getState().resetBasepos(StateManager.getState().advancedModeShapes.size() - 1);
		StateManager.getState().advancedModeShapes.get(StateManager.getState().advancedModeShapes.size() - 1).update();
		shapeList.addEntryExternal(StateManager.getState().advancedModeShapes.size() - 1);
		System.out.println(StateManager.getState().advancedModeShapes.size());
	});
	private Button buttonDelete = new Button(0, 100, 140, 20, new TranslationTextComponent("screen.buildguide.delete"), button -> {
		if(shapeList.getSelected() != null) {
			StateManager.getState().advancedModeShapes.remove(shapeList.getSelected().getShapeId());
			shapeList.removeEntry(shapeList.getSelected());
			StateManager.getState().iAdvanced = 0;
		}
	});
	//TODO: World manager button
	
	protected ShapelistScreen() {
		super(new TranslationTextComponent("screen.buildguide.shapelist"));
		
		titleNewShape = new TranslationTextComponent("screen.buildguide.newshape").getString();
		titleShapes = new TranslationTextComponent("screen.buildguide.shapes").getString();
	}
	
	@Override
	protected void init() {
		addButton(buttonBack);
		addButton(buttonNewShapePrevious);
		addButton(buttonNewShapeNext);
		addButton(buttonAdd);
		addButton(buttonDelete);
		
		this.shapeList = new ShapeList(this.minecraft, this.width, this.height - 60, 60, this.height, 20);
		this.children.add(shapeList);
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
		font.drawStringWithShadow(matrixStack, titleShapes, (width - font.getStringWidth(titleShapes)) / 2, 5, 0xFFFFFF);
		
		String newShapeName = new TranslationTextComponent(ShapeRegistry.getTranslationKeys().get(newShapeId)).getString();
		font.drawStringWithShadow(matrixStack, newShapeName, 20 + (100 - font.getStringWidth(newShapeName)) / 2, 55, 0xFFFFFF);
		
		shapeList.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	private void updateNewShape(int di) {
		newShapeId = Math.floorMod(newShapeId + di, ShapeRegistry.getNumberOfShapes());
	}
}
