package guimodule;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Textfield;
 
import org.geonames.*;

//CONTROLP5 Variables


public class MyDisplay extends PApplet {

	boolean searchEvent = false;
	//String searchName = "berlin"; // the string we want to lookup in geonames database
	ToponymSearchCriteria searchCity = new ToponymSearchCriteria(); // the object we need for our search
    ControlP5 cp5;
    String textValue = "";
    Textfield myTextfield;
    controlP5.Label label;
	                                                                                                                            
	 
	public void setup() {
		size(650, 440);
	 
	    WebService.setUserName("ZXgeo"); // add your username here 
        cp5 = new ControlP5(this);
        myTextfield = cp5.addTextfield("Search City").setPosition(20, 300).setSize(100, 20).setFocus(true);
        myTextfield.setAutoClear(true).keepFocus(true);
        label = myTextfield.getCaptionLabel();
        label.setColor(color(255, 255, 255));
	                                                                        
	    }
	 
	public void draw(){
	     
	    searchCity.setQ(myTextfield.getStringValue()); // setup the main search term, in our case "berlin"
	        if(searchEvent == true){
	            try {
	                ToponymSearchResult searchResult = WebService.search(searchCity); // a toponym search result as returned by the geonames webservice.
	 
	                for (Toponym toponym : searchResult.getToponyms()) {
	                    println(toponym.getName() + " " + toponym.getCountryName()
	                            + " " + toponym.getLongitude() + " "
	                            + toponym.getLatitude()); 
	                    break;// prints the search results. We have access on certain get-Functions. In our Case the Name, Country, Longitude and Latitude
	                        }
	 
	                        } catch (Exception e) {
	                            // TODO Auto-generated catch block
	                            e.printStackTrace();
	                }
	                searchEvent = false;
	                }
	     
	}
	
	
	public void input(String theText) {
	    // automatically receives results from controller input
	    println("a textfield event for controller 'input' : " + theText);
	}
	
	 
	public void controlEvent(ControlEvent theEvent) {
	    if (theEvent.isAssignableFrom(Textfield.class)) {
	        println("controlEvent: accessing a string from controller '" + theEvent.getName() + "': "
	                + theEvent.getStringValue());
	        searchEvent = true;
	    }
	}
	
}
