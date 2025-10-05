package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyEnum;
import brentmaas.buildguide.common.property.PropertyNonzeroInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;

public class ShapeHelicoid extends Shape {
    private enum direction {
        X,
        Y,
        Z
    }

    private enum rotationAngle {
        DEG_0(0),
        DEG_90(90),
        DEG_180(180),
        DEG_270(270);

        private final int degrees;
        rotationAngle(int degrees) { this.degrees = degrees; }
        public int getDegrees() { return degrees; }
    }

    public enum SpinDirection {
        CLOCKWISE,
        COUNTERCLOCKWISE
    }

    private String[] directionNames = {"X", "Y", "Z"};
    private String[] rotationNames = {"0°", "90°", "180°", "270°"};
    private String[] spinNames = {"Clockwise", "Counterclockwise"};

    private PropertyEnum<direction> propertyDir = new PropertyEnum<>(direction.Z, new Translatable("property.buildguide.direction"), () -> update(), directionNames);
    private PropertyPositiveFloat propertyRadius = new PropertyPositiveFloat(3, new Translatable("property.buildguide.radius"), () -> update());
    private PropertyNonzeroInt propertyTurns = new PropertyNonzeroInt(3, new Translatable("property.buildguide.turns"), () -> update());
    private PropertyNonzeroInt propertyHeight = new PropertyNonzeroInt(5, new Translatable("property.buildguide.height"), () -> update());
    private PropertyNonzeroInt propertyThickness = new PropertyNonzeroInt(1, new Translatable("property.buildguide.thickness"), () -> update());
    private PropertyEnum<rotationAngle> propertyRotation = new PropertyEnum<>(rotationAngle.DEG_0, new Translatable("property.buildguide.rotation"), () -> update(), rotationNames);
    private PropertyEnum<SpinDirection> propertySpin = new PropertyEnum<>(SpinDirection.COUNTERCLOCKWISE, new Translatable("property.buildguide.spin"), () -> update(), spinNames);
    private PropertyBoolean propertyEvenMode = new PropertyBoolean(false, new Translatable("property.buildguide.evenmode"), () -> update());

    public ShapeHelicoid() {
        super();
        properties.add(propertyDir);
        properties.add(propertyRadius);
        properties.add(propertyTurns);
        properties.add(propertyHeight);
        properties.add(propertyThickness);
        properties.add(propertyRotation);
        properties.add(propertySpin);
        properties.add(propertyEvenMode);
    }

    protected void updateShape(IShapeBuffer buffer) throws InterruptedException {
        float r = propertyRadius.value;
        int turns = propertyTurns.value;
        int h = propertyHeight.value;
        int thickness = Math.min(propertyThickness.value, (int) r);
        double offset = propertyEvenMode.value ? 0.5 : 0.0;

        double rotationRad = Math.toRadians(propertyRotation.value.getDegrees());

        // Signo según sentido de giro
        int spinSign = (propertySpin.value == SpinDirection.COUNTERCLOCKWISE) ? 1 : -1;

        switch(propertyDir.value) {
            case X -> setOriginOffset(0.0, offset, offset);
            case Y -> setOriginOffset(offset, 0.0, offset);
            case Z -> setOriginOffset(offset, offset, 0.0);
        }

        int stepsPerTurn = 36; 
        int totalSteps = stepsPerTurn * turns;
        double angleIncrement = 2 * Math.PI / stepsPerTurn;
        double heightIncrement = (double) h / totalSteps;

        for(int step = 0; step <= totalSteps; ++step) {
            double angle = spinSign * (step * angleIncrement + rotationRad); 
            double currentHeight = step * heightIncrement;

            int cx = 0, cy = 0, cz = 0;
            switch(propertyDir.value) {
                case X -> cx = (int) currentHeight;
                case Y -> cy = (int) currentHeight;
                case Z -> cz = (int) currentHeight;
            }

			for(int dr = 0; dr < thickness; dr++) {
				int effectiveRadius = (int) Math.round(r) - dr;
				
				double xOffsetD = (effectiveRadius + offset) * Math.cos(angle);
				double yOffsetD = (effectiveRadius + offset) * Math.sin(angle);

				int xOffset = (int) Math.round(xOffsetD);
				int yOffset = (int) Math.round(yOffsetD);

				switch(propertyDir.value) {
					case X -> addShapeCube(buffer, cx, xOffset, yOffset);
					case Y -> addShapeCube(buffer, xOffset, cy, yOffset);
					case Z -> addShapeCube(buffer, xOffset, yOffset, cz);
				}
			}
        }
    }
}
