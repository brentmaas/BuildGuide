package brentmaas.buildguide.forge;

import org.apache.commons.lang3.tuple.Pair;

import brentmaas.buildguide.common.AbstractConfig;
import brentmaas.buildguide.common.BuildGuide;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid=BuildGuide.modid, bus=EventBusSubscriber.Bus.MOD)
public class Config extends AbstractConfig {
	public static Config instance = new Config();
	protected ForgeConfigSpec.Builder builderInstance;
	
	public final ClientConfig clientConfig;
	public final ForgeConfigSpec clientConfigSpec;
	
	public Config() {
		final Pair<ClientConfig,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		clientConfig = specPair.getLeft();
		clientConfigSpec = specPair.getRight();
	}
	
	protected AbstractConfigElement<Boolean> createBooleanElement(String name, Boolean defaultValue, String comment) {
		return new BooleanConfigElement(name, defaultValue, comment);
	}
	
	protected void pushCategory(String category) {
		builderInstance.push(category);
	}
	
	protected void popCategory() {
		builderInstance.pop();
	}
	
	//Override build() as it needs to be called way before BuildGuideForge get instantiated
	@Override
	public void build() {
		
	}
	
	private void superbuild() {
		super.build();
	}
	
	protected class ClientConfig {
		
		
		public ClientConfig(ForgeConfigSpec.Builder builder) {
			Config.this.builderInstance = builder;
			Config.this.superbuild();
		}
	}
	
	protected class BooleanConfigElement extends AbstractConfigElement<Boolean> {
		protected BooleanValue configValue;
		
		protected BooleanConfigElement(String name, Boolean defaultValue, String comment) {
			super(name, defaultValue, comment);
		}
		
		protected void build() {
			configValue = comment != null ? Config.this.builderInstance.comment(comment).define(name, (boolean) value) : Config.this.builderInstance.define(name, (boolean) value);
		}
		
		protected void load() {
			value = configValue.get();
		}
	}
}