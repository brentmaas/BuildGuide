package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyFloat;
import brentmaas.buildguide.common.property.PropertyMinimumFloat;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeHelix extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};

	private PropertyEnum<direction> propertyDir = new PropertyEnum<direction>(direction.Y, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
	private PropertyMinimumFloat propertyOuterRadius = new PropertyMinimumFloat(5, new Translatable("property.buildguide.outerradius"), () -> updateOuter(), 2.0f, true);
	private PropertyMinimumFloat propertyInnerRadius = new PropertyMinimumFloat(3, new Translatable("property.buildguide.innerradius"), () -> updateInner(), 1.0f, true);
	private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(5, new Translatable("property.buildguide.height"), () -> update());
	private PropertyPositiveFloat propertyDegrees = new PropertyPositiveFloat(360, new Translatable("property.buildguide.degrees"), () -> update());
	private PropertyFloat propertyDegreesOffset = new PropertyFloat(0, new Translatable("property.buildguide.offset", new Translatable("property.buildguide.degrees").toString()), () -> update());
	private PropertyBoolean propertyInvert = new PropertyBoolean(false, new Translatable("property.buildguide.invert"), () -> update());
	private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());
	
	public ShapeHelix() {
		super();
		
		properties.add(propertyDir);
		properties.add(propertyOuterRadius);
		properties.add(propertyInnerRadius);
		properties.add(propertyHeight);
		properties.add(propertyDegrees);
		properties.add(propertyDegreesOffset);
		properties.add(propertyInvert);
		properties.add(propertyEvenMode);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
		float outerRadius = propertyOuterRadius.value;
		float innerRadius = propertyInnerRadius.value;
		int height = propertyHeight.value;
		float degrees = propertyDegrees.value;
		int turns = (int) Math.ceil(degrees / 360.0f);
		float heightPerTurn = 360.0f * (height + (height > 0 ? -1.0f : 1.0f)) / degrees;
		float degreesOffset = propertyDegreesOffset.value;
		boolean invert = propertyInvert.value ^ (height > 0);
		double offset = propertyEvenMode.value ? 0.5 : 0.0;
		setOriginOffset(propertyDir.value == direction.X ? 0 : offset, propertyDir.value == direction.Y ? 0 : offset, propertyDir.value == direction.Z ? 0 : offset);
		
		for(int x = (int) Math.floor(-outerRadius + offset);x <= (int) Math.ceil(outerRadius + offset);++x) {
			for(int y = (int) Math.floor(-outerRadius + offset);y <= (int) Math.ceil(outerRadius + offset);++y) {
				double r2 = (x - offset) * (x - offset) + (y - offset) * (y - offset);
				if(r2 < (innerRadius - 0.5) * (innerRadius - 0.5) || r2 > (outerRadius - 0.5) * (outerRadius - 0.5)) {
					continue;
				}
				
				double theta = (invert ? -1.0f : 1.0f) * (Math.toDegrees(Math.atan2(y - offset, x - offset)) - degreesOffset);
				double dtheta = Math.toDegrees(0.5f/ Math.sqrt(r2));
				double gradient = Math.abs(heightPerTurn / (2 * Math.PI * Math.sqrt(r2)));
				if(gradient <= 1.0f) { // Flat helix
					for(int z = (height > 0 ? 0 : height + 1);z < (height > 0 ? height : 1);++z) {
						for(int i = -1; i <= turns; ++i) {
							double helixHeight = (theta / 360.0f + i) * heightPerTurn;
							if(helixHeight >= z - 0.5 && helixHeight < z + 0.5) {
								double angle = theta + i * 360.0f;
								if(angle < Math.min(0.0f, degrees) || angle > Math.max(0.0f, degrees)) {
									continue;
								}
								
								switch(propertyDir.value) {
								case X:
									addShapeCube(buffer, z, x, y);
									break;
								case Y:
									addShapeCube(buffer, x, z, y);
									break;
								case Z:
									addShapeCube(buffer, x, y, z);
									break;
								}
								break;
							}
						}
					}
				}else { // Steep helix
					for(int z = (height > 0 ? 0 : height + 1);z < (height > 0 ? height : 1);++z) {
						for(int i = -1; i <= turns; ++i) {
							double helixHeightLeft = ((theta - dtheta) / 360.0f + i) * heightPerTurn;
							double helixHeightRight = ((theta + dtheta) / 360.0f + i) * heightPerTurn;
							if(Math.min(helixHeightLeft, helixHeightRight) < z + 0.5 && Math.max(helixHeightLeft, helixHeightRight) >= z - 0.5) {
								double angle = theta + i * 360.0f;
								if(angle < Math.min(0.0f, degrees) - dtheta || angle > Math.max(0.0f, degrees) + dtheta) {
									continue;
								}
								
								switch(propertyDir.value) {
								case X:
									addShapeCube(buffer, z, x, y);
									break;
								case Y:
									addShapeCube(buffer, x, z, y);
									break;
								case Z:
									addShapeCube(buffer, x, y, z);
									break;
								}
								break;
							}
						}
					}
				}
			}
		}
	}
	
	private void updateOuter() {
		if(propertyOuterRadius.value <= propertyInnerRadius.value) propertyInnerRadius.setValue(propertyOuterRadius.value - 1.0f);
		
		update();
	}
	
	private void updateInner() {
		if(propertyOuterRadius.value <= propertyInnerRadius.value) propertyOuterRadius.setValue(propertyInnerRadius.value + 1.0f);
		
		update();
	}
}
