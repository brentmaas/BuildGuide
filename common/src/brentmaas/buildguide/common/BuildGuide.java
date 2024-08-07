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
import brentmaas.buildguide.common.shape.ShapeRegistry;
import brentmaas.buildguide.common.shape.ShapeSphere;
import brentmaas.buildguide.common.shape.ShapeTorus;

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
		
		ShapeRegistry.registerShape(ShapeCatenary.class);
		ShapeRegistry.registerShape(ShapeCircle.class);
		ShapeRegistry.registerShape(ShapeCone.class);
		ShapeRegistry.registerShape(ShapeCuboid.class);
		ShapeRegistry.registerShape(ShapeEllipse.class);
		ShapeRegistry.registerShape(ShapeEllipsoid.class);
		ShapeRegistry.registerShape(ShapeGrid.class);
		ShapeRegistry.registerShape(ShapeLine.class);
		ShapeRegistry.registerShape(ShapeParabola.class);
		ShapeRegistry.registerShape(ShapeParaboloid.class);
		ShapeRegistry.registerShape(ShapePolygon.class);
		ShapeRegistry.registerShape(ShapeSphere.class);
		ShapeRegistry.registerShape(ShapeTorus.class);
		
		keyBindHandler.register();
		keyBindHandler.registerOnKeyInput();
		
		renderHandler.register();
	}
}
