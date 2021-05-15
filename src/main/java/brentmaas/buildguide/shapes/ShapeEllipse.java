package brentmaas.buildguide.shapes;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyNonzeroInt;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeEllipse extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, 145, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> {this.update();}, directionNames);
	private PropertyPositiveInt propertySemi1 = new PropertyPositiveInt(0, 165, 3, new TranslationTextComponent("property.buildguide.semiaxis", "Y"), () -> {this.update();});
	private PropertyPositiveInt propertySemi2 = new PropertyPositiveInt(0, 185, 3, new TranslationTextComponent("property.buildguide.semiaxis", "Z"), () -> {this.update();});
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(0, 205, 1, new TranslationTextComponent("property.buildguide.height"), () -> {this.update();});
	
	public ShapeEllipse() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertySemi1);
		properties.add(propertySemi2);
		properties.add(propertyHeight);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int da = propertySemi1.value;
		int db = propertySemi2.value;
		
		for(int a = -da; a <= da;++a) {
			for(int b = -db; b <= db;++b) {
				double theta = Math.atan2((double) da / db * b, a);
				double corr = Math.sqrt(1 + ((double) da * da / db / db - 1) * Math.sin(theta) * Math.sin(theta));
				double dra = 0.5 * Math.cos(theta) / corr;
				double drb = 0.5 * Math.sin(theta) * da / db / corr;
				double r2_inner = (a - dra) * (a - dra) / da / da + (b - drb) * (b - drb) / db / db;
				double r2_outer = (a + dra) * (a + dra) / da / da + (b + drb) * (b + drb) / db / db;
				if(r2_outer >= 1 && r2_inner <= 1) {
					for(int s = (propertyHeight.value > 0 ? 0 : propertyHeight.value + 1);s < (propertyHeight.value > 0 ? propertyHeight.value : 1);++s) {
						switch(propertyDir.value) {
						case X:
							addCube(builder, s + 0.2, a + 0.2, b + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
							break;
						case Y:
							addCube(builder, a + 0.2, s + 0.2, b + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
							break;
						case Z:
							addCube(builder, a + 0.2, b + 0.2, s + 0.2, 0.6, BuildGuide.state.colourShapeR, BuildGuide.state.colourShapeG, BuildGuide.state.colourShapeB, BuildGuide.state.colourShapeA);
							break;
						}
					}
				}
			}
		}
		
		switch(propertyDir.value) {
		case X:
			propertySemi1.setName(new TranslationTextComponent("property.buildguide.semiaxis", "Y"));
			propertySemi2.setName(new TranslationTextComponent("property.buildguide.semiaxis", "Z"));
			break;
		case Y:
			propertySemi1.setName(new TranslationTextComponent("property.buildguide.semiaxis", "X"));
			propertySemi2.setName(new TranslationTextComponent("property.buildguide.semiaxis", "Z"));
			break;
		case Z:
			propertySemi1.setName(new TranslationTextComponent("property.buildguide.semiaxis", "X"));
			propertySemi2.setName(new TranslationTextComponent("property.buildguide.semiaxis", "Y"));
			break;
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.ellipse";
	}
}
