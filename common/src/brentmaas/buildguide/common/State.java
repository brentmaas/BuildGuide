package brentmaas.buildguide.common;

import java.util.ArrayList;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.BuildGuideScreen;
import brentmaas.buildguide.common.screen.ConfigurationScreen;
import brentmaas.buildguide.common.screen.ShapelistScreen;
import brentmaas.buildguide.common.screen.VisualisationScreen;
import brentmaas.buildguide.common.shape.Shape;
import brentmaas.buildguide.common.shape.ShapeCircle;
import brentmaas.buildguide.common.shape.ShapeRegistry;
import brentmaas.buildguide.common.shape.ShapeSet;

public class State {
	private boolean initialised = false;
	public ArrayList<ShapeSet> shapeSets = new ArrayList<ShapeSet>();
	public int iShapeSet = 0;
	public int iShapeNew = ShapeRegistry.getShapeId(ShapeCircle.class);
	public boolean enabled = false;
	public boolean depthTest = true;
	public ActiveScreen currentScreen = ActiveScreen.BuildGuide;
	@Deprecated
	public PropertyBoolean propertyAdvancedModeRandomColours = new PropertyBoolean(BuildGuide.config.advancedRandomColorsDefaultEnabled.value, BuildGuide.screenHandler.translate("screen.buildguide.advancedmoderandomcolors"), null);
	
	public BaseScreen createNewScreen(ActiveScreen newActiveScreen) {
		currentScreen = newActiveScreen;
		return createNewScreen();
	}
	
	public BaseScreen createNewScreen() {
		switch(currentScreen) {
		case Visualisation:
			return new VisualisationScreen();
		case Shapelist:
			return new ShapelistScreen();
		case Settings:
			return new ConfigurationScreen();
		default:
			return new BuildGuideScreen();
		}
	}
	
	public Shape getCurrentShape() {
		return shapeSets.size() > 0 ? shapeSets.get(iShapeSet).getShape() : null;
	}
	
	public ShapeSet getCurrentShapeSet() {
		return shapeSets.size() > 0 ? shapeSets.get(iShapeSet) : null;
	}
	
	public void initCheck() {
		if(!initialised) {
			shapeSets.add(new ShapeSet(iShapeNew));
			if(shapeSets.get(0).origin == null) {
				shapeSets.get(0).resetOrigin();
			}
			initialised = true;
		}
	}
	
	public void shiftShape(int di) {
		shapeSets.get(iShapeSet).setIndex(Math.floorMod(shapeSets.get(iShapeSet).getIndex() + di, ShapeRegistry.getNumberOfShapes()));
	}
	
	public boolean isShapeAvailable() {
		return shapeSets.size() > 0;
	}
	
	public void resetOrigin() {
		shapeSets.get(iShapeSet).resetOrigin();
	}
	
	public void setOrigin(int x, int y, int z) {
		shapeSets.get(iShapeSet).origin.x = x;
		shapeSets.get(iShapeSet).origin.y = y;
		shapeSets.get(iShapeSet).origin.z = z;
	}
	
	public void setOriginX(int x) {
		shapeSets.get(iShapeSet).origin.x = x;
	}
	
	public void setOriginY(int y) {
		shapeSets.get(iShapeSet).origin.y = y;
	}
	
	public void setOriginZ(int z) {
		shapeSets.get(iShapeSet).origin.z = z;
	}
	
	public void shiftOrigin(int dx, int dy, int dz) {
		shapeSets.get(iShapeSet).shiftOrigin(dx, dy, dz);
	}
	
	public void setShapeColour(float r, float g, float b, float a) {
		shapeSets.get(iShapeSet).colourShapeR = r;
		shapeSets.get(iShapeSet).colourShapeG = g;
		shapeSets.get(iShapeSet).colourShapeB = b;
		shapeSets.get(iShapeSet).colourShapeA = a;
		shapeSets.get(iShapeSet).updateAllShapes();
	}
	
	public void setOriginColour(float r, float g, float b, float a) {
		shapeSets.get(iShapeSet).colourOriginR = r;
		shapeSets.get(iShapeSet).colourOriginG = g;
		shapeSets.get(iShapeSet).colourOriginB = b;
		shapeSets.get(iShapeSet).colourOriginA = a;
		shapeSets.get(iShapeSet).updateAllShapes();
	}
	
	public static enum ActiveScreen {
		BuildGuide,
		Visualisation,
		Shapelist,
		Settings
	}
}
