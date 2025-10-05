package brentmaas.buildguide.common;

import java.io.File;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.shape.IShapeHandler;
import brentmaas.buildguide.common.shape.ShapeCatenary;
import brentmaas.buildguide.common.shape.ShapeCircle;
import brentmaas.buildguide.common.shape.ShapeCone;
import brentmaas.buildguide.common.shape.ShapeCuboid;
import brentmaas.buildguide.common.shape.ShapeEllipse;
import brentmaas.buildguide.common.shape.ShapeEllipsoid;
import brentmaas.buildguide.common.shape.ShapeGrid;
import brentmaas.buildguide.common.shape.ShapeLine;
import brentmaas.buildguide.common.shape.ShapeParabola;
import brentmaas.buildguide.common.shape.ShapeParaboloid;
import brentmaas.buildguide.common.shape.ShapePolygon;
import brentmaas.buildguide.common.shape.ShapePolygonalPyramid;
import brentmaas.buildguide.common.shape.ShapeRegistry;
import brentmaas.buildguide.common.shape.ShapeSphere;
import brentmaas.buildguide.common.shape.ShapeTorus;
import brentmaas.buildguide.common.shape.ShapeHelicoid;

public class BuildGuide {
	public static final String modid = "buildguide";
	
	public static AbstractInputHandler keyBindHandler;
	public static AbstractScreenHandler screenHandler;
	public static AbstractWidgetHandler widgetHandler;
	public static AbstractStateManager stateManager;
	public static IShapeHandler shapeHandler;
	public static AbstractRenderHandler renderHandler;
	public static ILogHandler logHandler;
	public static Config config;
	
	public static void register(AbstractInputHandler keyBindHandler, AbstractScreenHandler screenHandler, AbstractWidgetHandler widgetHandler, AbstractStateManager stateManager, IShapeHandler shapeHandler, AbstractRenderHandler renderHandler, ILogHandler logHandler, File configFolder) {
		BuildGuide.keyBindHandler = keyBindHandler;
		BuildGuide.screenHandler = screenHandler;
		BuildGuide.widgetHandler = widgetHandler;
		BuildGuide.stateManager = stateManager;
		BuildGuide.shapeHandler = shapeHandler;
		BuildGuide.renderHandler = renderHandler;
		BuildGuide.logHandler = logHandler;
		BuildGuide.config = new Config(configFolder);
		
		ShapeRegistry.registerShape(ShapeCatenary.class, "shape.buildguide.catenary");
		ShapeRegistry.registerShape(ShapeCircle.class, "shape.buildguide.circle");
		ShapeRegistry.registerShape(ShapeCone.class, "shape.buildguide.cone");
		ShapeRegistry.registerShape(ShapeCuboid.class, "shape.buildguide.cuboid");
		ShapeRegistry.registerShape(ShapeEllipse.class, "shape.buildguide.ellipse");
		ShapeRegistry.registerShape(ShapeEllipsoid.class, "shape.buildguide.ellipsoid");
		ShapeRegistry.registerShape(ShapeGrid.class, "shape.buildguide.grid");
		ShapeRegistry.registerShape(ShapeLine.class, "shape.buildguide.line");
		ShapeRegistry.registerShape(ShapeParabola.class, "shape.buildguide.parabola");
		ShapeRegistry.registerShape(ShapeParaboloid.class, "shape.buildguide.paraboloid");
		ShapeRegistry.registerShape(ShapePolygon.class, "shape.buildguide.polygon");
		ShapeRegistry.registerShape(ShapePolygonalPyramid.class, "shape.buildguide.polygonalpyramid");
		ShapeRegistry.registerShape(ShapeSphere.class, "shape.buildguide.sphere");
		ShapeRegistry.registerShape(ShapeTorus.class, "shape.buildguide.torus");
		ShapeRegistry.registerShape(ShapeHelicoid.class, "shape.buildguide.helicoid");
		
		keyBindHandler.register();
		keyBindHandler.registerOnKeyInput();
		
		renderHandler.register();
	}
}
