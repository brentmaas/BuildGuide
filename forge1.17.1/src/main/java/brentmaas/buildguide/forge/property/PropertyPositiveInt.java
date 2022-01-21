package brentmaas.buildguide.property;

import net.minecraft.network.chat.BaseComponent;

public class PropertyPositiveInt extends PropertyMinimumInt{
	
	
	public PropertyPositiveInt(int slot, int value, BaseComponent name, Runnable onUpdate) {
		super(slot, value, name, onUpdate, 1);
	}
}
