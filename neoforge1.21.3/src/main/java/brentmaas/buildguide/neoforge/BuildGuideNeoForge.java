package brentmaas.buildguide.neoforge;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.neoforge.screen.ScreenHandler;
import brentmaas.buildguide.neoforge.screen.widget.WidgetHandler;
import brentmaas.buildguide.neoforge.shape.ShapeHandler;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(BuildGuide.modid)
public class BuildGuideNeoForge {
	private static final Logger logger = LogManager.getLogger();
	
	public BuildGuideNeoForge() {
		if(FMLLoader.getDist() == Dist.CLIENT) {
			BuildGuide.register(new InputHandler(), new ScreenHandler(), new WidgetHandler(), new StateManager(), new ShapeHandler(), new RenderHandler(), new LogHandler(logger), new File(Minecraft.getInstance().gameDirectory, "config"));
		}
	}
}
