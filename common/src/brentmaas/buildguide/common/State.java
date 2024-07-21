package brentmaas.buildguide.common;

import java.util.ArrayList;

import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.ConfigurationScreen;
import brentmaas.buildguide.common.screen.ShapeScreen;
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
	public ActiveScreen currentScreen = ActiveScreen.Shape;
	
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
		case Shape:
		default:
			return new ShapeScreen();
		}
	}
	
	public Shape getCurrentShape() {
		return shapeSets.size() > 0 ? shapeSets.get(iShapeSet).getShape() : null;
	}
	
	public ShapeSet getShapeSet(int index) {
		return shapeSets.size() > 0 ? shapeSets.get(index) : null;
	}
	
	public ShapeSet getCurrentShapeSet() {
		return getShapeSet(iShapeSet);
	}
	
	public void pushNewShapeSet() {
		ShapeSet newShapeSet = new ShapeSet(iShapeNew);
		newShapeSet.resetOrigin();
		if(BuildGuide.config.shapeListRandomColorsDefaultEnabled.value) {
			Random random = new Random();
			newShapeSet.colourShapeR = random.nextFloat();
			newShapeSet.colourShapeG = random.nextFloat();
			newShapeSet.colourShapeB = random.nextFloat();
			newShapeSet.colourOriginR = random.nextFloat();
			newShapeSet.colourOriginG = random.nextFloat();
			newShapeSet.colourOriginB = random.nextFloat();
		}
		newShapeSet.updateAllShapes();
		shapeSets.add(newShapeSet);
	}
	
	public void initCheck() {
		if(!initialised) {
			if(shapeSets.size() == 0) {
				pushNewShapeSet();
			}
			for(ShapeSet s: shapeSets) {
				if(s.origin == null) {
					s.resetOrigin();
				}
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
	
	public int getNumberOfShapeSets() {
		return shapeSets.size();
	}
	
	public void resetOrigin() {
		shapeSets.get(iShapeSet).resetOrigin();
	}
	
	public void setOriginX(int index, int x) {
		shapeSets.get(index).origin.x = x;
	}
	
	public void setOriginX(int x) {
		setOriginX(iShapeSet, x);
	}
	
	public void setOriginY(int y, int index) {
		shapeSets.get(index).origin.y = y;
	}
	
	public void setOriginY(int y) {
		setOriginX(iShapeSet, y);
	}
	
	public void setOriginZ(int index, int z) {
		shapeSets.get(index).origin.z = z;
	}
	
	public void setOriginZ(int z) {
		setOriginX(iShapeSet, z);
	}
	
	public void setOrigin(int index, int x, int y, int z) {
		shapeSets.get(index).origin.x = x;
		shapeSets.get(index).origin.y = y;
		shapeSets.get(index).origin.z = z;
	}
	
	public void setOrigin(int x, int y, int z) {
		setOrigin(iShapeSet, x, y, z);
	}
	
	public void shiftOrigin(int index, int dx, int dy, int dz) {
		shapeSets.get(index).shiftOrigin(dx, dy, dz);
	}
	
	public void shiftOrigin(int dx, int dy, int dz) {
		shiftOrigin(iShapeSet, dx, dy, dz);
	}
	
	public void shiftOrigins(int dx, int dy, int dz) {
		for(ShapeSet s: shapeSets) {
			s.shiftOrigin(dx, dy, dz);
		}
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
	
	public int getNumberOfBlocks() {
		int num = 0;
		for(ShapeSet s: shapeSets) {
			if(s.visible) num += s.getShape().getNumberOfBlocks();
		}
		return num;
	}
	
	public static enum ActiveScreen {
		Shape,
		Visualisation,
		Shapelist,
		Settings
	}
}
