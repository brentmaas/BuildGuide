package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.PropertyScreen;

public class PropertyEnum<T extends Enum<T>> extends Property<T> {
	private String[] names;
	
	public PropertyEnum(T value, String name, Runnable onPress, String[] names) {
		super(value, name);
		this.names = names;
		buttonList.add(BuildGuide.widgetHandler.createButton(90, y, 20, height, "<-", () -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() - 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onPress != null) onPress.run();
		}));
		buttonList.add(BuildGuide.widgetHandler.createButton(190, y, 20, height, "->", () -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() + 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onPress != null) onPress.run();
		}));
	}
	
	public void render(PropertyScreen screen) {
		super.render(screen);
		drawStringCentred(screen, names[value.ordinal()], 150, y + 5, 0xFFFFFF);
	}
}
