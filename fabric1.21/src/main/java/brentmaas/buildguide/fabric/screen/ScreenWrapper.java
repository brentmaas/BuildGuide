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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ScreenWrapper extends Screen implements IScreenWrapper {
	private BaseScreen attachedScreen;
	private DrawContext drawContextInstance;
	
	public ScreenWrapper(Text title) {
		super(title);
	}
	
	@Override
	public void init() {
		super.init();
		attachedScreen.init();
	}
	
	@Override
	public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
		super.render(drawContext, mouseX, mouseY, partialTicks);
		drawContextInstance = drawContext;
		attachedScreen.render();
	}
	
	@Override
	public void renderInGameBackground(DrawContext drawContext) {
		// Disable dark background
	}
	
	@Override
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
		((CheckboxRunnableButtonImpl) checkbox).initCheckboxIfNull();
		addDrawableChild(((CheckboxRunnableButtonImpl) checkbox).checkbox);
	}
	
	public void addSlider(ISlider slider) {
		addDrawableChild((SliderImpl) slider);
	}
	
	public void addShapeList(IShapeList shapeList) {
		addDrawableChild((ShapeListImpl) shapeList);
	}
	
	public void drawShadow(String text, int x, int y, int colour) {
		drawContextInstance.drawText(textRenderer, text, x, y, colour, true);
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
