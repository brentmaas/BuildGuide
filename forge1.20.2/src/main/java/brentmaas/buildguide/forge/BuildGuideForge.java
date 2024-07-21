package brentmaas.buildguide.forge;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.forge.screen.ScreenHandler;
import brentmaas.buildguide.forge.screen.widget.WidgetHandler;
import brentmaas.buildguide.forge.shape.ShapeHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(BuildGuide.modid)
public class BuildGuideForge {
	private static final Logger logger = LogManager.getLogger();
	
	public BuildGuideForge() {
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> new IExtensionPoint.DisplayTest(() -> IExtensionPoint.DisplayTest.IGNORESERVERONLY, (a, b) -> true));
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			BuildGuide.register(new InputHandler(), new ScreenHandler(), new WidgetHandler(), new StateManager(), new ShapeHandler(), new RenderHandler(), new LogHandler(logger), new File(Minecraft.getInstance().gameDirectory, "config"));
		});
	}
}
