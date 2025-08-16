package brentmaas.buildguide.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public abstract class AbstractStateManager {
	private static final String PERSISTENCE_EXTENSION = ".dat";
	
	private HashMap<String,State> stateStore;
	private File persistenceFolder;
	
	public AbstractStateManager(File gameDirectory) {
		stateStore = new HashMap<String,State>();
		persistenceFolder = new File(gameDirectory, "buildguide");
		if(!persistenceFolder.exists() || !persistenceFolder.isDirectory()) {
			persistenceFolder.mkdir();
		}
	}
	
	private String getKey() {
		String host = getWorldName();
		if(host == null) host = getServerAddress();
		if(host == null) host = "unknown";
		
		return host + "@" + getDimensionKey();
	}
	
	public boolean isStatePresent(String key) {
		return stateStore.containsKey(key);
	}
	
	public boolean isStatePresent() {
		return isStatePresent(getKey());
	}
	
	public State getState() {
		String key = getKey();
		
		if(!isStatePresent(key)) {
			stateStore.put(key, new State());
			File persistenceFile = getPersistenceFile(key);
			if(BuildGuide.config.persistenceEnabled.value && persistenceFile.exists()) {
				try {
					stateStore.get(key).loadPersistence(persistenceFile);
				}catch(IOException e) {
					BuildGuide.logHandler.sendChatMessage("Build Guide persistence failed to load: " + e.getMessage());
					BuildGuide.logHandler.error(e.getMessage() + "\n" + e.getStackTrace());
				}
			}
		}
		
		return stateStore.get(key);
	}
	
	private File getPersistenceFile(String key) {
		// https://stackoverflow.com/a/31976060/3874664
		String safeKey = key.replace("/", ".")
							.replace("<", ".")
							.replace(">", ".")
							.replace(":", ".")
							.replace("\"", ".")
							.replace("\\", ".")
							.replace("|", ".")
							.replace("?", ".")
							.replace("*", ".");
		return new File(persistenceFolder, safeKey + PERSISTENCE_EXTENSION);
	}
	
	public void savePersistence() throws IOException {
		String key = getKey();
		stateStore.get(key).savePersistence(getPersistenceFile(key));
	}
	
	protected abstract String getWorldName();
	
	protected abstract String getServerAddress();
	
	protected abstract String getDimensionKey();
}
