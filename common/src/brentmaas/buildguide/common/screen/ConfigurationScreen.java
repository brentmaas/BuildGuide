package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;

public class ConfigurationScreen extends BaseScreen {
	private ICheckboxRunnableButton buttonAsyncEnabled, buttonAdvancedRandomColorsDefaultEnabled, buttonDebugGenerationTiminigsEnabled;
	private IButton buttonAsyncEnabledDefault, buttonAdvancedRandomColorsDefaultEnabledDefault, buttonDebugGenerationTimingsEnabledDefault;
	
	public void init() {
		super.init();
		
		buttonAsyncEnabled = BuildGuide.widgetHandler.createCheckbox(255, 60, 20, 20, "", BuildGuide.config.asyncEnabled.value, false, () -> {
			BuildGuide.config.asyncEnabled.setValue(buttonAsyncEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonAsyncEnabledDefault = BuildGuide.widgetHandler.createButton(280, 60, 50, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
			buttonAsyncEnabled.setChecked(BuildGuide.config.asyncEnabled.getDefault());
			BuildGuide.config.write();
		});
		
		buttonAdvancedRandomColorsDefaultEnabled = BuildGuide.widgetHandler.createCheckbox(255, 110, 20, 20, "", BuildGuide.config.shapeListRandomColorsDefaultEnabled.value, false, () -> {
			BuildGuide.config.shapeListRandomColorsDefaultEnabled.setValue(buttonAdvancedRandomColorsDefaultEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonAdvancedRandomColorsDefaultEnabledDefault = BuildGuide.widgetHandler.createButton(280, 110, 50, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
			buttonAdvancedRandomColorsDefaultEnabled.setChecked(BuildGuide.config.shapeListRandomColorsDefaultEnabled.getDefault());
			BuildGuide.config.write();
		});
		
		buttonDebugGenerationTiminigsEnabled = BuildGuide.widgetHandler.createCheckbox(255, 160, 20, 20, "", BuildGuide.config.debugGenerationTimingsEnabled.value, false, () -> {
			BuildGuide.config.debugGenerationTimingsEnabled.setValue(buttonDebugGenerationTiminigsEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonDebugGenerationTimingsEnabledDefault = BuildGuide.widgetHandler.createButton(280, 160, 50, 20, BuildGuide.screenHandler.translate("screen.buildguide.default"), () -> {
			buttonDebugGenerationTiminigsEnabled.setChecked(BuildGuide.config.debugGenerationTimingsEnabled.getDefault());
			BuildGuide.config.write();
		});
		
		addWidget(buttonAsyncEnabled);
		addWidget(buttonAsyncEnabledDefault);
		addWidget(buttonAdvancedRandomColorsDefaultEnabled);
		addWidget(buttonAdvancedRandomColorsDefaultEnabledDefault);
		addWidget(buttonDebugGenerationTiminigsEnabled);
		addWidget(buttonDebugGenerationTimingsEnabledDefault);
	}
	
	public void render() {
		super.render();
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + BuildGuide.config.asyncEnabled.key, 10, 65, 0xFFFFFF);
		drawShadowLeft(BuildGuide.config.asyncEnabled.comment, 10, 84, 0xFFFFFF);
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + BuildGuide.config.shapeListRandomColorsDefaultEnabled.key, 10, 115, 0xFFFFFF);
		drawShadowLeft(BuildGuide.config.shapeListRandomColorsDefaultEnabled.comment, 10, 135, 0xFFFFFF);
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + BuildGuide.config.debugGenerationTimingsEnabled.key, 10, 165, 0xFFFFFF);
		drawShadowLeft(BuildGuide.config.debugGenerationTimingsEnabled.comment, 10, 185, 0xFFFFFF);
	}
}
