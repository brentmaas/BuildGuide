package brentmaas.buildguide.shapes;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import brentmaas.buildguide.State;
import brentmaas.buildguide.property.Property;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class Shape {
	protected ArrayList<Vector3d> posList = new ArrayList<Vector3d>();
	//TODO: Property<T> for shapes and basepos and stuff
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	
	public Shape() {
		
	}
	
	public abstract void update();
	public abstract String getTranslationKey();
	
	public void render(BufferBuilder buffer, Tessellator tessellator) {
		//Base position
		renderCube(buffer, tessellator, State.basePos.x + 0.4, State.basePos.y + 0.4, State.basePos.z + 0.4, 0.2, State.colourBaseposR, State.colourBaseposG, State.colourBaseposB, State.colourBaseposA);
		
		//Shape
		for(Vector3d p: this.posList) {
			renderCube(buffer, tessellator, p.x + 0.2, p.y + 0.2, p.z + 0.2, 0.6, State.colourShapeR, State.colourShapeG, State.colourShapeB, State.colourShapeA);
		}
	}
	
	private void renderCube(BufferBuilder buffer, Tessellator tessellator, double x, double y, double z, double s, float r, float g, float b, float a) {
		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
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
		
		tessellator.draw();
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
		return posList.size();
	}
}
