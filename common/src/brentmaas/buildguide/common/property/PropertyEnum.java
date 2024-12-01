package brentmaas.buildguide.common.property;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.BaseScreen;

public class PropertyEnum<T extends Enum<T>> extends Property<T> {
	private String[] names;
	
	public PropertyEnum(T value, Translatable name, Runnable onPress, String[] names) {
		super(value, name);
		this.names = names;
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 90, y, 20, height, new Translatable("<-"), () -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() - 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onPress != null) onPress.run();
		}));
		widgetList.add(BuildGuide.widgetHandler.createButton(x + 190, y, 20, height, new Translatable("->"), () -> {
			this.value = this.value.getDeclaringClass().getEnumConstants()[Math.floorMod(this.value.ordinal() + 1, this.value.getDeclaringClass().getEnumConstants().length)];
			if(onPress != null) onPress.run();
		}));
	}
	
	public void render(BaseScreen screen) {
		super.render(screen);
		drawStringCentred(screen, names[value.ordinal()], x + 150, y + 5, 0xFFFFFF);
	}
	
	public String getStringValue() {
		return value.name();
	}
	
	public boolean setValueFromString(String value) {
		for(int i = 0;i < this.value.getDeclaringClass().getEnumConstants().length;++i) {
			if(this.value.getDeclaringClass().getEnumConstants()[i].name().equals(value)) {
				setValue(this.value.getDeclaringClass().getEnumConstants()[i]);
				return true;
			}
		}
		return false;
	}
}
