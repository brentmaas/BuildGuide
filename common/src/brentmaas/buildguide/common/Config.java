package brentmaas.buildguide.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Config {
	private File configFile;
	private ArrayList<ConfigElement<?>> configElements = new ArrayList<ConfigElement<?>>();
	
	public ConfigElement<Boolean> debugGenerationTimingsEnabled = new BooleanConfigElement("debugGenerationTimingsEnabled", false, "Enable debug output telling you how long it took for a shape to generate. It's spams a lot in the debug log.");
	public ConfigElement<Boolean> asyncEnabled = new BooleanConfigElement("asyncEnabled", true, "Enable asynchronous (multithreaded) shape generation.");
	public ConfigElement<Boolean> advancedRandomColorsDefaultEnabled = new BooleanConfigElement("advancedRandomColorsDefaultEnabled", false, "Enable random colors for new shapes in advanced mode by default.");
	
	public Config(String configFolder) {
		configFile = new File(configFolder + "buildguide.cfg");
		
		configElements.add(debugGenerationTimingsEnabled);
		configElements.add(asyncEnabled);
		configElements.add(advancedRandomColorsDefaultEnabled);
		
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
				if(element.comment != null) {
					writer.write("# " + element.comment + "\n");
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
		protected String key;
		public T value;
		protected String comment;
		
		public ConfigElement(String key, T defaultValue){
			this(key, defaultValue, null);
		}
		
		public ConfigElement(String key, T defaultValue, String comment) {
			this.key = key;
			value = defaultValue;
			this.comment = comment;
		}
		
		public abstract void setValue(String value);
	}
	
	public class BooleanConfigElement extends ConfigElement<Boolean> {
		
		
		public BooleanConfigElement(String key, Boolean defaultValue) {
			super(key, defaultValue);
		}
		
		public BooleanConfigElement(String key, Boolean defaultValue, String comment) {
			super(key, defaultValue, comment);
		}
		
		public void setValue(String value) {
			this.value = Boolean.parseBoolean(value);
		}
	}
}
