package brentmaas.buildguide.forge;

import java.util.HashMap;

import net.minecraft.client.Minecraft;

public class StateManager {
	private static HashMap<String,State> stateStore;
	
	public static void init() {
		stateStore = new HashMap<String,State>();
	}
	
	private static String getKey() {
		String host;
		if(Minecraft.getInstance().getSingleplayerServer() != null) {
			host = Minecraft.getInstance().getSingleplayerServer().getWorldData().getLevelName();
		}else if(Minecraft.getInstance().getCurrentServer() != null) {
			host = Minecraft.getInstance().getCurrentServer().ip;
		}else {
			host = "unknown";
		}
		
		return host + "@" + Minecraft.getInstance().level.dimension().location();
	}
	
	public static State getState() {
		String key = getKey();
		
		if(!stateStore.containsKey(key)) stateStore.put(key, new State());
		
		return stateStore.get(key);
	}
}
