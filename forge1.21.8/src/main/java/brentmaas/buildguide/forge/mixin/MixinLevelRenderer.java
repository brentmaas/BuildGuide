package brentmaas.buildguide.forge.mixin;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.forge.RenderHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
	@Inject(method = "renderLevel", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false)
	private void renderLevelEnd(GraphicsResourceAllocator graphicsResourceAllocator, DeltaTracker deltaTracker, boolean renderBlockOutline, Camera camera, Matrix4f frustrumMatrix, Matrix4f projectionMatrix, GpuBufferSlice fogBuffer, Vector4f fogColor, boolean renderSky, CallbackInfo callbackInfo) {
		// Temporary try-catch because NeoForge also finds this mixin
		try {
			((RenderHandler) BuildGuide.renderHandler).onRenderBlock(projectionMatrix);
		}catch(ClassCastException e) {}
	}
}
