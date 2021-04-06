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
	
	public static Shape getCurrentShape() {
		return shapeStore[i_shape];
	}
}
