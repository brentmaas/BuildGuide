package brentmaas.buildguide.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class Config {
	private File configFile;
	public ArrayList<ConfigElement<?>> configElements = new ArrayList<ConfigElement<?>>();
	
	public ConfigElement<Boolean> asyncEnabled = new BooleanConfigElement("asyncEnabled", "config.buildguide.asyncEnabled", true, "config.buildguide.asyncEnabledComment");
	public ConfigElement<Boolean> shapeListRandomColorsDefaultEnabled = new BooleanConfigElement("shapeListRandomColorsDefaultEnabled", "config.buildguide.shapeListRandomColorsDefaultEnabled", false, "config.buildguide.shapeListRandomColorsDefaultEnabledComment");
	public ConfigElement<Boolean> persistenceEnabled = new BooleanConfigElement("persistenceEnabled", "config.buildguide.persistenceEnabled", false, "config.buildguide.persistenceEnabledComment");
	
	public Config(File configFolder) {
		configFile = new File(configFolder, "buildguide.cfg");
		
		configElements.add(asyncEnabled);
		configElements.add(shapeListRandomColorsDefaultEnabled);
		configElements.add(persistenceEnabled);
		
		if(!configFile.exists()) {
			write();
		}else {
			load();
		}
	}
	
	public void write() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			for(ConfigElement<?> element: configElements) {
				if(element.commentTranslationKey != null) {
					writer.write("# " + new Translatable(element.commentTranslationKey) + "\n");
				}
				writer.write(element.key + " = " + element.value + "\n");
			}
			writer.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void load() {
		try {
			ArrayList<String> keys = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			String line;
			while((line = reader.readLine()) != null) {
				line = line.trim();
				if(line.length() == 0) continue;
				if(line.charAt(0) == '#') continue;
				String[] split = line.split(" = ", 2);
				if(split.length < 2) {
					BuildGuide.logHandler.errorOrHigher("Invalid config line: '" + line + "'");
					continue;
				}
				keys.add(split[0]);
				values.add(split[1]);
			}
			reader.close();
			Boolean[] present = new Boolean[configElements.size()];
			Arrays.fill(present, false);
			for(int i = 0;i < keys.size();++i) {
				for(int j = 0;j < configElements.size();++j) {
					if(keys.get(i).equals(configElements.get(j).key)) {
						try {
							configElements.get(j).setValue(values.get(i));
						}catch(Exception e) {
							BuildGuide.logHandler.errorOrHigher("Could not read value '" + values.get(i) + "' for config '" + keys.get(j) + "', keeping '" + configElements.get(j).value + "'");
						}
						present[j] = true;
						break;
					}
				}
			}
			if(Arrays.asList(present).contains(false)) {
				write();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract class ConfigElement<T> {
		public String key;
		public String translationKey;
		public T value;
		private T defaultValue;
		public String commentTranslationKey;
		
		public ConfigElement(String key, String translationKey, T defaultValue){
			this(key, translationKey, defaultValue, null);
		}
		
		public ConfigElement(String key, String translationKey, T defaultValue, String commentTranslationKey) {
			this.key = key;
			this.translationKey = translationKey;
			value = defaultValue;
			this.defaultValue = defaultValue;
			this.commentTranslationKey = commentTranslationKey;
		}
		
		public T getDefault() {
			return defaultValue;
		}
		
		public void setValue(T value) {
			this.value = value;
		}
		
		public abstract void setValue(String value);
	}
	
	public class BooleanConfigElement extends ConfigElement<Boolean> {
		
		
		public BooleanConfigElement(String key, String translationKey, Boolean defaultValue) {
			super(key, translationKey, defaultValue);
		}
		
		public BooleanConfigElement(String key, String translationKey, Boolean defaultValue, String commentTranslationKey) {
			super(key, translationKey, defaultValue, commentTranslationKey);
		}
		
		public void setValue(String value) {
			this.value = Boolean.parseBoolean(value);
		}
	}
}
