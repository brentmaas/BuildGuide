package brentmaas.buildguide.shapes;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.StandardLevel;

import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.BuildGuide;
import brentmaas.buildguide.Config;
import brentmaas.buildguide.property.Property;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	private VertexBuffer buffer;
	private int nBlocks = 0;
	public boolean visible = true;
	
	public Vec3d basePos = null;
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
	public Shape() {
		
	}
	
	protected abstract void updateShape(BufferBuilder builder);
	public abstract String getTranslationKey();
	
	public void update() {
		nBlocks = -1; //Counteract the add from the base position
		long t = System.currentTimeMillis();
		BufferBuilder builder = new BufferBuilder(4); //4 is lowest working. Number of blocks isn't always known, so it'll have to grow on its own
		builder.begin(DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		builder.fixedColor((int) (255 * colourShapeR), (int) (255 * colourShapeG), (int) (255 * colourShapeB), (int) (255 * colourShapeA));
		this.updateShape(builder);
		builder.fixedColor((int) (255 * colourBaseposR), (int) (255 * colourBaseposG), (int) (255 * colourBaseposB), (int) (255 * colourBaseposA));
		addCube(builder, 0.4, 0.4, 0.4, 0.2); //Base position
		builder.end();
		if(buffer != null) buffer.close();
		buffer = new VertexBuffer();
		buffer.upload(builder);
		if(Config.debugGenerationTimingsEnabled) {
			BuildGuide.logger.atLevel(BuildGuide.logger.getLevel().intLevel() >= StandardLevel.DEBUG.intLevel() ? Level.DEBUG : BuildGuide.logger.getLevel()).log("Shape " + getTranslatedName() + " has been generated in " + (System.currentTimeMillis() - t) + " ms");
		}
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		this.buffer.setShader(model, projection, RenderSystem.getShader());
	}
	
	protected void addCube(BufferBuilder buffer, double x, double y, double z, double s) {
		//-X
		buffer.vertex(x, y, z).next();
		buffer.vertex(x, y, z+s).next();
		buffer.vertex(x, y+s, z+s).next();
		buffer.vertex(x, y+s, z).next();
		
		//-Y
		buffer.vertex(x, y, z).next();
		buffer.vertex(x+s, y, z).next();
		buffer.vertex(x+s, y, z+s).next();
		buffer.vertex(x, y, z+s).next();
		
		//-Z
		buffer.vertex(x, y, z).next();
		buffer.vertex(x, y+s, z).next();
		buffer.vertex(x+s, y+s, z).next();
		buffer.vertex(x+s, y, z).next();
		
		//+X
		buffer.vertex(x+s, y, z).next();
		buffer.vertex(x+s, y+s, z).next();
		buffer.vertex(x+s, y+s, z+s).next();
		buffer.vertex(x+s, y, z+s).next();
		
		//+Y
		buffer.vertex(x, y+s, z).next();
		buffer.vertex(x, y+s, z+s).next();
		buffer.vertex(x+s, y+s, z+s).next();
		buffer.vertex(x+s, y+s, z).next();
		
		//+Z
		buffer.vertex(x, y, z+s).next();
		buffer.vertex(x+s, y, z+s).next();
		buffer.vertex(x+s, y+s, z+s).next();
		buffer.vertex(x, y+s, z+s).next();
		
		nBlocks++;
	}
	
	protected void addShapeCube(BufferBuilder buffer, int x, int y, int z) {
		addCube(buffer, x + 0.2, y + 0.2, z + 0.2, 0.6);
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
		return new TranslatableText(getTranslationKey()).getString();
	}
	
	public int getNumberOfBlocks() {
		return nBlocks;
	}
	
	public void resetBasepos() {
		Vec3d pos = MinecraftClient.getInstance().player.getPos();
		basePos = new Vec3d(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
	}
	
	public void setBasepos(int x, int y, int z) {
		basePos = new Vec3d(x, y, z);
	}
	
	public void shiftBasepos(int dx, int dy, int dz) {
		basePos = new Vec3d(basePos.x + dx, basePos.y + dy, basePos.z + dz);
	}
}
