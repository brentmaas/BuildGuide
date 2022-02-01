package brentmaas.buildguide.common;

import java.util.HashMap;

public abstract class AbstractStateManager {
	private HashMap<String,State> stateStore;
	
	public AbstractStateManager() {
		stateStore = new HashMap<String,State>();
	}
	
	private String getKey() {
		String host = getWorldName();
		if(host == null) host = getServerAddress();
		if(host == null) host = "unknown";
		
		return host + "@" + getDimensionKey();
	}
	
	public State getState() {
		String key = getKey();
		
		if(!stateStore.containsKey(key)) stateStore.put(key, new State());
		
		return stateStore.get(key);
	}
	
	protected abstract String getWorldName();
	
	protected abstract String getServerAddress();
	
	protected abstract String getDimensionKey();
}
