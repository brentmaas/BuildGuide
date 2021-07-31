package brentmaas.buildguide.shapes;

import java.util.ArrayList;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.Property;
import net.minecraft.network.chat.TranslatableComponent;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	private VertexBuffer buffer;
	private int nBlocks = 0;
	
	public Shape() {
		buffer = new VertexBuffer();
	}
	
	protected abstract void updateShape(BufferBuilder builder);
	public abstract String getTranslationKey();
	
	public void update() {
		nBlocks = -1; //Counteract the add from the base position
		long t = System.currentTimeMillis();
		BufferBuilder builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		this.updateShape(builder);
		addCube(builder, 0.4, 0.4, 0.4, 0.2, BuildGuide.state.colourBaseposR, BuildGuide.state.colourBaseposG, BuildGuide.state.colourBaseposB, BuildGuide.state.colourBaseposA); //Base position
		builder.end();
		buffer.close();
		buffer = new VertexBuffer();
		buffer.upload(builder);
		if(BuildGuide.state.debugGenerationTimingsEnabled) {
			BuildGuide.logger.debug("Shape " + getTranslatedName() + " has been generated in " + (System.currentTimeMillis() - t) + " ms");
		}
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		buffer.drawWithShader(model, projection, RenderSystem.getShader());
	}
	
	protected void addCube(BufferBuilder buffer, double x, double y, double z, double s, float r, float g, float b, float a) {
		//-X
		buffer.vertex(x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x, y, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x, y+s, z).color(r, g, b, a).endVertex();
		
		//-Y
		buffer.vertex(x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x, y, z+s).color(r, g, b, a).endVertex();
		
		//-Z
		buffer.vertex(x, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x, y+s, z).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y+s, z).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y, z).color(r, g, b, a).endVertex();
		
		//+X
		buffer.vertex(x+s, y, z).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y+s, z).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y, z+s).color(r, g, b, a).endVertex();
		
		//+Y
		buffer.vertex(x, y+s, z).color(r, g, b, a).endVertex();
		buffer.vertex(x, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y+s, z).color(r, g, b, a).endVertex();
		
		//+Z
		buffer.vertex(x, y, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x+s, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.vertex(x, y+s, z+s).color(r, g, b, a).endVertex();
		
		nBlocks++;
	}
	
	public void onSelectedInGUI() {
		for(Property<?> p: properties) {
			p.onSelectedInGUI();
		}
	}
	
	public void onDeselectedInGUI() {
		for(Property<?> p: properties) {
			p.onDeselectedInGUI();
		}
	}
	
	public String getTranslatedName() {
		return new TranslatableComponent(getTranslationKey()).getString();
	}
	
	public int getNumberOfBlocks() {
		return nBlocks;
	}
}
