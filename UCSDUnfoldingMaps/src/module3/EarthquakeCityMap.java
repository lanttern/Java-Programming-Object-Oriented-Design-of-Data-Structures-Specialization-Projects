package module3;

//Java utilities libraries
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Zhihui Xie
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = false;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<Marker> markers = new ArrayList<Marker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // These print statements show you (1) all of the relevant properties 
	    // in the features, and (2) how to get one property and use it
	    if (earthquakes.size() > 0) {
	    	for (int i = 0; i < earthquakes.size(); i++){
	    	    PointFeature f = earthquakes.get(i);
	    	    System.out.println(f.getProperties());
	    	    Object magObj = f.getProperty("magnitude");
	    	    float mag = Float.parseFloat(magObj.toString());
	    	    // PointFeatures also have a getLocation method
	    	    SimplePointMarker point = createMarker(f);
	    	    //set up color for different mag
	    	    if (mag < THRESHOLD_LIGHT){
	    	    	int magColor = color(0, 0, 255);
	    	    	point.setColor(magColor);
	    	    	point.setRadius(4);
	    	    	markers.add(point);
	    	    }
	    	    if (mag >= THRESHOLD_LIGHT && mag <THRESHOLD_MODERATE){
	    	    	int magColor = color(255, 255, 0);
	    	    	point.setColor(magColor);
	    	    	point.setRadius(8);
	    	    	markers.add(point);
	    	    }
	    	    if (mag >= THRESHOLD_MODERATE){
	    	    	int magColor = color(255, 0, 0);
	    	    	point.setColor(magColor);
	    	    	point.setRadius(16);
	    	    	markers.add(point);
	    	    }
	        }
	    
	    // add markers to map  
	    System.out.println(markers);
	    map.addMarkers(markers);
	    }
	    
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.
		return new SimplePointMarker(feature.getLocation());
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	private void addKey() 
	{	
		// Remember you can use Processing's graphics methods here
		//add key indicator
	    fill(255, 255, 200);
	    rect(20, 120, 150, 250, 7);
	    fill(0, 0, 0);
	    textSize(15);
	    text("Earthquake key", 30, 150);
	    
	    //add indicator for high earthquakes
	    fill(255, 0, 0);
	    ellipse(40, 200, 16, 16);
	    fill(0, 0, 0);
	    textSize(12);
	    text("5.0+ Magnitude", 60, 208);
	    
	    //add indicator for medium earthquakes
	    fill(255, 255, 50);
	    ellipse(40, 250, 8, 8);
	    fill(0, 0, 0);
	    textSize(12);
	    text("4.0+ Magnitude", 60, 254);
	    
	    //add indicator for light earthquakes
	    fill(0, 0, 255);
	    ellipse(40, 300, 4, 4);
	    fill(0, 0, 0);
	    textSize(12);
	    text("Below 4.0", 60, 302);
	    
	}
}