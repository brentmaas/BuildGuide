package brentmaas.buildguide.forge.screen.widget;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.IShapeList.IEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class ShapeListImpl extends ExtendedList<ShapeListImpl.Entry> implements IShapeList {
	private Runnable update;
	
	public ShapeListImpl(Minecraft minecraft, int left, int right, int top, int bottom, int slotHeight, Runnable update) {
		super(minecraft, right - left, bottom - top, top, bottom, slotHeight);
		x0 = left;
		x1 = right;
		setRenderBackground(false);
		setRenderTopAndBottom(false);
		
		this.update = update;
		
		for(int shapeId = 0;shapeId < BuildGuide.stateManager.getState().shapeSets.size();++shapeId) {
			addEntry(new Entry(shapeId));
			if(shapeId == BuildGuide.stateManager.getState().iShapeSet) setSelected(children().get(children().size() - 1));
		}
	}
	
	public void addEntry(int shapeId) {
		addEntry(new Entry(shapeId));
		setSelected(children().get(shapeId));
	}
	
	public boolean removeEntry(Entry entry) {
		for(Entry e: children()) {
			if(e.getShapeSetId() > entry.getShapeSetId()) {
				e.setShapeSetId(e.getShapeSetId() - 1);
			}
		}
		if(children().size() > entry.getShapeSetId() + 1) setSelected(children().get(entry.getShapeSetId() + 1));
		else if(children().size() > 1) setSelected(children().get(entry.getShapeSetId() - 1));
		return super.removeEntry(entry);
	}
	
	public boolean removeEntry(IEntry entry) {
		return removeEntry((Entry) entry);
	}
	
	public void setSelected(@Nullable Entry entry) {
		super.setSelected(entry);
		if(entry != null) BuildGuide.stateManager.getState().iShapeSet = entry.getShapeSetId();
		update.run();
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		boolean hasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		RenderSystem.color4f(0, 0, 0, 0.2f);
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		bufferBuilder.vertex(x0, y0, 0).endVertex();
		bufferBuilder.vertex(x0, y1, 0).endVertex();
		bufferBuilder.vertex(x1, y1, 0).endVertex();
		bufferBuilder.vertex(x1, y0, 0).endVertex();
		tessellator.end();
		
		if(!hasBlend) RenderSystem.disableBlend();
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	protected void renderList(MatrixStack matrixStack, int x, int y, int mouseX, int mouseY, float partialTicks) {
		boolean hasDepthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
		boolean hasDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
		int depthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
		boolean hasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		
		if(!hasDepthTest) RenderSystem.enableDepthTest();
		if(!hasDepthMask) RenderSystem.depthMask(true);
		if(depthFunc != GL11.GL_LEQUAL) RenderSystem.depthFunc(GL11.GL_LEQUAL);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		RenderSystem.color4f(0, 0, 0, 0);
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		bufferBuilder.vertex(x0, y0 - itemHeight - 4, 0.1).endVertex();
		bufferBuilder.vertex(x0, y0, 0.1).endVertex();
		bufferBuilder.vertex(x1, y0, 0.1).endVertex();
		bufferBuilder.vertex(x1, y0 - itemHeight - 4, 0.1).endVertex();
		tessellator.end();
		
		super.renderList(matrixStack, x, y, mouseX, mouseY, partialTicks);
		
		if(!hasBlend) RenderSystem.disableBlend();
		if(depthFunc != GL11.GL_LEQUAL) RenderSystem.depthFunc(depthFunc);
		if(!hasDepthMask) RenderSystem.depthMask(false);
		if(!hasDepthTest) RenderSystem.disableDepthTest();
	}

	@Override
	public int getRowWidth() {
		return width - 12;
	}

	@Override
	protected int getScrollbarPosition() {
		return x1 - 6;
	}
		
	public final class Entry extends ExtendedList.AbstractListEntry<ShapeListImpl.Entry> implements IEntry {
		private int shapeId;
		
		public Entry(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public void render(MatrixStack matrixStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			Minecraft.getInstance().font.drawShadow(matrixStack, (BuildGuide.stateManager.getState().shapeSets.get(shapeId).visible ? "" : BuildGuide.screenHandler.TEXT_MODIFIER_STRIKETHROUGH) + BuildGuide.stateManager.getState().shapeSets.get(shapeId).getShape().getTranslatedName(), x + 5, y + 4, 0xFFFFFF);
		}
		
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ShapeListImpl.this.setSelected(this);
			return false;
		}
		
		public void setShapeSetId(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public int getShapeSetId() {
			return shapeId;
		}
		
		public TextComponent getNarration() {
			return new StringTextComponent("");
		}
	}
}
