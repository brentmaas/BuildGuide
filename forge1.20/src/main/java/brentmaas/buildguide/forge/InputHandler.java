package brentmaas.buildguide.forge;

import org.apache.commons.lang3.ArrayUtils;

import com.mojang.blaze3d.platform.InputConstants;

import brentmaas.buildguide.common.AbstractInputHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent.Key;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InputHandler extends AbstractInputHandler{
	
	
	public IKeyBind registerKeyBind(String name, int keyCode) {
		return new KeyBindImpl(name, keyCode);
	}
	
	public void registerOnKeyInput() {
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onKeyInputProxy(Key event) {
		onKeyInput();
	}
	
	public class KeyBindImpl implements IKeyBind {
		private KeyMapping bind;
		
		public KeyBindImpl(String name, int keyCode) {
			bind = new KeyMapping(name, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, keyCode, category);
			Minecraft.getInstance().options.keyMappings = ArrayUtils.add(Minecraft.getInstance().options.keyMappings, bind); //RegisterKeyMappingsEvent won't trigger, so I'm doing it directly myself
		}
		
		public boolean isDown() {
			return bind.isDown();
		}
	}
}
