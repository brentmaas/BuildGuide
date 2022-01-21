package brentmaas.buildguide.forge.screen;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.forge.property.Property;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.ITextComponent;

public abstract class PropertyScreen extends Screen{
	protected ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	public PropertyScreen(ITextComponent title) {
		super(title);
	}
	
	protected void addProperty(Property<?> p) {
		properties.add(p);
		if(p.mightNeedTextFields()) p.addTextFields(font);
		p.addToPropertyScreen(this);
	}
	
	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		for(Property<?> p: properties) {
			p.render(matrixStack, mouseX, mouseY, partialTicks, font);
		}
	}
	
	@Override
	public boolean isPauseScreen() {
		return false;
	}
	
	public void addButtonExternal(AbstractButton button) {
		addButton(button);
	}
	
	public void addTextFieldExternal(TextFieldWidget tfw) {
		children.add(tfw);
	}
}
