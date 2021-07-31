package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.State;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
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
		
		sliderShapeR = new Slider(0, 40, 100, 20, new StringTextComponent("R: "), 0.0, 1.0, BuildGuide.state.colourShapeR);
		sliderShapeG = new Slider(0, 60, 100, 20, new StringTextComponent("G: "), 0.0, 1.0, BuildGuide.state.colourShapeG);
		sliderShapeB = new Slider(0, 80, 100, 20, new StringTextComponent("B: "), 0.0, 1.0, BuildGuide.state.colourShapeB);
		sliderShapeA = new Slider(0, 100, 100, 20, new StringTextComponent("A: "), 0.0, 1.0, BuildGuide.state.colourShapeA);
		sliderBaseposR = new Slider(120, 40, 100, 20, new StringTextComponent("R: "), 0.0, 1.0, BuildGuide.state.colourBaseposR);
		sliderBaseposG = new Slider(120, 60, 100, 20, new StringTextComponent("G: "), 0.0, 1.0, BuildGuide.state.colourBaseposG);
		sliderBaseposB = new Slider(120, 80, 100, 20, new StringTextComponent("B: "), 0.0, 1.0, BuildGuide.state.colourBaseposB);
		sliderBaseposA = new Slider(120, 100, 100, 20, new StringTextComponent("A: "), 0.0, 1.0, BuildGuide.state.colourBaseposA);
		
		buttonSetShape = new Button(0, 120, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			BuildGuide.state.colourShapeR = (float) sliderShapeR.getValue();
			BuildGuide.state.colourShapeG = (float) sliderShapeG.getValue();
			BuildGuide.state.colourShapeB = (float) sliderShapeB.getValue();
			BuildGuide.state.colourShapeA = (float) sliderShapeA.getValue();
			
			State.updateCurrentShape();
		});
		buttonSetBasepos = new Button(120, 120, 100, 20, new TranslationTextComponent("screen.buildguide.set"), button -> {
			BuildGuide.state.colourBaseposR = (float) sliderBaseposR.getValue();
			BuildGuide.state.colourBaseposG = (float) sliderBaseposG.getValue();
			BuildGuide.state.colourBaseposB = (float) sliderBaseposB.getValue();
			BuildGuide.state.colourBaseposA = (float) sliderBaseposA.getValue();
			
			State.updateCurrentShape();
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
			BuildGuide.state.colourShapeR = 1.0f;
			BuildGuide.state.colourShapeG = 1.0f;
			BuildGuide.state.colourShapeB = 1.0f;
			BuildGuide.state.colourShapeA = 0.5f;
			State.updateCurrentShape();
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
	}
	
	class Slider extends AbstractSlider {
		private double min, max;
		private String prefix;
		
		public Slider(int x, int y, int width, int height, TextComponent name, double min, double max, double value) {
			super(x, y, width, height, new StringTextComponent(name.getString() + ": " + Math.round(10.0 * value) / 10.0), (value - min) / (max - min));
			this.min = min;
			this.max = max;
			prefix = name.getString() + ": ";
		}
		
		protected void func_230979_b_() { //updateMessage
			setMessage(new StringTextComponent(prefix + Math.round(10.0 * getValue()) / 10.0));
		}
		
		protected void func_230972_a_() { //applyValue
			
		}
		
		public void updateSlider() {
			func_230979_b_();
		}
		
		public void setManualValue(double value) {
			this.sliderValue = (value - min) / (max - min);
		}
		
		public double getValue() {
			return sliderValue * (max - min) + min;
		}
	}
}
