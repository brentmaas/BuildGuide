package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

//TODO Odd sphere + even sphere
public class ShapeSphere extends Shape{
	private int radius = 3;
	
	private Button radiusDisplayButton = new Button(20, 80, 60, 20, new StringTextComponent("Radius: " + this.radius), null);
	private Button radiusDecreaseButton = new Button(0, 80, 20, 20, new StringTextComponent("-"), button -> {
		if(radius > 1) --radius;
		this.radiusDisplayButton.setMessage(new StringTextComponent("Radius: " + this.radius));
		update();
	});
	private Button radiusIncreaseButton = new Button(80, 80, 20, 20, new StringTextComponent("+"), button -> {
		++radius;
		this.radiusDisplayButton.setMessage(new StringTextComponent("Radius: " + this.radius));
		update();
	});
	
	public ShapeSphere() {
		super();
		
		update();
		
		radiusDisplayButton.active = false;
		this.buttonList.add(radiusDisplayButton);
		this.buttonList.add(radiusDecreaseButton);
		this.buttonList.add(radiusIncreaseButton);
		
		onDeselectedInGUI();
	}
	
	public void update() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		for(int x = -radius; x <= radius;++x) {
			for(int y = -radius; y <= radius;++y) {
				for(int z = -radius; z <= radius;++z) {
					int r2 = x * x + y * y + z * z;
					if(r2 >= (radius - 0.5) * (radius - 0.5) && r2 <= (radius + 0.5) * (radius + 0.5)) {
						this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z + z));
					}
				}
			}
		}
	}
	
	public String getName() {
		return "Sphere";
	}
}
