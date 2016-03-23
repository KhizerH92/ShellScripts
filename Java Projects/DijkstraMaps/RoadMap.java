import java.awt.Point;
import java.io.BufferedReader;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.datastructures.*;
import net.datastructures.AdjacencyMapGraph;

public class RoadMap{
	
	private Map<String, Vertex<City>> cityMap;
	private AdjacencyMapGraph<City, Highway> finalGraph;
	private Map<Edge<Highway>, Double> edgeWeights;
	
	public RoadMap(String cityFile, String linkFile) throws IOException{
		finalGraph = new AdjacencyMapGraph<City,Highway>(false);
		readCityFile(cityFile); //call the methods to make a map of cities and coordinates
		createEdges(linkFile); //call the method to get the map of all the Highways
	}
	
	//read the city file and figure out where the different cities are on the map given to us
	//here again the skeleton of the reader code was taken from the website cited below
	
	//@SuppressWarnings("finally")
	public Map<String, Vertex<City>> readCityFile(String cityFile) throws IOException{
		cityMap = new HashMap<String, Vertex<City>>(); 
		BufferedReader reader = null;
		String line = "";
		String splitUsing = ",";

		try {

			reader = new BufferedReader(new FileReader(cityFile));
			
			line = reader.readLine();
			while (line != null) {
				// use comma as separator
				String[] cityline = line.split(splitUsing);
				Point citypoint = new Point(Integer.parseInt(cityline[1]),Integer.parseInt(cityline[2]));
				City newCity = new City(cityline[0], citypoint);
				cityMap.put(cityline[0], finalGraph.insertVertex(newCity));
				//System.out.println(cityMap.toString());
				line = reader.readLine();
			}
			reader.close();
			
			return cityMap; //return the map of cities to where they are 

		} 
		catch (IOException e) {
			System.out.println("City-File not found"); //otherwise catch exception and let us know
			reader.close();							// that the city file wasn't found
			return null;
		} 
		//http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
		//Citing Sources
	}
	
	public Iterable<Vertex<City>> getVertices(){
		return finalGraph.vertices(); //getting the set of iterable vertices 
	}
	
	private Iterable<Edge<Highway>> getEdges(){
		return finalGraph.edges(); //getting the set of iterable edges
	}
	
	
	//figure out which city is currently under or close to where the cursor is clicking
	public Vertex<City> cityAt(Point d){
		
		Iterator<Vertex<City>> iter = getVertices().iterator();
		//make the method using 'for' loop
		Vertex<City> tempval = null;
		
		while(iter.hasNext()){
			Vertex<City> temp = iter.next();
			
			if(temp.getElement().isNear(d)){
				tempval = temp; //if there is a city close by it returns that
			}
		}
		return tempval; //otherwise just returns null
	}
	
	
	//read the link file and store the links as Highways that have information about the 
	//cities they are connecting and what the time and distance weights are for the connections
	//between the two cities.
	//the skeleton of the reader code was taken from the web cited below
	//@SuppressWarnings("finally")
	public AdjacencyMapGraph<City, Highway> createEdges(String linkFile) throws IOException{
		
		BufferedReader reader = null;
		String line = "";
		String splitUsing = ",";

		try {

			reader = new BufferedReader(new FileReader(linkFile));
			line = reader.readLine();
			while (line != null) {
			        // use comma as separator
				String[] cityedgeline = line.split(splitUsing);
				
				Vertex<City> city1 = cityMap.get(cityedgeline[0]); //get both cities that are linked
				Vertex<City> city2 = cityMap.get(cityedgeline[1]);
				double traveldist = Double.parseDouble(cityedgeline[2]); //get their distance weight
				int traveltime = 60*Integer.parseInt(cityedgeline[3])+
						Integer.parseInt(cityedgeline[4]); //calculate their time weight in minutes to 
															// be uniform
				Highway edge = new Highway(traveldist, traveltime); //create the Highway with all the info
				finalGraph.insertEdge(city1, city2, edge); //insert the edge with cities and the highway 
															//between them
				
				line = reader.readLine(); //advance
				}
			reader.close(); //close the reader 
			return finalGraph; //return the adjacency graph
		
			}
		
		catch (IOException e) { //catch exceptions and close the readers
			System.out.println("links-File not found");
			reader.close();
			return null;
		} 
		//http://www.mkyong.com/java/how-to-read-and-parse-csv-file-in-java/
		//Citing Sources 
		
	}
	
	//creating a map of all the edges with distance as the weight associated
	
	public Map<Edge<Highway>, Double> createDistMap(){
		Map<Edge<Highway>, Double> distedgeWeights = new HashMap<Edge<Highway>, Double>();
		Iterator<Edge<Highway>> edgeiter = getEdges().iterator();
		//make the method using 'for' loop
		while(edgeiter.hasNext()){
			Edge<Highway> tempedge = edgeiter.next();
			distedgeWeights.put(tempedge, tempedge.getElement().getTravelDist());
			}
		return distedgeWeights;
	}
	
	//creating a map of all the edges with time as the weight associated
	
	
	public Map<Edge<Highway>, Double> createTimeMap(){
		Map<Edge<Highway>, Double> timeedgeWeights = new HashMap<Edge<Highway>, Double>();
		Iterator<Edge<Highway>> edgeiter = getEdges().iterator();
		//make the method using 'for' loop
		while(edgeiter.hasNext()){
			Edge<Highway> tempedge = edgeiter.next();
			timeedgeWeights.put(tempedge, tempedge.getElement().getTravelTime());
			}
		return timeedgeWeights;
	}
	
	
	//most of the code done here is just a slight modification of the code given in the book
	//on page 660.
	public Map<Vertex<City>, Vertex<City>> shortestPathLength(boolean isDistance, Vertex<City> source){
		AdaptablePriorityQueue<Double,Vertex<City>> pq = new HeapAdaptablePriorityQueue<>();
		Map<Vertex<City>, Double> d = new HashMap<Vertex<City>, Double>();
		Map<Vertex<City>,Vertex<City>> Predecessors = new HashMap<>();
		Map<Vertex<City>, Entry<Double, Vertex<City>>> tokens = new HashMap<>();
		
		for(Vertex<City> vertex: getVertices()){
			if(vertex == source){
				d.put(vertex, (double) 0);
			}
			else{
				d.put(vertex, Double.MAX_VALUE);
			}
			tokens.put(vertex, pq.insert(d.get(vertex), vertex));
		}
		
		if(isDistance){ //based on the boolean we choose here which of the weights to use
			edgeWeights = createDistMap();
		}
		else{
			edgeWeights = createTimeMap();
		}
		
		while(!pq.isEmpty()){
			Vertex<City> city = pq.removeMin().getValue();
			
			for(Edge<Highway> e: finalGraph.outgoingEdges(city)){
				Vertex<City> v = finalGraph.opposite(city, e);


				
				if(d.get(city)+edgeWeights.get(e) < d.get(v)){
					d.put(v,d.get(city)+edgeWeights.get(e));
					pq.replaceKey(tokens.get(v), d.get(v));
					//made change here
					Predecessors.put(v, city);
					}
				}
			}
		//make the method using 'for' loop
		return Predecessors;
	}
	
}