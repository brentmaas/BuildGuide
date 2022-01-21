package brentmaas.buildguide.forge;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.forge.shapes.ShapeCatenary;
import brentmaas.buildguide.forge.shapes.ShapeCircle;
import brentmaas.buildguide.forge.shapes.ShapeCuboid;
import brentmaas.buildguide.forge.shapes.ShapeEllipse;
import brentmaas.buildguide.forge.shapes.ShapeEllipsoid;
import brentmaas.buildguide.forge.shapes.ShapeLine;
import brentmaas.buildguide.forge.shapes.ShapeParabola;
import brentmaas.buildguide.forge.shapes.ShapeParaboloid;
import brentmaas.buildguide.forge.shapes.ShapePolygon;
import brentmaas.buildguide.forge.shapes.ShapeRegistry;
import brentmaas.buildguide.forge.shapes.ShapeSphere;
import brentmaas.buildguide.forge.shapes.ShapeTorus;
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
	
	public BuildGuide() {
		ModLoadingContext.get().registerExtensionPoint(ExtensionPoint.DISPLAYTEST, () -> Pair.of(() -> FMLNetworkConstants.IGNORESERVERONLY, (a, b) -> true));
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			ShapeRegistry.registerShape(ShapeCatenary.class);
			ShapeRegistry.registerShape(ShapeCircle.class);
			ShapeRegistry.registerShape(ShapeCuboid.class);
			ShapeRegistry.registerShape(ShapeEllipse.class);
			ShapeRegistry.registerShape(ShapeEllipsoid.class);
			ShapeRegistry.registerShape(ShapeLine.class);
			ShapeRegistry.registerShape(ShapeParabola.class);
			ShapeRegistry.registerShape(ShapeParaboloid.class);
			ShapeRegistry.registerShape(ShapePolygon.class);
			ShapeRegistry.registerShape(ShapeSphere.class);
			ShapeRegistry.registerShape(ShapeTorus.class);
			
			StateManager.init();
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
			ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientConfigSpec);
		});
	}
	
	private void setup(final FMLCommonSetupEvent event) {
		InputHandler.register();
		RenderHandler.register();
	}
	
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		logger.warn("Build Guide is a client-only mod! Running it on a server is discouraged!");
	}
}
