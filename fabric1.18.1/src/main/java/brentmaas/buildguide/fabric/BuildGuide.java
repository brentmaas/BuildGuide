package brentmaas.buildguide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.shapes.ShapeCatenary;
import brentmaas.buildguide.shapes.ShapeCircle;
import brentmaas.buildguide.shapes.ShapeCuboid;
import brentmaas.buildguide.shapes.ShapeEllipse;
import brentmaas.buildguide.shapes.ShapeEllipsoid;
import brentmaas.buildguide.shapes.ShapeLine;
import brentmaas.buildguide.shapes.ShapeParabola;
import brentmaas.buildguide.shapes.ShapeParaboloid;
import brentmaas.buildguide.shapes.ShapePolygon;
import brentmaas.buildguide.shapes.ShapeRegistry;
import brentmaas.buildguide.shapes.ShapeSphere;
import brentmaas.buildguide.shapes.ShapeTorus;
import net.fabricmc.api.ClientModInitializer;

public class BuildGuide implements ClientModInitializer {
	public static final Logger logger = LogManager.getLogger();
	
	@Override
	public void onInitializeClient() {
		ShapeRegistry.registerShape(ShapeCatenary.class);
		ShapeRegistry.registerShape(ShapeCircle.class);
		ShapeRegistry.registerShape(ShapeCuboid.class);
		ShapeRegistry.registerShape(ShapeEllipse.class);
		ShapeRegistry.registerShape(ShapeEllipsoid.class);
		ShapeRegistry.registerShape(ShapeLine.class);
		ShapeRegistry.registerShape(ShapeParabola.class);
		ShapeRegistry.registerShape(ShapeParaboloid.class);
		ShapeRegistry.registerShape(ShapePolygon.class);
		ShapeRegistry.registerShape(ShapeSphere.class);
		ShapeRegistry.registerShape(ShapeTorus.class);
		
		StateManager.init();
		
		Config.load();
		
		InputHandler.register();
		
		RenderHandler.register();
	}
}
