package brentmaas.buildguide.common.screen.widget;

public interface ISelectorList extends IWidget {
	
	
	public static interface IEntry {
		
	}
	
	public interface ISelectorListCallback {
		public void run(int selected);
	}
}
