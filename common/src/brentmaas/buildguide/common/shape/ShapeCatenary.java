package brentmaas.buildguide.common.shape;

import brentmaas.buildguide.common.BuildGuide;
import brentmaas.buildguide.common.property.PropertyBoolean;
import brentmaas.buildguide.common.property.PropertyInt;
import brentmaas.buildguide.common.property.PropertyPositiveFloat;
import brentmaas.buildguide.common.property.PropertyRunnable;
import brentmaas.buildguide.common.shape.ShapeSet.Origin;

public class ShapeCatenary extends Shape {
	private static final double eps = 0.001;
	
	private PropertyInt propertyDx = new PropertyInt(3, BuildGuide.screenHandler.translate("property.buildguide.delta", "X"), () -> update());
	private PropertyInt propertyDy = new PropertyInt(0, BuildGuide.screenHandler.translate("property.buildguide.delta", "Y"), () -> update());
	private PropertyInt propertyDz = new PropertyInt(0, BuildGuide.screenHandler.translate("property.buildguide.delta", "Z"), () -> update());
	private PropertyRunnable propertySetEndpoint = new PropertyRunnable(() -> {
		Origin pos = BuildGuide.shapeHandler.getPlayerPosition();
		propertyDx.setValue(pos.x - shapeSet.origin.x);
		propertyDy.setValue(pos.y - shapeSet.origin.y);
		propertyDz.setValue(pos.z - shapeSet.origin.z);
		update();
	}, BuildGuide.screenHandler.translate("property.buildguide.setendpoint"));
	private PropertyPositiveFloat propertyAddLength = new PropertyPositiveFloat(1, BuildGuide.screenHandler.translate("property.buildguide.addlength"), () -> update());
	private PropertyBoolean propertyInvert = new PropertyBoolean(false, BuildGuide.screenHandler.translate("property.buildguide.invert"), () -> update());
	
	public ShapeCatenary() {
		super();
		
		properties.add(propertyDx);
		properties.add(propertyDy);
		properties.add(propertyDz);
		properties.add(propertySetEndpoint);
		properties.add(propertyAddLength);
		properties.add(propertyInvert);
	}
	
	protected void updateShape(IShapeBuffer buffer) throws Exception {
		double dr = Math.sqrt(propertyDx.value * propertyDx.value + propertyDz.value * propertyDz.value);
		int dy = propertyDy.value;
		float ds = propertyAddLength.value;
		boolean inv = propertyInvert.value;
		if(dr >= 1) {
			double s = Math.sqrt(dr * dr + dy * dy) + ds;
			
			double a = 1;
			double chi;
			while(Math.abs(chi = 2 * a * Math.sinh(dr / 2 / a) - Math.sqrt(s * s - dy * dy)) > eps) {
				double dchi = 2 * Math.sinh(dr / 2 / a) - dr / a * Math.cosh(dr / 2 / a);
				a = Math.max(a - chi / dchi, a / 2);
			}
			if(!Double.isFinite(a)) {
				throw new IllegalStateException(BuildGuide.screenHandler.translate("error.buildguide.invalidvalue"));
			}
			
			double rl = 0;
			while(Math.abs(chi = a * Math.cosh(rl / a) + dy - a * Math.cosh((rl + dr) / a)) > eps) {
				double dchi = Math.sinh(rl / a) - Math.sinh((rl + dr) / a);
				rl -= chi / dchi;
			}
			
			int d = Math.max(Math.abs(propertyDx.value), Math.abs(propertyDz.value));
			double dx = ((double) propertyDx.value) / d;
			double dz = ((double) propertyDz.value) / d;
			double fac = Math.sqrt(dx * dx + dz * dz);
			int h = (int) (Math.min(0, dy) - a * Math.cosh(rl / a));
			for(int i = 0;i <= d;++i) {
				for(int j = h;j <= Math.max(0, dy);++j) {
					//Dirty hacks so the ends are actually at (0,0,0) and (dx,dy,dz)
					if(i == 0 && j > 0) continue;
					if(i == d && j > dy) continue;
					
					if((a * Math.cosh((i * fac + rl) / a) - a * Math.cosh(rl / a) >= j - 0.5 && a * Math.cosh((i * fac + rl) / a) - a * Math.cosh(rl / a) < j + 0.5) //Check down
					|| (a * Math.cosh(((i - 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) >= j && a * Math.cosh(((i + 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) < j) //Check left
					|| (a * Math.cosh(((i + 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) >= j && a * Math.cosh(((i - 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) < j)) { //Check right
						addShapeCube(buffer, inv ? (int) (dx * (d - i) + Math.signum(dx) * 0.5) : (int) (dx * i + Math.signum(dx) * 0.5), inv ? dy - j : j, inv ? (int) (dz * (d - i) + Math.signum(dz) * 0.5) : (int) (dz * i + Math.signum(dz) * 0.5));
					}
				}
			}
		}else { //No valid catenary solution. Instead, a thin rope would go only vertically and double up on itself below the lower starting point
			for(int i = (int) (-ds / 2 - 0.5 + (dy >= 0 ? 0 : dy));i <= (dy >= 0 ? dy : 0);++i) {
				addShapeCube(buffer, 0, i, 0);
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.catenary";
	}
}
