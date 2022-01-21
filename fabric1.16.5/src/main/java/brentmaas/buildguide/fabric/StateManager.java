package brentmaas.buildguide.fabric;

import java.util.HashMap;

import net.minecraft.client.MinecraftClient;

public class StateManager {
	private static HashMap<String,State> stateStore;
	
	public static void init() {
		stateStore = new HashMap<String,State>();
	}
	
	private static String getKey() {
		String host;
		if(MinecraftClient.getInstance().getServer() != null) {
			host = MinecraftClient.getInstance().getServer().getSaveProperties().getLevelName();
		}else if(MinecraftClient.getInstance().getCurrentServerEntry() != null) {
			host = MinecraftClient.getInstance().getCurrentServerEntry().address;
		}else {
			host = "unknown";
		}
		
		return host + "@" + MinecraftClient.getInstance().world.getRegistryKey().getValue();
	}
	
	public static State getState() {
		String key = getKey();
		
		if(!stateStore.containsKey(key)) stateStore.put(key, new State());
		
		return stateStore.get(key);
	}
}
