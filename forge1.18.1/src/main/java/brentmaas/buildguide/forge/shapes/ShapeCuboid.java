package brentmaas.buildguide.forge.shapes;

import com.mojang.blaze3d.vertex.BufferBuilder;

import brentmaas.buildguide.forge.property.PropertyEnum;
import brentmaas.buildguide.forge.property.PropertyNonzeroInt;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

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
	
	private PropertyNonzeroInt propertyX = new PropertyNonzeroInt(0, 3, new TextComponent("X"), () -> update());
	private PropertyNonzeroInt propertyY = new PropertyNonzeroInt(1, 3, new TextComponent("Y"), () -> update());
	private PropertyNonzeroInt propertyZ = new PropertyNonzeroInt(2, 3, new TextComponent("Z"), () -> update());
	private PropertyEnum<walls> propertyWalls = new PropertyEnum<walls>(3, walls.ALL, new TranslatableComponent("property.buildguide.walls"), () -> update(), wallsNames);
	
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
		
		//Wireframe
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			addShapeCube(builder, x, 0, 0);
			if(!(dy == 1 || dy == -1)) {
				addShapeCube(builder, x, (dy > 0 ? dy - 1 : dy + 1), 0);
			}
			if(!(dz == 1 || dz == -1)) {
				addShapeCube(builder, x, 0, (dz > 0 ? dz - 1 : dz + 1));
			}
			if(!(dy == 1 || dy == -1 || dz == 1 || dz == -1)) {
				addShapeCube(builder, x, (dy > 0 ? dy - 1 : dy + 1), (dz > 0 ? dz - 1 : dz + 1));
			}
		}
		for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
			addShapeCube(builder, 0, y, 0);
			if(!(dx == 1 || dx == -1)) {
				addShapeCube(builder, (dx > 0 ? dx - 1 : dx + 1), y, 0);
			}
			if(!(dz == 1 || dz == -1)) {
				addShapeCube(builder, 0, y, (dz > 0 ? dz - 1 : dz + 1));
			}
			if(!(dx == 1 || dx == -1 || dz == 1 || dz == -1)) {
				addShapeCube(builder, (dx > 0 ? dx - 1 : dx + 1), y, (dz > 0 ? dz - 1 : dz + 1));
			}
		}
		for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
			addShapeCube(builder, 0, 0, z);
			if(!(dx == 1 || dx == -1)) {
				addShapeCube(builder, (dx > 0 ? dx - 1 : dx + 1), 0, z);
			}
			if(!(dy == 1 || dy == -1)) {
				addShapeCube(builder, 0, (dy > 0 ? dy - 1 : dy + 1), z);
			}
			if(!(dx == 1 || dx == -1 || dy == 1 || dy == -1)) {
				addShapeCube(builder, (dx > 0 ? dx - 1 : dx + 1), (dy > 0 ? dy - 1 : dy + 1), z);
			}
		}
		
		//X wall
		if(w == walls.ALL || w == walls.X || w == walls.XY || w == walls.XZ) {
			for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
				for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
					addShapeCube(builder, 0, y, z);
					if(!(dx == 1 || dx == -1)) {
						addShapeCube(builder, (dx > 0 ? dx - 1 : dx + 1), y, z);
					}
				}
			}
		}
		
		//Y wall
		if(w == walls.ALL || w == walls.Y || w == walls.XY || w == walls.YZ) {
			for(int x = (dx > 0 ? 1 : dx + 2);x < (dx > 0 ? dx - 1 : 0);++x) {
				for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
					addShapeCube(builder, x, 0, z);
					if(!(dy == 1 || dy == -1)) {
						addShapeCube(builder, x, (dy > 0 ? dy - 1 : dy + 1), z);
					}
				}
			}
		}
		
		//Z wall
		if(w == walls.ALL || w == walls.Z || w == walls.XZ || w == walls.YZ) {
			for(int x = (dx > 0 ? 1 : dx + 2);x < (dx > 0 ? dx - 1 : 0);++x) {
				for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
					addShapeCube(builder, x, y, 0);
					if(!(dz == 1 || dz == -1)) {
						addShapeCube(builder, x, y, (dz > 0 ? dz - 1 : dz + 1));
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.cuboid";
	}
}
