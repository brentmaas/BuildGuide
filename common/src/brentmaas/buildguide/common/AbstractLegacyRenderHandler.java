package brentmaas.buildguide.common;

import brentmaas.buildguide.common.shape.ShapeSet;

public abstract class AbstractLegacyRenderHandler extends AbstractRenderHandler {
	protected abstract boolean isCompatibilityProfile();
	
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
	
	@Override
	protected void renderShapeSet(ShapeSet shapeSet) {
		if(shapeSet.getShape().lock.tryLock()) {
			try {
				if(shapeSet.visible && shapeSet.getShape().ready && !shapeSet.getShape().error && shapeSet.getShape().buffer != null) { // TODO: Nullcheck on buffer should not be necessary because we check `ready`
					if(!shapeSet.getShape().vertexBufferUnpacked) {
						shapeSet.getShape().buffer.end();
						shapeSet.getShape().vertexBufferUnpacked = true;
					}
					
					setupRenderingShapeSet(shapeSet);
					
					boolean toggleTexture = isCompatibilityProfile() && textureEnabled();
					
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
					
					endRenderingShapeSet();
				}
			}finally {
				shapeSet.getShape().lock.unlock();
			}
		}
	}
}
