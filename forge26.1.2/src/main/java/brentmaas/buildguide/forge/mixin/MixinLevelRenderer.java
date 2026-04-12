package brentmaas.buildguide.forge.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.buffers.GpuBufferSlice;
import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import com.mojang.blaze3d.framegraph.FramePass;

import brentmaas.buildguide.common.BuildGuide;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LevelTargetBundle;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
	@Shadow
	private LevelTargetBundle targets;
	
	@Inject(method = "addWeatherPass", at = @At(value = "RETURN", shift = At.Shift.BEFORE), remap = false)
	private void addWeatherEnd(FrameGraphBuilder frameGraphBuilder, GpuBufferSlice gpuBufferSlice, CallbackInfo callbackInfo) {
		try {
			FramePass framePass = frameGraphBuilder.addPass(BuildGuide.modid);
			this.targets.main = framePass.readsAndWrites(this.targets.main);
			
			framePass.executes(() -> {
				BuildGuide.renderHandler.render();
			});
		}catch(ClassCastException e) {}
	}
}
