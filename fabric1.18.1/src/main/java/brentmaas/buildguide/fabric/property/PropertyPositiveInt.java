package brentmaas.buildguide.property;

import net.minecraft.text.Text;

public class PropertyPositiveInt extends PropertyMinimumInt{
	
	
	public PropertyPositiveInt(int slot, int value, Text name, Runnable onUpdate) {
		super(slot, value, name, onUpdate, 1);
	}
}
