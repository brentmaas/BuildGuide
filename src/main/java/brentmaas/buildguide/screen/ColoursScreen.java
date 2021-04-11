package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

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
	
	public ColoursScreen() {
		super(new TranslationTextComponent("screen.buildguide.colours"));
	}
	
	@Override
	protected void init() {
		titleShape = new TranslationTextComponent("screen.buildguide.shape").getString();
		titleBasepos = new TranslationTextComponent("screen.buildguide.basepos").getString();
		
		sliderShapeR = new Slider(0, 40, 100, 20, new StringTextComponent("R: "), new StringTextComponent(""), 0.0, 1.0, State.colourShapeR, true, true, null);
		sliderShapeG = new Slider(0, 60, 100, 20, new StringTextComponent("G: "), new StringTextComponent(""), 0.0, 1.0, State.colourShapeG, true, true, null);
		sliderShapeB = new Slider(0, 80, 100, 20, new StringTextComponent("B: "), new StringTextComponent(""), 0.0, 1.0, State.colourShapeB, true, true, null);
		sliderShapeA = new Slider(0, 100, 100, 20, new StringTextComponent("A: "), new StringTextComponent(""), 0.0, 1.0, State.colourShapeA, true, true, null);
		sliderBaseposR = new Slider(120, 40, 100, 20, new StringTextComponent("R: "), new StringTextComponent(""), 0.0, 1.0, State.colourBaseposR, true, true, null);
		sliderBaseposG = new Slider(120, 60, 100, 20, new StringTextComponent("G: "), new StringTextComponent(""), 0.0, 1.0, State.colourBaseposG, true, true, null);
		sliderBaseposB = new Slider(120, 80, 100, 20, new StringTextComponent("B: "), new StringTextComponent(""), 0.0, 1.0, State.colourBaseposB, true, true, null);
		sliderBaseposA = new Slider(120, 100, 100, 20, new StringTextComponent("A: "), new StringTextComponent(""), 0.0, 1.0, State.colourBaseposA, true, true, null);
		
		addButton(buttonBack);
		addButton(sliderShapeR);
		addButton(sliderShapeG);
		addButton(sliderShapeB);
		addButton(sliderShapeA);
		addButton(sliderBaseposR);
		addButton(sliderBaseposG);
		addButton(sliderBaseposB);
		addButton(sliderBaseposA);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleShape, (100 - font.getStringWidth(titleShape)) / 2, 25, 0xFFFFFF);
		font.drawStringWithShadow(matrixStack, titleBasepos, 120 + (100 - font.getStringWidth(titleBasepos)) / 2, 25, 0xFFFFFF);
		
		State.colourShapeR = (float) sliderShapeR.getValue();
		State.colourShapeG = (float) sliderShapeG.getValue();
		State.colourShapeB = (float) sliderShapeB.getValue();
		State.colourShapeA = (float) sliderShapeA.getValue();
		State.colourBaseposR = (float) sliderBaseposR.getValue();
		State.colourBaseposG = (float) sliderBaseposG.getValue();
		State.colourBaseposB = (float) sliderBaseposB.getValue();
		State.colourBaseposA = (float) sliderBaseposA.getValue();
	}
	
	@Override
	public void onClose() {
		State.colourShapeR = (float) sliderShapeR.getValue();
		State.colourShapeG = (float) sliderShapeG.getValue();
		State.colourShapeB = (float) sliderShapeB.getValue();
		State.colourShapeA = (float) sliderShapeA.getValue();
		State.colourBaseposR = (float) sliderBaseposR.getValue();
		State.colourBaseposG = (float) sliderBaseposG.getValue();
		State.colourBaseposB = (float) sliderBaseposB.getValue();
		State.colourBaseposA = (float) sliderBaseposA.getValue();
		
		super.onClose();
	}
}
