package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

//TODO Odd sphere + even sphere?
public class ShapeCircle extends Shape {
	private enum direction{
		X,
		Y,
		Z
	}
	
	private String[] directionNames = {"X", "Y", "Z"};
	
	private direction dir = direction.Y;
	private int radius = 3;
	
	private Button directionButton = new Button(0, 80, 100, 20, new TranslationTextComponent("property.buildguide.direction", directionNames[dir.ordinal()]), button -> {
		dir = direction.values()[(dir.ordinal() + 1) % direction.values().length];
		update();
		button.setMessage(new TranslationTextComponent("property.buildguide.direction", directionNames[dir.ordinal()]));
	});
	private Button radiusDisplayButton = new Button(20, 100, 60, 20, new TranslationTextComponent("property.buildguide.radius", this.radius), null);
	private Button radiusDecreaseButton = new Button(0, 100, 20, 20, new StringTextComponent("-"), button -> {
		if(radius > 1) --radius;
		this.radiusDisplayButton.setMessage(new TranslationTextComponent("property.buildguide.radius", this.radius));
		update();
	});
	private Button radiusIncreaseButton = new Button(80, 100, 20, 20, new StringTextComponent("+"), button -> {
		++radius;
		this.radiusDisplayButton.setMessage(new TranslationTextComponent("property.buildguide.radius", this.radius));
		update();
	});
	
	public ShapeCircle() {
		super();
		
		update();
		
		this.buttonList.add(directionButton);
		radiusDisplayButton.active = false;
		this.buttonList.add(radiusDisplayButton);
		this.buttonList.add(radiusDecreaseButton);
		this.buttonList.add(radiusIncreaseButton);
		
		onDeselectedInGUI();
	}
	
	public void update() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		int dx = this.radius, dy = this.radius, dz = this.radius;
		switch(this.dir) {
		case X:
			dx = 0;
			break;
		case Y:
			dy = 0;
			break;
		case Z:
			dz = 0;
			break;
		}
		
		for(int x = -dx; x <= dx;++x) {
			for(int y = -dy; y <= dy;++y) {
				for(int z = -dz; z <= dz;++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
						this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z + z));
					}
				}
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.circle";
	}
}
