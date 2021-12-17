package brentmaas.buildguide.shapes;

import com.mojang.blaze3d.vertex.BufferBuilder;

public class ShapeCatenary extends Shape{
	
	
	public ShapeCatenary() {
		super();
	}
	
	protected void updateShape(BufferBuilder builder) {
		
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.catenary";
	}
}
