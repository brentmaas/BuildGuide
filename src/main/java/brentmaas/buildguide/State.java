package brentmaas.buildguide;

import org.apache.commons.lang3.tuple.Pair;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeEmpty;
import brentmaas.buildguide.shapes.ShapeLine;
import brentmaas.buildguide.shapes.ShapeSphere;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;

@EventBusSubscriber(modid=BuildGuide.modid, bus=EventBusSubscriber.Bus.MOD)
public class State {
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
	
	public static Shape[] shapeStore = {new ShapeEmpty(), new ShapeLine(), new ShapeCuboid(), new ShapeCircle(), new ShapeSphere()};
	public static int i_shape = 0;
	public static Vector3d basePos = null;
	public static PropertyBoolean propertyDepthTest = new PropertyBoolean(0, 80, true, new TranslationTextComponent("screen.buildguide.depthtest"), null);
	
	public static float colourShapeR = 1.0f;
	public static float colourShapeG = 1.0f;
	public static float colourShapeB = 1.0f;
	public static float colourShapeA = 0.5f;
	
	public static float colourBaseposR = 1.0f;
	public static float colourBaseposG = 0.0f;
	public static float colourBaseposB = 0.0f;
	public static float colourBaseposA = 0.5f;
	
	public static void bakeConfig() {
		debugGenerationTimingsEnabled = clientConfig.debugGenerationTimingsEnabled.get();
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		if(event.getConfig().getSpec() == State.clientConfigSpec) {
			State.bakeConfig();
		}
	}
	
	public static Shape getCurrentShape() {
		return shapeStore[i_shape];
	}
	
	public static void updateCurrentShape() {
		shapeStore[i_shape].update();
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
