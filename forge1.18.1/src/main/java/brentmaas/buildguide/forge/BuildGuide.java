package brentmaas.buildguide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.shapes.ShapeCatenary;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeEllipse;
import brentmaas.buildguide.shapes.ShapeEllipsoid;
import brentmaas.buildguide.shapes.ShapeLine;
import brentmaas.buildguide.shapes.ShapeParabola;
import brentmaas.buildguide.shapes.ShapeParaboloid;
import brentmaas.buildguide.shapes.ShapePolygon;
import brentmaas.buildguide.shapes.ShapeRegistry;
import brentmaas.buildguide.shapes.ShapeSphere;
import brentmaas.buildguide.shapes.ShapeTorus;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkConstants;

@Mod(BuildGuide.modid)
public class BuildGuide {
	public static final String modid = "buildguide";
	public static final Logger logger = LogManager.getLogger();
	
	public BuildGuide() {
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true));
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
	public void onServerStarting(ServerStartingEvent event) {
		logger.warn("Build Guide is a client-only mod! Running it on a server is discouraged!");
	}
}
