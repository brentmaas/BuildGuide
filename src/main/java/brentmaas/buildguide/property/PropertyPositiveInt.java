package brentmaas.buildguide.property;

import net.minecraft.network.chat.BaseComponent;

public class PropertyPositiveInt extends PropertyMinimumInt{
	
	
	public PropertyPositiveInt(int x, int y, int value, BaseComponent name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate, 1);
	}
}
