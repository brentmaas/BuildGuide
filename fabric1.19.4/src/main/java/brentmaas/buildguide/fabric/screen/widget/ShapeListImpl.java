package brentmaas.buildguide.fabric.screen.widget;

import org.jetbrains.annotations.Nullable;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IShapeList;
import brentmaas.buildguide.common.screen.widget.IShapeList.IEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class ShapeListImpl extends AlwaysSelectedEntryListWidget<ShapeListImpl.Entry> implements IShapeList {
	private Runnable update;
	
	public ShapeListImpl(MinecraftClient client, int left, int right, int top, int bottom, int slotHeight, Runnable update) {
		super(client, right - left, bottom - top, top, bottom, slotHeight);
		this.left = left;
		this.right = right;
		setRenderBackground(false);
		setRenderHorizontalShadows(false);
		
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
	
	public IEntry getSelected() {
		return getSelectedOrNull();
	}
	
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		fill(matrixStack, left, top, right, bottom, (int) 0x33000000);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	protected void drawSelectionHighlight(MatrixStack matrixStack, int top, int width, int height, int colourOuter, int colourInner) {
		int left = this.left + (this.width - width) / 2;
		int right = this.left + (this.width + width) / 2;
		boundedFill(matrixStack, left, top - 2, right, top + height + 2, this.left, this.top, this.right, this.bottom, colourOuter);
		boundedFill(matrixStack, left + 1, top - 1, right - 1, top + height + 1, this.left, this.top, this.right, this.bottom, colourInner);
	}
	
	private void boundedFill(MatrixStack poseStack, int left, int top, int right, int bottom, int boundLeft, int boundTop, int boundRight, int boundBottom, int colour) {
		if(left < boundRight && top < boundBottom && right > boundLeft && bottom > boundTop) {
			fill(poseStack, Math.max(left, boundLeft), Math.max(top, boundTop), Math.min(right, boundRight), Math.min(bottom, boundBottom), colour);
		}
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
			return Text.literal("");
		}
	}
}
