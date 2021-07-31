package brentmaas.buildguide.shapes;

import com.mojang.blaze3d.vertex.BufferBuilder;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.network.chat.TranslatableComponent;

public class ShapeTorus extends Shape{
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslatableComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyPositiveInt propertyOuterRadius = new PropertyPositiveInt(0, 165, 5, new TranslatableComponent("property.buildguide.outerradius"), () -> {this.updateOuter();});
	private PropertyPositiveInt propertyInnerRadius = new PropertyPositiveInt(0, 185, 3, new TranslatableComponent("property.buildguide.innerradius"), () -> {this.updateInner();});
	
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
							addCube(builder, a + 0.2, b + 0.2, c + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
							break;
						case Y:
							addCube(builder, b + 0.2, c + 0.2, a + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
							break;
						case Z:
							addCube(builder, c + 0.2, a + 0.2, b + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
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
