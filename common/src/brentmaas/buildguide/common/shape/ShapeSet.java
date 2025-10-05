package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.BaseScreen;

public class ShapeSet {
	private static final String PERSISTENCE_INDEX = "index";
	private static final String PERSISTENCE_ORIGIN = "origin";
	private static final String PERSISTENCE_VISIBLE = "visible";
	private static final String PERSISTENCE_SHAPECOLOUR = "shapeColour";
	private static final String PERSISTENCE_ORIGINCOLOUR = "originColour";
	private static final String PERSISTENCE_SHAPECUBESIZE = "shapeCubeSize";
	private static final String PERSISTENCE_ORIGINCUBESIZE = "originCubeSize";
	
	public Shape[] shapes;
	private int index;
	
	private Origin origin;
	
	private boolean visible = true;

	public static final float defaultColourShapeR = 1.0f;
	public static final float defaultColourShapeG = 1.0f;
	public static final float defaultColourShapeB = 1.0f;
	public static final float defaultColourShapeA = 0.5f;
	private float colourShapeR = defaultColourShapeR;
	private float colourShapeG = defaultColourShapeG;
	private float colourShapeB = defaultColourShapeB;
	private float colourShapeA = defaultColourShapeA;

	public static final float defaultColourOriginR = 1.0f;
	public static final float defaultColourOriginG = 0.0f;
	public static final float defaultColourOriginB = 0.0f;
	public static final float defaultColourOriginA = 0.5f;
	private float colourOriginR = defaultColourOriginR;
	private float colourOriginG = defaultColourOriginG;
	private float colourOriginB = defaultColourOriginB;
	private float colourOriginA = defaultColourOriginA;

	public static final double defaultShapeCubeSize = 0.6;
	public static final double defaultOriginCubeSize = 0.2;
	private double shapeCubeSize = defaultShapeCubeSize;
	private double originCubeSize = defaultOriginCubeSize;
	
	public ShapeSet(int startIndex) {
		shapes = new Shape[ShapeRegistry.getNumberOfShapes()];
		index = startIndex;
		shapes[index] = initialiseShape(ShapeRegistry.getClassIdentifier(index));
	}
	
	private Shape initialiseShape(String shapeId) {
		Shape newShape = ShapeRegistry.getNewInstance(shapeId);
		newShape.shapeSet = this;
		BaseScreen.shouldUpdatePersistence = true;
		return newShape;
	}
	
