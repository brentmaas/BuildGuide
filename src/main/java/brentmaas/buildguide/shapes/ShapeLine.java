package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

public class ShapeLine extends Shape{
	private enum direction{
		NEGATIVE_X,
		NEGATIVE_Y,
		NEGATIVE_Z,
		POSITIVE_X,
		POSITIVE_Y,
		POSITIVE_Z
	}
	
	private final String[] directionNames = {"-X", "-Y", "-Z", "+X", "+Y", "+Z"};
	
	private direction dir = direction.NEGATIVE_X;
	private int length = 5;
	
	private Button directionButton = new Button(0, 80, 100, 20, new StringTextComponent("Direction: " + directionNames[dir.ordinal()]), button -> {
		dir = direction.values()[(dir.ordinal() + 1) % direction.values().length];
		update();
		button.setMessage(new StringTextComponent("Direction: " + directionNames[dir.ordinal()]));
	});
	private Button lengthDisplayButton = new Button(20, 100, 60, 20, new StringTextComponent("Length: " + this.length), null);
	private Button lengthDecreaseButton = new Button(0, 100, 20, 20, new StringTextComponent("-"), button -> {
		if(length > 1) --length;
		this.lengthDisplayButton.setMessage(new StringTextComponent("Length: " + this.length));
		update();
	});
	private Button lengthIncreaseButton = new Button(80, 100, 20, 20, new StringTextComponent("+"), button -> {
		++length;
		this.lengthDisplayButton.setMessage(new StringTextComponent("Length: " + this.length));
		update();
	});
	
	public ShapeLine() {
		super();
		
		update();
		
		this.buttonList.add(directionButton);
		lengthDisplayButton.active = false;
		this.buttonList.add(lengthDisplayButton);
		this.buttonList.add(lengthDecreaseButton);
		this.buttonList.add(lengthIncreaseButton);
		
		onDeselectedInGUI();
	}
	
	public void update() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		int dx = 0, dy = 0, dz = 0;
		switch(this.dir) {
		case NEGATIVE_X:
			dx = -1;
			break;
		case NEGATIVE_Y:
			dy = -1;
			break;
		case NEGATIVE_Z:
			dz = -1;
			break;
		case POSITIVE_X:
			dx = 1;
			break;
		case POSITIVE_Y:
			dy = 1;
			break;
		case POSITIVE_Z:
			dz = 1;
			break;
		}
		
		for(int i = 0;i < this.length;++i) {
			posList.add(new Vector3d(State.basePos.x + dx * i, State.basePos.y + dy * i, State.basePos.z + dz * i));
		}
	}
	
	public String getName() {
		return "Line";
	}
}
