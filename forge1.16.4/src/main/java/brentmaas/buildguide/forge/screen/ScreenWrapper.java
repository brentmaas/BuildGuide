package brentmaas.buildguide.forge.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.forge.screen.widget.ButtonImpl;
import brentmaas.buildguide.forge.screen.widget.CheckboxRunnableButtonImpl;
import brentmaas.buildguide.forge.screen.widget.ShapeListImpl;
import brentmaas.buildguide.forge.screen.widget.SliderImpl;
import brentmaas.buildguide.forge.screen.widget.TextFieldImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TextComponent;

public class ScreenWrapper extends Screen implements IScreenWrapper {
	private BaseScreen attachedScreen;
	private MatrixStack poseStackInstance;
	
	private ArrayList<ShapeListImpl> shapeLists = new ArrayList<ShapeListImpl>();
	
	public ScreenWrapper(TextComponent title) {
		super(title);
	}
	
	@Override
	public void init() {
		super.init();
		attachedScreen.init();
	}
	
	@Override
	public void render(MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
		super.render(poseStack, mouseX, mouseY, partialTicks);
		poseStackInstance = poseStack;
		attachedScreen.render();
		for(ShapeListImpl shapeList: shapeLists) {
			shapeList.render(poseStack, mouseX, mouseY, partialTicks);
		}
	}
	
	@Override
	public boolean isPauseScreen() {
		return attachedScreen.isPauseScreen();
	}
	
	@Override
	public void onClose() {
		super.onClose();
		attachedScreen.onScreenClosed();
	}
	
	public void attachScreen(BaseScreen screen) {
		attachedScreen = screen;
		screen.setWrapper(this);
	}
	
	public void show() {
		Minecraft.getInstance().setScreen(this);
	}
	
	public void addButton(IButton button) {
		super.addButton((ButtonImpl) button);
	}
	
	public void addTextField(ITextField textField) {
		super.addButton((TextFieldImpl) textField);
	}
	
	public void addCheckbox(ICheckboxRunnableButton checkbox) {
		super.addButton((CheckboxRunnableButtonImpl) checkbox);
	}
	
	public void addSlider(ISlider slider) {
		super.addButton((SliderImpl) slider);
	}
	
	public void addShapeList(IShapeList shapeList) {
		super.addWidget((ShapeListImpl) shapeList);
		shapeLists.add((ShapeListImpl) shapeList);
	}
	
	public void drawShadow(String text, int x, int y, int colour) {
		font.drawShadow(poseStackInstance, text, x, y, colour);
	}
	
	public int getTextWidth(String text) {
		return font.width(text);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
