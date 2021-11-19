package brentmaas.buildguide.screen.widget;

import javax.annotation.Nullable;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShapeList extends ExtendedList<ShapeList.Entry>{
	private Runnable update;
	
	public ShapeList(Minecraft minecraft, int width, int height, int top, int bottom, int slotHeight, Runnable updateOnSelected) {
		super(minecraft, width, height, top, bottom, slotHeight);
		func_244605_b(false); //Disable background part 1
		func_244606_c(false); //Disable background part 2
		
		update = updateOnSelected;
		
		for(int shapeId = 0;shapeId < StateManager.getState().advancedModeShapes.size();++shapeId) {
			addEntry(new Entry(shapeId));
			if(shapeId == StateManager.getState().iAdvanced) setSelected(getEventListeners().get(getEventListeners().size() - 1));
		}
	}
	
	public void addEntryExternal(int shapeId) {
		addEntry(new Entry(shapeId));
		if(getEventListeners().size() == 1) setSelected(getEventListeners().get(0));
	}
	
	public boolean removeEntry(Entry entry) {
		for(Entry e: getEventListeners()) {
			if(e.getShapeId() > entry.getShapeId()) {
				e.setShapeId(e.getShapeId() - 1);
			}
		}
		if(getEventListeners().size() > 0 && entry.getShapeId() != 0) setSelected(getEventListeners().get(0));
		else if(getEventListeners().size() > 1) setSelected(getEventListeners().get(1));
		return super.removeEntry(entry);
	}
	
	public void setSelected(@Nullable Entry entry) {
		super.setSelected(entry);
		if(entry != null) StateManager.getState().iAdvanced = entry.getShapeId();
		update.run();
	}
	
	@OnlyIn(Dist.CLIENT)
	public final class Entry extends ExtendedList.AbstractListEntry<ShapeList.Entry> {
		private int shapeId;
		
		public Entry(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public void render(MatrixStack matrixStack, int p_230432_2_, int top, int left, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
			//Found strikethrough code at https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1437428-guide-1-7-2-how-to-make-button-tooltips?comment=3
			Minecraft.getInstance().fontRenderer.drawStringWithShadow(matrixStack, (StateManager.getState().advancedModeShapes.get(shapeId).visible ? "" : "\247m") + StateManager.getState().advancedModeShapes.get(shapeId).getTranslatedName(), left + 35, top + 5, 0xFFFFFF);
		}
		
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ShapeList.this.setSelected(this);
			return false;
		}
		
		public void setShapeId(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public int getShapeId() {
			return shapeId;
		}
	}
}
