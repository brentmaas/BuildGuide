package brentmaas.buildguide.screen;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapelistScreen extends Screen{
	private Button buttonBack = new Button(0, 0, 20, 20, new StringTextComponent("<-"), button -> Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen()));
	//TODO: World manager button
	
	protected ShapelistScreen() {
		super(new TranslationTextComponent("screen.buildguide.shapelist"));
	}
	
	@Override
	protected void init() {
		addButton(buttonBack);
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		font.drawStringWithShadow(matrixStack, title.getString(), (width - font.getStringWidth(title.getString())) / 2, 5, 0xFFFFFF);
	}
}
