package brentmaas.buildguide.fabric;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;

import brentmaas.buildguide.common.AbstractRenderHandler;
import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;
import brentmaas.buildguide.fabric.shape.ShapeBuffer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.world.phys.Vec3;

public class RenderHandler extends AbstractRenderHandler {
	private Camera cameraInstance;
	private PoseStack poseStackInstance;
	private Matrix4f projectionMatrixInstance, rotationMatrixInstance;
	private static final RenderPipeline.Snippet BUILD_GUIDE_SNIPPET = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
			.withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
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
	public static final RenderSystem.AutoStorageIndexBuffer QUAD_INDICES = RenderSystem.getSequentialBuffer(VertexFormat.Mode.QUADS);
	
	public void register() {
		WorldRenderEvents.LAST.register(this::onRenderBlock);
	}
	
	public void onRenderBlock(WorldRenderContext context) {
		poseStackInstance = context.matrixStack();
		poseStackInstance.pushPose();
		Matrix4f inverseRotationMatrix = new Matrix4f();
		rotationMatrixInstance = new Matrix4f();
		cameraInstance = Minecraft.getInstance().gameRenderer.getMainCamera();
		rotationMatrixInstance.rotate((float) -((cameraInstance.getYRot() - 180) * Math.PI / 180), new Vector3f(0, 1, 0));
		rotationMatrixInstance.rotate((float) -(cameraInstance.getXRot() * Math.PI / 180), new Vector3f(1, 0, 0));
		inverseRotationMatrix.rotate((float) (cameraInstance.getXRot() * Math.PI / 180), new Vector3f(1, 0, 0));
		inverseRotationMatrix.rotate((float) ((cameraInstance.getYRot() - 180) * Math.PI / 180), new Vector3f(0, 1, 0));
		poseStackInstance.mulPose(inverseRotationMatrix);
		projectionMatrixInstance = context.projectionMatrix();
		
		render();
		
		poseStackInstance.popPose();
	}
	
	public void renderShapeBuffer(Shape shape) {
		((ShapeBuffer) shape.buffer).render(poseStackInstance.last().pose(), projectionMatrixInstance);
	}
	
	protected void setupRenderingShapeSet(ShapeSet shapeSet) {
		poseStackInstance.pushPose();
		Vec3 projectedView = cameraInstance.getPosition();
		poseStackInstance.translate(-projectedView.x + shapeSet.origin.x, -projectedView.y + shapeSet.origin.y, -projectedView.z + shapeSet.origin.z);
		poseStackInstance.mulPose(rotationMatrixInstance);
	}
	
	protected void endRenderingShape() {
		poseStackInstance.popPose();
	}
	
	protected void pushProfiler(String key) {
		Profiler.get().push(key);
	}
	
	protected void popProfiler() {
		Profiler.get().pop();
	}
	
	public static RenderPipeline getRenderPipeline() {
		return BuildGuide.stateManager.getState().depthTest ? BUILD_GUIDE_DEPTH_TEST : BUILD_GUIDE;
	}
	
	// Legacy functions to be removed
	protected boolean isCompatibilityProfile() {return false;}
	protected boolean textureEnabled() {return false;}
	protected boolean depthTestEnabled() {return false;}
	protected boolean depthMaskEnabled() {return false;}
	protected boolean blendEnabled() {return true;}
	protected void setTexture(boolean enabled) {}
	protected void setDepthTest(boolean enabled) {}
	protected void setDepthMask(boolean enabled) {}
	protected void setBlend(boolean enabled) {}
	protected void setupNotCulling() {}
	protected void setupBlendFunc() {}
}
