package brentmaas.buildguide.forge.screen.widget;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL32;

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
		
		for(int shapeSetId = 0;shapeSetId < BuildGuide.stateManager.getState().shapeSets.size();++shapeSetId) {
			addEntry(new Entry(shapeSetId));
			if(shapeSetId == BuildGuide.stateManager.getState().getShapeSetIndex()) setSelected(children().get(children().size() - 1));
		}
	}
	
	public void setYPosition(int y) {
		//Shape lists don't do y position
	}
	
	public void setVisibility(boolean visible) {
		//Shape lists don't do visibility
	}
	
	public void addEntry(int shapeSetId) {
		addEntry(new Entry(shapeSetId));
		setSelected(children().get(shapeSetId));
	}
	
	@Override
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

	@Override
	public void setSelected(@Nullable Entry entry) {
		super.setSelected(entry);
		if(entry != null) BuildGuide.stateManager.getState().setShapeSetIndex(entry.getShapeSetId());
		update.run();
	}

	@Override
	public void render(MatrixStack poseStack, int mouseX, int mouseY, float partialTicks) {
		boolean hasBlend = GL32.glIsEnabled(GL32.GL_BLEND);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		RenderSystem.color4f(0, 0, 0, 0.2f);
		bufferBuilder.begin(GL32.GL_QUADS, DefaultVertexFormats.POSITION);
		bufferBuilder.vertex(x0, y0, 0).endVertex();
		bufferBuilder.vertex(x0, y1, 0).endVertex();
		bufferBuilder.vertex(x1, y1, 0).endVertex();
		bufferBuilder.vertex(x1, y0, 0).endVertex();
		tessellator.end();
		
		if(!hasBlend) RenderSystem.disableBlend();
		
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}

	@Override
	protected void renderList(MatrixStack poseStack, int x, int y, int mouseX, int mouseY, float partialTicks) {
		boolean hasDepthTest = GL32.glIsEnabled(GL32.GL_DEPTH_TEST);
		boolean hasDepthMask = GL32.glGetBoolean(GL32.GL_DEPTH_WRITEMASK);
		int depthFunc = GL32.glGetInteger(GL32.GL_DEPTH_FUNC);
		boolean hasBlend = GL32.glIsEnabled(GL32.GL_BLEND);
		
		if(!hasDepthTest) RenderSystem.enableDepthTest();
		if(!hasDepthMask) RenderSystem.depthMask(true);
		if(depthFunc != GL32.GL_LEQUAL) RenderSystem.depthFunc(GL32.GL_LEQUAL);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuilder();
		RenderSystem.color4f(0, 0, 0, 0);
		bufferBuilder.begin(GL32.GL_QUADS, DefaultVertexFormats.POSITION);
		bufferBuilder.vertex(x0, y0 - itemHeight - 4, 0.1).endVertex();
		bufferBuilder.vertex(x0, y0, 0.1).endVertex();
		bufferBuilder.vertex(x1, y0, 0.1).endVertex();
		bufferBuilder.vertex(x1, y0 - itemHeight - 4, 0.1).endVertex();
		tessellator.end();
		
		super.renderList(poseStack, x, y, mouseX, mouseY, partialTicks);
		
		if(!hasBlend) RenderSystem.disableBlend();
		if(depthFunc != GL32.GL_LEQUAL) RenderSystem.depthFunc(depthFunc);
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
		private int shapeSetId;
		
		public Entry(int shapeSetId) {
			this.shapeSetId = shapeSetId;
		}
		
		public void render(MatrixStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			Minecraft.getInstance().font.drawShadow(poseStack, BuildGuide.screenHandler.getFormattedShapeName(BuildGuide.stateManager.getState().shapeSets.get(shapeSetId)), x + 5, y + 4, BuildGuide.screenHandler.getShapeProgressColour(BuildGuide.stateManager.getState().shapeSets.get(shapeSetId).getShape()));
		}

		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ShapeListImpl.this.setSelected(this);
			return false;
		}
		
		public void setShapeSetId(int shapeSetId) {
			this.shapeSetId = shapeSetId;
		}
		
		public int getShapeSetId() {
			return shapeSetId;
		}
		
		public TextComponent getNarration() {
			return new StringTextComponent("");
		}
	}
}
