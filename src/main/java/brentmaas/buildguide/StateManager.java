package brentmaas.buildguide;

import java.util.HashMap;

import net.minecraft.client.Minecraft;

public class StateManager {
	private static HashMap<String,State> stateStore;
	
	public static void init() {
		stateStore = new HashMap<String,State>();
	}
	
	private static String getKey() {
		String host;
		if(Minecraft.getInstance().getIntegratedServer() != null) {
			host = Minecraft.getInstance().getIntegratedServer().getServerConfiguration().getWorldName();
		}else if(Minecraft.getInstance().getCurrentServerData() != null) {
			host = Minecraft.getInstance().getCurrentServerData().serverIP;
		}else {
			host = "unknown";
		}
		
		return host + "@" + Minecraft.getInstance().world.getDimensionKey().getLocation();
	}
	
	public static State getState() {
		String key = getKey();
		
		if(!stateStore.containsKey(key)) stateStore.put(key, new State());
		
		return stateStore.get(key);
	}
}
