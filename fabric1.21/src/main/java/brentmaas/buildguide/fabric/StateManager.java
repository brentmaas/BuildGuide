package brentmaas.buildguide.fabric;

import brentmaas.buildguide.common.AbstractStateManager;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public class StateManager extends AbstractStateManager {
	public StateManager() {
		super(FabricLoader.getInstance().getGameDir().toFile());
	}
	
	protected String getWorldName() {
		if(Minecraft.getInstance().getSingleplayerServer() != null) return Minecraft.getInstance().getSingleplayerServer().getWorldData().getLevelName();
		return null;
	}
	
	protected String getServerAddress() {
		if(Minecraft.getInstance().getCurrentServer() != null) return Minecraft.getInstance().getCurrentServer().ip;
		return null;
	}
	
	protected String getDimensionKey() {
		return Minecraft.getInstance().level.dimension().location().toString();
	}
}
