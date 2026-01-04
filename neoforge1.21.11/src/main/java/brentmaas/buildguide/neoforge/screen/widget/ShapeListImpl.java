package brentmaas.buildguide.neoforge.screen.widget;

import javax.annotation.Nullable;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.IShapeList.IEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;

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
	public void removeEntry(Entry entry) {
		for(Entry e: children()) {
			if(e.getShapeSetId() > entry.getShapeSetId()) {
				e.setShapeSetId(e.getShapeSetId() - 1);
			}
		}
		if(children().size() > entry.getShapeSetId() + 1) setSelected(children().get(entry.getShapeSetId() + 1));
		else if(children().size() > 1) setSelected(children().get(entry.getShapeSetId() - 1));
		super.removeEntry(entry);
	}
	
	public boolean removeEntry(IEntry entry) {
		removeEntry((Entry) entry);
		return true;
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
	public int getRowWidth() {
		return width - 12;
	}
	
	@Override
	protected int scrollBarX() {
		return getRight() - 6;
	}
	
	public final class Entry extends ObjectSelectionList.Entry<ShapeListImpl.Entry> implements IEntry {
		private int shapeSetId;
		
		public Entry(int shapeSetId) {
			this.shapeSetId = shapeSetId;
		}
		
		public void renderContent(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			int colour = BuildGuide.screenHandler.getShapeProgressColour(BuildGuide.stateManager.getState().shapeSets.get(shapeSetId).getShape());
			guiGraphics.drawString(Minecraft.getInstance().font, BuildGuide.screenHandler.getFormattedShapeName(BuildGuide.stateManager.getState().shapeSets.get(shapeSetId)), getX() + 5, getY() + (getHeight() - Minecraft.getInstance().font.lineHeight) / 2, ARGB.color((colour >> 16) & 0xFF, (colour >> 8) & 0xFF, colour & 0xFF), true);
		}

		@Override
		public boolean mouseClicked(MouseButtonEvent event, boolean b) {
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
