package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;

public class ShapeEllipse extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.X, BuildGuide.screenHandler.translate("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveFloat propertySemi1 = new PropertyPositiveFloat(3, BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Y"), () -> update());
	private PropertyPositiveFloat propertySemi2 = new PropertyPositiveFloat(3, BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Z"), () -> update());
	private PropertyNonzeroInt propertyDepth = new PropertyNonzeroInt(1, BuildGuide.screenHandler.translate("property.buildguide.depth"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.evenmode"), () -> update());
	
	public ShapeEllipse() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertySemi1);
		properties.add(propertySemi2);
		properties.add(propertyDepth);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float da = propertySemi1.value, db = propertySemi2.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		switch(propertyDir.value) {
		case X:
			setBaseposOffset(0.0, offset, offset);
			break;
		case Y:
			setBaseposOffset(offset, 0.0, offset);
			break;
		case Z:
			setBaseposOffset(offset, offset, 0.0);
			break;
		}
		
		for(int a = (int) Math.floor(-da + offset); a <= (int) Math.ceil(da + offset);++a) {
			for(int b = (int) Math.floor(-db + offset); b <= (int) Math.ceil(db + offset);++b) {
				double theta = Math.atan2((double) da / db * (b - offset), a - offset);
				double corr = Math.sqrt(1 + ((double) da * da / db / db - 1) * Math.sin(theta) * Math.sin(theta));
				double dra = 0.5 * Math.cos(theta) / corr;
				double drb = 0.5 * Math.sin(theta) * da / db / corr;
				double r2_inner = (a - offset - dra) * (a - offset - dra) / da / da + (b - offset - drb) * (b - offset - drb) / db / db;
				double r2_outer = (a - offset + dra) * (a - offset + dra) / da / da + (b - offset + drb) * (b - offset + drb) / db / db;
				if(!(r2_outer >= 1 && r2_inner <= 1) && a - offset != 0 && b - offset != 0) continue; //a != 0 and b != 0 for edge cases
				
				//Edge cases
				if(a - offset == 0 && Math.abs(b - offset) != db) {
					double theta2 = Math.atan2((double) da / db * b - offset, 1);
					double corr2 = Math.sqrt(1 + ((double) da * da / db / db - 1) * Math.sin(theta2) * Math.sin(theta2));
					double dra2 = 0.5 * Math.cos(theta2) / corr2;
					double drb2 = 0.5 * Math.sin(theta2) * da / db / corr2;
					double r2_inner2 = (1 - dra2) * (1 - dra2) / da / da + (b - offset - drb2) * (b - offset - drb2) / db / db;
					double r2_outer2 = (1 + dra2) * (1 + dra2) / da / da + (b - offset + drb2) * (b - offset + drb2) / db / db;
					if((r2_outer2 >= 1 && r2_inner2 <= 1) || 1.0 / da / da + (double) (b - offset) * (b - offset) / db / db < 1) continue;
				}
				if(b - offset == 0 && Math.abs(a - offset) != da) {
					double theta2 = Math.atan2((double) da / db, a - offset);
					double corr2 = Math.sqrt(1 + ((double) da * da / db / db - 1) * Math.sin(theta2) * Math.sin(theta2));
					double dra2 = 0.5 * Math.cos(theta2) / corr2;
					double drb2 = 0.5 * Math.sin(theta2) * da / db / corr2;
					double r2_inner2 = (a - offset - dra2) * (a - offset - dra2) / da / da + (1 - drb2) * (1 - drb2) / db / db;
					double r2_outer2 = (a - offset + dra2) * (a - offset + dra2) / da / da + (1 + drb2) * (1 + drb2) / db / db;
					if((r2_outer2 >= 1 && r2_inner2 <= 1) || (double) (a - offset) * (a - offset) / da / da + 1.0 / db / db < 1) continue;
				}
				
				for(int s = (propertyDepth.value > 0 ? 0 : propertyDepth.value + 1);s < (propertyDepth.value > 0 ? propertyDepth.value : 1);++s) {
					switch(propertyDir.value) {
					case X:
						addShapeCube(buffer, s, a, b);
						break;
					case Y:
						addShapeCube(buffer, a, s, b);
						break;
					case Z:
						addShapeCube(buffer, a, b, s);
						break;
					}
				}
			}
		}
		
		switch(propertyDir.value) {
		case X:
			propertySemi1.setName(BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Y"));
			propertySemi2.setName(BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Z"));
			break;
		case Y:
			propertySemi1.setName(BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "X"));
			propertySemi2.setName(BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Z"));
			break;
		case Z:
			propertySemi1.setName(BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "X"));
			propertySemi2.setName(BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Y"));
			break;
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.ellipse";
	}
}
