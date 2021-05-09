package brentmaas.buildguide.property;

import net.minecraft.util.text.TextComponent;

public class PropertyPositiveInt extends PropertyMinimumInt{
	
	
	public PropertyPositiveInt(int x, int y, int value, TextComponent name, Runnable onUpdate) {
		super(x, y, value, name, onUpdate, 1);
	}
}
