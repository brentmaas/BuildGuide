package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.BaseScreen;

public class PropertyEnum<T extends Enum<T>> extends Property<T> {
	private String[] names;
	
	public PropertyEnum(T value, String name, Runnable onPress, String[] names) {
		super(value, name);
		this.names = names;
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, 20, height, "<-", () -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() - 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onPress != null) onPress.run();
		}));
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, 20, height, "->", () -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() + 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onPress != null) onPress.run();
		}));
	}
	
	public void render(BaseScreen screen) {
		super.render(screen);
		drawStringCentred(screen, names[value.ordinal()], x + 150, y + 5, 0xFFFFFF);
	}
}
