package brentmaas.buildguide.common.shape;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.Property;
import brentmaas.buildguide.common.screen.BaseScreen;
import brentmaas.buildguide.common.screen.ShapeScreen;
import brentmaas.buildguide.common.screen.widget.AbstractWidgetHandler;

public abstract class Shape {
	public ArrayList<Property<?>> properties = new ArrayList<Property<?>>();
	public IShapeBuffer buffer;
	private int nBlocks = 0;
	public boolean ready = false;
	public boolean vertexBufferUnpacked = false;
	protected ShapeSet shapeSet;
	
	protected double originOffsetX = 0.0;
	protected double originOffsetY = 0.0;
	protected double originOffsetZ = 0.0;
	
	private static ExecutorService executor = Executors.newCachedThreadPool();
	public ReentrantLock lock = new ReentrantLock();
	private Future<?> future = null;
	private long completedAt = 0;
	public boolean error = false;
	
	protected abstract void updateShape(IShapeBuffer builder) throws Exception;
	
	public void update() {
		BaseScreen.shouldUpdatePersistence = true;
		
		ready = false;
		vertexBufferUnpacked = false;
		if(BuildGuide.config.asyncEnabled.value) {
			cancelFuture();
			if(buffer != null) buffer.close(); // Can only be done in render thread
			future = executor.submit(() -> {
				try {
					lock.lock();
					ready = false; // Again in case of a second thread started before a first thread ended
					vertexBufferUnpacked = false;
					error = false;
					doUpdate();
				}catch(InterruptedException e) {
					error = true;
				}catch(Exception e) {
					error = true;
					BuildGuide.logHandler.debugThrowable("An exception occurred while rendering a shape.", e);
				}finally {
					completedAt = System.currentTimeMillis();
					ready = true;
					lock.unlock();
				}
			});
		}else {
			error = false;
			if(buffer != null) buffer.close();
			try {
				doUpdate();
			}catch(InterruptedException e) {
				error = true;
			}catch(Exception e) {
				error = true;
				BuildGuide.logHandler.debugThrowable("An exception occurred while rendering a shape.", e);
			}finally {
				completedAt = System.currentTimeMillis();
				ready = true;
			}
		}
	}
	
	private void cancelFuture() {
		if(future != null && !(future.isDone() || future.isCancelled())) future.cancel(true);
	}
	
	private void doUpdate() throws Exception {
		nBlocks = 0;
		buffer = BuildGuide.shapeHandler.newBuffer();
		buffer.setColour((int) (255 * shapeSet.colourShapeR), (int) (255 * shapeSet.colourShapeG), (int) (255 * shapeSet.colourShapeB), (int) (255 * shapeSet.colourShapeA));
		updateShape(buffer);
		buffer.setColour((int) (255 * shapeSet.colourOriginR), (int) (255 * shapeSet.colourOriginG), (int) (255 * shapeSet.colourOriginB), (int) (255 * shapeSet.colourOriginA));
		addCube(buffer, 0.5 - shapeSet.originCubeSize / 2 + originOffsetX, 0.5 - shapeSet.originCubeSize / 2 + originOffsetY, 0.5 - shapeSet.originCubeSize / 2 + originOffsetZ, shapeSet.originCubeSize);
	}
	
	private void addCube(IShapeBuffer buffer, double x, double y, double z, double s) throws InterruptedException {
		if(Thread.currentThread().isInterrupted()) throw new InterruptedException(); //Interrupt check for concurrent shape generation
		
		//-X
		buffer.pushVertex(x, y, z);
		buffer.pushVertex(x, y, z+s);
		buffer.pushVertex(x, y+s, z+s);
		buffer.pushVertex(x, y+s, z);
		
		//-Y
		buffer.pushVertex(x, y, z);
		buffer.pushVertex(x+s, y, z);
		buffer.pushVertex(x+s, y, z+s);
		buffer.pushVertex(x, y, z+s);
		
		//-Z
		buffer.pushVertex(x, y, z);
		buffer.pushVertex(x, y+s, z);
		buffer.pushVertex(x+s, y+s, z);
		buffer.pushVertex(x+s, y, z);
		
		//+X
		buffer.pushVertex(x+s, y, z);
		buffer.pushVertex(x+s, y+s, z);
		buffer.pushVertex(x+s, y+s, z+s);
		buffer.pushVertex(x+s, y, z+s);
		
		//+Y
		buffer.pushVertex(x, y+s, z);
		buffer.pushVertex(x, y+s, z+s);
		buffer.pushVertex(x+s, y+s, z+s);
		buffer.pushVertex(x+s, y+s, z);
		
		//+Z
		buffer.pushVertex(x, y, z+s);
		buffer.pushVertex(x+s, y, z+s);
		buffer.pushVertex(x+s, y+s, z+s);
		buffer.pushVertex(x, y+s, z+s);
	}
	
	protected void addShapeCube(IShapeBuffer buffer, int x, int y, int z) throws InterruptedException {
		addCube(buffer, x + 0.5 - shapeSet.shapeCubeSize / 2, y + 0.5 - shapeSet.shapeCubeSize / 2, z + 0.5 - shapeSet.shapeCubeSize / 2, shapeSet.shapeCubeSize);
		
		++nBlocks;
	}
	
	protected void setOriginOffset(double dx, double dy, double dz) {
		originOffsetX = dx;
		originOffsetY = dy;
		originOffsetZ = dz;
	}
	
	public void onSelectedInGUI() {
		for(int i = 0;i < properties.size();++i) {
			properties.get(i).setX(ShapeScreen.basePropertiesX);
			properties.get(i).setY(ShapeScreen.basePropertiesY + i * AbstractWidgetHandler.defaultSize);
			properties.get(i).setVisibility(true);
		}
	}
	
	public void onDeselectedInGUI() {
		for(Property<?> p: properties) {
			p.setVisibility(false);
		}
	}
	
	public int getNumberOfBlocks() {
		if(!ready) return 0;
		return nBlocks;
	}
	
	public long getHowLongAgoCompletedMillis() {
		return System.currentTimeMillis() - completedAt;
	}
	
	public final String getTranslationKey() {
		return ShapeRegistry.getTranslationKey(this);
	}
	
	public String toPersistence() {
		String persistenceData = "";
		for(Property<?> property: properties) {
			persistenceData += property.getStringValue() + ",";
		}
		return persistenceData.substring(0, persistenceData.length() - 1);
	}
	
	public void restorePersistence(String persistenceData) {
		String splitData[] = persistenceData.split(",");
		boolean success = true;
		for(int i = 0;i < Math.min(properties.size(), splitData.length);++i) {
			success = success & properties.get(i).setValueFromString(splitData[i]);
		}
		error = !success;
	}
}
