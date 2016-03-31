###Create interactive graphical earthquake map
==================================================================
In this project, I used Java to:

•	Create an interactive graphical map that displays earthquake and city geospatial data using Java.

•	Implemente a search button to search a city and display information of earthquakes affected that city.

To implement the city search button, I implemented new methods in the EarthquakeCityMap class:

	•	Used controlP5 and geonames packages (import these packages)
	
	•	First, the controlP5 library was used to build a search button and a ControlEvent that passes input values from typing.
	
	•	Then, the searchCity method was implemented, which uses geonames webservice (WebService.search) to search the input city name and returns a toponym search results. Usually, the first item returned by the search is the extract match item, and it's thus been used to get the location (toponym.getLatitude(), toponym.getLongitude()) of that city.
	
	•	Next, based on the location of the first searching result, the unfoldingmap method zoomAndPanTo was used to localize and zoom in the city area.

I also modified the following code to show a pop-up window when a marker is clicked:

	•	Used javax.swing.JOptionPane
	
	•	Modified checkCitiesForClick method to get the unhidden quake markers that impacted the clicked city and show the information of the quakes (magnitude, distance, location) by utilizing JOptionPane.showMessageDialog.
	
	•	Modified checkEarthquakesForClick method to get the unhidden city markers that were impacted by the clicked quake and show the information of the cities (city name and population) by utilizing JOptionPane.showMessageDialog.

References:
	•	https://www.coursera.org/learn/object-oriented-java/ 
	
	•	http://unfoldingmaps.org/tutorials/geonames-lookup-basic.html 
	
	•	http://unfoldingmaps.org/tutorials/interactions-simple.html 
	
	•	https://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html 
	
	•	http://www.geonames.org/export/web-services.html 
	
	•	https://github.com/sojamo/controlp5

####INSTALLATION

Import this folder in Eclipse ('File' -> 'Import' -> 'Existing Projects into
Workspace', Select this folder, 'Finish')


####MANUAL INSTALLATION

If the import does not work follow the steps below.

- Create new Java project

- Copy+Paste all files into project

- Add all lib/*.jars to build path

- Set native library location for jogl.jar. Choose appropriate folder for your OS.

- Add data/ as src


TROUBLE SHOOTING

Switch Java Compiler to 1.6 if you get VM problems. (Processing should work with Java 1.6, and 1.7)




