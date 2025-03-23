package brentmaas.buildguide.common.screen;

import java.util.List;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.screen.AbstractScreenHandler.Translatable;
import brentmaas.buildguide.common.screen.widget.IButton;
import brentmaas.buildguide.common.screen.widget.ISelectorList;

public class DropdownOverlayScreen extends BaseScreen implements IButton {
	private BaseScreen parent;
	private int x, y, width, height, current;
	private List<Translatable> titles;
	private IDropdownCallback callback;
	
	private IButton openButton;
	
	private IButton closeButton;
	private ISelectorList shapeSelectorList;
	
	public DropdownOverlayScreen(BaseScreen parent, int x, int y, int width, int height, List<Translatable> titles, int current, IDropdownCallback callback) {
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.titles = titles;
		this.current = current;
		this.callback = callback;
		openButton = BuildGuide.widgetHandler.createButton(x + width - height, y, height, height, new Translatable("V"), () -> {
			openButton.setVisibility(false);
			BuildGuide.screenHandler.showScreen(this);
		});
	}
	
	public void init() {
		super.init();
		
		closeButton = BuildGuide.widgetHandler.createButton(x + width - height, y, height, height, new Translatable("X"), () -> {
			BuildGuide.screenHandler.showScreen(parent);
			openButton.setVisibility(true);
		});
		shapeSelectorList = BuildGuide.widgetHandler.createSelectorList(x, x + width, y + height, wrapper.getHeight(), 20, titles, current, (int selected) -> {
			callback.run(selected);
			BuildGuide.screenHandler.showScreen(parent);
			current = selected;
			openButton.setVisibility(true);
		});
		
		addWidget(closeButton);
		addWidget(shapeSelectorList);
	}
	
	public void render() {
		super.render();

		drawShadowCentred(titles.get(current).toString(), x + width / 2, y + 5, 0xFFFFFF);
	}
	
	public IButton getOpenButton() {
		return openButton;
	}
	
	public void setVisibility(boolean visible) {
		openButton.setVisibility(visible);
	}
	
	public void setYPosition(int y) {
		this.y = y;
		openButton.setYPosition(y);
		closeButton.setYPosition(y);
	}
	
	public void setActive(boolean active) {
		openButton.setActive(active);
	}
	
	public interface IDropdownCallback {
		public void run(int selected);
	}
}
