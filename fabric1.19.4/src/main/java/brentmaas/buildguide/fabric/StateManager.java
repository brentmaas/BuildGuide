package brentmaas.buildguide.fabric;

import brentmaas.buildguide.common.AbstractStateManager;
import net.minecraft.client.MinecraftClient;

public class StateManager extends AbstractStateManager {
	
	
	protected String getWorldName() {
		if(MinecraftClient.getInstance().getServer() != null) return MinecraftClient.getInstance().getServer().getSaveProperties().getLevelName();
		return null;
	}
	
	protected String getServerAddress() {
		if(MinecraftClient.getInstance().getCurrentServerEntry() != null) return MinecraftClient.getInstance().getCurrentServerEntry().address;
		return null;
	}
	
	protected String getDimensionKey() {
		return MinecraftClient.getInstance().world.getRegistryKey().getValue().toString();
	}
}
