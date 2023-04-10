package brentmaas.buildguide.common;

import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeSet;

public abstract class AbstractRenderHandler {
	
	
	public abstract void register();
	
	public abstract void renderShapeBuffer(Shape shape);
	
	protected abstract void setupRenderingShapeSet(ShapeSet shape);
	
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
		
		if(BuildGuide.stateManager.getState().enabled && BuildGuide.stateManager.getState().isShapeAvailable() && BuildGuide.stateManager.getState().getCurrentShapeSet().origin != null) {
			for(ShapeSet s: BuildGuide.stateManager.getState().shapeSets) renderShapeSet(s); 
		}
		
		popProfiler();
	}
	
	private void renderShapeSet(ShapeSet shapeSet) {
		if(shapeSet.getShape().lock.tryLock()) {
			try {
				if(shapeSet.visible && shapeSet.getShape().ready && !shapeSet.getShape().error) {
					if(!shapeSet.getShape().vertexBufferUnpacked) {
						shapeSet.getShape().buffer.end();
						shapeSet.getShape().vertexBufferUnpacked = true;
					}
					
					setupRenderingShapeSet(shapeSet);
					
					boolean toggleTexture = textureEnabled();
					
					boolean hasDepthTest = depthTestEnabled();
					boolean toggleDepthTest = BuildGuide.stateManager.getState().depthTest ^ hasDepthTest;
					
					boolean toggleDepthMask = depthMaskEnabled();
					
					boolean toggleBlend = !blendEnabled();
					
					if(toggleTexture) setTexture(false);
					if(toggleDepthTest && hasDepthTest) setDepthTest(false);
					else if(toggleDepthTest) setDepthTest(true);
					if(toggleDepthMask) setDepthMask(false);
					setupNotCulling();
					setupBlendFunc();
					if(toggleBlend) setBlend(true);
					
					renderShapeBuffer(shapeSet.getShape());
					
					if(toggleBlend) setBlend(false);
					if(toggleDepthTest && hasDepthTest) setDepthTest(true);
					else if(toggleDepthTest) setDepthTest(false);
					if(toggleDepthMask) setDepthMask(true);
					if(toggleTexture) setTexture(true);
					
					endRenderingShape();
				}
			}finally {
				shapeSet.getShape().lock.unlock();
			}
		}
	}
}
