package brentmaas.buildguide.common.property;

public class PropertyPositiveFloat extends PropertyMinimumFloat {
	public PropertyPositiveFloat(float value, String name, Runnable onPress) {
		super(value, name, onPress, 0, false);
	}
}
