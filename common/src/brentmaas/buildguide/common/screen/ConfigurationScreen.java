package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;

public class ConfigurationScreen extends BaseScreen {
	private ICheckboxRunnableButton buttonDebugGenerationTiminigsEnabled, buttonAsyncEnabled, buttonAdvancedRandomColorsDefaultEnabled;
	private IButton buttonDebugGenerationTimingsEnabledDefault, buttonAsyncEnabledDefault, buttonAdvancedRandomColorsDefaultEnabledDefault;
	
	public void init() {
		super.init();
		
		buttonDebugGenerationTiminigsEnabled = BuildGuide.widgetHandler.createCheckbox(255, 60, 20, 20, "", BuildGuide.config.debugGenerationTimingsEnabled.value, false, () -> {
			BuildGuide.config.debugGenerationTimingsEnabled.setValue(buttonDebugGenerationTiminigsEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonDebugGenerationTimingsEnabledDefault = BuildGuide.widgetHandler.createButton(280, 60, 50, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
			BuildGuide.config.debugGenerationTimingsEnabled.resetToDefault();
			buttonDebugGenerationTiminigsEnabled.setChecked(BuildGuide.config.debugGenerationTimingsEnabled.value);
			BuildGuide.config.write();
		});
		
		buttonAsyncEnabled = BuildGuide.widgetHandler.createCheckbox(255, 110, 20, 20, "", BuildGuide.config.asyncEnabled.value, false, () -> {
			BuildGuide.config.asyncEnabled.setValue(buttonAsyncEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonAsyncEnabledDefault = BuildGuide.widgetHandler.createButton(280, 110, 50, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
			BuildGuide.config.asyncEnabled.resetToDefault();
			buttonAsyncEnabled.setChecked(BuildGuide.config.asyncEnabled.value);
			BuildGuide.config.write();
		});
		
		buttonAdvancedRandomColorsDefaultEnabled = BuildGuide.widgetHandler.createCheckbox(255, 160, 20, 20, "", BuildGuide.config.advancedRandomColorsDefaultEnabled.value, false, () -> {
			BuildGuide.config.advancedRandomColorsDefaultEnabled.setValue(buttonAdvancedRandomColorsDefaultEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonAdvancedRandomColorsDefaultEnabledDefault = BuildGuide.widgetHandler.createButton(280, 160, 50, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
			BuildGuide.config.advancedRandomColorsDefaultEnabled.resetToDefault();
			buttonAdvancedRandomColorsDefaultEnabled.setChecked(BuildGuide.config.advancedRandomColorsDefaultEnabled.value);
			BuildGuide.config.write();
		});
		
		addWidget(buttonDebugGenerationTiminigsEnabled);
		addWidget(buttonDebugGenerationTimingsEnabledDefault);
		addWidget(buttonAsyncEnabled);
		addWidget(buttonAsyncEnabledDefault);
		addWidget(buttonAdvancedRandomColorsDefaultEnabled);
		addWidget(buttonAdvancedRandomColorsDefaultEnabledDefault);
	}
	
	public void render() {
		super.render();
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + BuildGuide.config.debugGenerationTimingsEnabled.key, 10, 65, 0xFFFFFF);
		drawShadowLeft(BuildGuide.config.debugGenerationTimingsEnabled.comment, 10, 85, 0xFFFFFF);
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + BuildGuide.config.asyncEnabled.key, 10, 115, 0xFFFFFF);
		drawShadowLeft(BuildGuide.config.asyncEnabled.comment, 10, 135, 0xFFFFFF);
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + BuildGuide.config.advancedRandomColorsDefaultEnabled.key, 10, 165, 0xFFFFFF);
		drawShadowLeft(BuildGuide.config.advancedRandomColorsDefaultEnabled.comment, 10, 185, 0xFFFFFF);
	}
}
