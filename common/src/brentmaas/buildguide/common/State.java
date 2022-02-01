package brentmaas.buildguide.common;

import java.util.ArrayList;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.screen.BuildGuideScreen;
import brentmaas.buildguide.common.shapes.Shape;
import brentmaas.buildguide.common.shapes.ShapeCircle;
import brentmaas.buildguide.common.shapes.ShapeRegistry;

public class State {
	private boolean initialised = false;
	public Shape[] simpleModeShapes;
	public int iSimple = 0;
	public ArrayList<Shape> advancedModeShapes = new ArrayList<Shape>();
	public int iAdvanced = 0;
	public PropertyBoolean propertyEnable = new PropertyBoolean(-4, false, BuildGuide.screenHandler.translate("screen.buildguide.enable"), null);
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(2, true, BuildGuide.screenHandler.translate("screen.buildguide.depthtest"), null);
	public PropertyBoolean propertyAdvancedMode = new PropertyBoolean(-2, false, BuildGuide.screenHandler.translate("screen.buildguide.advancedmode"), () -> BuildGuide.screenHandler.showScreen(new BuildGuideScreen()));
	
	public State() {
		ArrayList<String> classIdentifiers = ShapeRegistry.getClassIdentifiers();
		simpleModeShapes = new Shape[classIdentifiers.size()];
		for(int i = 0;i < classIdentifiers.size();++i) {
			simpleModeShapes[i] = ShapeRegistry.getNewInstance(classIdentifiers.get(i));
			simpleModeShapes[i].update();
			if(classIdentifiers.get(i) == ShapeCircle.class.getName()) {
				iSimple = i;
			}
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
			if(simpleModeShapes[0].basepos == null) {
				for(Shape s: simpleModeShapes) {
					s.resetBasepos();
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
	
	public void resetBasepos() {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).resetBasepos();
		}else {
			for(Shape s: simpleModeShapes) s.resetBasepos();
		}
	}
	
	public void resetBasepos(int advancedModeId) {
		advancedModeShapes.get(advancedModeId).resetBasepos();
	}
	
	public void setBasepos(int x, int y, int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos(x, y, z);
		}else {
			for(Shape s: simpleModeShapes) s.setBasepos(x, y, z);
		}
	}
	
	public void setBaseposX(int x) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).basepos.x = x;
		}else {
			for(Shape s: simpleModeShapes) s.basepos.x = x;
		}
	}
	
	public void setBaseposY(int y) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).basepos.y = y;
		}else {
			for(Shape s: simpleModeShapes) s.basepos.y = y;
		}
	}
	
	public void setBaseposZ(int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).basepos.z = z;
		}else {
			for(Shape s: simpleModeShapes) s.basepos.z = z;
		}
	}
	
	public void shiftBasepos(int dx, int dy, int dz) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).shiftBasepos(dx, dy, dz);
		}else {
			for(Shape s: simpleModeShapes) s.shiftBasepos(dx, dy, dz);
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
	
	public void setBaseposColour(float r, float g, float b, float a) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).colourBaseposR = r;
			advancedModeShapes.get(iAdvanced).colourBaseposG = g;
			advancedModeShapes.get(iAdvanced).colourBaseposB = b;
			advancedModeShapes.get(iAdvanced).colourBaseposA = a;
			advancedModeShapes.get(iAdvanced).update();
		}else {
			for(Shape s: simpleModeShapes) {
				s.colourBaseposR = r;
				s.colourBaseposG = g;
				s.colourBaseposB = b;
				s.colourBaseposA = a;
				s.update();
			}
		}
	}
}
