package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import net.minecraft.util.math.vector.Vector3d;

public class ShapeCuboid extends Shape{
	private PropertyNonzeroInt propertyX = new PropertyNonzeroInt(0, 145, 3, "X", this);
	private PropertyNonzeroInt propertyY = new PropertyNonzeroInt(0, 165, 3, "Y", this);
	private PropertyNonzeroInt propertyZ = new PropertyNonzeroInt(0, 185, 3, "Z", this);
	
	public ShapeCuboid() {
		super();
		
		update();
		
		properties.add(propertyX);
		properties.add(propertyY);
		properties.add(propertyZ);
		
		onDeselectedInGUI();
	}
	
	public void update() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		int dx = propertyX.value;
		int dy = propertyY.value;
		int dz = propertyZ.value;
		
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int z = (dz > 0 ? 0 : dz + 1);z < (dz > 0 ? dz : 1);++z) {
				this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y, State.basePos.z + z));
				if(!(dy == 1 || dy == -1)) {
					this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + (dy > 0 ? dy - 1 : dy + 1), State.basePos.z + z));
				}
			}
		}
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
				this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z));
				if(!(dz == 1 || dz == -1)) {
					this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z + (dz > 0 ? dz - 1 : dz + 1)));
				}
			}
		}
		for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
			for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
				this.posList.add(new Vector3d(State.basePos.x, State.basePos.y + y, State.basePos.z + z));
				if(!(dx == 1 || dx == -1)) {
					this.posList.add(new Vector3d(State.basePos.x + (dx > 0 ? dx - 1 : dx + 1), State.basePos.y + y, State.basePos.z + z));
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.cuboid";
	}
}
