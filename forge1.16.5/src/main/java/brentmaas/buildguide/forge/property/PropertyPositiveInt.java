package brentmaas.buildguide.property;

import net.minecraft.util.text.TextComponent;

public class PropertyPositiveInt extends PropertyMinimumInt{
	
	
	public PropertyPositiveInt(int slot, int value, TextComponent name, Runnable onUpdate) {
		super(slot, value, name, onUpdate, 1);
	}
}
