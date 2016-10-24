package roadgraph;
import java.util.*;
import geography.GeographicPoint;
/**
 * A class which represents a node in graph with geographic locations and edges
 * 
 */
public class MapNode implements Comparable{
    //set up variables: geographic point location, a list of MapEdge (edges), 
	//a list of neignbor nodes (nodeNeighbors)
	private GeographicPoint location;
	private List<MapEdge> edges;
	private List<MapNode> nodeNeighbors;
	private double distance;
	private double gdistance;
	private double travelTime;
	private double gtravelTime;
	/**
	 * create an empty node
	 * @param location the geographic location.
	 */
	public MapNode(GeographicPoint location){
		this.location = location;
		edges = new ArrayList<MapEdge>();
		nodeNeighbors = new ArrayList<MapNode>();
		distance = 0.0;
		gdistance = 0.0;
		travelTime = 0.0;
		gtravelTime = 0.0;
	}
	
	/**
	 * Adds a directed edge to the graph from start to end.  
	 * @param start The starting point of the edge
	 * @param end The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @return false If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 * @return true If adds a new edge
	 */
	public boolean addEdge(MapNode start, MapNode end, String roadName, String roadType, double length){
		//check if node if is valid. if it's invalid, don't add edge and return false. 
		if (start == null || end == null || roadName == null || roadType == null || length <= 0) {
			return false;
		}
		MapEdge newEdge = new MapEdge(start, end, roadName, roadType, length);
		//check if new edge is already included, if yes, don't add edge and return false
		if (edges.contains(newEdge)) {
			return false;
		}
		//add new edge and return true
		edges.add(newEdge);
		return true;
	}
	
	/**
	 * get number of edges
	 */
	public int getNumEdge(){
		return edges.size();
	}
	
	/**
	 * get edges
	 */
	public List<MapEdge> getEdge(){
		return edges;
	}
	
	/**
	 * get length of edge from start node to end node
	 */
	public double getEdgeLength(MapNode endNode){
		double length = Double.MAX_VALUE;
		for (MapEdge edge: edges) {
			if (edge.getEndNode().equals(endNode)){
				length  = edge.getRoadLength();
			}
		}
		if (length < Double.MAX_VALUE){
            return length;
        } else{
            throw new IllegalArgumentException("No edge for these two nodes!");
        }
	}
	
	/**
	 * get the speed for a road
	 */
	public double getEdgeSpeed(MapNode endNode){
		double speed = Double.MAX_VALUE;
		for (MapEdge edge: edges) {
			if (edge.getEndNode().equals(endNode)){
				speed  = edge.getRoadSpeed();
			}
		}
		if (speed < Double.MAX_VALUE){
            return speed;
        } else{
            throw new IllegalArgumentException("No edge for these two nodes!");
        }
	}
	
	/**
	 * get neighbors of a node
	 */
	public List<MapNode> getNeighbors(){
		for (MapEdge edge: edges){
			//add end node to neighbors list
			nodeNeighbors.add(edge.getEndNode());
		}
		return nodeNeighbors;
	}
	
	/**
	 * get location
	 */
	public GeographicPoint getLocation(){
		return location;
	}
	
	/**
	 * get travel time from current node to start
	 */
	public double getTravelTime(){
		return this.travelTime;
	}
	
	/**
	 * set travel time
	 */
	public void setTravelTime(double travelTime){
		this.travelTime = travelTime;
	}
	
	/**
	 * get actual travelTime
	 */
	public double getGTravelTime(){
		return this.gtravelTime;
	}
	
	/**
	 * set gtravelTime
	 */
	public void setGtravelTime(double gtravelTime){
		this.gtravelTime = gtravelTime;
	}
	
	/**
	 * get distance from current node to start
	 */
	public double getDistance(){
		return this.distance;
	}
	
	/**
	 * set distance
	 */
	public void setDistance(double distance){
		this.distance = distance;
	}
	
	/**
	 * get actual distance
	 */
	public double getGDistance(){
		return this.gdistance;
	}
	
	/**
	 * get gdistance
	 */
	public void setGDistance(double gdistance){
		this.gdistance = gdistance;
	}
	
	/**
	 * implement comparable
	 */
	public int compareTo(Object obj) {
		MapNode node = (MapNode) obj;
		//compare distance
		return ((Double)this.getDistance()).compareTo ((Double) node.getDistance());
		//compare travel time
		//return ((Double)this.getTravelTime()).compareTo ((Double) node.getTravelTime());
	}
	
}
