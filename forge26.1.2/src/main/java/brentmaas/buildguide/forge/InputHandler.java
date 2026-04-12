package brentmaas.buildguide.forge;

import com.mojang.blaze3d.platform.InputConstants;

import brentmaas.buildguide.common.AbstractInputHandler;
import brentmaas.buildguide.common.BuildGuide;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyMapping.Category;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;

public class InputHandler extends AbstractInputHandler{
	private RegisterKeyMappingsEvent eventInstance;
	
	public InputHandler(RegisterKeyMappingsEvent event) {
		this.eventInstance = event;
	}
	
	public IKeyBind registerKeyBind(String name, int keyCode) {
		return new KeyBindImpl(name, keyCode, eventInstance);
	}
	
	public void registerOnKeyInput() {
		Key.BUS.addListener(this::onKeyInputProxy);
	}
	
	@SubscribeEvent
	public void onKeyInputProxy(Key event) {
		onKeyInput();
	}
	
	public class KeyBindImpl implements IKeyBind {
		private KeyMapping bind;
		private static final Category keyCategory = Category.register(Identifier.tryBuild(BuildGuide.modid, BuildGuide.modid));
		
		public KeyBindImpl(String name, int keyCode, RegisterKeyMappingsEvent event) {
			bind = new KeyMapping(name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, keyCode, keyCategory, 0);
			event.register(bind);
		}
		
		public boolean isDown() {
			return bind.isDown();
		}
	}
}
