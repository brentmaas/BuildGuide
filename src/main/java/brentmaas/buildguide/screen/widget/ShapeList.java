package brentmaas.buildguide.screen.widget;

import com.mojang.blaze3d.matrix.MatrixStack;

import brentmaas.buildguide.StateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShapeList extends ExtendedList<ShapeList.Entry>{
	
	
	public ShapeList(Minecraft mcIn, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
		super(mcIn, widthIn, heightIn, topIn, bottomIn, slotHeightIn);
		this.func_244605_b(false); //Disable background part 1
		this.func_244606_c(false); //Disable background part 2
		
		for(int shapeId = 0;shapeId < StateManager.getState().advancedModeShapes.size();++shapeId) {
			this.addEntry(new Entry(shapeId));
			if(shapeId == StateManager.getState().iAdvanced) this.setSelected(this.getEventListeners().get(this.getEventListeners().size() - 1));
		}
	}
	
	public void addEntryExternal(int shapeId) {
		this.addEntry(new Entry(shapeId));
		if(this.getEventListeners().size() == 1) this.setSelected(this.getEventListeners().get(0));
	}
	
	public boolean removeEntry(Entry entry) {
		for(Entry e: this.getEventListeners()) {
			if(e.getShapeId() > entry.getShapeId()) {
				e.setShapeId(e.getShapeId() - 1);
			}
		}
		if(this.getEventListeners().size() > 0 && entry.getShapeId() != 0) this.setSelected(this.getEventListeners().get(0));
		else if(this.getEventListeners().size() > 1) this.setSelected(this.getEventListeners().get(1));
		return super.removeEntry(entry);
	}
	
	@OnlyIn(Dist.CLIENT)
	public final class Entry extends ExtendedList.AbstractListEntry<ShapeList.Entry> {
		private int shapeId;
		
		public Entry(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public void render(MatrixStack matrixStack, int p_230432_2_, int topIn, int leftIn, int p_230432_5_, int p_230432_6_, int p_230432_7_, int p_230432_8_, boolean p_230432_9_, float p_230432_10_) {
			//Found strikethrough code at https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1437428-guide-1-7-2-how-to-make-button-tooltips?comment=3
			Minecraft.getInstance().fontRenderer.drawStringWithShadow(matrixStack, (StateManager.getState().advancedModeShapes.get(shapeId).visible ? "" : "\247m") + StateManager.getState().advancedModeShapes.get(shapeId).getTranslatedName(), leftIn + 35, topIn + 1, 0xFFFFFF);
		}
		
		public boolean mouseClicked(double mouseX, double mouseY, int button) {
			ShapeList.this.setSelected(this);
			StateManager.getState().iAdvanced = this.shapeId;
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
