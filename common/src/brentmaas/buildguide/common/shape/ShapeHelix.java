package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyFloat;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeHelix extends Shape {
	private enum direction { X, Y, Z }

	private String[] directionNames = { "X", "Y", "Z" };

	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.Y, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyPositiveFloat propertyRadius = new PropertyPositiveFloat(3, new Translatable("property.buildguide.radius"), () -> update());
	private PropertyPositiveFloat propertyDegrees = new PropertyPositiveFloat(360, new Translatable("property.buildguide.degrees"), () -> update());
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(10, new Translatable("property.buildguide.height"), () -> update());
	private PropertyPositiveFloat propertyWidth = new PropertyPositiveFloat(1, new Translatable("property.buildguide.width"), () -> update());
	private PropertyFloat propertyRotation = new PropertyFloat(0, new Translatable("property.buildguide.rotation"), () -> update());
	private PropertyBoolean propertyFlip = new PropertyBoolean(false, new Translatable("property.buildguide.invert"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());

	public ShapeHelix() {
		super();

		properties.add(propertyDir);
		properties.add(propertyRadius);
		properties.add(propertyDegrees);
		properties.add(propertyHeight);
		properties.add(propertyWidth);
		properties.add(propertyRotation);
		properties.add(propertyFlip);
		properties.add(propertyEvenMode);
	}

	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float radius = propertyRadius.value;
		float degrees = propertyDegrees.value;
		int height = propertyHeight.value;
		float width = propertyWidth.value;
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		setOriginOffset(propertyDir.value == direction.X ? 0 : offset, propertyDir.value == direction.Y ? 0 : offset, propertyDir.value == direction.Z ? 0 : offset);

		double radiansMax = Math.toRadians(degrees);

		// Sample density based on the arc length to avoid gaps.
		// Using a step size that is smaller than 1 block to ensure connectivity.
		double angleStep = 0.1;
		if (radius > 0) {
			angleStep = Math.min(0.1, 1.0 / (radius * 2));
		}

		double rotationRadians = Math.toRadians(propertyRotation.value);
		double sign = propertyFlip.value ? -1.0 : 1.0;
		for (double theta = 0; theta <= radiansMax; theta += angleStep) {
			double h = (theta / radiansMax) * height;
			double currentTheta = theta * sign;
		
			for (double r = radius; r <= radius + width; r += 0.5) {
				double xC = r * Math.cos(currentTheta + rotationRadians);
				double zC = r * Math.sin(currentTheta + rotationRadians);

				int ix = (int) Math.round(xC + offset);
				int iz = (int) Math.round(zC + offset);
				int ih = (int) Math.round(h + offset);

				switch (propertyDir.value) {
					case X:
						addShapeCube(buffer, ih, ix, iz);
						break;
					case Y:
						addShapeCube(buffer, ix, ih, iz);
						break;
					case Z:
						addShapeCube(buffer, ix, iz, ih);
						break;
				}
			}
		}
	}
}
