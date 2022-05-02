package brentmaas.buildguide.fabric.screen;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.fabric.screen.widget.ButtonImpl;
import brentmaas.buildguide.fabric.screen.widget.CheckboxRunnableButtonImpl;
import brentmaas.buildguide.fabric.screen.widget.ShapeListImpl;
import brentmaas.buildguide.fabric.screen.widget.SliderImpl;
import brentmaas.buildguide.fabric.screen.widget.TextFieldImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ScreenWrapper extends Screen implements IScreenWrapper {
	private BaseScreen attachedScreen;
	private MatrixStack matrixStackInstance;
	
	public ScreenWrapper(Text title) {
		super(title);
	}
	
	public void init() {
		super.init();
		attachedScreen.init();
	}
	
	public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
		super.render(stack, mouseX, mouseY, partialTicks);
		matrixStackInstance = stack;
		attachedScreen.render();
	}
	
	public boolean shouldPause() {
		return attachedScreen.isPauseScreen();
	}
	
	public void attachScreen(BaseScreen screen) {
		attachedScreen = screen;
		screen.setWrapper(this);
	}
	
	public void show() {
		MinecraftClient.getInstance().setScreen(this);
	}
	
	public void addButton(IButton button) {
		addDrawableChild((ButtonImpl) button);
	}
	
	public void addTextField(ITextField textField) {
		addDrawableChild((TextFieldImpl) textField);
	}
	
	public void addCheckbox(ICheckboxRunnableButton checkbox) {
		addDrawableChild((CheckboxRunnableButtonImpl) checkbox);
	}
	
	public void addSlider(ISlider slider) {
		addDrawableChild((SliderImpl) slider);
	}
	
	public void addShapeList(IShapeList shapeList) {
		addDrawableChild((ShapeListImpl) shapeList);
	}
	
	public void drawShadow(String text, int x, int y, int colour) {
		textRenderer.drawWithShadow(matrixStackInstance, text, x, y, colour);
	}
	
	public int getTextWidth(String text) {
		return textRenderer.getWidth(text);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
