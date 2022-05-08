package brentmaas.buildguide.common.shape;

public interface IShapeBuffer {
	
	
	public void setColour(int r, int g, int b, int a);
	
	public void pushVertex(double x, double y, double z);
	
	public void end();
	
	public void close();
}
