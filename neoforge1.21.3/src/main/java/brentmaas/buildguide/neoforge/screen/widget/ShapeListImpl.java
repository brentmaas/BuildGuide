package brentmaas.buildguide.neoforge.screen.widget;

import javax.annotation.Nullable;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.IShapeList.IEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class ShapeListImpl extends ObjectSelectionList<ShapeListImpl.Entry> implements IShapeList {
	private Runnable update;
	
	public ShapeListImpl(Minecraft minecraft, int left, int right, int top, int bottom, int slotHeight, Runnable update) {
		super(minecraft, right - left, bottom - top, top, slotHeight);
		setX(left);
		//setRenderBackground(false);
		
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
	public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		guiGraphics.fill(getX(), getY(), getRight(), getBottom(), (int) 0x33000000);
		super.renderWidget(guiGraphics, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void renderSelection(GuiGraphics guiGraphics, int top, int width, int height, int colourOuter, int colourInner) {
		int left = getX() + (this.width - width) / 2;
		int right = getX() + (this.width + width) / 2;
		boundedFill(guiGraphics, left, top - 2, right, top + height + 2, getX(), getY(), getRight(), getBottom(), colourOuter);
		boundedFill(guiGraphics, left + 1, top - 1, right - 1, top + height + 1, getX(), getY(), getRight(), getBottom(), colourInner);
	}
	
	private void boundedFill(GuiGraphics guiGraphics, int left, int top, int right, int bottom, int boundLeft, int boundTop, int boundRight, int boundBottom, int colour) {
		if(left < boundRight && top < boundBottom && right > boundLeft && bottom > boundTop) {
			guiGraphics.fill(Math.max(left, boundLeft), Math.max(top, boundTop), Math.min(right, boundRight), Math.min(bottom, boundBottom), colour);
		}
	}
	
	@Override
	public int getRowWidth() {
		return width - 12;
	}
	
	@Override
	protected int getScrollbarPosition() {
		return getRight() - 6;
	}
	
	public final class Entry extends ObjectSelectionList.Entry<ShapeListImpl.Entry> implements IEntry {
		private int shapeSetId;
		
		public Entry(int shapeSetId) {
			this.shapeSetId = shapeSetId;
		}
		
		public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			guiGraphics.drawString(Minecraft.getInstance().font, BuildGuide.screenHandler.getFormattedShapeName(BuildGuide.stateManager.getState().shapeSets.get(shapeSetId)), x + 5, y + 4, BuildGuide.screenHandler.getShapeProgressColour(BuildGuide.stateManager.getState().shapeSets.get(shapeSetId).getShape()), true);
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
		
		public Component getNarration() {
			return Component.literal("");
		}
	}
}
