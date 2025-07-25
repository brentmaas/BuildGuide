package brentmaas.buildguide.forge;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.forge.screen.ScreenHandler;
import brentmaas.buildguide.forge.screen.widget.WidgetHandler;
import brentmaas.buildguide.forge.shape.ShapeHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(BuildGuide.modid)
public class BuildGuideForge {
	private static final Logger logger = LogManager.getLogger();
	
	public BuildGuideForge() {
		if(FMLEnvironment.dist.isClient()) {
			BuildGuide.register(new InputHandler(), new ScreenHandler(), new WidgetHandler(), new StateManager(), new ShapeHandler(), new RenderHandler(), new LogHandler(logger), new File(Minecraft.getInstance().gameDirectory, "config"));
		}
	}
}
