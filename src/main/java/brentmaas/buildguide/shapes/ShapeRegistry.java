package brentmaas.buildguide.shapes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import brentmaas.buildguide.BuildGuide;

public class ShapeRegistry {
	private static Map<String,Class<? extends Shape>> shapeRegistry = new HashMap<String,Class<? extends Shape>>();
	private static ArrayList<String> classIdentifiers = new ArrayList<String>();
	private static ArrayList<String> translatedNames = new ArrayList<String>();
	
	public static void registerShape(Class<? extends Shape> shapeClass) {
		shapeRegistry.put(shapeClass.getName(), shapeClass);
		classIdentifiers.add(shapeClass.getName());
		translatedNames.add(getNewInstance(shapeClass.getName()).getTranslatedName());
	}
	
	public static Shape getNewInstance(String classIdentifier) {
		try {
			return shapeRegistry.get(classIdentifier).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			BuildGuide.logger.error("Unable to instantiate Shape class '" + classIdentifier + "'");
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	public static ArrayList<String> getClassIdentifiers(){
		return classIdentifiers;
	}
	
	public static ArrayList<String> getTranslationKeys(){
		return translatedNames;
	}
	
	public static int getNumberOfShapes() {
		return classIdentifiers.size();
	}
}
