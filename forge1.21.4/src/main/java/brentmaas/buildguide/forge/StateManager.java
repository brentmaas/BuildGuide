package brentmaas.buildguide.forge;

import brentmaas.buildguide.common.AbstractStateManager;
import net.minecraft.client.Minecraft;

public class StateManager extends AbstractStateManager {
	public StateManager() {
		super(Minecraft.getInstance().gameDirectory);
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
