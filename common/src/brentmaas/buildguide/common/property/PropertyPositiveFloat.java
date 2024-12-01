package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class PropertyPositiveFloat extends PropertyMinimumFloat {
	public PropertyPositiveFloat(float value, Translatable name, Runnable onPress) {
		super(value, name, onPress, 0, false);
	}
}
