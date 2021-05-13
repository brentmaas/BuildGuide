package brentmaas.buildguide;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.input.Keybindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.FMLNetworkConstants;

@Mod(BuildGuide.modid)
public class BuildGuide {
	public static final String modid = "buildguide";
	public static final Logger logger = LogManager.getLogger();
	public static State state;
	
	public BuildGuide() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
			state = new State();
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, State.clientConfigSpec);
		});
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		Keybindings.register();
		RenderHandler.register();
	}
	
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		logger.warn("Build Guide is a client-only mod! Running it on a server is discouraged!");
	}
}
