package brentmaas.buildguide.neoforge.screen.widget;

import java.util.List;

import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.ISelectorList;
import brentmaas.buildguide.common.screen.widget.ISelectorList.IEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class SelectorListImpl extends ObjectSelectionList<SelectorListImpl.Entry> implements ISelectorList {
	protected ISelectorListCallback callback;
	
	public SelectorListImpl(Minecraft minecraft, int left, int right, int top, int bottom, int slotHeight, List<Translatable> titles, int current, ISelectorListCallback callback) {
		super(minecraft, right - left, bottom - top, top, slotHeight);
		setX(left);
		//setRenderBackground(false);
		
		this.callback = callback;
		
		for(int i = 0;i < titles.size();++i) {
			addEntry(new Entry(i, titles.get(i)));
		}
		setSelected(children().get(current));
	}
	
	public void setYPosition(int y) {
		//Selector lists don't do y position
	}
	
	public void setVisibility(boolean visible) {
		//Selector lists don't do visibility
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
	protected int getScrollbarPosition() {
		return getRight() - 6;
	}
	
	public final class Entry extends ObjectSelectionList.Entry<SelectorListImpl.Entry> implements IEntry {
		private int index;
		private Translatable title;
		
		public Entry(int index, Translatable title) {
			this.index = index;
			this.title = title;
		}
		
		public void render(GuiGraphics guiGraphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
			guiGraphics.drawString(Minecraft.getInstance().font, title.toString(), x + 5, y + 4, 0xFFFFFF, true);
		}
		
		@Override
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			SelectorListImpl.this.callback.run(index);
			return false;
		}
		
		public Component getNarration() {
			return Component.literal("");
		}
	}
}
