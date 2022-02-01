package brentmaas.buildguide.common.shapes;

import java.util.ArrayList;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.Property;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	public IShapeBuffer buffer;
	private int nBlocks = 0;
	public boolean visible = true;
	
	public Basepos basepos = null;
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
	protected abstract void updateShape(IShapeBuffer builder);
	public abstract String getTranslationKey();
	
	public void update() {
		nBlocks = 0;
		long t = System.currentTimeMillis();
		if(buffer != null) buffer.close();
		buffer = BuildGuide.shapeHandler.newBuffer();
		buffer.setColour((int) (255 * colourShapeR), (int) (255 * colourShapeG), (int) (255 * colourShapeB), (int) (255 * colourShapeA));
		updateShape(buffer);
		buffer.setColour((int) (255 * colourBaseposR), (int) (255 * colourBaseposG), (int) (255 * colourBaseposB), (int) (255 * colourBaseposA));
		addCube(buffer, 0.4, 0.4, 0.4, 0.2);
		buffer.end();
		if(BuildGuide.config.debugGenerationTimingsEnabled.value) {
			BuildGuide.logHandler.debugOrHigher("Shape " + getTranslatedName() + " has been generated in " + (System.currentTimeMillis() - t) + " ms");
		}
	}
	
	protected void addCube(IShapeBuffer buffer, double x, double y, double z, double s) {
		//-X
		buffer.pushVertex(x, y, z);
		buffer.pushVertex(x, y, z+s);
		buffer.pushVertex(x, y+s, z+s);
		buffer.pushVertex(x, y+s, z);
		
		//-Y
		buffer.pushVertex(x, y, z);
		buffer.pushVertex(x+s, y, z);
		buffer.pushVertex(x+s, y, z+s);
		buffer.pushVertex(x, y, z+s);
		
		//-Z
		buffer.pushVertex(x, y, z);
		buffer.pushVertex(x, y+s, z);
		buffer.pushVertex(x+s, y+s, z);
		buffer.pushVertex(x+s, y, z);
		
		//+X
		buffer.pushVertex(x+s, y, z);
		buffer.pushVertex(x+s, y+s, z);
		buffer.pushVertex(x+s, y+s, z+s);
		buffer.pushVertex(x+s, y, z+s);
		
		//+Y
		buffer.pushVertex(x, y+s, z);
		buffer.pushVertex(x, y+s, z+s);
		buffer.pushVertex(x+s, y+s, z+s);
		buffer.pushVertex(x+s, y+s, z);
		
		//+Z
		buffer.pushVertex(x, y, z+s);
		buffer.pushVertex(x+s, y, z+s);
		buffer.pushVertex(x+s, y+s, z+s);
		buffer.pushVertex(x, y+s, z+s);
	}
	
	protected void addShapeCube(IShapeBuffer buffer, int x, int y, int z) {
		addCube(buffer, x + 0.2, y + 0.2, z + 0.2, 0.6);
		
		++nBlocks;
	}
	
	public void onSelectedInGUI() {
		for(Property<?> p: properties) {
			p.onSelectedInGUI();
		}
	}
	
	public void onDeselectedInGUI() {
		for(Property<?> p: properties) {
			p.onDeselectedInGUI();
		}
	}
	
	public String getTranslatedName() {
		return BuildGuide.screenHandler.translate(getTranslationKey());
	}
	
	public int getNumberOfBlocks() {
		return nBlocks;
	}
	
	public void resetBasepos() {
		basepos = BuildGuide.shapeHandler.getPlayerPosition();
	}
	
	public void setBasepos(int x, int y, int z) {
		basepos = new Basepos(x, y, z);
	}
	
	public void shiftBasepos(int dx, int dy, int dz) {
		basepos.x += dx;
		basepos.y += dy;
		basepos.z += dz;
	}
	
	public static class Basepos {
		public int x, y, z;
		
		public Basepos(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
