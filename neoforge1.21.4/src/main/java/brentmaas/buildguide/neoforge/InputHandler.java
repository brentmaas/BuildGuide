package brentmaas.buildguide.neoforge;

import com.mojang.blaze3d.platform.InputConstants;

import brentmaas.buildguide.common.AbstractInputHandler;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.InputEvent.Key;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;

public class InputHandler extends AbstractInputHandler{
	private RegisterKeyMappingsEvent eventInstance;
	
	public InputHandler(RegisterKeyMappingsEvent event) {
		this.eventInstance = event;
	}
	
	public IKeyBind registerKeyBind(String name, int keyCode) {
		return new KeyBindImpl(name, keyCode, eventInstance);
	}
	
	public void registerOnKeyInput() {
		NeoForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onKeyInputProxy(Key event) {
		onKeyInput();
	}
	
	public class KeyBindImpl implements IKeyBind {
		private KeyMapping bind;
		
		public KeyBindImpl(String name, int keyCode, RegisterKeyMappingsEvent event) {
			bind = new KeyMapping(name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, keyCode, category);
			event.register(bind);
		}
		
		public boolean isDown() {
			return bind.isDown();
		}
	}
}
