package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeCuboid extends Shape {
	private enum walls{
		ALL,
		X,
		Y,
		Z,
		XY,
		XZ,
		YZ,
		NONE
	}
	
	private String[] wallsNames = {"XYZ", "X", "Y", "Z", "XY", "XZ", "YZ", "-"};
	
	private PropertyNonzeroInt propertyX = new PropertyNonzeroInt(3, new Translatable("X"), () -> update());
	private PropertyNonzeroInt propertyY = new PropertyNonzeroInt(3, new Translatable("Y"), () -> update());
	private PropertyNonzeroInt propertyZ = new PropertyNonzeroInt(3, new Translatable("Z"), () -> update());
	private PropertyEnum<walls> propertyWalls = new PropertyEnum<walls>(walls.ALL, new Translatable("property.buildguide.walls"), () -> update(), wallsNames);
	private PropertyBoolean propertyCentredOrigin = new PropertyBoolean(false, new Translatable("property.buildguide.centredorigin"), () -> update());
	
	public ShapeCuboid() {
		super();
		
		properties.add(propertyX);
		properties.add(propertyY);
		properties.add(propertyZ);
		properties.add(propertyWalls);
		properties.add(propertyCentredOrigin);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int dx = propertyX.value;
		int dy = propertyY.value;
		int dz = propertyZ.value;
		walls w = propertyWalls.value;
		boolean centredOrigin = propertyCentredOrigin.value;
		
		int lowerX, lowerY, lowerZ, upperX, upperY, upperZ;
		if(centredOrigin) {
			lowerX = -Math.abs(dx) + 1;
			lowerY = -Math.abs(dy) + 1;
			lowerZ = -Math.abs(dz) + 1;
			upperX = Math.abs(dx);
			upperY = Math.abs(dy);
			upperZ = Math.abs(dz);
		}else {
			lowerX = dx > 0 ? 0 : dx + 1;
			lowerY = dy > 0 ? 0 : dy + 1;
			lowerZ = dz > 0 ? 0 : dz + 1;
			upperX = dx > 0 ? dx : 1;
			upperY = dy > 0 ? dy : 1;
			upperZ = dz > 0 ? dz : 1;
		}
		
		//Wireframe
		for(int x = lowerX;x < upperX;++x) {
			addShapeCube(buffer, x, lowerY, lowerZ);
			if(upperY - lowerY > 1) {
				addShapeCube(buffer, x, upperY - 1, lowerZ);
			}
			if(upperZ - lowerZ > 1) {
				addShapeCube(buffer, x, lowerY, upperZ - 1);
			}
			if(upperY - lowerY > 1 && upperZ - lowerZ > 1) {
				addShapeCube(buffer, x, upperY - 1, upperZ - 1);
			}
		}
		for(int y = lowerY + 1;y < upperY - 1;++y) {
			addShapeCube(buffer, lowerX, y, lowerZ);
			if(upperX - lowerX > 1) {
				addShapeCube(buffer, upperX - 1, y, lowerZ);
			}
			if(upperZ - lowerZ > 1) {
				addShapeCube(buffer, lowerX, y, upperZ - 1);
			}
			if(upperX - lowerX > 1 && upperZ - lowerZ > 1) {
				addShapeCube(buffer, upperX - 1, y, upperZ - 1);
			}
		}
		for(int z = lowerZ + 1;z < upperZ - 1;++z) {
			addShapeCube(buffer, lowerX, lowerY, z);
			if(upperX - lowerX > 1) {
				addShapeCube(buffer, upperX - 1, lowerY, z);
			}
			if(upperY - lowerY > 1) {
				addShapeCube(buffer, lowerX, upperY - 1, z);
			}
			if(upperX - lowerX > 1 && upperY - lowerY > 1) {
				addShapeCube(buffer, upperX - 1, upperY - 1, z);
			}
		}
		
		//X wall
		if(w == walls.ALL || w == walls.X || w == walls.XY || w == walls.XZ) {
			for(int y = lowerY + 1;y < upperY - 1;++y) {
				for(int z = lowerZ + 1;z < upperZ - 1;++z) {
					addShapeCube(buffer, lowerX, y, z);
					if(upperX - lowerX > 1) {
						addShapeCube(buffer, upperX - 1, y, z);
					}
				}
			}
		}
		
		//Y wall
		if(w == walls.ALL || w == walls.Y || w == walls.XY || w == walls.YZ) {
			for(int x = lowerX + 1;x < upperX - 1;++x) {
				for(int z = lowerZ + 1;z < upperZ - 1;++z) {
					addShapeCube(buffer, x, lowerY, z);
					if(upperY - lowerY > 1) {
						addShapeCube(buffer, x, upperY - 1, z);
					}
				}
			}
		}
		
		//Z wall
		if(w == walls.ALL || w == walls.Z || w == walls.XZ || w == walls.YZ) {
			for(int x = lowerX + 1;x < upperX - 1;++x) {
				for(int y = lowerY + 1;y < upperY - 1;++y) {
					addShapeCube(buffer, x, y, lowerZ);
					if(upperZ - lowerZ > 1) {
						addShapeCube(buffer, x, y, upperZ - 1);
					}
				}
			}
		}
	}
}
