package brentmaas.buildguide.fabric.screen.widget;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.IShapeList.IEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class ShapeListImpl extends AlwaysSelectedEntryListWidget<ShapeListImpl.Entry> implements IShapeList {
	private Runnable update;
	
	public ShapeListImpl(MinecraftClient client, int left, int right, int top, int bottom, int slotHeight, Runnable update) {
		super(client, right - left, bottom - top, top, bottom, slotHeight);
		this.left = left;
		this.right = right;
		method_31322(false); //Disable background part 1
		method_31323(false); //Disable background part 2
		
		this.update = update;
		
		for(int shapeId = 0;shapeId < BuildGuide.stateManager.getState().advancedModeShapes.size();++shapeId) {
			addEntry(new Entry(shapeId));
			if(shapeId == BuildGuide.stateManager.getState().iAdvanced) setSelected(children().get(children().size() - 1));
		}
	}
	
	public void addEntry(int shapeId) {
		addEntry(new Entry(shapeId));
		setSelected(children().get(shapeId));
	}
	
	public boolean removeEntry(Entry entry) {
		for(Entry e: children()) {
			if(e.getShapeId() > entry.getShapeId()) {
				e.setShapeId(e.getShapeId() - 1);
			}
		}
		if(children().size() > entry.getShapeId() + 1) setSelected(children().get(entry.getShapeId() + 1));
		else if(children().size() > 1) setSelected(children().get(entry.getShapeId() - 1));
		return super.removeEntry(entry);
	}
	
	public boolean removeEntry(IEntry entry) {
		return removeEntry((Entry) entry);
	}
	
	public void setSelected(@Nullable Entry entry) {
		super.setSelected(entry);
		if(entry != null) BuildGuide.stateManager.getState().iAdvanced = entry.getShapeId();
		update.run();
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		boolean hasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.color4f(0, 0, 0, 0.2f);
		bufferBuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION);
		bufferBuilder.vertex(left, top, 0).next();
		bufferBuilder.vertex(left, bottom, 0).next();
		bufferBuilder.vertex(right, bottom, 0).next();
		bufferBuilder.vertex(right, top, 0).next();
		tessellator.draw();
		
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
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.color4f(0, 0, 0, 0);
		bufferBuilder.begin(GL11.GL_QUADS, VertexFormats.POSITION);
		bufferBuilder.vertex(left, top - itemHeight - 4, 0.1).next();
		bufferBuilder.vertex(left, top, 0.1).next();
		bufferBuilder.vertex(right, top, 0.1).next();
		bufferBuilder.vertex(right, top - itemHeight - 4, 0.1).next();
		tessellator.draw();
		
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
	protected int getScrollbarPositionX() {
		return right - 6;
	}
	
	public final class Entry extends AlwaysSelectedEntryListWidget.Entry<ShapeListImpl.Entry> implements IEntry {
		private int shapeId;
		
		public Entry(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public void render(MatrixStack stack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			MinecraftClient.getInstance().textRenderer.drawWithShadow(stack, (BuildGuide.stateManager.getState().advancedModeShapes.get(shapeId).visible ? "" : "\247m") + BuildGuide.stateManager.getState().advancedModeShapes.get(shapeId).getTranslatedName(), x + 5, y + 4, 0xFFFFFF);
		}
		
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ShapeListImpl.this.setSelected(this);
			return false;
		}
		
		public void setShapeId(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public int getShapeId() {
			return shapeId;
		}
		
		public Text getNarration() {
			return new LiteralText("");
		}
	}
}
