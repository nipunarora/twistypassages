package maze.g3.data;

import java.util.HashMap;

public class Graph {

	HashMap<Integer, Node> map;
	
	public void Grap(){
		map = new HashMap<Integer, Node>();
	}
	
	public void addNode(Node n){
		int key= map.size()+1;
		map.put(key, n);
	}
	
	public int sizeOfGraph(){
		return this.map.size();
	}

}
