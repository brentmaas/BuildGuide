package brentmaas.buildguide.shapes;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.vector.Matrix4f;

public class ShapeEmpty extends Shape{
	
	
	public ShapeEmpty() {
		
	}
	
	protected void updateShape(BufferBuilder builder) {
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Matrix4f matrix) {
		
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
