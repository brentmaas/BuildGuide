package brentmaas.buildguide.screen;

import java.util.ArrayList;

import brentmaas.buildguide.property.Property;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class PropertyScreen extends Screen{
	protected ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	public PropertyScreen(Text title) {
		super(title);
	}
	
	protected void addProperty(Property<?> p) {
		properties.add(p);
		if(p.mightNeedTextFields()) p.addTextFields(textRenderer);
		p.addToPropertyScreen(this);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, textRenderer);
		}
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	public void addWidgetExternal(ClickableWidget widget) {
		addDrawableChild(widget);
	}
}
