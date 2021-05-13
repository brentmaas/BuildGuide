package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.State;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.gui.widget.Slider;

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
		
		sliderShapeR = new Slider(0, 40, 100, 20, new StringTextComponent("R: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourShapeR, true, true, null);
		sliderShapeG = new Slider(0, 60, 100, 20, new StringTextComponent("G: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourShapeG, true, true, null);
		sliderShapeB = new Slider(0, 80, 100, 20, new StringTextComponent("B: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourShapeB, true, true, null);
		sliderShapeA = new Slider(0, 100, 100, 20, new StringTextComponent("A: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourShapeA, true, true, null);
		sliderBaseposR = new Slider(120, 40, 100, 20, new StringTextComponent("R: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourBaseposR, true, true, null);
		sliderBaseposG = new Slider(120, 60, 100, 20, new StringTextComponent("G: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourBaseposG, true, true, null);
		sliderBaseposB = new Slider(120, 80, 100, 20, new StringTextComponent("B: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourBaseposB, true, true, null);
		sliderBaseposA = new Slider(120, 100, 100, 20, new StringTextComponent("A: "), new StringTextComponent(""), 0.0, 1.0, BuildGuide.state.colourBaseposA, true, true, null);
		
		buttonSetShape = new Button(0, 120, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> State.updateCurrentShape());
		buttonSetBasepos = new Button(120, 120, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> State.updateCurrentShape());
		
		buttonDefaultShape = new Button(0, 140, 100, 20, new TranslationTextComponent("screen.buildguide.default"), button -> {
			sliderShapeR.setValue(1.0);
			sliderShapeG.setValue(1.0);
			sliderShapeB.setValue(1.0);
			sliderShapeA.setValue(0.5);
			sliderShapeR.updateSlider();
			sliderShapeG.updateSlider();
			sliderShapeB.updateSlider();
			sliderShapeA.updateSlider();
			BuildGuide.state.colourShapeR = 1.0f;
			BuildGuide.state.colourShapeG = 1.0f;
			BuildGuide.state.colourShapeB = 1.0f;
			BuildGuide.state.colourShapeA = 0.5f;
			State.updateCurrentShape();
		});
		buttonDefaultBasepos = new Button(120, 140, 100, 20, new TranslationTextComponent("screen.buildguide.default"), button -> {
			sliderBaseposR.setValue(1.0);
			sliderBaseposG.setValue(0.0);
			sliderBaseposB.setValue(0.0);
			sliderBaseposA.setValue(0.5);
			sliderBaseposR.updateSlider();
			sliderBaseposG.updateSlider();
			sliderBaseposB.updateSlider();
			sliderBaseposA.updateSlider();
			BuildGuide.state.colourBaseposR = 1.0f;
			BuildGuide.state.colourBaseposG = 0.0f;
			BuildGuide.state.colourBaseposB = 0.0f;
			BuildGuide.state.colourBaseposA = 0.5f;
			State.updateCurrentShape();
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
		
		BuildGuide.state.colourShapeR = (float) sliderShapeR.getValue();
		BuildGuide.state.colourShapeG = (float) sliderShapeG.getValue();
		BuildGuide.state.colourShapeB = (float) sliderShapeB.getValue();
		BuildGuide.state.colourShapeA = (float) sliderShapeA.getValue();
		BuildGuide.state.colourBaseposR = (float) sliderBaseposR.getValue();
		BuildGuide.state.colourBaseposG = (float) sliderBaseposG.getValue();
		BuildGuide.state.colourBaseposB = (float) sliderBaseposB.getValue();
		BuildGuide.state.colourBaseposA = (float) sliderBaseposA.getValue();
	}
	
	@Override
	public void onClose() {
		BuildGuide.state.colourShapeR = (float) sliderShapeR.getValue();
		BuildGuide.state.colourShapeG = (float) sliderShapeG.getValue();
		BuildGuide.state.colourShapeB = (float) sliderShapeB.getValue();
		BuildGuide.state.colourShapeA = (float) sliderShapeA.getValue();
		BuildGuide.state.colourBaseposR = (float) sliderBaseposR.getValue();
		BuildGuide.state.colourBaseposG = (float) sliderBaseposG.getValue();
		BuildGuide.state.colourBaseposB = (float) sliderBaseposB.getValue();
		BuildGuide.state.colourBaseposA = (float) sliderBaseposA.getValue();
		
		super.onClose();
	}
}
