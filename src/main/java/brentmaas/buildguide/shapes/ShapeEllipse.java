package brentmaas.buildguide.shapes;

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
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(0, direction.X, new TranslationTextComponent("property.buildguide.direction"), () -> this.update(), directionNames);
	private PropertyPositiveInt propertySemi1 = new PropertyPositiveInt(1, 3, new TranslationTextComponent("property.buildguide.semiaxis", "Y"), () -> this.update());
	private PropertyPositiveInt propertySemi2 = new PropertyPositiveInt(2, 3, new TranslationTextComponent("property.buildguide.semiaxis", "Z"), () -> this.update());
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(3, 1, new TranslationTextComponent("property.buildguide.height"), () -> this.update());
	
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
				if(!(r2_outer >= 1 && r2_inner <= 1) && a != 0 && b != 0) continue; //a != 0 and b != 0 for edge cases
				
				//Edge cases
				if(a == 0 && Math.abs(b) != db) {
					double theta2 = Math.atan2((double) da / db * b, 1);
					double corr2 = Math.sqrt(1 + ((double) da * da / db / db - 1) * Math.sin(theta2) * Math.sin(theta2));
					double dra2 = 0.5 * Math.cos(theta2) / corr2;
					double drb2 = 0.5 * Math.sin(theta2) * da / db / corr2;
					double r2_inner2 = (1 - dra2) * (1 - dra2) / da / da + (b - drb2) * (b - drb2) / db / db;
					double r2_outer2 = (1 + dra2) * (1 + dra2) / da / da + (b + drb2) * (b + drb2) / db / db;
					if((r2_outer2 >= 1 && r2_inner2 <= 1) || 1.0 / da / da + (double) b * b / db / db < 1) continue;
				}
				if(b == 0 && Math.abs(a) != da) {
					double theta2 = Math.atan2((double) da / db, a);
					double corr2 = Math.sqrt(1 + ((double) da * da / db / db - 1) * Math.sin(theta2) * Math.sin(theta2));
					double dra2 = 0.5 * Math.cos(theta2) / corr2;
					double drb2 = 0.5 * Math.sin(theta2) * da / db / corr2;
					double r2_inner2 = (a - dra2) * (a - dra2) / da / da + (1 - drb2) * (1 - drb2) / db / db;
					double r2_outer2 = (a + dra2) * (a + dra2) / da / da + (1 + drb2) * (1 + drb2) / db / db;
					if((r2_outer2 >= 1 && r2_inner2 <= 1) || (double) a * a / da / da + 1.0 / db / db < 1) continue;
				}
				
				for(int s = (propertyHeight.value > 0 ? 0 : propertyHeight.value + 1);s < (propertyHeight.value > 0 ? propertyHeight.value : 1);++s) {
					switch(propertyDir.value) {
					case X:
						addShapeCube(builder, s, a, b);
						break;
					case Y:
						addShapeCube(builder, a, s, b);
						break;
					case Z:
						addShapeCube(builder, a, b, s);
						break;
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
