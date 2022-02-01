package brentmaas.buildguide.common.property;

public class PropertyPositiveInt extends PropertyMinimumInt {
	
	
	public PropertyPositiveInt(int slot, int value, String name, Runnable onPress) {
		super(slot, value, name, onPress, 1);
	}
}
