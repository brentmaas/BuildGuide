package brentmaas.buildguide.common.screen.widget;

public interface IShapeList {
	
	
	public void addEntry(int shapeId);
	
	public boolean removeEntry(IEntry entry);
	
	public IEntry getSelected();
	
	public static interface IEntry {
		
		
		public void setShapeId(int shapeId);
		
		public int getShapeId();
	}
}
