package brentmaas.buildguide.common.screen;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ICheckboxRunnableButton;

public class ConfigurationScreen extends BaseScreen {
	private ICheckboxRunnableButton buttonAsyncEnabled, buttonAdvancedRandomColorsDefaultEnabled, buttonPersistenceEnabled, buttonDebugGenerationTiminigsEnabled;
	private IButton buttonAsyncEnabledDefault, buttonAdvancedRandomColorsDefaultEnabledDefault, buttonPersistenceEnabledDefault, buttonDebugGenerationTimingsEnabledDefault;
	
	public void init() {
		super.init();
		
		buttonAsyncEnabled = BuildGuide.widgetHandler.createCheckbox(255, 60, new Translatable(""), BuildGuide.config.asyncEnabled.value, false, () -> {
			BuildGuide.config.asyncEnabled.setValue(buttonAsyncEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonAsyncEnabledDefault = BuildGuide.widgetHandler.createButton(280, 60, 50, AbstractWidgetHandler.defaultSize, new Translatable("screen.buildguide.default"), () -> {
			buttonAsyncEnabled.setChecked(BuildGuide.config.asyncEnabled.getDefault());
			BuildGuide.config.write();
		});
		
		buttonAdvancedRandomColorsDefaultEnabled = BuildGuide.widgetHandler.createCheckbox(255, 110, new Translatable(""), BuildGuide.config.shapeListRandomColorsDefaultEnabled.value, false, () -> {
			BuildGuide.config.shapeListRandomColorsDefaultEnabled.setValue(buttonAdvancedRandomColorsDefaultEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonAdvancedRandomColorsDefaultEnabledDefault = BuildGuide.widgetHandler.createButton(280, 110, 50, AbstractWidgetHandler.defaultSize, new Translatable("screen.buildguide.default"), () -> {
			buttonAdvancedRandomColorsDefaultEnabled.setChecked(BuildGuide.config.shapeListRandomColorsDefaultEnabled.getDefault());
			BuildGuide.config.write();
		});
		
		buttonPersistenceEnabled = BuildGuide.widgetHandler.createCheckbox(255, 160, new Translatable(""), BuildGuide.config.persistenceEnabled.value, false, () -> {
			BuildGuide.config.persistenceEnabled.setValue(buttonPersistenceEnabled.isCheckboxSelected());
			BuildGuide.config.write();
		});
		buttonPersistenceEnabledDefault = BuildGuide.widgetHandler.createButton(280, 160, 50, AbstractWidgetHandler.defaultSize, new Translatable("screen.buildguide.default"), () -> {
			buttonPersistenceEnabled.setChecked(BuildGuide.config.persistenceEnabled.getDefault());
			BuildGuide.config.write();
		});
		
		addWidget(buttonAsyncEnabled);
		addWidget(buttonAsyncEnabledDefault);
		addWidget(buttonAdvancedRandomColorsDefaultEnabled);
		addWidget(buttonAdvancedRandomColorsDefaultEnabledDefault);
		addWidget(buttonPersistenceEnabled);
		addWidget(buttonPersistenceEnabledDefault);
		addWidget(buttonDebugGenerationTiminigsEnabled);
		addWidget(buttonDebugGenerationTimingsEnabledDefault);
	}
	
	public void render() {
		super.render();
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + new Translatable(BuildGuide.config.asyncEnabled.translationKey), 10, 65, 0xFFFFFF);
		drawShadowLeft(new Translatable(BuildGuide.config.asyncEnabled.commentTranslationKey).toString(), 10, 84, 0xFFFFFF);
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + new Translatable(BuildGuide.config.shapeListRandomColorsDefaultEnabled.translationKey), 10, 115, 0xFFFFFF);
		drawShadowLeft(new Translatable(BuildGuide.config.shapeListRandomColorsDefaultEnabled.commentTranslationKey).toString(), 10, 135, 0xFFFFFF);
		
		drawShadowLeft(BuildGuide.screenHandler.TEXT_MODIFIER_UNDERLINE + new Translatable(BuildGuide.config.persistenceEnabled.translationKey), 10, 165, 0xFFFFFF);
		drawShadowLeft(new Translatable(BuildGuide.config.persistenceEnabled.commentTranslationKey).toString(), 10, 185, 0xFFFFFF);
	}
}
