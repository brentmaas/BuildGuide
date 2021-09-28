package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ColoursScreen extends Screen{
	private String titleShape;
	private String titleBasepos;
	
	private Button buttonBack = new Button(0, 0, 20, 20, new StringTextComponent("<-"), button -> Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen()));
	private Slider sliderShapeR;
	private Slider sliderShapeG;
	private Slider sliderShapeB;
	private Slider sliderShapeA;
	private Slider sliderBaseposR;
	private Slider sliderBaseposG;
	private Slider sliderBaseposB;
	private Slider sliderBaseposA;
	private Button buttonSetShape;
	private Button buttonSetBasepos;
	private Button buttonDefaultShape;
	private Button buttonDefaultBasepos;
	
	public ColoursScreen() {
		super(new TranslationTextComponent("screen.buildguide.colours"));
	}
	
	@Override
	protected void init() {
		titleShape = new TranslationTextComponent("screen.buildguide.shape").getString();
		titleBasepos = new TranslationTextComponent("screen.buildguide.basepos").getString();
		
		sliderShapeR = new Slider(0, 40, 100, 20, new StringTextComponent("R: "), 0.0, 1.0, StateManager.getState().colourShapeR);
		sliderShapeG = new Slider(0, 60, 100, 20, new StringTextComponent("G: "), 0.0, 1.0, StateManager.getState().colourShapeG);
		sliderShapeB = new Slider(0, 80, 100, 20, new StringTextComponent("B: "), 0.0, 1.0, StateManager.getState().colourShapeB);
		sliderShapeA = new Slider(0, 100, 100, 20, new StringTextComponent("A: "), 0.0, 1.0, StateManager.getState().colourShapeA);
		sliderBaseposR = new Slider(120, 40, 100, 20, new StringTextComponent("R: "), 0.0, 1.0, StateManager.getState().colourBaseposR);
		sliderBaseposG = new Slider(120, 60, 100, 20, new StringTextComponent("G: "), 0.0, 1.0, StateManager.getState().colourBaseposG);
		sliderBaseposB = new Slider(120, 80, 100, 20, new StringTextComponent("B: "), 0.0, 1.0, StateManager.getState().colourBaseposB);
		sliderBaseposA = new Slider(120, 100, 100, 20, new StringTextComponent("A: "), 0.0, 1.0, StateManager.getState().colourBaseposA);
		
		buttonSetShape = new Button(0, 120, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			StateManager.getState().colourShapeR = (float) sliderShapeR.getValue();
			StateManager.getState().colourShapeG = (float) sliderShapeG.getValue();
			StateManager.getState().colourShapeB = (float) sliderShapeB.getValue();
			StateManager.getState().colourShapeA = (float) sliderShapeA.getValue();
			
			StateManager.getState().updateCurrentShape();
		});
		buttonSetBasepos = new Button(120, 120, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			StateManager.getState().colourBaseposR = (float) sliderBaseposR.getValue();
			StateManager.getState().colourBaseposG = (float) sliderBaseposG.getValue();
			StateManager.getState().colourBaseposB = (float) sliderBaseposB.getValue();
			StateManager.getState().colourBaseposA = (float) sliderBaseposA.getValue();
			
			StateManager.getState().updateCurrentShape();
		});
		
		buttonDefaultShape = new Button(0, 140, 100, 20, new TranslationTextComponent("screen.buildguide.default"), button -> {
			sliderShapeR.setManualValue(1.0);
			sliderShapeG.setManualValue(1.0);
			sliderShapeB.setManualValue(1.0);
			sliderShapeA.setManualValue(0.5);
			sliderShapeR.updateSlider();
			sliderShapeG.updateSlider();
			sliderShapeB.updateSlider();
			sliderShapeA.updateSlider();
			StateManager.getState().colourShapeR = 1.0f;
			StateManager.getState().colourShapeG = 1.0f;
			StateManager.getState().colourShapeB = 1.0f;
			StateManager.getState().colourShapeA = 0.5f;
			StateManager.getState().updateCurrentShape();
		});
		buttonDefaultBasepos = new Button(120, 140, 100, 20, new TranslationTextComponent("screen.buildguide.default"), button -> {
			sliderBaseposR.setManualValue(1.0);
			sliderBaseposG.setManualValue(0.0);
			sliderBaseposB.setManualValue(0.0);
			sliderBaseposA.setManualValue(0.5);
			sliderBaseposR.updateSlider();
			sliderBaseposG.updateSlider();
			sliderBaseposB.updateSlider();
			sliderBaseposA.updateSlider();
			StateManager.getState().colourBaseposR = 1.0f;
			StateManager.getState().colourBaseposG = 0.0f;
			StateManager.getState().colourBaseposB = 0.0f;
			StateManager.getState().colourBaseposA = 0.5f;
			StateManager.getState().updateCurrentShape();
		});
		
		addButton(buttonBack);
		addButton(sliderShapeR);
		addButton(sliderShapeG);
		addButton(sliderShapeB);
		addButton(sliderShapeA);
		addButton(sliderBaseposR);
		addButton(sliderBaseposG);
		addButton(sliderBaseposB);
		addButton(sliderBaseposA);
		addButton(buttonSetShape);
		addButton(buttonSetBasepos);
		addButton(buttonDefaultShape);
		addButton(buttonDefaultBasepos);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleShape, (100 - font.getStringWidth(titleShape)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleBasepos, 120 + (100 - font.getStringWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
	}
}
