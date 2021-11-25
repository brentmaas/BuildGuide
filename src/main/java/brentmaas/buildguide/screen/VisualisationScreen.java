package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class VisualisationScreen extends PropertyScreen{
	private String titleColours;
	private String titleShape;
	private String titleBasepos;
	
	private Button buttonClose;
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
	
	public VisualisationScreen() {
		super(new TranslationTextComponent("screen.buildguide.visualisation"));
	}
	
	@Override
	protected void init() {
		titleColours = new TranslationTextComponent("screen.buildguide.colours").getString();
		titleShape = new TranslationTextComponent("screen.buildguide.shape").getString();
		titleBasepos = new TranslationTextComponent("screen.buildguide.basepos").getString();
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new StringTextComponent("X"), button -> Minecraft.getInstance().displayGuiScreen(null));
		
		sliderShapeR = new Slider(0, 35, 100, 20, new StringTextComponent("R: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeR : 1.0);
		sliderShapeG = new Slider(0, 55, 100, 20, new StringTextComponent("G: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeG : 1.0);
		sliderShapeB = new Slider(0, 75, 100, 20, new StringTextComponent("B: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeB : 1.0);
		sliderShapeA = new Slider(0, 95, 100, 20, new StringTextComponent("A: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeA : 0.5);
		sliderBaseposR = new Slider(110, 35, 100, 20, new StringTextComponent("R: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposR : 1.0);
		sliderBaseposG = new Slider(110, 55, 100, 20, new StringTextComponent("G: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposG : 0.0);
		sliderBaseposB = new Slider(110, 75, 100, 20, new StringTextComponent("B: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposB : 0.0);
		sliderBaseposA = new Slider(110, 95, 100, 20, new StringTextComponent("A: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposA : 0.5);
		
		buttonSetShape = new Button(0, 115, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourShapeR = (float) sliderShapeR.getValue();
				StateManager.getState().getCurrentShape().colourShapeG = (float) sliderShapeG.getValue();
				StateManager.getState().getCurrentShape().colourShapeB = (float) sliderShapeB.getValue();
				StateManager.getState().getCurrentShape().colourShapeA = (float) sliderShapeA.getValue();
				
				StateManager.getState().updateCurrentShape();
			}
		});
		buttonSetBasepos = new Button(110, 115, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourBaseposR = (float) sliderBaseposR.getValue();
				StateManager.getState().getCurrentShape().colourBaseposG = (float) sliderBaseposG.getValue();
				StateManager.getState().getCurrentShape().colourBaseposB = (float) sliderBaseposB.getValue();
				StateManager.getState().getCurrentShape().colourBaseposA = (float) sliderBaseposA.getValue();
				
				StateManager.getState().updateCurrentShape();
			}
		});
		
		buttonDefaultShape = new Button(0, 135, 100, 20, new TranslationTextComponent("screen.buildguide.default"), button -> {
			sliderShapeR.setManualValue(1.0);
			sliderShapeG.setManualValue(1.0);
			sliderShapeB.setManualValue(1.0);
			sliderShapeA.setManualValue(0.5);
			sliderShapeR.updateSlider();
			sliderShapeG.updateSlider();
			sliderShapeB.updateSlider();
			sliderShapeA.updateSlider();
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourShapeR = 1.0f;
				StateManager.getState().getCurrentShape().colourShapeG = 1.0f;
				StateManager.getState().getCurrentShape().colourShapeB = 1.0f;
				StateManager.getState().getCurrentShape().colourShapeA = 0.5f;
				StateManager.getState().updateCurrentShape();
			}
		});
		buttonDefaultBasepos = new Button(110, 135, 100, 20, new TranslationTextComponent("screen.buildguide.default"), button -> {
			sliderBaseposR.setManualValue(1.0);
			sliderBaseposG.setManualValue(0.0);
			sliderBaseposB.setManualValue(0.0);
			sliderBaseposA.setManualValue(0.5);
			sliderBaseposR.updateSlider();
			sliderBaseposG.updateSlider();
			sliderBaseposB.updateSlider();
			sliderBaseposA.updateSlider();
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().getCurrentShape().colourBaseposR = 1.0f;
				StateManager.getState().getCurrentShape().colourBaseposG = 0.0f;
				StateManager.getState().getCurrentShape().colourBaseposB = 0.0f;
				StateManager.getState().getCurrentShape().colourBaseposA = 0.5f;
				StateManager.getState().updateCurrentShape();
			}
		});
		
		if(!StateManager.getState().isShapeAvailable()) {
			sliderShapeR.active = false;
			sliderShapeG.active = false;
			sliderShapeB.active = false;
			sliderShapeA.active = false;
			sliderBaseposR.active = false;
			sliderBaseposG.active = false;
			sliderBaseposB.active = false;
			sliderBaseposA.active = false;
			buttonSetShape.active = false;
			buttonDefaultShape.active = false;
			buttonSetBasepos.active = false;
			buttonDefaultBasepos.active = false;
		}
		
		addButton(buttonClose);
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
		
		addProperty(StateManager.getState().propertyDepthTest);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleColours, (210 - font.getStringWidth(titleColours)) / 2, 15, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleShape, (100 - font.getStringWidth(titleShape)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleBasepos, 110 + (100 - font.getStringWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
	}
}
