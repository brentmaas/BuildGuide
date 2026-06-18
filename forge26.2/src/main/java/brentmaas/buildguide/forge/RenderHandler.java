package brentmaas.buildguide.forge;

import org.joml.Matrix4f;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.BlendFactor;
import com.mojang.blaze3d.platform.CompareOp;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.AbstractRenderHandler;
import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.forge.shape.ShapeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.phys.Vec3;

public class RenderHandler extends AbstractRenderHandler {
	private static final RenderPipeline.Snippet BUILD_GUIDE_SNIPPET = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withColorTargetState(new ColorTargetState(new BlendFunction(BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA, BlendFactor.SRC_ALPHA, BlendFactor.ONE_MINUS_SRC_ALPHA)))
			.withCull(true)
			.buildSnippet();
	private static final RenderPipeline BUILD_GUIDE = RenderPipeline.builder(BUILD_GUIDE_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(BuildGuide.modid, "pipeline/build_guide"))
			.withDepthStencilState(new DepthStencilState(CompareOp.ALWAYS_PASS, false))
			.build();
	private static final RenderPipeline BUILD_GUIDE_DEPTH_TEST = RenderPipeline.builder(BUILD_GUIDE_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(BuildGuide.modid, "pipeline/build_guide_depth_test"))
			.withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, false))
			.build();
	
	private Matrix4f modelViewInstance;
	
	public void register() {
		
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(modelViewInstance);
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		modelViewInstance = RenderSystem.getModelViewMatrixCopy();
		Vec3 projectedView = Minecraft.getInstance().gameRenderer.mainCamera().position();
		modelViewInstance.translate((float) (-projectedView.x + shapeSet.getOriginX()), (float) (-projectedView.y + shapeSet.getOriginY()), (float) (-projectedView.z + shapeSet.getOriginZ()));
	}
	
	protected void endRenderingShapeSet() {
		
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
