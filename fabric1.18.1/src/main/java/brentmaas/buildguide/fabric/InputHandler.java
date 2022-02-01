package brentmaas.buildguide.fabric;

import brentmaas.buildguide.common.AbstractInputHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class InputHandler extends AbstractInputHandler {
	
	
	public IKeyBind registerKeyBind(String name, int keyCode) {
		return new KeyBindImpl(name, keyCode);
	}
	
	public void registerOnKeyInput() {
		ClientTickEvents.END_CLIENT_TICK.register(this::onKeyInputProxy);
	}
	
	public void onKeyInputProxy(MinecraftClient client) {
		onKeyInput();
	}
	
	public class KeyBindImpl implements IKeyBind {
		private KeyBinding bind;
		
		public KeyBindImpl(String name, int keyCode) {
			bind = KeyBindingHelper.registerKeyBinding(new KeyBinding(name, InputUtil.Type.KEYSYM, keyCode, category));
		}
		
		public boolean isDown() {
			return bind.wasPressed();
		}
	}
}
