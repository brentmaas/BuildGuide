package brentmaas.buildguide.fabric;

import com.mojang.blaze3d.platform.InputConstants;

import brentmaas.buildguide.common.AbstractInputHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class InputHandler extends AbstractInputHandler {
	public IKeyBind registerKeyBind(String name, int keyCode) {
		return new KeyBindImpl(name, keyCode);
	}
	
	public void registerOnKeyInput() {
		ClientTickEvents.END_CLIENT_TICK.register(this::onKeyInputProxy);
	}
	
	public void onKeyInputProxy(Minecraft minecraft) {
		onKeyInput();
	}
	
	public class KeyBindImpl implements IKeyBind {
		private KeyMapping bind;
		
		public KeyBindImpl(String name, int keyCode) {
			bind = KeyBindingHelper.registerKeyBinding(new KeyMapping(name, InputConstants.Type.KEYSYM, keyCode, category));
		}
		
		public boolean isDown() {
			return bind.isDown();
		}
	}
}
