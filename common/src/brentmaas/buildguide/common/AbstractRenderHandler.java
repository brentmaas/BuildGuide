package brentmaas.buildguide.common;

import brentmaas.buildguide.common.shape.Shape;

public abstract class AbstractRenderHandler {
	
	
	public abstract void register();
	
	public abstract void renderShapeBuffer(Shape shape);
	
	protected abstract void setupRenderingShape(Shape shape);
	
	protected abstract void endRenderingShape();
	
	protected abstract boolean textureEnabled();
	
	protected abstract boolean depthTestEnabled();
	
	protected abstract boolean depthMaskEnabled();
	
	protected abstract boolean blendEnabled();
	
	protected abstract void setTexture(boolean enabled);
	
	protected abstract void setDepthTest(boolean enabled);
	
	protected abstract void setDepthMask(boolean enabled);
	
	protected abstract void setBlend(boolean enabled);
	
	protected abstract void setupNotCulling();
	
	protected abstract void setupBlendFunc();
	
	protected abstract void pushProfiler(String key);
	
	protected abstract void popProfiler();
	
	public void render() {
		pushProfiler(BuildGuide.modid);
		
		if(BuildGuide.stateManager.getState().propertyEnable.value && BuildGuide.stateManager.getState().isShapeAvailable() && BuildGuide.stateManager.getState().getCurrentShape().origin != null) {
			if(BuildGuide.stateManager.getState().propertyAdvancedMode.value) {
				for(Shape s: BuildGuide.stateManager.getState().advancedModeShapes) renderShape(s);
			}else {
				renderShape(BuildGuide.stateManager.getState().getCurrentShape());
			}
		}
		
		popProfiler();
	}
	
	private void renderShape(Shape shape) {
		if(shape.lock.tryLock()) {
			try {
				if(shape.visible && shape.ready && !shape.error) {
					if(!shape.vertexBufferUnpacked) {
						shape.buffer.end();
						shape.vertexBufferUnpacked = true;
					}
					
					setupRenderingShape(shape);
					
					boolean toggleTexture = textureEnabled();
					
					boolean hasDepthTest = depthTestEnabled();
					boolean toggleDepthTest = BuildGuide.stateManager.getState().propertyDepthTest.value ^ hasDepthTest;
					
					boolean toggleDepthMask = depthMaskEnabled();
					
					boolean toggleBlend = !blendEnabled();
					
					if(toggleTexture) setTexture(false);
					if(toggleDepthTest && hasDepthTest) setDepthTest(false);
					else if(toggleDepthTest) setDepthTest(true);
					if(toggleDepthMask) setDepthMask(false);
					setupNotCulling();
					setupBlendFunc();
					if(toggleBlend) setBlend(true);
					
					renderShapeBuffer(shape);
					
					if(toggleBlend) setBlend(false);
					if(toggleDepthTest && hasDepthTest) setDepthTest(true);
					else if(toggleDepthTest) setDepthTest(false);
					if(toggleDepthMask) setDepthMask(true);
					if(toggleTexture) setTexture(true);
					
					endRenderingShape();
				}
			}finally {
				shape.lock.unlock();
			}
		}
	}
}
