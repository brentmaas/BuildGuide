package brentmaas.buildguide;

import java.util.ArrayList;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.screen.BuildGuideScreen;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class State {
	public Shape[] basicModeShapes;
	public int iBasic = 0;
	public Shape basicModeShape = ShapeRegistry.getNewInstance(ShapeRegistry.getClassIdentifiers().get(0));
	public ArrayList<Shape> advancedModeShapes = new ArrayList<Shape>();
	public int iAdvanced = 0;
	public Vector3d basePos = null;
	public PropertyBoolean propertyRender = new PropertyBoolean(0, 60, false, new TranslationTextComponent("screen.buildguide.render"), null);
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(0, 100, true, new TranslationTextComponent("screen.buildguide.depthtest"), null);
	public PropertyBoolean propertyAdvancedMode = new PropertyBoolean(0, 120, false, new TranslationTextComponent("screen.buildguide.advancedmode"), () -> {Minecraft.getInstance().displayGuiScreen(new BuildGuideScreen());});
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
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
}
