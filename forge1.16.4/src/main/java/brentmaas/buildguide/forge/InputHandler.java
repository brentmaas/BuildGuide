package brentmaas.buildguide.forge;

import brentmaas.buildguide.common.AbstractInputHandler;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InputHandler extends AbstractInputHandler {
	public IKeyBind registerKeyBind(String name, int keyCode) {
		return new KeyBindImpl(name, keyCode);
	}
	
	public void registerOnKeyInput() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onKeyInputProxy(KeyInputEvent event) {
		onKeyInput();
	}
	
	public class KeyBindImpl implements IKeyBind {
		private KeyBinding bind;
		
		public KeyBindImpl(String name, int keyCode) {
			bind = new KeyBinding(name, KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, keyCode, category);
			ClientRegistry.registerKeyBinding(bind);
		}
		
		public boolean isDown() {
			return bind.isDown();
		}
	}
}
