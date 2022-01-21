package brentmaas.buildguide.forge.shapes;

import java.util.ArrayList;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.StandardLevel;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import brentmaas.buildguide.forge.BuildGuideForge;
import brentmaas.buildguide.forge.Config;
import brentmaas.buildguide.forge.property.Property;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.phys.Vec3;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	private VertexBuffer buffer;
	private int nBlocks = 0;
	public boolean visible = true;
	
	public Vec3 basePos = null;
	
	public float colourShapeR = 1.0f;
	public float colourShapeG = 1.0f;
	public float colourShapeB = 1.0f;
	public float colourShapeA = 0.5f;
	
	public float colourBaseposR = 1.0f;
	public float colourBaseposG = 0.0f;
	public float colourBaseposB = 0.0f;
	public float colourBaseposA = 0.5f;
	
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
		builder.defaultColor((int) (255 * colourShapeR), (int) (255 * colourShapeG), (int) (255 * colourShapeB), (int) (255 * colourShapeA));
		this.updateShape(builder);
		builder.defaultColor((int) (255 * colourBaseposR), (int) (255 * colourBaseposG), (int) (255 * colourBaseposB), (int) (255 * colourBaseposA));
		addCube(builder, 0.4, 0.4, 0.4, 0.2); //Base position
		builder.end();
		buffer.close();
		buffer = new VertexBuffer();
		buffer.upload(builder);
		if(Config.debugGenerationTimingsEnabled) {
			BuildGuideForge.logger.atLevel(BuildGuideForge.logger.getLevel().intLevel() >= StandardLevel.DEBUG.intLevel() ? Level.DEBUG : BuildGuideForge.logger.getLevel()).log("Shape " + getTranslatedName() + " has been generated in " + (System.currentTimeMillis() - t) + " ms");
		}
	}
	
	public void render(Matrix4f model, Matrix4f projection) {
		buffer.drawWithShader(model, projection, RenderSystem.getShader());
	}
	
	protected void addCube(BufferBuilder buffer, double x, double y, double z, double s) {
		//-X
		buffer.vertex(x, y, z).endVertex();
		buffer.vertex(x, y, z+s).endVertex();
		buffer.vertex(x, y+s, z+s).endVertex();
		buffer.vertex(x, y+s, z).endVertex();
		
		//-Y
		buffer.vertex(x, y, z).endVertex();
		buffer.vertex(x+s, y, z).endVertex();
		buffer.vertex(x+s, y, z+s).endVertex();
		buffer.vertex(x, y, z+s).endVertex();
		
		//-Z
		buffer.vertex(x, y, z).endVertex();
		buffer.vertex(x, y+s, z).endVertex();
		buffer.vertex(x+s, y+s, z).endVertex();
		buffer.vertex(x+s, y, z).endVertex();
		
		//+X
		buffer.vertex(x+s, y, z).endVertex();
		buffer.vertex(x+s, y+s, z).endVertex();
		buffer.vertex(x+s, y+s, z+s).endVertex();
		buffer.vertex(x+s, y, z+s).endVertex();
		
		//+Y
		buffer.vertex(x, y+s, z).endVertex();
		buffer.vertex(x, y+s, z+s).endVertex();
		buffer.vertex(x+s, y+s, z+s).endVertex();
		buffer.vertex(x+s, y+s, z).endVertex();
		
		//+Z
		buffer.vertex(x, y, z+s).endVertex();
		buffer.vertex(x+s, y, z+s).endVertex();
		buffer.vertex(x+s, y+s, z+s).endVertex();
		buffer.vertex(x, y+s, z+s).endVertex();
		
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
		return new TranslatableComponent(getTranslationKey()).getString();
	}
	
	public int getNumberOfBlocks() {
		return nBlocks;
	}
	
	public void resetBasepos() {
		Vec3 pos = Minecraft.getInstance().player.position();
		basePos = new Vec3(Math.floor(pos.x), Math.floor(pos.y), Math.floor(pos.z));
	}
	
	public void setBasepos(int x, int y, int z) {
		basePos = new Vec3(x, y, z);
	}
	
	public void shiftBasepos(int dx, int dy, int dz) {
		basePos = new Vec3(basePos.x + dx, basePos.y + dy, basePos.z + dz);
	}
}
