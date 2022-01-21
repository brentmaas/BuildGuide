package brentmaas.buildguide.fabric.shapes;

import brentmaas.buildguide.fabric.property.PropertyEnum;
import brentmaas.buildguide.fabric.property.PropertyPositiveInt;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.text.TranslatableText;

public class ShapeTorus extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslatableText("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveInt propertyOuterRadius = new PropertyPositiveInt(1, 5, new TranslatableText("property.buildguide.outerradius"), () -> updateOuter());
	private PropertyPositiveInt propertyInnerRadius = new PropertyPositiveInt(2, 3, new TranslatableText("property.buildguide.innerradius"), () -> updateInner());
	
	public ShapeTorus() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyOuterRadius);
		properties.add(propertyInnerRadius);
	}
	
	protected void updateShape(BufferBuilder builder) {
		for(int a = -propertyOuterRadius.value - propertyInnerRadius.value;a < propertyOuterRadius.value + propertyInnerRadius.value + 1;++a) {
			for(int b = -propertyOuterRadius.value - propertyInnerRadius.value;b < propertyOuterRadius.value + propertyInnerRadius.value + 1;++b) {
				double theta = Math.atan2(b, a);
				double a_circ = propertyOuterRadius.value * Math.cos(theta);
				double b_circ = propertyOuterRadius.value * Math.sin(theta);
				for(int c = -propertyInnerRadius.value;c < propertyInnerRadius.value + 1;++c) {
					double r2 = (a - a_circ) * (a - a_circ) + (b - b_circ) * (b - b_circ) + c * c;
					if(r2 >= (propertyInnerRadius.value - 0.5) * (propertyInnerRadius.value - 0.5) && r2 < (propertyInnerRadius.value + 0.5) * (propertyInnerRadius.value + 0.5)) {
						switch(propertyDir.value) {
						case X:
							addShapeCube(builder, a, b, c);
							break;
						case Y:
							addShapeCube(builder, b, c, a);
							break;
						case Z:
							addShapeCube(builder, c, a, b);
							break;
						}
					}
				}
			}
		}
	}
	
	private void updateOuter() {
		if(propertyOuterRadius.value < propertyInnerRadius.value) propertyInnerRadius.setValue(propertyOuterRadius.value);;
		
		update();
	}
	
	private void updateInner() {
		if(propertyOuterRadius.value < propertyInnerRadius.value) propertyOuterRadius.setValue(propertyInnerRadius.value);
		
		update();
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.torus";
	}
}
