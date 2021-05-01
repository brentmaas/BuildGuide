package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import net.minecraft.client.renderer.BufferBuilder;

public class ShapeCuboid extends Shape{
	private PropertyNonzeroInt propertyX = new PropertyNonzeroInt(0, 145, 3, "X", this);
	private PropertyNonzeroInt propertyY = new PropertyNonzeroInt(0, 165, 3, "Y", this);
	private PropertyNonzeroInt propertyZ = new PropertyNonzeroInt(0, 185, 3, "Z", this);
	
	public ShapeCuboid() {
		super();
		
		properties.add(propertyX);
		properties.add(propertyY);
		properties.add(propertyZ);
		
		onDeselectedInGUI();
	}
	
	protected void updateShape(BufferBuilder builder) {
		if(State.basePos == null) return;
		
		int dx = propertyX.value;
		int dy = propertyY.value;
		int dz = propertyZ.value;
		
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int z = (dz > 0 ? 0 : dz + 1);z < (dz > 0 ? dz : 1);++z) {
				addCube(builder, State.basePos.x + x + 0.2, State.basePos.y + 0.2, State.basePos.z + z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				if(!(dy == 1 || dy == -1)) {
					addCube(builder, State.basePos.x + x + 0.2, State.basePos.y + (dy > 0 ? dy - 1 : dy + 1) + 0.2, State.basePos.z + z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				}
			}
		}
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
				addCube(builder, State.basePos.x + x + 0.2, State.basePos.y + y + 0.2, State.basePos.z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				if(!(dz == 1 || dz == -1)) {
					addCube(builder, State.basePos.x + x + 0.2, State.basePos.y + y + 0.2, State.basePos.z + (dz > 0 ? dz - 1 : dz + 1) + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				}
			}
		}
		for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
			for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
				addCube(builder, State.basePos.x + 0.2, State.basePos.y + y + 0.2, State.basePos.z + z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				if(!(dx == 1 || dx == -1)) {
					addCube(builder, State.basePos.x + (dx > 0 ? dx - 1 : dx + 1) + 0.2, State.basePos.y + y + 0.2, State.basePos.z + z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.cuboid";
	}
}
