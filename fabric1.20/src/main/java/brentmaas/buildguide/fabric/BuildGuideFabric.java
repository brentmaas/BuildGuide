package brentmaas.buildguide.fabric;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.fabric.screen.ScreenHandler;
import brentmaas.buildguide.fabric.screen.widget.WidgetHandler;
import brentmaas.buildguide.fabric.shape.ShapeHandler;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class BuildGuideFabric implements ClientModInitializer {
	private static final Logger logger = LogManager.getLogger();	
	
	public void onInitializeClient() {
		BuildGuide.registerClient(new ScreenHandler(), new WidgetHandler(), new StateManager(), new ShapeHandler(), new RenderHandler(), new LogHandler(logger), FabricLoader.getInstance().getConfigDir().toFile());
		BuildGuide.registerInputHandler(new InputHandler());
	}
}
