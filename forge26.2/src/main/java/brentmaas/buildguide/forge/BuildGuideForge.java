package brentmaas.buildguide.forge;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.forge.screen.ScreenHandler;
import brentmaas.buildguide.forge.screen.widget.WidgetHandler;
import brentmaas.buildguide.forge.shape.ShapeHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(BuildGuide.modid)
public class BuildGuideForge {
	private static final Logger logger = LogManager.getLogger();
	
	public BuildGuideForge(FMLJavaModLoadingContext context) {
		if(FMLEnvironment.dist.isClient()) {
			BusGroup busGroup = context.getModBusGroup();
			FMLClientSetupEvent.getBus(busGroup).addListener(this::onClientSetup);
			RegisterKeyMappingsEvent.BUS.addListener(this::onRegisterKeyMappings);
		}
	}
	
	private void onClientSetup(final FMLClientSetupEvent event) {
		BuildGuide.registerClient(new ScreenHandler(), new WidgetHandler(), new StateManager(), new ShapeHandler(), new RenderHandler(), new LogHandler(logger), new File(Minecraft.getInstance().gameDirectory, "config"));
	}
	
	private void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
		BuildGuide.registerInputHandler(new InputHandler(event));
	}
}
