/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import geography.GeographicPoint;
import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between two locations
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	// add variables graph as a map and number of edge (numEdge) as integer
	private Map<GeographicPoint, MapNode> graph;
	private Map<Map<GeographicPoint, GeographicPoint>, List<GeographicPoint>> bfsPath;
	private Map<Map<GeographicPoint, GeographicPoint>, List<GeographicPoint>> dPath;
	private Map<Map<GeographicPoint, GeographicPoint>, List<GeographicPoint>> aStarPath;
	private int numEdge = 0;
	
	/** 
	 * Create a new empty MapGraph using HashMap as data structure
	 */
	public MapGraph()
	{
		// TODO: Implement in this constructor in WEEK 2
		graph = new HashMap<GeographicPoint, MapNode>();
		//variables to store found paths
		bfsPath = new HashMap<Map<GeographicPoint, GeographicPoint>, List<GeographicPoint>>();
		dPath = new HashMap<Map<GeographicPoint, GeographicPoint>, List<GeographicPoint>>();
		aStarPath = new HashMap<Map<GeographicPoint, GeographicPoint>, List<GeographicPoint>>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return graph.size();
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return graph.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		for (MapNode node: graph.values()){
			numEdge += node.getNumEdge();
		}
		return numEdge;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		//@return false if location parameter is invalid
		if (location == null){
			return false;
		}
		Set<GeographicPoint> vertex = getVertices();
		//@return false if location in graph
		if (vertex.contains(location)){
			return false;
		}
		//@return true: create a new MapNode and put it to graph
		MapNode newNode = new MapNode(location);
		graph.put(location, newNode);
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {

		//TODO: Implement this method in WEEK 2
		//get start and end nodes
		MapNode startNode = graph.get(from);
		MapNode endNode = graph.get(to);
		//add a new edge to startNode
		startNode.addEdge(startNode, endNode, roadName, roadType, length);
		//update startNode in graph
		graph.put(from, startNode);
	}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// check if path was found and stored 
		Map <GeographicPoint, GeographicPoint> searchTarget = new HashMap<>();
		if (bfsPath.containsKey(searchTarget)){
			return bfsPath.get(searchTarget);
		}
		// TODO: Implement this method in WEEK 2
		if (start == null || goal == null) {
			System.out.println("Invalid start or goal location");
			return null;
		}
		//add variables queue to temporary store points, visited list to store visited points 
		//and map to store find points
		Queue <GeographicPoint> queue = new LinkedList<GeographicPoint>();
		List <GeographicPoint> visited = new ArrayList<GeographicPoint>();
		Map <GeographicPoint, GeographicPoint> map = new HashMap<GeographicPoint, GeographicPoint>();
		
		queue.add(start);
		visited.add(start);
		while(!queue.isEmpty()){
			GeographicPoint curr = queue.remove();
			// Hook for visualization.  See writeup.
			nodeSearched.accept(curr);
			//stop when reaches goal
			if (goal.equals(curr)){
				break;
			}
			MapNode currNode = graph.get(curr);
			//explore each neighbor
			for (MapNode neighbor: currNode.getNeighbors()){
				GeographicPoint neighborLocation = neighbor.getLocation();
				if(!visited.contains(neighborLocation)){
					queue.add(neighborLocation);
					map.put(neighborLocation, curr);
					visited.add(neighborLocation);
					}
			}
			
		}
		//@return null if path is not found
		if(!map.keySet().contains(goal)){
			System.out.println("No Path Exists from " + start + " to " + goal);
			return null;
		}
		//store searched results
		bfsPath.put(searchTarget, getPath(start, goal, map));
        //return the path if it's found
		return getPath(start, goal, map);
	}
	
	/** Find the path from start to goal based on map generated by breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param map exploration map generated by breadth first search.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> getPath(GeographicPoint start, 
		     GeographicPoint goal, Map <GeographicPoint, GeographicPoint> map){
		GeographicPoint startPoint = goal;
		List<GeographicPoint> path = new LinkedList<GeographicPoint>();
		while(!startPoint.equals(start)){
			path.add(startPoint);
			startPoint = map.get(startPoint);
		}
		//add start point
		path.add(start);
		//reverse points generate sequence from start to goal
		Collections.reverse(path);
		return path;
	}
	
	
	

	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		int count =0;
		if (start == null || goal == null) {
			System.out.println("Invalid start or goal location");
			return null;
		}
		// check if path was found and stored 
		Map <GeographicPoint, GeographicPoint> searchTarget = new HashMap<>();
		if (dPath.containsKey(searchTarget)){
			return dPath.get(searchTarget);
		}
		//add variables queue to temporary store points, visited list to store visited points 
		//and map to store find points
		PriorityQueue <MapNode> queue = new PriorityQueue<>();
		List <GeographicPoint> visited = new ArrayList<GeographicPoint>();
		Map <GeographicPoint, GeographicPoint> map = new HashMap<GeographicPoint, GeographicPoint>();
		for (MapNode node: graph.values()){
			if(!node.getLocation().equals(start)){
				node.setDistance(Double.MAX_VALUE);
			}
		}
		queue.add(graph.get(start));
		visited.add(start);
		while(!queue.isEmpty()){
			GeographicPoint curr = queue.remove().getLocation();
			count ++;
			// Hook for visualization.  See writeup.
			nodeSearched.accept(curr);
			//stop when reaches goal
			if (goal.equals(curr)){
				break;
			}
			MapNode currNode = graph.get(curr);
			//calculate distance between currNode and start
			double currDist = currNode.getDistance();
			//calculate travel time between currNode and start
			//double currTime = currNode.getTravelTime();
			//explore each neighbor
			for (MapNode neighbor: currNode.getNeighbors()){
				GeographicPoint neighborLocation = neighbor.getLocation();
				if(!visited.contains(neighborLocation)){
					/**
					if(currTime + currNode.getEdgeLength(neighbor)/currNode.getEdgeSpeed(neighbor) < neighbor.getTravelTime()){
						neighbor.setTravelTime(currTime + currNode.getEdgeLength(neighbor)/currNode.getEdgeSpeed(neighbor));
					    queue.add(neighbor);
					    map.put(neighborLocation, curr);
					}
					*/
					if(currDist + currNode.getEdgeLength(neighbor) < neighbor.getDistance()){
						neighbor.setDistance(currDist + currNode.getEdgeLength(neighbor));
					    queue.add(neighbor);
					    map.put(neighborLocation, curr);
					}
					visited.add(neighborLocation);
					}
			}
			
		}
		System.out.println("Nodes visited using dijkstra: " + count);
		//@return null if path is not found
		if(!map.keySet().contains(goal)){
			System.out.println("No Path Exists from " + start + " to " + goal);
			return null;
		}
		//store searched results
		System.out.println(map);
		dPath.put(searchTarget, getPath(start, goal, map));
        //return the path if it's found
		return getPath(start, goal, map);
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
	    // TODO: Implement this method in WEEK 3
		int count = 0;
	    if (start == null || goal == null) {
		    System.out.println("Invalid start or goal location");
		    return null;
	    }
		// check if path was found and stored 
		Map <GeographicPoint, GeographicPoint> searchTarget = new HashMap<>();
		if (aStarPath.containsKey(searchTarget)){
			return aStarPath.get(searchTarget);
		}
	    //add variables queue to temporary store points, visited list to store visited points 
	   //and map to store find points
	    PriorityQueue <MapNode> queue = new PriorityQueue<>();
	    List <GeographicPoint> visited = new ArrayList<GeographicPoint>();
	    Map <GeographicPoint, GeographicPoint> map = new HashMap<GeographicPoint, GeographicPoint>();
	    for (MapNode node: graph.values()){
		    if(!node.getLocation().equals(start)){
			    node.setDistance(Double.MAX_VALUE);
			    node.setGDistance(Double.MAX_VALUE);
		    }
	    }
	    queue.add(graph.get(start));
	    visited.add(start);
	    while(!queue.isEmpty()){
		    GeographicPoint curr = queue.remove().getLocation();
		    count ++;
		    // Hook for visualization.  See writeup.
		    nodeSearched.accept(curr);
		   //stop when reaches goal
		   if (goal.equals(curr)){
			    break;
		    }
		    MapNode currNode = graph.get(curr);
		    //calculate distance between currNode and start
		   double currDist = currNode.getGDistance();

		    //explore each neighbor
		    for (MapNode neighbor: currNode.getNeighbors()){
			    GeographicPoint neighborLocation = neighbor.getLocation();
			    if(!visited.contains(neighborLocation)){
                   //gdistance -- actually found distance from start, distance-the predict distance from start to goal
				    if (currDist + currNode.getEdgeLength(neighbor)  < neighbor.getGDistance() ){
					    neighbor.setGDistance(currDist + currNode.getEdgeLength(neighbor));
					    neighbor.setDistance(neighborLocation.distance(goal) + neighbor.getGDistance());
				        queue.add(neighbor);
				        map.put(neighborLocation, curr);
				    }
				    visited.add(neighborLocation);
				    }
		    }
		
	    }
	    System.out.println("Nodes visited using A-Star: " + count);
	    //@return null if path is not found
	    if(!map.keySet().contains(goal)){
		    System.out.println("No Path Exists from " + start + " to " + goal);
		    return null;
	    }
		//store searched results
		aStarPath.put(searchTarget, getPath(start, goal, map));
	
        //return the path if it's found
	    return getPath(start, goal, map);
	
    }

	/** print the path from start to goal for debugging
	 * @param foundMap path extracted from map generated by breadth first search.
	 */
	public static void printMap(List<GeographicPoint> foundMap){
		for(GeographicPoint point: foundMap){
			System.out.println("->");
			System.out.println(point);
		}
		System.out.println("There are " + (foundMap.size() - 1) + " steps");
	}
	
	/** 
	 * print the simple test case
	 */
	public static void main(String[] args)
	{   
		/*
		//test simpletest map
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		System.out.println("Number of edges in graph: " + theMap.getNumEdges() + "\n");
		System.out.println("Number of nodes in graph: " + theMap.getNumVertices() + "\n");
		GeographicPoint start1 = new GeographicPoint(1.0, 1.0);
		GeographicPoint goal1 = new GeographicPoint(8.0, -1.0);
		List<GeographicPoint> map1 = theMap.bfs(start1, goal1);
		System.out.println("The path from (" + start1 + ") to (" + goal1 + ") is: ");
		printMap(map1);
		
		GeographicPoint start2 = new GeographicPoint(4.0, 2.0);
		GeographicPoint goal2 = new GeographicPoint(4.0, 0.0);
		List<GeographicPoint> map2 = theMap.bfs(start2, goal2);
		System.out.println("The path from (" + start2 + ") to (" + goal2 + ") is: ");
		printMap(map2);
		*/
		// You can use this method for testing.  
		
		//Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		//List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);
		System.out.println("The path from (" + start + ") to (" + end + ") is: ");
		printMap(route2);
		
		System.out.print("Making a new map...");
		MapGraph theSimpleMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theSimpleMap);
		GeographicPoint start1 = new GeographicPoint(7.0, 3.0);
		GeographicPoint goal1 = new GeographicPoint(4.0, -1.0);
		List<GeographicPoint> route1 = theSimpleMap.dijkstra(start1,goal1);
		List<GeographicPoint> route3 = theSimpleMap.aStarSearch(start1,goal1);
		System.out.println("The path from (" + start1 + ") to (" + goal1 + ") is searched by dijkstra: ");
		printMap(route1);
		System.out.println("The path from (" + start1 + ") to (" + goal1 + ") is searched by A-Star: ");
		printMap(route3);
		
		//quiz
		MapGraph theMapQuiz = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMapQuiz);
		System.out.println("DONE.");

		GeographicPoint startQuiz = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint endQuiz = new GeographicPoint(32.8660691, -117.217393);

		List<GeographicPoint> routeQuiz = theMap.dijkstra(startQuiz,endQuiz);
		List<GeographicPoint> route2Quiz = theMap.aStarSearch(startQuiz,endQuiz);
		printMap(routeQuiz);
		printMap(route2Quiz);
	}
	
}
