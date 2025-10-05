package brentmaas.buildguide.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
	private static final String PERSISTENCE_ENABLED = "enabled";
	private static final String PERSISTENCE_DEPTHTEST = "depthTest";
	private static final String PERSISTENCE_SHAPESET = "shapeSet";
	private static final String PERSISTENCE_ISHAPESET = "iShapeSet";
	private static final String PERSISTENCE_ISHAPENEW = "iShapeNew";
	
	private boolean initialised = false;
	private boolean fromPersistence = false;
	public ArrayList<ShapeSet> shapeSets = new ArrayList<ShapeSet>();
	private int iShapeSet = 0;
	private int iShapeNew = ShapeRegistry.getShapeId(ShapeCircle.class);
	private boolean enabled = false;
	private boolean depthTest = true;
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
	
	public int getCurrentShapeIndex() {
		return shapeSets.size() > 0 ? shapeSets.get(iShapeSet).getIndex() : 0;
	}
	
	public ShapeSet getShapeSet(int index) {
		return shapeSets.size() > 0 ? shapeSets.get(index) : null;
	}
	
	public ShapeSet getCurrentShapeSet() {
		return getShapeSet(iShapeSet);
	}
	
	public void removeShapeSet(int index) {
		shapeSets.remove(index);
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void pushNewShapeSet() {
		ShapeSet newShapeSet = new ShapeSet(iShapeNew);
		newShapeSet.resetOrigin();
		if(BuildGuide.config.shapeListRandomColorsDefaultEnabled.value) {
			Random random = new Random();
			newShapeSet.setShapeColour(random.nextFloat(), random.nextFloat(), random.nextFloat(), newShapeSet.getShapeColourA());
			newShapeSet.setOriginColour(random.nextFloat(), random.nextFloat(), random.nextFloat(), newShapeSet.getOriginColourA());
		}
		shapeSets.add(newShapeSet);
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void initCheck() {
		if(!initialised) {
			if(fromPersistence) {
				for(ShapeSet shapeSet: shapeSets) {
					shapeSet.updateAllShapes();
				}
			}
			
			if(shapeSets.size() == 0) {
				pushNewShapeSet();
				shapeSets.get(0).updateAllShapes();
			}
			for(ShapeSet s: shapeSets) {
				if(!s.hasOrigin()) {
					s.resetOrigin();
				}
			}
			
			initialised = true;
		}
	}
	
	public void setShape(int i) {
		shapeSets.get(iShapeSet).setIndex(i);
	}
	
	public int getShapeSetIndex() {
		return iShapeSet;
	}
	
	public void setShapeSetIndex(int iShapeSet) {
		this.iShapeSet = iShapeSet;
		BaseScreen.shouldUpdatePersistence = true;
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
		shapeSets.get(index).setOriginX(x);
	}
	
	public void setOriginX(int x) {
		setOriginX(iShapeSet, x);
	}
	
	public void setOriginY(int index, int y) {
		shapeSets.get(index).setOriginY(y);
	}
	
	public void setOriginY(int y) {
		setOriginY(iShapeSet, y);
	}
	
	public void setOriginZ(int index, int z) {
		shapeSets.get(index).setOriginZ(z);
	}
	
	public void setOriginZ(int z) {
		setOriginZ(iShapeSet, z);
	}
	
	public void setOrigin(int index, int x, int y, int z) {
		shapeSets.get(index).setOrigin(x, y, z);
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
	
	public int getNumberOfBlocks() {
		int num = 0;
		for(ShapeSet s: shapeSets) {
			if(s.isVisible()) num += s.getShape().getNumberOfBlocks();
		}
		return num;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public boolean isDepthTest() {
		return depthTest;
	}
	
	public int getIShapeNew() {
		return iShapeNew;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void setDepthTest(boolean depthTest) {
		this.depthTest = depthTest;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void setIShapeNew(int iShapeNew) {
		this.iShapeNew = iShapeNew;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void loadPersistence(File persistenceFile) throws IOException {
		Scanner scanner = new Scanner(persistenceFile);
		while(scanner.hasNext()) {
			String line = scanner.nextLine();
			int separatorIndex = line.indexOf("=");
			if(separatorIndex > 0) {
				String key = line.substring(0, separatorIndex);
				String value = line.substring(separatorIndex + 1);
				if(key.equals(PERSISTENCE_ENABLED)) {
					enabled = Boolean.parseBoolean(value);
				}else if(key.equals(PERSISTENCE_DEPTHTEST)) {
					depthTest = Boolean.parseBoolean(value);
				}else if(key.equals(PERSISTENCE_SHAPESET)) {
					pushNewShapeSet();
					shapeSets.get(shapeSets.size() - 1).restorePersistence(value);
					shapeSets.get(shapeSets.size() - 1).updateShape();
				}else if(key.equals(PERSISTENCE_ISHAPESET)) {
					iShapeSet = Integer.parseInt(value);
				}else if(key.equals(PERSISTENCE_ISHAPENEW)) {
					iShapeNew = Integer.parseInt(value);
				}
			}
		}
		scanner.close();
		iShapeSet = Math.max(0, Math.min(shapeSets.size() - 1, iShapeSet));
		iShapeNew = Math.max(0, Math.min(ShapeRegistry.getNumberOfShapes() - 1, iShapeNew));
		
		fromPersistence = true;
	}
	
	public void savePersistence(File persistenceFile) throws IOException {
		String persistenceData = "";
		persistenceData += PERSISTENCE_ENABLED + "=" + enabled + "\n";
		persistenceData += PERSISTENCE_DEPTHTEST + "=" + depthTest + "\n";
		for(ShapeSet s: shapeSets) {
			persistenceData += PERSISTENCE_SHAPESET + "=" + s.toPersistence() + "\n";
		}
		persistenceData += PERSISTENCE_ISHAPESET + "=" + iShapeSet + "\n";
		persistenceData += PERSISTENCE_ISHAPENEW + "=" + iShapeNew + "\n";
		
		persistenceFile.createNewFile();
		FileOutputStream fileStream = new FileOutputStream(persistenceFile);
		OutputStreamWriter fileWriter = new OutputStreamWriter(fileStream);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		writer.write(persistenceData);
		writer.close();
		fileWriter.close();
		fileStream.close();
	}
	
	public static enum ActiveScreen {
		Shape,
		Visualisation,
		Shapelist,
		Settings
	}
}
