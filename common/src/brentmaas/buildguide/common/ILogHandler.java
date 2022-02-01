package brentmaas.buildguide.common;

public interface ILogHandler {
	
	
	public void fatal(String message);
	
	public void error(String message);
	
	public void debugOrHigher(String message);
}
