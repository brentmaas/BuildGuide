package brentmaas.buildguide.forge.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.forge.property.Property;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class PropertyScreen extends Screen{
	protected ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	public PropertyScreen(Component title) {
		super(title);
	}
	
	protected void addProperty(Property<?> p) {
		properties.add(p);
		if(p.mightNeedTextFields()) p.addTextFields(font);
		p.addToPropertyScreen(this);
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	public void addWidgetExternal(AbstractWidget widget) {
		addRenderableWidget(widget);
	}
}
