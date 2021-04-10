package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

//TODO Odd sphere + even sphere
public class ShapeSphere extends Shape{
	private PropertyPositiveInt propertyRadius = new PropertyPositiveInt(0, 145, 3, new TranslationTextComponent("property.buildguide.radius").getString(), this);
	
	public ShapeSphere() {
		super();
		
		update();
		
		properties.add(propertyRadius);
		
		onDeselectedInGUI();
	}
	
	public void update() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		int radius = propertyRadius.value;
		
		for(int x = -radius; x <= radius;++x) {
			for(int y = -radius; y <= radius;++y) {
				for(int z = -radius; z <= radius;++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
						this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z + z));
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.sphere";
	}
}
