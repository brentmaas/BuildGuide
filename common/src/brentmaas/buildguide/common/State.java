package brentmaas.buildguide.common;

import java.util.ArrayList;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.screen.BuildGuideScreen;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeCircle;
import brentmaas.buildguide.common.shape.ShapeRegistry;

public class State {
	private boolean initialised = false;
	public Shape[] simpleModeShapes;
	public int iSimple = ShapeRegistry.getShapeId(ShapeCircle.class);
	public ArrayList<Shape> advancedModeShapes = new ArrayList<Shape>();
	public int iAdvanced = 0;
	public int iAdvancedNew = ShapeRegistry.getShapeId(ShapeCircle.class);
	public PropertyBoolean propertyEnable = new PropertyBoolean(false, BuildGuide.screenHandler.translate("screen.buildguide.enable"), null);
	public PropertyBoolean propertyAdvancedMode = new PropertyBoolean(false, BuildGuide.screenHandler.translate("screen.buildguide.advancedmode"), () -> BuildGuide.screenHandler.showScreen(new BuildGuideScreen()));
	public PropertyBoolean propertyAdvancedModeRandomColours = new PropertyBoolean(BuildGuide.config.advancedRandomColorsDefaultEnabled.value, BuildGuide.screenHandler.translate("screen.buildguide.advancedmoderandomcolors"), null);
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(true, BuildGuide.screenHandler.translate("screen.buildguide.depthtest"), null);
	
	public State() {
		ArrayList<String> classIdentifiers = ShapeRegistry.getClassIdentifiers();
		simpleModeShapes = new Shape[classIdentifiers.size()];
		for(int i = 0;i < classIdentifiers.size();++i) {
			simpleModeShapes[i] = ShapeRegistry.getNewInstance(classIdentifiers.get(i));
			simpleModeShapes[i].update();
		}
	}
	
	public Shape getCurrentShape() {
		if(propertyAdvancedMode.value) {
			return advancedModeShapes.size() > 0 ? advancedModeShapes.get(iAdvanced) : null;
		}
		return simpleModeShapes[iSimple];
	}
	
	public void initCheck() {
		if(!initialised) {
			if(simpleModeShapes[0].origin == null) {
				for(Shape s: simpleModeShapes) {
					s.resetOrigin();
				}
			}
			//Advanced mode shapes should be empty
			initialised = true;
		}
	}
	
	public void updateCurrentShape() {
		if(propertyAdvancedMode.value) {
			for(int i = 0;i < advancedModeShapes.size();++i) {
				advancedModeShapes.get(i).update();
			}
		}else {
			simpleModeShapes[iSimple].update();
		}
	}
	
	public boolean isShapeAvailable() {
		return !propertyAdvancedMode.value || advancedModeShapes.size() > 0;
	}
	
	public void resetOrigin() {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).resetOrigin();
		}else {
			for(Shape s: simpleModeShapes) s.resetOrigin();
		}
	}
	
	public void resetOrigin(int advancedModeId) {
		advancedModeShapes.get(advancedModeId).resetOrigin();
	}
	
	public void setOrigin(int x, int y, int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setOrigin(x, y, z);
		}else {
			for(Shape s: simpleModeShapes) s.setOrigin(x, y, z);
		}
	}
	
	public void setOriginX(int x) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).origin.x = x;
		}else {
			for(Shape s: simpleModeShapes) s.origin.x = x;
		}
	}
	
	public void setOriginY(int y) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).origin.y = y;
		}else {
			for(Shape s: simpleModeShapes) s.origin.y = y;
		}
	}
	
	public void setOriginZ(int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).origin.z = z;
		}else {
			for(Shape s: simpleModeShapes) s.origin.z = z;
		}
	}
	
	public void shiftOrigin(int dx, int dy, int dz) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).shiftOrigin(dx, dy, dz);
		}else {
			for(Shape s: simpleModeShapes) s.shiftOrigin(dx, dy, dz);
		}
	}
	
	public void setShapeColour(float r, float g, float b, float a) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).colourShapeR = r;
			advancedModeShapes.get(iAdvanced).colourShapeG = g;
			advancedModeShapes.get(iAdvanced).colourShapeB = b;
			advancedModeShapes.get(iAdvanced).colourShapeA = a;
			advancedModeShapes.get(iAdvanced).update();
		}else {
			for(Shape s: simpleModeShapes) {
				s.colourShapeR = r;
				s.colourShapeG = g;
				s.colourShapeB = b;
				s.colourShapeA = a;
				s.update();
			}
		}
	}
	
	public void setOriginColour(float r, float g, float b, float a) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).colourOriginR = r;
			advancedModeShapes.get(iAdvanced).colourOriginG = g;
			advancedModeShapes.get(iAdvanced).colourOriginB = b;
			advancedModeShapes.get(iAdvanced).colourOriginA = a;
			advancedModeShapes.get(iAdvanced).update();
		}else {
			for(Shape s: simpleModeShapes) {
				s.colourOriginR = r;
				s.colourOriginG = g;
				s.colourOriginB = b;
				s.colourOriginA = a;
				s.update();
			}
		}
	}
}
