package brentmaas.buildguide.forge.shapes;

import brentmaas.buildguide.forge.property.PropertyInt;
import brentmaas.buildguide.forge.property.PropertyPositiveInt;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.text.TranslationTextComponent;

public class ShapeCatenary extends Shape{
private static final double eps = 0.001;
	
	private PropertyInt propertyDx = new PropertyInt(0, 3, new TranslationTextComponent("property.buildguide.delta", "X"), () -> update());
	private PropertyInt propertyDy = new PropertyInt(1, 0, new TranslationTextComponent("property.buildguide.delta", "Y"), () -> update());
	private PropertyInt propertyDz = new PropertyInt(2, 0, new TranslationTextComponent("property.buildguide.delta", "Z"), () -> update());
	private PropertyPositiveInt propertyAddLength = new PropertyPositiveInt(3, 1, new TranslationTextComponent("property.buildguide.addlength"), () -> update());
	
	public ShapeCatenary() {
		super();
		
		properties.add(propertyDx);
		properties.add(propertyDy);
		properties.add(propertyDz);
		properties.add(propertyAddLength);
	}
	
	protected void updateShape(BufferBuilder builder) {
		double dr = Math.sqrt(propertyDx.value * propertyDx.value + propertyDz.value * propertyDz.value);
		int dy = propertyDy.value;
		int ds = propertyAddLength.value;
		if(dr >= 1) {
			double s = Math.sqrt(dr * dr + dy * dy) + ds;
			
			double a = 1;
			double chi;
			while(Math.abs(chi = 2 * a * Math.sinh(dr / 2 / a) - Math.sqrt(s * s - dy * dy)) > eps) {
				double dchi = 2 * Math.sinh(dr / 2 / a) - dr / a * Math.cosh(dr / 2 / a);
				a = Math.max(a - chi / dchi, a / 2);
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
			for(int i = 0;i <= d;++i) {
				for(int j = (int) (Math.min(0, dy) - a * Math.cosh(rl / a));j <= Math.max(0, dy);++j) {
					//Dirty hacks so the ends are actually at (0,0,0) and (dx,dy,dz)
					if(i == 0 && j > 0) continue;
					if(i == d && j > dy) continue;
					
					if((a * Math.cosh((i * fac + rl) / a) - a * Math.cosh(rl / a) >= j - 0.5 && a * Math.cosh((i * fac + rl) / a) - a * Math.cosh(rl / a) < j + 0.5) //Check down
					|| (a * Math.cosh(((i - 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) >= j && a * Math.cosh(((i + 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) < j) //Check left
					|| (a * Math.cosh(((i + 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) >= j && a * Math.cosh(((i - 0.5) * fac + rl) / a) - a * Math.cosh(rl / a) < j)) { //Check right
						addShapeCube(builder, (int) (dx * i + Math.signum(dx) * 0.5), j, (int) (dz * i + Math.signum(dz) * 0.5));
					}
				}
			}
		}else { //No valid catenary solution. Instead, a thin rope would go only vertically and double up on itself below the lower starting point
			for(int i = (int) (-ds / 2 - 0.5 + (dy >= 0 ? 0 : dy));i <= (dy >= 0 ? dy : 0);++i) {
				addShapeCube(builder, 0, i, 0);
			}
		}
	}
	
	public String getTranslationKey() {
		return "shape.buildguide.catenary";
	}
}
