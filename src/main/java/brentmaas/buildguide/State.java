package brentmaas.buildguide;

import java.util.ArrayList;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TranslationTextComponent;

public class State {
	public Shape[] basicModeShapes;
	public int iBasic = 0;
	public Shape basicModeShape = ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(0));
	public ArrayList<Shape> advancedModeShapes = new ArrayList<Shape>();
	public int iAdvanced = 0;
	public PropertyBoolean propertyRender = new PropertyBoolean(0, 60, false, new TranslationTextComponent("screen.buildguide.render"), null);
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(0, 100, true, new TranslationTextComponent("screen.buildguide.depthtest"), null);
	public PropertyBoolean propertyAdvancedMode = new PropertyBoolean(0, 120, false, new TranslationTextComponent("screen.buildguide.advancedmode"), () -> {Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen());});
	
	public State() {
		ArrayList<String> classIdentifiers = ShapeRegistry.getClassIdentifiers();
		basicModeShapes = new Shape[classIdentifiers.size()];
		for(int i = 0;i < classIdentifiers.size();++i) {
			basicModeShapes[i] = ShapeRegistry.getNewInstance(classIdentifiers.get(i));
		}
	}
	
	public Shape getCurrentShape() {
		if(propertyAdvancedMode.value) {
			return advancedModeShapes.size() > 0 ? advancedModeShapes.get(iAdvanced) : null;
		}
		return basicModeShapes[iBasic];
	}
	
	public void updateCurrentShape() {
		if(propertyAdvancedMode.value) {
			for(int i = 0;i < advancedModeShapes.size();++i) {
				advancedModeShapes.get(i).update();
			}
		}else {
			basicModeShapes[iBasic].update();
		}
	}
	
	public boolean isShapeAvailable() {
		return !propertyAdvancedMode.value || advancedModeShapes.size() > 0;
	}
	
	public void resetBasepos() {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).resetBasepos();
		} else {
			for(Shape s: basicModeShapes) s.resetBasepos();
		}
	}
	
	public void resetBasepos(int advancedModeId) {
		advancedModeShapes.get(advancedModeId).resetBasepos();
	}
	
	public void setBasepos(int x, int y, int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos(x, y, z);
		} else {
			for(Shape s: basicModeShapes) s.setBasepos(x, y, z);
		}
	}
	
	public void setBaseposX(int x) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos(x, (int) advancedModeShapes.get(iAdvanced).basePos.y, (int) advancedModeShapes.get(iAdvanced).basePos.z);
		} else {
			for(Shape s: basicModeShapes) s.setBasepos(x, (int) s.basePos.y, (int) s.basePos.z);
		}
	}
	
	public void setBaseposY(int y) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos((int) advancedModeShapes.get(iAdvanced).basePos.x, y, (int) advancedModeShapes.get(iAdvanced).basePos.z);
		} else {
			for(Shape s: basicModeShapes) s.setBasepos((int) s.basePos.x, y, (int) s.basePos.z);
		}
	}
	
	public void setBaseposZ(int z) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).setBasepos((int) advancedModeShapes.get(iAdvanced).basePos.x, (int) advancedModeShapes.get(iAdvanced).basePos.y, z);
		} else {
			for(Shape s: basicModeShapes) s.setBasepos((int) s.basePos.x, (int) s.basePos.y, z);
		}
	}
	
	public void shiftBasepos(int dx, int dy, int dz) {
		if(propertyAdvancedMode.value) {
			advancedModeShapes.get(iAdvanced).shiftBasepos(dx, dy, dz);
		} else {
			for(Shape s: basicModeShapes) s.shiftBasepos(dx, dy, dz);
		}
	}
}
