package brentmaas.buildguide.forge.mixin;

import org.joml.Matrix4fc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;

import brentmaas.buildguide.common.BuildGuide;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.ChunkSectionsToRender;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
	private FramePass framePass;
	
	@Inject(method = "addMainPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void addMainPassEnd(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Matrix4fc modelViewMatrix, GpuBufferSlice terrainFog, boolean renderOutline, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, ChunkSectionsToRender chunkSectionsToRender, CallbackInfo callbackInfo, @Local FramePass pass) {
		try {
			framePass = frameGraphBuilder.addPass(BuildGuide.modid);
			framePass.requires(pass);
			
			framePass.executes(() -> {
				BuildGuide.renderHandler.render();
			});
		}catch(ClassCastException e) {}
	}
	
	@Inject(method = "addCloudsPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void addCloudsPassEnd(FrameGraphBuilder frame, CloudStatus cloudStatus, Vec3 cameraPosition, long gameTime, float partialTicks, int cloudColor, float cloudHeight, int cloudRange, CallbackInfo callbackInfo, @Local FramePass pass) {
		try {
			framePass.requires(pass);
		}catch(ClassCastException e) {}
	}
	
	@Inject(method = "addWeatherPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private void addWeatherEnd(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice fog, CallbackInfo callbackInfo, int i, @Local FramePass pass) {
		try {
			pass.requires(framePass);
		}catch(ClassCastException e) {}
	}
}
