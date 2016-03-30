package guimodule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import processing.core.PApplet;
import de.fhpotsdam.*;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class LifeExpect extends PApplet{
	UnfoldingMap map;
	Map<String, Float> lifeExpByCountry;
	List<Feature> countries;
	List<Marker> countryMarkers;
	
	public void setup(){
		size(800, 600, OPENGL);
		map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
		MapUtils.createDefaultEventDispatcher(this, map);
		lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
		countries = GeoJSONReader.loadData(this, "countries.geo.json");
		System.out.println(countries);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		System.out.println(countryMarkers);
		map.addMarkers(countryMarkers);
		shadeCountries();
	}
	
	private void shadeCountries() {
		// TODO Auto-generated method stub
		for (Marker marker : countryMarkers){
			String countryId = marker.getId();
			if (lifeExpByCountry.containsKey(countryId)){
				float lifeExp = lifeExpByCountry.get(countryId);
				int colorLevel = (int) map(lifeExp, 40, 90, 10, 255);
				marker.setColor(color(255-colorLevel, 100, colorLevel));
			}
			else {
				marker.setColor(color(150, 150, 150));
			}
		}
	}

	private Map<String, Float> loadLifeExpectancyFromCSV(String fileName) {
		// TODO Auto-generated method stub
		Map<String, Float>lifeExpMap = new HashMap<String, Float>();
		String[] rows = loadStrings(fileName);
		for(String row:rows) {
			String[] columns = row.split(",");
			if (columns.length == 6 && !columns[5].equals("..")) {
			    float value = Float.parseFloat(columns[5]);
			    lifeExpMap.put(columns[4], value);
			}
		}
		return lifeExpMap;
	}

	public void draw() {
		map.draw();
	}

}
