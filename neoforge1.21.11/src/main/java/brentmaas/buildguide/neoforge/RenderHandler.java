package brentmaas.buildguide.neoforge;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.common.AbstractRenderHandler;
import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.neoforge.shape.ShapeBuffer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PerspectiveProjectionMatrixBuffer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.FrameGraphSetupEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.common.NeoForge;

public class RenderHandler extends AbstractRenderHandler {
	private Camera cameraInstance;
	private PoseStack poseStackInstance;
	private Matrix4f projectionMatrixInstance;
	public static PerspectiveProjectionMatrixBuffer projectionMatrixBuffer;
	private static final RenderPipeline.Snippet BUILD_GUIDE_SNIPPET = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withBlend(new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA))
			.withCull(true)
			.withDepthWrite(false)
			.buildSnippet();
	private static final RenderPipeline BUILD_GUIDE = RenderPipeline.builder(BUILD_GUIDE_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(BuildGuide.modid, "pipeline/build_guide"))
			.withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
			.build();
	private static final RenderPipeline BUILD_GUIDE_DEPTH_TEST = RenderPipeline.builder(BUILD_GUIDE_SNIPPET)
			.withLocation(Identifier.fromNamespaceAndPath(BuildGuide.modid, "pipeline/build_guide_depth_test"))
			.withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
			.build();
	
	public void register() {
		NeoForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onFrameGraphSetupEvent(FrameGraphSetupEvent event) {
		projectionMatrixInstance = event.getProjectionMatrix();
	}
	
	@SubscribeEvent
	public void onRenderBlock(RenderLevelStageEvent.AfterLevel event) {
		poseStackInstance = event.getPoseStack();
		poseStackInstance.pushPose();
		Matrix4f rotationMatrix = new Matrix4f();
		cameraInstance = Minecraft.getInstance().gameRenderer.getMainCamera();
		rotationMatrix.rotate((float) (cameraInstance.xRot() * Math.PI / 180), new Vector3f(1, 0, 0));
		rotationMatrix.rotate((float) ((cameraInstance.yRot() - 180) * Math.PI / 180), new Vector3f(0, 1, 0));
		poseStackInstance.mulPose(rotationMatrix);
		if(projectionMatrixBuffer == null) projectionMatrixBuffer = new PerspectiveProjectionMatrixBuffer("buildguide");
		
		render();
		
		poseStackInstance.popPose();
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(poseStackInstance.last().pose(), projectionMatrixInstance);
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		poseStackInstance.pushPose();
		Vec3 projectedView = cameraInstance.position();
		poseStackInstance.translate(-projectedView.x + shapeSet.getOriginX(), -projectedView.y + shapeSet.getOriginY(), -projectedView.z + shapeSet.getOriginZ());
	}
	
	protected void endRenderingShapeSet() {
		poseStackInstance.popPose();
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
