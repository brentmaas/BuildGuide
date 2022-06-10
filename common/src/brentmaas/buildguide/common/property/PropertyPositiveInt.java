package brentmaas.buildguide.common.property;

public class PropertyPositiveInt extends PropertyMinimumInt {
	
	
	public PropertyPositiveInt(int value, String name, Runnable onPress) {
		super(value, name, onPress, 1);
	}
}
