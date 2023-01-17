package brentmaas.buildguide.forge.screen.widget;

import javax.annotation.Nullable;

import com.mojang.blaze3d.vertex.PoseStack;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.IShapeList.IEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class ShapeListImpl extends ObjectSelectionList<ShapeListImpl.Entry> implements IShapeList {
	private Runnable update;
	
	public ShapeListImpl(Minecraft minecraft, int left, int right, int top, int bottom, int slotHeight, Runnable update) {
		super(minecraft, right - left, bottom - top, top, bottom, slotHeight);
		x0 = left;
		x1 = right;
		setRenderBackground(false);
		setRenderTopAndBottom(false);
		
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
	
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
		fill(poseStack, x0, y0, x1, y1, (int) 0x33000000);
		super.render(poseStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void renderSelection(PoseStack poseStack, int top, int width, int height, int colourOuter, int colourInner) {
		int left = x0 + (this.width - width) / 2;
		int right = x0 + (this.width + width) / 2;
		boundedFill(poseStack, left, top - 2, right, top + height + 2, x0, y0, x1, y1, colourOuter);
		boundedFill(poseStack, left + 1, top - 1, right - 1, top + height + 1, x0, y0, x1, y1, colourInner);
	}
	
	private void boundedFill(PoseStack poseStack, int left, int top, int right, int bottom, int boundLeft, int boundTop, int boundRight, int boundBottom, int colour) {
		if(left < boundRight && top < boundBottom && right > boundLeft && bottom > boundTop) {
			fill(poseStack, Math.max(left, boundLeft), Math.max(top, boundTop), Math.min(right, boundRight), Math.min(bottom, boundBottom), colour);
		}
	}
	
	@Override
	public int getRowWidth() {
		return width - 12;
	}
	
	@Override
	protected int getScrollbarPosition() {
		return x1 - 6;
	}
	
	public final class Entry extends ObjectSelectionList.Entry<ShapeListImpl.Entry> implements IEntry {
		private int shapeId;
		
		public Entry(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public void render(PoseStack poseStack, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			Minecraft.getInstance().font.drawShadow(poseStack, (BuildGuide.stateManager.getState().advancedModeShapes.get(shapeId).visible ? "" : "\247m") + BuildGuide.stateManager.getState().advancedModeShapes.get(shapeId).getTranslatedName(), x + 5, y + 4, 0xFFFFFF);
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
		
		public Component getNarration() {
			return Component.literal("");
		}
	}
}
