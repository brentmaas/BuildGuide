package brentmaas.buildguide.shapes;

import brentmaas.buildguide.State;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;

public class ShapeCuboid extends Shape{
	private int dx = 3, dy = 3, dz = 3;
	
	private Button sizeXDisplayButton = new Button(20, 80, 60, 20, new StringTextComponent("X: " + dx), null);
	private Button sizeXDecreaseButton = new Button(0, 80, 20, 20, new StringTextComponent("-"), button -> shiftSize(-1, 0, 0));
	private Button sizeXIncreaseButton = new Button(80, 80, 20, 20, new StringTextComponent("+"), button -> shiftSize(1, 0, 0));
	private Button sizeYDisplayButton = new Button(20, 100, 60, 20, new StringTextComponent("Y: " + dy), null);
	private Button sizeYDecreaseButton = new Button(0, 100, 20, 20, new StringTextComponent("-"), button -> shiftSize(0, -1, 0));
	private Button sizeYIncreaseButton = new Button(80, 100, 20, 20, new StringTextComponent("+"), button -> shiftSize(0, 1, 0));
	private Button sizeZDisplayButton = new Button(20, 120, 60, 20, new StringTextComponent("Z: " + dz), null);
	private Button sizeZDecreaseButton = new Button(0, 120, 20, 20, new StringTextComponent("-"), button -> shiftSize(0, 0, -1));
	private Button sizeZIncreaseButton = new Button(80, 120, 20, 20, new StringTextComponent("+"), button -> shiftSize(0, 0, 1));
	
	public ShapeCuboid() {
		super();
		
		update();
		
		sizeXDisplayButton.active = false;
		this.buttonList.add(sizeXDisplayButton);
		this.buttonList.add(sizeXDecreaseButton);
		this.buttonList.add(sizeXIncreaseButton);
		sizeYDisplayButton.active = false;
		this.buttonList.add(sizeYDisplayButton);
		this.buttonList.add(sizeYDecreaseButton);
		this.buttonList.add(sizeYIncreaseButton);
		sizeZDisplayButton.active = false;
		this.buttonList.add(sizeZDisplayButton);
		this.buttonList.add(sizeZDecreaseButton);
		this.buttonList.add(sizeZIncreaseButton);
		
		onDeselectedInGUI();
	}
	
	public void update() {
		if(State.basePos == null) return;
		
		this.posList.clear();
		
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int z = (dz > 0 ? 0 : dz + 1);z < (dz > 0 ? dz : 1);++z) {
				this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y, State.basePos.z + z));
				if(!(dy == 1 || dy == -1)) {
					this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + (dy > 0 ? dy - 1 : dy + 1), State.basePos.z + z));
				}
			}
		}
		for(int x = (dx > 0 ? 0 : dx + 1);x < (dx > 0 ? dx : 1);++x) {
			for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
				this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z));
				if(!(dz == 1 || dz == -1)) {
					this.posList.add(new Vector3d(State.basePos.x + x, State.basePos.y + y, State.basePos.z + (dz > 0 ? dz - 1 : dz + 1)));
				}
			}
		}
		for(int y = (dy > 0 ? 1 : dy + 2);y < (dy > 0 ? dy - 1 : 0);++y) {
			for(int z = (dz > 0 ? 1 : dz + 2);z < (dz > 0 ? dz - 1 : 0);++z) {
				this.posList.add(new Vector3d(State.basePos.x, State.basePos.y + y, State.basePos.z + z));
				if(!(dx == 1 || dx == -1)) {
					this.posList.add(new Vector3d(State.basePos.x + (dx > 0 ? dx - 1 : dx + 1), State.basePos.y + y, State.basePos.z + z));
				}
			}
		}
	}
	
	private void shiftSize(int dx, int dy, int dz) {
		this.dx += dx;
		this.dy += dy;
		this.dz += dz;
		if(this.dx == 0 || this.dy == 0 || this.dz == 0) { //Assumes only one direction is ever changed at a time by one
			this.dx += dx;
			this.dy += dy;
			this.dz += dz;
		}
		this.sizeXDisplayButton.setMessage(new StringTextComponent("X: " + this.dx));
		this.sizeYDisplayButton.setMessage(new StringTextComponent("Y: " + this.dy));
		this.sizeZDisplayButton.setMessage(new StringTextComponent("Z: " + this.dz));
		update();
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.cuboid";
	}
}
