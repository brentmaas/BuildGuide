package brentmaas.buildguide.fabric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import brentmaas.buildguide.common.AbstractConfig;
import brentmaas.buildguide.common.BuildGuide;
import net.fabricmc.loader.api.FabricLoader;

public class Config extends AbstractConfig {
	private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toString() + "/" + BuildGuide.modid + "-client.toml");
	private BufferedWriter writer;
	
	//TODO Need better config stuff, currently mimics Forge config
	public Config() {
		if(!configFile.exists()) {
			try {
				writer = new BufferedWriter(new FileWriter(configFile));
				writer.write("\n");
			}catch(IOException e) {
				BuildGuide.logHandler.error("Could not initialise config file");
				e.printStackTrace();
			}
		}
	}
	
	protected AbstractConfigElement<Boolean> createBooleanElement(String name, Boolean defaultValue, String comment){
		return new BooleanConfigElement(name, defaultValue, comment);
	}
	
	protected void pushCategory(String category) {
		if(writer != null) {
			try{
				writer.write("[" + category + "]\n");
			}catch(IOException e) {
				BuildGuide.logHandler.error("Could not initialise config file");
				e.printStackTrace();
			}
		}
	}
	
	protected void popCategory() {
		
	}
	
	@Override
	public void build() {
		super.build();
		if(writer != null) {
			try {
				writer.write("\n\n");
				writer.close();
			}catch(IOException e) {
				BuildGuide.logHandler.error("Could not initialise config file");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void load() {
		if(configFile.exists()) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(configFile));
				String line = reader.readLine().trim();
				while(reader.ready()) {
					if(line.length() > 0 && line.charAt(0) != '[' && line.charAt(0) != '#') {
						String[] splitted = line.split(" = ", 2);
						if(splitted.length == 2) {
							for(AbstractConfigElement<?> element: elements) {
								if(element.name == splitted[0]) {
									if(element instanceof BooleanConfigElement) {
										((BooleanConfigElement) element).value = Boolean.parseBoolean(splitted[1]);
									}else {
										BuildGuide.logHandler.error("Could not resolve config type for entry '" + splitted[0] + "'");
									}
								}
							}
						}
					}
					line = reader.readLine().trim();
				}
				reader.close();
			}catch(IOException e) {
				BuildGuide.logHandler.error("Could not read config file");
				e.printStackTrace();
			}
		}
	}
	
	protected class BooleanConfigElement extends AbstractConfigElement<Boolean> {
		
		
		protected BooleanConfigElement(String name, Boolean defaultValue, String comment) {
			super(name, defaultValue, comment);
		}
		
		protected void build() {
			if(Config.this.writer != null) {
				try {
					writer.write((comment != null ? "\t#" + comment : "\n") + "\t" + name + " = " + value + "\n");
				}catch(IOException e) {
					BuildGuide.logHandler.error("Could not initialise config file");
					e.printStackTrace();
				}
			}
		}
		
		protected void load() {
			
		}
	}
}
