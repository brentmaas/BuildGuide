package brentmaas.buildguide.forge.screen;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.IScreenWrapper;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;
import brentmaas.buildguide.common.screen.widget.ISelectorList;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.ISlider;
import brentmaas.buildguide.common.screen.widget.ITextField;
import brentmaas.buildguide.forge.screen.widget.ButtonImpl;
import brentmaas.buildguide.forge.screen.widget.CheckboxRunnableButtonImpl;
import brentmaas.buildguide.forge.screen.widget.SelectorListImpl;
import brentmaas.buildguide.forge.screen.widget.ShapeListImpl;
import brentmaas.buildguide.forge.screen.widget.SliderImpl;
import brentmaas.buildguide.forge.screen.widget.TextFieldImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

public class ScreenWrapper extends Screen implements IScreenWrapper {
	private BaseScreen attachedScreen;
	private GuiGraphics guiGraphicsInstance;
	
	public ScreenWrapper(Component title) {
		super(title);
	}
	
	@Override
	public void init() {
		super.init();
		attachedScreen.init();
	}
	
	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		guiGraphicsInstance = guiGraphics;
		attachedScreen.render();
	}
	
	@Override
	public void renderTransparentBackground(GuiGraphics guiGraphics) {
		// Disable dark background
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
		addRenderableWidget((ButtonImpl) button);
	}
	
	public void addTextField(ITextField textField) {
		addRenderableWidget((TextFieldImpl) textField);
	}
	
	public void addCheckbox(ICheckboxRunnableButton checkbox) {
		((CheckboxRunnableButtonImpl) checkbox).initCheckboxIfNull();
		addRenderableWidget(((CheckboxRunnableButtonImpl) checkbox).checkbox);
	}
	
	public void addSlider(ISlider slider) {
		slider.updateText();
		addRenderableWidget((SliderImpl) slider);
	}
	
	public void addShapeList(IShapeList shapeList) {
		addRenderableWidget((ShapeListImpl) shapeList);
	}
	
	public void addSelectorList(ISelectorList selectorList) {
		addRenderableWidget((SelectorListImpl) selectorList);
	}
	
	public void drawShadow(String text, int x, int y, int colour) {
		guiGraphicsInstance.drawString(font, text, x, y, ARGB.color((colour >> 16) & 0xFF, (colour >> 8) & 0xFF, colour & 0xFF), true);
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
