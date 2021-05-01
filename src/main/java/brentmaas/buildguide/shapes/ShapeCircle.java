package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

//TODO Odd sphere + even sphere?
public class ShapeCircle extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslationTextComponent("property.buildguide.direction").getString(), this, directionNames);
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 165, 3, new TranslationTextComponent("property.buildguide.radius").getString(), this);
	
	public ShapeCircle() {
		super();
		
		update();
		
		properties.add(propertyDir);
		properties.add(propertyRadius);
		
		onDeselectedInGUI();
	}
	
	protected void updateShape() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		int dx = propertyRadius.value, dy = propertyRadius.value, dz = propertyRadius.value;
		switch(propertyDir.value) {
		case X:
			dx = 0;
			break;
		case Y:
			dy = 0;
			break;
		case Z:
			dz = 0;
			break;
		}
		
		for(int x = -dx; x <= dx;++x) {
			for(int y = -dy; y <= dy;++y) {
				for(int z = -dz; z <= dz;++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (propertyRadius.value - 0.5) * (propertyRadius.value - 0.5) && r2 <= (propertyRadius.value + 0.5) * (propertyRadius.value + 0.5)) {
						this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z + z));
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.circle";
	}
}
