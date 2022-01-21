package brentmaas.buildguide.shapes;

import brentmaas.buildguide.property.PropertyEnum;
import brentmaas.buildguide.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeEllipsoid extends Shape{
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
	
	private PropertyPositiveInt propertySemiX = new PropertyPositiveInt(0, 3, new TranslationTextComponent("property.buildguide.semiaxis", "X"), () -> update());
	private PropertyPositiveInt propertySemiY = new PropertyPositiveInt(1, 3, new TranslationTextComponent("property.buildguide.semiaxis", "Y"), () -> update());
	private PropertyPositiveInt propertySemiZ = new PropertyPositiveInt(2, 3, new TranslationTextComponent("property.buildguide.semiaxis", "Z"), () -> update());
	private PropertyEnum<dome> propertyDome = new PropertyEnum<dome>(3, dome.NO, new TranslationTextComponent("property.buildguide.dome"), () -> update(), domeNames);
	
	public ShapeEllipsoid() {
		super();
		
		properties.add(propertySemiX);
		properties.add(propertySemiY);
		properties.add(propertySemiZ);
		properties.add(propertyDome);
	}
	
	protected void updateShape(BufferBuilder builder) {
		int dx = propertySemiX.value;
		int dy = propertySemiY.value;
		int dz = propertySemiZ.value;
		
		for(int x = propertyDome.value == dome.POSITIVE_X ? 0 : -dx; x <= (propertyDome.value == dome.NEGATIVE_X ? 0 : dx);++x) {
			for(int y = propertyDome.value == dome.POSITIVE_Y ? 0 : -dy; y <= (propertyDome.value == dome.NEGATIVE_Y ? 0 : dy);++y) {
				for(int z = propertyDome.value == dome.POSITIVE_Z ? 0 : -dz; z <= (propertyDome.value == dome.NEGATIVE_Z ? 0 : dz);++z) {
					double phi = Math.atan2((double) dx / dy * y, x);
					double theta = Math.atan2(Math.sqrt(x * x + (double) dx * dx / dy / dy * y * y), (double) dx / dz * z);
					double corr = Math.sqrt(1 + ((double) dx * dx / dy / dy - 1) * Math.sin(phi) * Math.sin(phi) * Math.sin(theta) * Math.sin(theta) + ((double) dx * dx / dz / dz - 1) * Math.cos(theta) * Math.cos(theta));
					double drx = 0.5 * Math.cos(phi) * Math.sin(theta) / corr;
					double dry = 0.5 * Math.sin(phi) * Math.sin(theta) * dx / dy / corr;
					double drz = 0.5 * Math.cos(theta) * dx / dz / corr;
					double r2_inner = (x - drx) * (x - drx) / dx / dx + (y - dry) * (y - dry) / dy / dy + (z - drz) * (z - drz) / dz / dz;
					double r2_outer = (x + drx) * (x + drx) / dx / dx + (y + dry) * (y + dry) / dy / dy + (z + drz) * (z + drz) / dz / dz;
					if(r2_outer >= 1 && r2_inner <= 1) {
						addShapeCube(builder, x, y, z);
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.ellipsoid";
	}
}
