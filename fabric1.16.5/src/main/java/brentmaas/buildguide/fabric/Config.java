package brentmaas.buildguide.fabric;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.fabricmc.loader.api.FabricLoader;

public class Config {
	private final static File configFile = new File(FabricLoader.getInstance().getConfigDir().toString() + "/buildguide-client.toml");
	//Initial value is default
	public static boolean debugGenerationTimingsEnabled = false;
	
	//TODO: Need better config stuff, currently mimics Forge config
	private static void initConfig() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));
			writer.write("\n[Debug]\n\t#Enable debug output telling you how long it took for a shape to generate. It's spams a lot in the debug log.\n\tdebugGenerationTimingsEnabled = " + debugGenerationTimingsEnabled + "\n\n");
			writer.close();
		} catch (IOException e) {
			BuildGuide.logger.error("Could not initialise config file");
			e.printStackTrace();
		}
	}
	
	//TODO: Need better config stuff, currently mimics Forge config
	public static void load() {
		if(!configFile.exists()) initConfig();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(configFile));
			String line = reader.readLine().trim();
			while(reader.ready()) {
				if(line.length() > 0 && line.charAt(0) != '[' && line.charAt(0) != '#') {
					String[] splitted = line.split(" = ", 2);
					if(splitted.length == 2) {
						switch(splitted[0]) {
						case "debugGenerationTimingsEnabled":
							debugGenerationTimingsEnabled = Boolean.parseBoolean(splitted[1]);
							break;
						}
					}
				}
				line = reader.readLine().trim();
			}
			reader.close();
		} catch (IOException e) {
			BuildGuide.logger.error("Could not read config file");
			e.printStackTrace();
		}
	}
}
