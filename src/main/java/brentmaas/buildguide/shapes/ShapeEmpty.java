package brentmaas.buildguide.shapes;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.math.Matrix4f;

public class ShapeEmpty extends Shape{
	
	
	public ShapeEmpty() {
		
	}
	
	protected void updateShape(BufferBuilder builder) {
		
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(Matrix4f model, Matrix4f projection) {
		
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
