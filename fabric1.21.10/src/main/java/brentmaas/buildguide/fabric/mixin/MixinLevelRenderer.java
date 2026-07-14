package brentmaas.buildguide.fabric.mixin;

import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;

import brentmaas.buildguide.common.BuildGuide;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.LevelRenderState;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
	private FramePass framePass;
	
	@Inject(method = "addMainPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false)
	private void addMainPassEnd(FrameGraphBuilder frameGraphBuilder, Frustum frustum, Matrix4f matrix4f, GpuBufferSlice gpuBufferSlice, boolean bl, LevelRenderState levelRenderState, DeltaTracker deltaTracker, ProfilerFiller profiler, CallbackInfo callbackInfo, @Local FramePass pass) {
		framePass = frameGraphBuilder.addPass(BuildGuide.modid);
		framePass.requires(pass);
		
		framePass.executes(() -> {
			BuildGuide.renderHandler.render();
		});
	}
	
	@Inject(method = "addCloudsPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false)
	private void addCloudsPassEnd(FrameGraphBuilder frame, CloudStatus cloudStatus, Vec3 vec3, float f, int i, float g, CallbackInfo callbackInfo, @Local FramePass pass) {
		framePass.requires(pass);
	}
	
	@Inject(method = "addWeatherPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false)
	private void addWeatherPassEnd(FrameGraphBuilder frameGraphBuilder, Vec3 vec3, GpuBufferSlice gpuBufferSlice, CallbackInfo callbackInfo, @Local FramePass pass) {
		pass.requires(framePass);
	}
}
