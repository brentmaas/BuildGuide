package brentmaas.buildguide;

import brentmaas.buildguide.shapes.Shape;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeSphere;
import brentmaas.buildguide.shapes.ShapeEmpty;
import brentmaas.buildguide.shapes.ShapeLine;
import net.minecraft.util.math.vector.Vector3d;

public class State {
	public static Shape[] shapeStore = {new ShapeEmpty(), new ShapeLine(), new ShapeCuboid(), new ShapeCircle(), new ShapeSphere()};
	public static int i_shape = 0;
	public static Vector3d basePos = null;
	public static boolean depthTest = true;
	
	public static float colourShapeR = 1.0f;
	public static float colourShapeG = 1.0f;
	public static float colourShapeB = 1.0f;
	public static float colourShapeA = 0.5f;
	
	public static float colourBaseposR = 1.0f;
	public static float colourBaseposG = 0.0f;
	public static float colourBaseposB = 0.0f;
	public static float colourBaseposA = 0.5f;
	
	public static Shape getCurrentShape() {
		return shapeStore[i_shape];
	}
}