	public void resetOrigin() {
		origin = BuildGuide.shapeHandler.getPlayerPosition();
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void updateShape() {
		if(shapes[index] != null) {
			shapes[index].update();
		}
	}
	
	public void updateAllShapes() {
		for(Shape s: shapes) {
			if(s != null) {
				s.update();
			}
		}
	}
	
	public void setOriginX(int x) {
		origin.x = x;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void setOriginY(int y) {
		origin.y = y;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void setOriginZ(int z) {
		origin.z = z;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void setOrigin(int x, int y, int z) {
		origin.x = x;
		origin.y = y;
		origin.z = z;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void shiftOrigin(int dx, int dy, int dz) {
		origin.x += dx;
		origin.y += dy;
		origin.z += dz;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public float getShapeColourR() {
		return colourShapeR;
	}
	
	public float getShapeColourG() {
		return colourShapeG;
	}
	
	public float getShapeColourB() {
		return colourShapeB;
	}
	
	public float getShapeColourA() {
		return colourShapeA;
	}
	
	public void setShapeColour(float r, float g, float b, float a) {
		colourShapeR = r;
		colourShapeG = g;
		colourShapeB = b;
		colourShapeA = a;
		updateAllShapes();
	}
	
	public float getOriginColourR() {
		return colourOriginR;
	}
	
	public float getOriginColourG() {
		return colourOriginG;
	}
	
	public float getOriginColourB() {
		return colourOriginB;
	}
	
	public float getOriginColourA() {
		return colourOriginA;
	}
	
	public void setOriginColour(float r, float g, float b, float a) {
		colourOriginR = r;
		colourOriginG = g;
		colourOriginB = b;
		colourOriginA = a;
		updateAllShapes();
	}
	
	public void setCubeSize(double shapeCubeSize, double originCubeSize) {
		this.shapeCubeSize = shapeCubeSize;
		this.originCubeSize = originCubeSize;
		updateAllShapes();
	}
	
	public double getShapeCubeSize() {
		return shapeCubeSize;
	}
	
	public double getOriginCubeSize() {
		return originCubeSize;
	}
	
	public boolean isShapeAvailable() {
		return isShapeAvailable(index);
	}
	
	public boolean isShapeAvailable(int index) {
		return shapes[index] != null;
	}
	
	public Shape getShape() {
		if(shapes[index] == null) {
			shapes[index] = initialiseShape(ShapeRegistry.getClassIdentifier(index));
			shapes[index].update();
		}
		
		return shapes[index];
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
		BaseScreen.shouldUpdatePersistence = true;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean hasOrigin() {
		return origin != null;
	}
	
	public int getOriginX() {
		return origin.x;
	}
	
	public int getOriginY() {
		return origin.y;
	}
	
	public int getOriginZ() {
		return origin.z;
	}
	
	public String toPersistence() {
		String persistenceData = "";
		persistenceData += PERSISTENCE_INDEX + "=" + index + ";";
		persistenceData += PERSISTENCE_ORIGIN + "=" + origin.x + "," + origin.y + "," + origin.z + ";";
		persistenceData += PERSISTENCE_VISIBLE + "=" + visible + ";";
		persistenceData += PERSISTENCE_SHAPECOLOUR + "=" + colourShapeR + "," + colourShapeG + "," + colourShapeB + "," + colourShapeA + ";";
		persistenceData += PERSISTENCE_ORIGINCOLOUR + "=" + colourOriginR + "," + colourOriginG + "," + colourOriginB + "," + colourOriginA + ";";
		persistenceData += PERSISTENCE_SHAPECUBESIZE + "=" + shapeCubeSize + ";";
		persistenceData += PERSISTENCE_ORIGINCUBESIZE + "=" + originCubeSize + ";";
		for(Shape s: shapes) {
			if(s != null) {
				persistenceData += s.getClass().getName() + "=" + s.toPersistence() + ";";
			}
		}
		return persistenceData;
	}
	
	public void restorePersistence(String persistenceData) {
		String[] splitData = persistenceData.split(";");
		for(String entry: splitData) {
			int separatorIndex = entry.indexOf("=");
			if(separatorIndex > 0) {
				String key = entry.substring(0, separatorIndex);
				String value = entry.substring(separatorIndex + 1);
				if(key.equals(PERSISTENCE_INDEX)) {
					index = Integer.parseInt(value);
				}else if(key.equals(PERSISTENCE_ORIGIN)) {
					String[] splitEntry = value.split(",");
					if(splitEntry.length == 3) {
						origin.x = Integer.parseInt(splitEntry[0]);
						origin.y = Integer.parseInt(splitEntry[1]);
						origin.z = Integer.parseInt(splitEntry[2]);
					}
				}else if (key.equals(PERSISTENCE_VISIBLE)) {
					visible = Boolean.parseBoolean(value);
				}else if(key.equals(PERSISTENCE_SHAPECOLOUR)) {
					String[] splitEntry = value.split(",");
					if(splitEntry.length == 4) {
						colourShapeR = Float.parseFloat(splitEntry[0]);
						colourShapeG = Float.parseFloat(splitEntry[1]);
						colourShapeB = Float.parseFloat(splitEntry[2]);
						colourShapeA = Float.parseFloat(splitEntry[3]);
					}
				}else if(key.equals(PERSISTENCE_ORIGINCOLOUR)) {
					String[] splitEntry = value.split(",");
					if(splitEntry.length == 4) {
						colourOriginR = Float.parseFloat(splitEntry[0]);
						colourOriginG = Float.parseFloat(splitEntry[1]);
						colourOriginB = Float.parseFloat(splitEntry[2]);
						colourOriginA = Float.parseFloat(splitEntry[3]);
					}
				}else if(key.equals(PERSISTENCE_SHAPECUBESIZE)){
					shapeCubeSize = Double.parseDouble(value);
				}else if(key.equals(PERSISTENCE_ORIGINCUBESIZE)){
					originCubeSize = Double.parseDouble(value);
				}else {
					int index = ShapeRegistry.getShapeId(key);
					if(index >= 0) {
						shapes[index] = initialiseShape(key);
						shapes[index].restorePersistence(value);
					}
				}
			}
		}
		index = Math.max(0, Math.min(shapes.length - 1, index));
	}
	
	public static class Origin {
		public int x, y, z;
		
		public Origin(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
