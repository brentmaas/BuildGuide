package brentmaas.buildguide.forge;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@EventBusSubscriber(modid=BuildGuide.modid, bus=EventBusSubscriber.Bus.MOD)
public class Config {
	//Config
	//https://cadiboo.github.io/tutorials/1.15.1/forge/3.3-config/
	public static final ClientConfig clientConfig;
	public static final ForgeConfigSpec clientConfigSpec;
	static {
		final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		clientConfig = specPair.getLeft();
		clientConfigSpec = specPair.getRight();
	}
	public static boolean debugGenerationTimingsEnabled;
	
	public static void bakeConfig() {
		Config.debugGenerationTimingsEnabled = clientConfig.debugGenerationTimingsEnabled.get();
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfigEvent event) {
		if(event.getConfig().getSpec() == Config.clientConfigSpec) {
			Config.bakeConfig();
		}
	}
	
	public static class ClientConfig{
		public final BooleanValue debugGenerationTimingsEnabled;
		
		public ClientConfig(ForgeConfigSpec.Builder builder) {
			builder.push("Debug");
			debugGenerationTimingsEnabled = builder.comment("Enable debug output telling you how long it took for a shape to generate. It's spams a lot in the debug log.").translation("config.buildguide.debugGenerationTimingsEnabled").define("debugGenerationTimingsEnabled", false);
			builder.pop();
		}
	}
}
