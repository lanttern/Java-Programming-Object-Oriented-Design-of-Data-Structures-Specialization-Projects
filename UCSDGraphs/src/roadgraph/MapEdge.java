package roadgraph;

/**
 * A class which represents a information of an edge between start point and end point
 *  
 */

public class MapEdge {
    //set variables: startPoint (location start), endPoint (location end), 
	//roadName(street name), roadType (street type) and roadLength (distance between startPoint and endPoint)
	private MapNode startPoint;
	private MapNode endPoint;
	String roadName;
	String roadType;
	double roadLength;
	
	/**
	 * create a new edge
	 * @param startPoint The starting point of the edge
	 * @param endPoint The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 */
	public MapEdge(MapNode startPoint, MapNode endPoint, String roadName, String roadType, Double roadLength){
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.roadName = roadName;
		this.roadType = roadType;
		this.roadLength = roadLength;
	}

	/**
	 * get start node
	 */
	public MapNode getStartNode(){
		
		return startPoint;
	}
	
	/**
	 * get end node
	 */
	public MapNode getEndNode(){
		return endPoint;
	}
	
	/**
	 * get name of the road
	 */
	public String getRoadName(){
		return roadName;
	}
	
	/**
	 * get type of the road
	 */
	public String getRoadType(){
		return roadType;
	}
	
	/**
	 * get the length of the road
	 */
	public double getRoadLength(){
		return roadLength;
	}
	
	public double getRoadSpeed(){
		String roadType = getRoadType();
		System.out.println(roadType);
		if (roadType.equals("residential")){
			return 25.0;
		}
		return 40.0;
	}
	
	
	/**
	 * print out road information between start location and end location
	 */
	public void roadInfo(){
		System.out.println("The start location is: " + startPoint.getLocation());
		System.out.println("The end location is: " + endPoint.getLocation());
		System.out.println("The road is: " + roadName + " " + roadType);
		System.out.println("The distance from " + startPoint.getLocation()
		                  + " to " + endPoint.getLocation() + " is: " + roadLength);
	}
}
