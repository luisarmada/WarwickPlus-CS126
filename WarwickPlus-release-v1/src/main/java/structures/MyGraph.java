package structures;

public class MyGraph{

    // GraphNode class stores information about a single node
    // This includes it's own ID, and an arrayList of adjacent nodes and their IDs
    class GraphNode{

        public int selfID;
        ArrayList<Integer> adjList;
    
        public GraphNode(int id){
            selfID = id;
            adjList = new ArrayList<>();
        }
    
        public void addAdjacency(int id){
        if(!adjList.contains(id))
            adjList.add(id);
        }
    
        public void remove(int id){
            adjList.removeIndex(adjList.indexOf(id));
        }
    
    }

    // Hashmap containing a node's ID as a key and it's GraphNode class
    HashMap<Integer, GraphNode> nodeMap;
    

    public MyGraph(){
        nodeMap = new HashMap<Integer, GraphNode>();
    }

    public void addNode(int id, int[] adjNodes){
        if(nodeMap.containsKey(id)) return; // If node already exists, skip
        
        // Create new GraphNode and put into hashmap
        GraphNode node = new GraphNode(id);

        nodeMap.put(id, node);

        // Iterate through adj nodes and add to adj list
        // Also add self to other nodes' adj list
        for(int i = 0; i < adjNodes.length; i++){
            int currID = adjNodes[i];
            if(currID == id) continue; //skip self
            node.addAdjacency(currID);

            if(nodeMap.containsKey(currID)){
                nodeMap.get(currID).addAdjacency(id);
            } else {
                GraphNode newNode = new GraphNode(currID);

                newNode.addAdjacency(id);

                nodeMap.put(currID, newNode);
            }
        }

    }

    public void removeNode(int id){
        if(!nodeMap.containsKey(id)) return; // If doesnt exist, skip

        GraphNode node = new GraphNode(id);

        // Remove from adjacent nodes' adjList
        for(int i = 0; i < node.adjList.size(); i++){
            ArrayList<Integer> otherList = nodeMap.get(node.adjList.get(i)).adjList;
            if(otherList.contains(id)){
                otherList.removeIndex(otherList.indexOf(id));
            }
        }

        nodeMap.put(id, null); // remove from hashmap
        
    }

    public int[] findDistance(int start, int end){ // bfs

        if(start == end) return new int[]{end};

        Queue<Integer> queue = new Queue<>();
        ArrayList<Integer> visited = new ArrayList<>();
        HashMap<Integer, Integer> relation = new HashMap<>();
        
        queue.enqueue(start);
        visited.add(start);
        relation.put(start, -1);

        while(!queue.isEmpty()){
            int node = queue.dequeue();

            GraphNode gNode = nodeMap.get(node);

            for(int i = 0; i < gNode.adjList.size(); i++){ // Start at a node and iterate through all adjacent nodes (bfs)
                int adjacentNode = gNode.adjList.get(i);
                if(!visited.contains(adjacentNode)){

                    relation.put(adjacentNode, node);

                    if(adjacentNode == end){ // Keep continuing until end node is found
                        
                        // Refer to hashmap to find parent node, keep backtracking until start node is reached
                        ArrayList<Integer> path = new ArrayList<>();
                        int currentNodeInPath = end;
                        while(currentNodeInPath != start){
                            path.add(currentNodeInPath);
                            if(relation.get(currentNodeInPath) == null) break;
                            currentNodeInPath = relation.get(currentNodeInPath);
                        }

                        // Reverse path and convert to array
                        int[] returnArr = new int[path.size()];
                        for(int j = returnArr.length - 1; j >= 0; j--){
                            returnArr[returnArr.length - (j+1)] = path.get(j);
                        }
                        return returnArr;
                    } 

                
                    visited.add(adjacentNode);
                    queue.enqueue(adjacentNode);
                }
            }
        }

        return new int[0]; // No connection found
    }

}
