package brentmaas.buildguide.screen;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.StateManager;
import brentmaas.buildguide.screen.widget.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

public class VisualisationScreen extends PropertyScreen{
	private String titleColours;
	private String titleShape;
	private String titleBasepos;
	
	private Button buttonClose;
	private Button buttonBack = new Button(0, 0, 20, 20, new TextComponent("<-"), button -> Minecraft.getInstance().setScreen(new BuildGuideScreen()));
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
		super(new TranslatableComponent("screen.buildguide.visualisation"));
	}
	
	@Override
	protected void init() {
		titleColours = new TranslatableComponent("screen.buildguide.colours").getString();
		titleShape = new TranslatableComponent("screen.buildguide.shape").getString();
		titleBasepos = new TranslatableComponent("screen.buildguide.basepos").getString();
		
		buttonClose = new Button(this.width - 20, 0, 20, 20, new TextComponent("X"), button -> Minecraft.getInstance().setScreen(null));
		
		sliderShapeR = new Slider(0, 35, 100, 20, new TextComponent("R: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeR : 1.0);
		sliderShapeG = new Slider(0, 55, 100, 20, new TextComponent("G: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeG : 1.0);
		sliderShapeB = new Slider(0, 75, 100, 20, new TextComponent("B: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeB : 1.0);
		sliderShapeA = new Slider(0, 95, 100, 20, new TextComponent("A: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourShapeA : 0.5);
		sliderBaseposR = new Slider(110, 35, 100, 20, new TextComponent("R: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposR : 1.0);
		sliderBaseposG = new Slider(110, 55, 100, 20, new TextComponent("G: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposG : 1.0);
		sliderBaseposB = new Slider(110, 75, 100, 20, new TextComponent("B: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposB : 1.0);
		sliderBaseposA = new Slider(110, 95, 100, 20, new TextComponent("A: "), 0.0, 1.0, StateManager.getState().isShapeAvailable() ? StateManager.getState().getCurrentShape().colourBaseposA : 0.5);
		
		buttonSetShape = new Button(0, 115, 100, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().setShapeColour((float) sliderShapeR.getValue(), (float) sliderShapeG.getValue(), (float) sliderShapeB.getValue(), (float) sliderShapeA.getValue());
			}
		});
		buttonSetBasepos = new Button(110, 115, 100, 20, new TranslatableComponent("screen.buildguide.set"), button -> {
			if(StateManager.getState().isShapeAvailable()) {
				StateManager.getState().setBaseposColour((float) sliderBaseposR.getValue(), (float) sliderBaseposG.getValue(), (float) sliderBaseposB.getValue(), (float) sliderBaseposA.getValue());
			}
		});
		
		buttonDefaultShape = new Button(0, 135, 100, 20, new TranslatableComponent("screen.buildguide.default"), button -> {
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
		buttonDefaultBasepos = new Button(110, 135, 100, 20, new TranslatableComponent("screen.buildguide.default"), button -> {
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
		
		addRenderableWidget(buttonClose);
		addRenderableWidget(buttonBack);
		addRenderableWidget(sliderShapeR);
		addRenderableWidget(sliderShapeG);
		addRenderableWidget(sliderShapeB);
		addRenderableWidget(sliderShapeA);
		addRenderableWidget(sliderBaseposR);
		addRenderableWidget(sliderBaseposG);
		addRenderableWidget(sliderBaseposB);
		addRenderableWidget(sliderBaseposA);
		addRenderableWidget(buttonSetShape);
		addRenderableWidget(buttonSetBasepos);
		addRenderableWidget(buttonDefaultShape);
		addRenderableWidget(buttonDefaultBasepos);
		
		addProperty(StateManager.getState().propertyDepthTest);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawShadow(matrixStack, title.getString(), (width - font.width(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawShadow(matrixStack, titleColours, (210 - font.width(titleColours)) / 2, 15, 0xFFFFFF);
		font.drawShadow(matrixStack, titleShape, (100 - font.width(titleShape)) / 2, 25, 0xFFFFFF);
		font.drawShadow(matrixStack, titleBasepos, 110 + (100 - font.width(titleBasepos)) / 2, 25, 0xFFFFFF);
	}
}
