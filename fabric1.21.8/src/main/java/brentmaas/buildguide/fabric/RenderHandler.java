package brentmaas.buildguide.fabric;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.AbstractRenderHandler;
import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.fabric.shape.ShapeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.phys.Vec3;

public class RenderHandler extends AbstractRenderHandler {
	private static final RenderPipeline.Snippet BUILD_GUIDE_SNIPPET = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withBlend(new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA))
			.withCull(true)
			.withDepthWrite(false)
			.buildSnippet();
	private static final RenderPipeline BUILD_GUIDE = RenderPipeline.builder(BUILD_GUIDE_SNIPPET)
			.withLocation(ResourceLocation.fromNamespaceAndPath(BuildGuide.modid, "pipeline/build_guide"))
			.withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
			.build();
	private static final RenderPipeline BUILD_GUIDE_DEPTH_TEST = RenderPipeline.builder(BUILD_GUIDE_SNIPPET)
			.withLocation(ResourceLocation.fromNamespaceAndPath(BuildGuide.modid, "pipeline/build_guide_depth_test"))
			.withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
			.build();
	
	public void register() {
		try {
			Class<?> irisApiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
			Class<?> irisProgramClass = Class.forName("net.irisshaders.iris.api.v0.IrisProgram");
			
			Method irisGetInstanceMethod = irisApiClass.getMethod("getInstance");
			Method irisAssignPipelineMethod = irisApiClass.getMethod("assignPipeline", RenderPipeline.class, irisProgramClass);
			Method irisProgramEnumValueOfMethod = irisProgramClass.getMethod("valueOf", String.class);
			
			Object irisApiInstance = irisGetInstanceMethod.invoke(irisApiClass);
			Object irisProgramEnumBasic = irisProgramEnumValueOfMethod.invoke(irisProgramClass, "BASIC");
			irisAssignPipelineMethod.invoke(irisApiInstance, BUILD_GUIDE, irisProgramEnumBasic);
			irisAssignPipelineMethod.invoke(irisApiInstance, BUILD_GUIDE_DEPTH_TEST, irisProgramEnumBasic);
			System.out.println("Iris compatibility applied");
		} catch (ClassNotFoundException e) {
			BuildGuide.logHandler.debugOrHigher("Iris not found");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | InvocationTargetException e) {
			BuildGuide.logHandler.error("Could not apply Iris compatibility");
			e.printStackTrace();
		}
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render();
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		RenderSystem.getModelViewStack().pushMatrix();
		Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
		RenderSystem.getModelViewStack().translate((float) (-projectedView.x + shapeSet.getOriginX()), (float) (-projectedView.y + shapeSet.getOriginY()), (float) (-projectedView.z + shapeSet.getOriginZ()));
	}
	
	protected void endRenderingShapeSet() {
		RenderSystem.getModelViewStack().popMatrix();
	}
	
	protected void pushProfiler(String key) {
		Profiler.get().push(key);
	}
	
	protected void popProfiler() {
		Profiler.get().pop();
	}
	
	public static RenderPipeline getRenderPipeline() {
		return BuildGuide.stateManager.getState().isDepthTest() ? BUILD_GUIDE_DEPTH_TEST : BUILD_GUIDE;
	}
}
