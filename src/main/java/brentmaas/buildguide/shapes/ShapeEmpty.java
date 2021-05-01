package brentmaas.buildguide.shapes;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;

public class ShapeEmpty extends Shape{
	
	
	public ShapeEmpty() {
		super();
	}
	
	protected void updateShape() {
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(BufferBuilder buffer, Tessellator tessellator) {
		
	}
	
	@Override
	public void onSelectedInGUI() {
		
	}
	
	@Override
	public void onDeselectedInGUI() {
		
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.none";
	}
	
}
