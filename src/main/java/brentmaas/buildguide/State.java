package brentmaas.buildguide;

import org.apache.commons.lang3.tuple.Pair;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeEmpty;
import brentmaas.buildguide.shapes.ShapeLine;
import brentmaas.buildguide.shapes.ShapePolygon;
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
	public boolean debugGenerationTimingsEnabled;
	
	public Shape[] shapeStore = {new ShapeEmpty(), new ShapeLine(), new ShapeCuboid(), new ShapePolygon(), new ShapeCircle(), new ShapeSphere()};
	public int i_shape = 0;
	public Vector3d basePos = null;
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(0, 80, true, new TranslationTextComponent("screen.buildguide.depthtest"), null);
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
	public static void bakeConfig() {
		BuildGuide.state.debugGenerationTimingsEnabled = clientConfig.debugGenerationTimingsEnabled.get();
	}
	
	@SubscribeEvent
	public static void onModConfigEvent(final ModConfig.ModConfigEvent event) {
		if(event.getConfig().getSpec() == State.clientConfigSpec) {
			State.bakeConfig();
		}
	}
	
	public static Shape getCurrentShape() {
		return BuildGuide.state.shapeStore[BuildGuide.state.i_shape];
	}
	
	public static void updateCurrentShape() {
		BuildGuide.state.shapeStore[BuildGuide.state.i_shape].update();
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
