package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeCuboid extends Shape{
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
	
	private PropertyNonzeroInt propertyX = new PropertyNonzeroInt(0, 145, 3, new StringTextComponent("X"), () -> {this.update();});
	private PropertyNonzeroInt propertyY = new PropertyNonzeroInt(0, 165, 3, new StringTextComponent("Y"), () -> {this.update();});
	private PropertyNonzeroInt propertyZ = new PropertyNonzeroInt(0, 185, 3, new StringTextComponent("Z"), () -> {this.update();});
	private PropertyEnum<walls> propertyWalls = new PropertyEnum<walls>(0, 205, walls.ALL, new TranslationTextComponent("property.buildguide.walls"), () -> {this.update();}, wallsNames);
	
	
	public ShapeCuboid() {
		super();
		
		properties.add(propertyX);
		properties.add(propertyY);
		properties.add(propertyZ);
		properties.add(propertyWalls);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int dx = propertyX.value;
		int dy = propertyY.value;
		int dz = propertyZ.value;
		walls w = propertyWalls.value;
		
		/*for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int z = (dz > 0 ? 0 : dz + 1);z < (dz > 0 ? dz : 1);++z) {
				addCube(builder, x + 0.2, 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				if(!(dy == 1 || dy == -1)) {
					addCube(builder, x + 0.2, (dy > 0 ? dy - 1 : dy + 1) + 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				}
			}
		}
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
				addCube(builder, x + 0.2, y + 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				if(!(dz == 1 || dz == -1)) {
					addCube(builder, x + 0.2, y + 0.2, (dz > 0 ? dz - 1 : dz + 1) + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				}
			}
		}
		for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
			for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
				addCube(builder, 0.2, y + 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				if(!(dx == 1 || dx == -1)) {
					addCube(builder, (dx > 0 ? dx - 1 : dx + 1) + 0.2, y + 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				}
			}
		}*/
		
		//Wireframe
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			addCube(builder, x + 0.2, 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			if(!(dy == 1 || dy == -1)) {
				addCube(builder, x + 0.2, (dy > 0 ? dy - 0.8 : dy + 1.2), 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
			if(!(dz == 1 || dz == -1)) {
				addCube(builder, x + 0.2, 0.2, (dz > 0 ? dz - 0.8 : dz + 1.2), 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
			if(!(dy == 1 || dy == -1 || dz == 1 || dz == -1)) {
				addCube(builder, x + 0.2, (dy > 0 ? dy - 0.8 : dy + 1.2), (dz > 0 ? dz - 0.8 : dz + 1.2), 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
		}
		for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
			addCube(builder, 0.2, y + 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			if(!(dx == 1 || dx == -1)) {
				addCube(builder, (dx > 0 ? dx - 0.8 : dx + 1.2), y + 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
			if(!(dz == 1 || dz == -1)) {
				addCube(builder, 0.2, y + 0.2, (dz > 0 ? dz - 0.8 : dz + 1.2), 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
			if(!(dx == 1 || dx == -1 || dz == 1 || dz == -1)) {
				addCube(builder, (dx > 0 ? dx - 0.8 : dx + 1.2), y + 0.2, (dz > 0 ? dz - 0.8 : dz + 1.2), 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
		}
		for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
			addCube(builder, 0.2, 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			if(!(dx == 1 || dx == -1)) {
				addCube(builder, (dx > 0 ? dx - 0.8 : dx + 1.2), 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
			if(!(dy == 1 || dy == -1)) {
				addCube(builder, 0.2, (dy > 0 ? dy - 0.8 : dy + 1.2), z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
			if(!(dx == 1 || dx == -1 || dy == 1 || dy == -1)) {
				addCube(builder, (dx > 0 ? dx - 0.8 : dx + 1.2), (dy > 0 ? dy - 0.8 : dy + 1.2), z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
			}
		}
		
		//X wall
		if(w == walls.ALL || w == walls.X || w == walls.XY || w == walls.XZ) {
			for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
				for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
					addCube(builder, 0.2, y + 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					if(!(dx == 1 || dx == -1)) {
						addCube(builder, (dx > 0 ? dx - 0.8 : dx + 1.2), y + 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					}
				}
			}
		}
		
		//Y wall
		if(w == walls.ALL || w == walls.Y || w == walls.XY || w == walls.YZ) {
			for(int x = (dx > 0 ? 1 : dx + 2);x < (dx > 0 ? dx - 1 : 0);++x) {
				for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
					addCube(builder, x + 0.2, 0.2, z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					if(!(dy == 1 || dy == -1)) {
						addCube(builder, x + 0.2, (dy > 0 ? dy - 0.8 : dy + 1.2), z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					}
				}
			}
		}
		
		//Z wall
		if(w == walls.ALL || w == walls.Z || w == walls.XZ || w == walls.YZ) {
			for(int x = (dx > 0 ? 1 : dx + 2);x < (dx > 0 ? dx - 1 : 0);++x) {
				for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
					addCube(builder, x + 0.2, y + 0.2, 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					if(!(dz == 1 || dz == -1)) {
						addCube(builder, x + 0.2, y + 0.2, (dz > 0 ? dz - 0.8 : dz + 1.2), 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.cuboid";
	}
}
