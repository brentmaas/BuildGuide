package brentmaas.buildguide.fabric.screen;

import brentmaas.buildguide.fabric.StateManager;
import brentmaas.buildguide.fabric.screen.widget.Slider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class VisualisationScreen extends PropertyScreen{
	private String titleColours;
	private String titleShape;
	private String titleBasepos;
	
	private ButtonWidget buttonClose;
	private ButtonWidget buttonBack = new ButtonWidget(0, 0, 20, 20, new LiteralText("<-"), ButtonWidget -> MinecraftClient.getInstance().setScreen(new BuildGuideScreen()));
	private Slider sliderShapeR;
	private Slider sliderShapeG;
	private Slider sliderShapeB;
	private Slider sliderShapeA;
	private Slider sliderBaseposR;
	private Slider sliderBaseposG;
	private Slider sliderBaseposB;
	private Slider sliderBaseposA;
	private ButtonWidget buttonSetShape;
	private ButtonWidget buttonSetBasepos;
	private ButtonWidget buttonDefaultShape;
	private ButtonWidget buttonDefaultBasepos;
	
	public VisualisationScreen() {
		super(new TranslatableText("screen.buildguide.visualisation"));
	}
	
	@Override
	protected void init() {
		titleColours = new TranslatableText("screen.buildguide.colours").getString();
		titleShape = new TranslatableText("screen.buildguide.shape").getString();
		titleBasepos = new TranslatableText("screen.buildguide.basepos").getString();
		
		buttonClose = new ButtonWidget(this.width - 20, 0, 20, 20, new LiteralText("X"), button -> MinecraftClient.getInstance().setScreen(null));
		
		sliderShapeR = new Slider(0, 35, 100, 20, new LiteralText("R"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeR : 1.0);
		sliderShapeG = new Slider(0, 55, 100, 20, new LiteralText("G"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeG : 1.0);
		sliderShapeB = new Slider(0, 75, 100, 20, new LiteralText("B"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeB : 1.0);
		sliderShapeA = new Slider(0, 95, 100, 20, new LiteralText("A"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeA : 0.5);
		sliderBaseposR = new Slider(110, 35, 100, 20, new LiteralText("R"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposR : 1.0);
		sliderBaseposG = new Slider(110, 55, 100, 20, new LiteralText("G"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposG : 1.0);
		sliderBaseposB = new Slider(110, 75, 100, 20, new LiteralText("B"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposB : 1.0);
		sliderBaseposA = new Slider(110, 95, 100, 20, new LiteralText("A"), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposA : 0.5);
		
		buttonSetShape = new ButtonWidget(0, 115, 100, 20, new TranslatableText("screen.buildguide.set"), button -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().setShapeColour((float) sliderShapeR.getValue(), (float) sliderShapeG.getValue(), (float) sliderShapeB.getValue(), (float) sliderShapeA.getValue());
			}
		});
		buttonSetBasepos = new ButtonWidget(110, 115, 100, 20, new TranslatableText("screen.buildguide.set"), button -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().setBaseposColour((float) sliderBaseposR.getValue(), (float) sliderBaseposG.getValue(), (float) sliderBaseposB.getValue(), (float) sliderBaseposA.getValue());
			}
		});
		
		buttonDefaultShape = new ButtonWidget(0, 135, 100, 20, new TranslatableText("screen.buildguide.default"), button -> {
			sliderShapeR.setManualValue(1.0);
			sliderShapeG.setManualValue(1.0);
			sliderShapeB.setManualValue(1.0);
			sliderShapeA.setManualValue(0.5);
			sliderShapeR.updateMessage();
			sliderShapeG.updateMessage();
			sliderShapeB.updateMessage();
			sliderShapeA.updateMessage();
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().setShapeColour(1.0f, 1.0f, 1.0f, 0.5f);
			}
		});
		buttonDefaultBasepos = new ButtonWidget(110, 135, 100, 20, new TranslatableText("screen.buildguide.default"), button -> {
			sliderBaseposR.setManualValue(1.0);
			sliderBaseposG.setManualValue(0.0);
			sliderBaseposB.setManualValue(0.0);
			sliderBaseposA.setManualValue(0.5);
			sliderBaseposR.updateMessage();
			sliderBaseposG.updateMessage();
			sliderBaseposB.updateMessage();
			sliderBaseposA.updateMessage();
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().setBaseposColour(1.0f, 0.0f, 0.0f, 0.5f);
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
		
		addDrawableChild(buttonClose);
		addDrawableChild(buttonBack);
		addDrawableChild(sliderShapeR);
		addDrawableChild(sliderShapeG);
		addDrawableChild(sliderShapeB);
		addDrawableChild(sliderShapeA);
		addDrawableChild(sliderBaseposR);
		addDrawableChild(sliderBaseposG);
		addDrawableChild(sliderBaseposB);
		addDrawableChild(sliderBaseposA);
		addDrawableChild(buttonSetShape);
		addDrawableChild(buttonSetBasepos);
		addDrawableChild(buttonDefaultShape);
		addDrawableChild(buttonDefaultBasepos);
		
		addProperty(StateManager.getState().propertyDepthTest);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		textRenderer.drawWithShadow(matrixStack, title.getString(), (width - textRenderer.getWidth(title.getString())) / 2, 5, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleColours, (210 - textRenderer.getWidth(titleColours)) / 2, 15, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleShape, (100 - textRenderer.getWidth(titleShape)) / 2, 25, 0xFFFFFF);
		textRenderer.drawWithShadow(matrixStack, titleBasepos, 110 + (100 - textRenderer.getWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
	}
}
