package brentmaas.buildguide.common;

import java.util.ArrayList;

public abstract class AbstractConfig {
	protected ArrayList<AbstractConfigElement<?>> elements = new ArrayList<AbstractConfigElement<?>>();
	
	public AbstractConfigElement<Boolean> debugGenerationTimingsEnabled;
	
	public AbstractConfig() {
		debugGenerationTimingsEnabled = createBooleanElement("debugGenerationTimingsEnabled", false, "Enable debug output telling you how long it took for a shape to generate. It's spams a lot in the debug log.");
		
		addConfigElement(debugGenerationTimingsEnabled);
	}
	
	protected abstract AbstractConfigElement<Boolean> createBooleanElement(String name, Boolean defaultValue, String comment);
	
	protected abstract void pushCategory(String category);
	
	protected abstract void popCategory();
	
	public void build() {
		pushCategory("Debug");
		for(AbstractConfigElement<?> element: elements) {
			element.build();
		}
		popCategory();
	}
	
	public void load() {
		for(AbstractConfigElement<?> element: elements) {
			element.load();
		}
	}
	
	protected void addConfigElement(AbstractConfigElement<?> element) {
		elements.add(element);
	}
	
	protected AbstractConfigElement<Boolean> createBooleanElement(String name, Boolean defaultValue){
		return createBooleanElement(name, defaultValue, null);
	}
	
	public abstract class AbstractConfigElement<T> {
		public T value;
		protected String name;
		protected String comment;
		
		public AbstractConfigElement(String name, T defaultValue, String comment){
			this.name = name;
			value = defaultValue;
			this.comment = comment;
		}
		
		protected abstract void build();
		
		protected abstract void load();
	}
}
