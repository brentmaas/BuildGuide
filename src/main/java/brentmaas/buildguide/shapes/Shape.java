package brentmaas.buildguide.shapes;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.Config;
import brentmaas.buildguide.StateManager;
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
		builder.setDefaultColor((int) (255 * StateManager.getState().colourShapeR), (int) (255 * StateManager.getState().colourShapeG), (int) (255 * StateManager.getState().colourShapeB), (int) (255 * StateManager.getState().colourShapeA));
		this.updateShape(builder);
		builder.setDefaultColor((int) (255 * StateManager.getState().colourBaseposR), (int) (255 * StateManager.getState().colourBaseposG), (int) (255 * StateManager.getState().colourBaseposB), (int) (255 * StateManager.getState().colourBaseposA));
		addCube(builder, 0.4, 0.4, 0.4, 0.2); //Base position
		builder.finishDrawing();
		buffer.close();
		buffer = new VertexBuffer(DefaultVertexFormats.POSITION_COLOR);
		buffer.upload(builder);
		if(Config.debugGenerationTimingsEnabled) {
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
	
	protected void addCube(BufferBuilder buffer, double x, double y, double z, double s) {
		//-X
		buffer.pos(x, y, z).endVertex();
		buffer.pos(x, y, z+s).endVertex();
		buffer.pos(x, y+s, z+s).endVertex();
		buffer.pos(x, y+s, z).endVertex();
		
		//-Y
		buffer.pos(x, y, z).endVertex();
		buffer.pos(x+s, y, z).endVertex();
		buffer.pos(x+s, y, z+s).endVertex();
		buffer.pos(x, y, z+s).endVertex();
		
		//-Z
		buffer.pos(x, y, z).endVertex();
		buffer.pos(x, y+s, z).endVertex();
		buffer.pos(x+s, y+s, z).endVertex();
		buffer.pos(x+s, y, z).endVertex();
		
		//+X
		buffer.pos(x+s, y, z).endVertex();
		buffer.pos(x+s, y+s, z).endVertex();
		buffer.pos(x+s, y+s, z+s).endVertex();
		buffer.pos(x+s, y, z+s).endVertex();
		
		//+Y
		buffer.pos(x, y+s, z).endVertex();
		buffer.pos(x, y+s, z+s).endVertex();
		buffer.pos(x+s, y+s, z+s).endVertex();
		buffer.pos(x+s, y+s, z).endVertex();
		
		//+Z
		buffer.pos(x, y, z+s).endVertex();
		buffer.pos(x+s, y, z+s).endVertex();
		buffer.pos(x+s, y+s, z+s).endVertex();
		buffer.pos(x, y+s, z+s).endVertex();
		
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
