package brentmaas.buildguide.common;

import brentmaas.buildguide.common.screen.AbstractScreenHandler;
import brentmaas.buildguide.common.screen.widget.IWidgetHandler;
import brentmaas.buildguide.common.shapes.IShapeHandler;
import brentmaas.buildguide.common.shapes.ShapeCatenary;
import brentmaas.buildguide.common.shapes.ShapeCircle;
import brentmaas.buildguide.common.shapes.ShapeCuboid;
import brentmaas.buildguide.common.shapes.ShapeEllipse;
import brentmaas.buildguide.common.shapes.ShapeEllipsoid;
import brentmaas.buildguide.common.shapes.ShapeLine;
import brentmaas.buildguide.common.shapes.ShapeParabola;
import brentmaas.buildguide.common.shapes.ShapeParaboloid;
import brentmaas.buildguide.common.shapes.ShapePolygon;
import brentmaas.buildguide.common.shapes.ShapeRegistry;
import brentmaas.buildguide.common.shapes.ShapeSphere;
import brentmaas.buildguide.common.shapes.ShapeTorus;

public class BuildGuide {
	public static final String modid = "buildguide";
	
	public static AbstractInputHandler keyBindHandler;
	public static AbstractScreenHandler screenHandler;
	public static IWidgetHandler widgetHandler;
	public static AbstractStateManager stateManager;
	public static IShapeHandler shapeHandler;
	public static AbstractRenderHandler renderHandler;
	public static ILogHandler logHandler;
	public static AbstractConfig config;
	
	public static void register(AbstractInputHandler keyBindHandler, AbstractScreenHandler screenHandler, IWidgetHandler widgetHandler, AbstractStateManager stateManager, IShapeHandler shapeHandler, AbstractRenderHandler renderHandler, ILogHandler logHandler, AbstractConfig config) {
		BuildGuide.keyBindHandler = keyBindHandler;
		BuildGuide.screenHandler = screenHandler;
		BuildGuide.widgetHandler = widgetHandler;
		BuildGuide.stateManager = stateManager;
		BuildGuide.shapeHandler = shapeHandler;
		BuildGuide.renderHandler = renderHandler;
		BuildGuide.logHandler = logHandler;
		BuildGuide.config = config;
		
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
		
		keyBindHandler.register();
		keyBindHandler.registerOnKeyInput();
		
		renderHandler.register();
		
		config.build();
		config.load();
	}
}
