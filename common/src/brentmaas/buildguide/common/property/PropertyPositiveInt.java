package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class PropertyPositiveInt extends PropertyMinimumInt {
	public PropertyPositiveInt(int value, Translatable name, Runnable onPress) {
		super(value, name, onPress, 1);
	}
}
