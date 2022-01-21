package brentmaas.buildguide.forge.screen.widget;

import javax.annotation.Nullable;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import brentmaas.buildguide.forge.StateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ShapeList extends ObjectSelectionList<ShapeList.Entry>{
	private Runnable update;
	
	public ShapeList(Minecraft minecraft, int left, int right, int top, int bottom, int slotHeight, Runnable updateOnSelected) {
		super(minecraft, right - left, bottom - top, top, bottom, slotHeight);
		x0 = left;
		x1 = right;
		setRenderBackground(false);
		setRenderTopAndBottom(false);
		
		update = updateOnSelected;
		
		for(int shapeId = 0;shapeId < StateManager.getState().advancedModeShapes.size();++shapeId) {
			addEntry(new Entry(shapeId));
			if(shapeId == StateManager.getState().iAdvanced) setSelected(children().get(children().size() - 1));
		}
	}
	
	public void addEntryExternal(int shapeId) {
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
	
	public void setSelected(@Nullable Entry entry) {
		super.setSelected(entry);
		if(entry != null) StateManager.getState().iAdvanced = entry.getShapeId();
		update.run();
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		boolean hasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0, 0, 0, 0.2f);
		bufferBuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferBuilder.vertex(x0, y0, 0).endVertex();
		bufferBuilder.vertex(x0, y1, 0).endVertex();
		bufferBuilder.vertex(x1, y1, 0).endVertex();
		bufferBuilder.vertex(x1, y0, 0).endVertex();
		tesselator.end();
		
		if(!hasBlend) RenderSystem.disableBlend();
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	//Incredibly ugly hack to fix entries clipping out of the list
	@Override
	protected void renderList(PoseStack matrixStack, int p_93453_, int p_93454_, int p_93455_, int p_93456_, float p_93457_) {
		boolean hasDepthTest = GL11.glIsEnabled(GL11.GL_DEPTH_TEST);
		boolean hasDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
		int depthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
		boolean hasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		
		if(!hasDepthTest) RenderSystem.enableDepthTest();
		if(!hasDepthMask) RenderSystem.depthMask(true);
		if(depthFunc != GL11.GL_LEQUAL) RenderSystem.depthFunc(GL11.GL_LEQUAL);
		if(!hasBlend) RenderSystem.enableBlend();
		
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();
		RenderSystem.setShader(GameRenderer::getPositionShader);
		RenderSystem.setShaderColor(0, 0, 0, 0);
		bufferBuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferBuilder.vertex(x0, y0 - itemHeight - 4, 0.1).endVertex();
		bufferBuilder.vertex(x0, y0, 0.1).endVertex();
		bufferBuilder.vertex(x1, y0, 0.1).endVertex();
		bufferBuilder.vertex(x1, y0 - itemHeight - 4, 0.1).endVertex();
		tesselator.end();
		
		super.renderList(matrixStack, p_93453_, p_93454_, p_93455_, p_93456_, p_93457_);
		
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
	
	@OnlyIn(Dist.CLIENT)
	public final class Entry extends ObjectSelectionList.Entry<ShapeList.Entry>{
		private int shapeId;
		
		public Entry(int shapeId) {
			this.shapeId = shapeId;
		}
		
		public void render(PoseStack matrixStack, int p_93524_, int top, int left, int p_93527_, int p_93528_, int p_93529_, int p_93530_, boolean p_93531_, float p_93532_) {
			//Found strikethrough code at https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/1437428-guide-1-7-2-how-to-make-button-tooltips?comment=3
			Minecraft.getInstance().font.drawShadow(matrixStack, (StateManager.getState().advancedModeShapes.get(shapeId).visible ? "" : "\247m") + StateManager.getState().advancedModeShapes.get(shapeId).getTranslatedName(), left + 5, top + 4, 0xFFFFFF);
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
		
		public Component getNarration() {
			return new TextComponent(""); //TODO Set translated name of shape as narration
		}
	}
}
