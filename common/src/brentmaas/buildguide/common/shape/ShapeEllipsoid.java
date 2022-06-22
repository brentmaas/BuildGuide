package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyPositiveInt;

public class ShapeEllipsoid extends Shape {
	private enum dome {
		NO,
		POSITIVE_X,
		POSITIVE_Y,
		POSITIVE_Z,
		NEGATIVE_X,
		NEGATIVE_Y,
		NEGATIVE_Z
	}
	
	private String[] domeNames = {"-", "+X", "+Y", "+Z", "-X", "-Y", "-Z"};
	
	private PropertyPositiveInt propertySemiX = new PropertyPositiveInt(3, BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "X"), () -> update());
	private PropertyPositiveInt propertySemiY = new PropertyPositiveInt(3, BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Y"), () -> update());
	private PropertyPositiveInt propertySemiZ = new PropertyPositiveInt(3, BuildGuide.screenHandler.translate("property.buildguide.semiaxis", "Z"), () -> update());
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(dome.NO, BuildGuide.screenHandler.translate("property.buildguide.dome"), () -> update(), domeNames);
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.evenmode"), () -> update());
	
	public ShapeEllipsoid() {
		super();
		
		properties.add(propertySemiX);
		properties.add(propertySemiY);
		properties.add(propertySemiZ);
		properties.add(propertyDome);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		int dx = propertySemiX.value;
		int dy = propertySemiY.value;
		int dz = propertySemiZ.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		setBaseposOffset(offset, offset, offset);
		
		for(int x = (int) Math.floor((propertyDome.value == dome.POSITIVE_X ? 0 : -dx) + offset); x <= (int) Math.ceil((propertyDome.value == dome.NEGATIVE_X ? 0 : dx) + offset);++x) {
			for(int y = (int) Math.floor((propertyDome.value == dome.POSITIVE_Y ? 0 : -dy) + offset); y <= (int) Math.ceil((propertyDome.value == dome.NEGATIVE_Y ? 0 : dy) + offset);++y) {
				for(int z = (int) Math.floor((propertyDome.value == dome.POSITIVE_Z ? 0 : -dz) + offset); z <= (int) Math.ceil((propertyDome.value == dome.NEGATIVE_Z ? 0 : dz) + offset);++z) {
					double phi = Math.atan2((double) dx / dy * (y - offset), x - offset);
					double theta = Math.atan2(Math.sqrt((x - offset) * (x - offset) + (double) dx * dx / dy / dy * (y - offset) * (y - offset)), (double) dx / dz * (z - offset));
					double corr = Math.sqrt(1 + ((double) dx * dx / dy / dy - 1) * Math.sin(phi) * Math.sin(phi) * Math.sin(theta) * Math.sin(theta) + ((double) dx * dx / dz / dz - 1) * Math.cos(theta) * Math.cos(theta));
					double drx = 0.5 * Math.cos(phi) * Math.sin(theta) / corr;
					double dry = 0.5 * Math.sin(phi) * Math.sin(theta) * dx / dy / corr;
					double drz = 0.5 * Math.cos(theta) * dx / dz / corr;
					double r2_inner = (x - offset - drx) * (x - offset - drx) / dx / dx + (y - offset - dry) * (y - offset - dry) / dy / dy + (z - offset - drz) * (z - offset - drz) / dz / dz;
					double r2_outer = (x - offset + drx) * (x - offset + drx) / dx / dx + (y - offset + dry) * (y - offset + dry) / dy / dy + (z - offset + drz) * (z - offset + drz) / dz / dz;
					if(r2_outer >= 1 && r2_inner <= 1) {
						addShapeCube(buffer, x, y, z);
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.ellipsoid";
	}
}
