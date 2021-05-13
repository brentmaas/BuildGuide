package brentmaas.buildguide.shapes;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.property.Property;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	private VertexBuffer buffer;
	private int nBlocks = 0;
	
	public Shape() {
		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_COLOR);
	}
	
	protected abstract void updateShape(BufferBuilder builder);
	public abstract String getTranslationKey();
	
	public void update() {
		nBlocks = -1; //Counteract the add from the base position
		long t = System.currentTimeMillis();
		BufferBuilder builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		this.updateShape(builder);
		addCube(builder, 0.4, 0.4, 0.4, 0.2, BuildGuide.state.colourBaseposR, BuildGuide.state.colourBaseposG, BuildGuide.state.colourBaseposB, BuildGuide.state.colourBaseposA); //Base position
		builder.finishDrawing();
		buffer.close();
		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_COLOR);
		buffer.upload(builder);
		if(BuildGuide.state.debugGenerationTimingsEnabled) {
			BuildGuide.logger.debug("Shape " + getTranslatedName() + " has been generated in " + (System.currentTimeMillis() - t) + " ms");
		}
	}
	
	public void render(Matrix4f matrix) {
		//https://gist.github.com/gigaherz/87939db73d8adf4aace6ec7cf611bd2d
		this.buffer.bindBuffer();
		DefaultVertexFormats.POSITION_COLOR.setupBufferState(0);
		this.buffer.draw(matrix, GL11.GL_QUADS);
		VertexBuffer.unbindBuffer();
		DefaultVertexFormats.POSITION_COLOR.clearBufferState();
	}
	
	protected void addCube(BufferBuilder buffer, double x, double y, double z, double s, float r, float g, float b, float a) {
		//-X
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x, y+s, z).color(r, g, b, a).endVertex();
		
		//-Y
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x, y, z+s).color(r, g, b, a).endVertex();
		
		//-Z
		buffer.pos(x, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y+s, z).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y+s, z).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y, z).color(r, g, b, a).endVertex();
		
		//+X
		buffer.pos(x+s, y, z).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y+s, z).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y, z+s).color(r, g, b, a).endVertex();
		
		//+Y
		buffer.pos(x, y+s, z).color(r, g, b, a).endVertex();
		buffer.pos(x, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y+s, z).color(r, g, b, a).endVertex();
		
		//+Z
		buffer.pos(x, y, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x+s, y+s, z+s).color(r, g, b, a).endVertex();
		buffer.pos(x, y+s, z+s).color(r, g, b, a).endVertex();
		
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
		return new TranslationTextComponent(getTranslationKey()).getString();
	}
	
	public int getNumberOfBlocks() {
		return nBlocks;
	}
}
