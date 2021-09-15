package brentmaas.buildguide;

import brentmaas.buildguide.property.PropertyBoolean;
import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeEllipse;
import brentmaas.buildguide.shapes.ShapeEllipsoid;
import brentmaas.buildguide.shapes.ShapeEmpty;
import brentmaas.buildguide.shapes.ShapeLine;
import brentmaas.buildguide.shapes.ShapePolygon;
import brentmaas.buildguide.shapes.ShapeSphere;
import brentmaas.buildguide.shapes.ShapeTorus;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class State {
	public Shape[] shapeStore = {new ShapeEmpty(), new ShapeCircle(), new ShapeCuboid(), new ShapeEllipse(), new ShapeEllipsoid(), new ShapeLine(), new ShapePolygon(), new ShapeSphere(), new ShapeTorus()};
	public int i_shape = 0;
	public Vector3d basePos = null;
	public PropertyBoolean propertyDepthTest = new PropertyBoolean(0, 80, true, new TranslationTextComponent("screen.buildguide.depthtest"), null);
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
	public Shape getCurrentShape() {
		return shapeStore[i_shape];
	}
	
	public void updateCurrentShape() {
		shapeStore[i_shape].update();
	}
}
