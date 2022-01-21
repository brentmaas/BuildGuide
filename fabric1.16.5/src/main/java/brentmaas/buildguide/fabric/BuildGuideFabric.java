package brentmaas.buildguide.fabric;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import brentmaas.buildguide.fabric.shapes.ShapeCatenary;
import brentmaas.buildguide.fabric.shapes.ShapeCircle;
import brentmaas.buildguide.fabric.shapes.ShapeCuboid;
import brentmaas.buildguide.fabric.shapes.ShapeEllipse;
import brentmaas.buildguide.fabric.shapes.ShapeEllipsoid;
import brentmaas.buildguide.fabric.shapes.ShapeLine;
import brentmaas.buildguide.fabric.shapes.ShapeParabola;
import brentmaas.buildguide.fabric.shapes.ShapeParaboloid;
import brentmaas.buildguide.fabric.shapes.ShapePolygon;
import brentmaas.buildguide.fabric.shapes.ShapeRegistry;
import brentmaas.buildguide.fabric.shapes.ShapeSphere;
import brentmaas.buildguide.fabric.shapes.ShapeTorus;
import net.fabricmc.api.ClientModInitializer;

public class BuildGuideFabric implements ClientModInitializer {
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
