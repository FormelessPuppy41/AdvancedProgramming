import java.util.List;


public class DirectedGraphInitialiser {
    protected final Sorter sorter;
    protected List<Movie> data;

    protected DirectedGraph<Movie> graph = new DirectedGraph<>();
    protected DirectedGraphArc<Movie> arcs;

    protected final Movie startNode;
    protected int indexStartNode;

    /*
     * Constructor for the DirectedGraphInitialiser class.
     * 
     * 
     */
    public DirectedGraphInitialiser(List<Movie> data, Movie startingNode, Sorter sorter) {
        this.data = data;
        this.startNode = startingNode;
        this.sorter = sorter;
        
        initialiseGraph();
    }

    public DirectedGraph<Movie> getGraph() {
        return this.graph;
    }

    public DirectedGraphArc<Movie> getArcs() {
        return this.arcs;
    }
    
    private void initialiseGraph() {
        System.out.println("-----------------");
        System.out.println("Initialising graph");
        initialiseNodes();
        initialiseStartNode();
        initialiseArcs();
        System.out.println("Graph initialised.");

    }

    private void initialiseNodes() {
        for (Movie movie : this.data) {
            this.graph.addNode(movie); // Add the movie name as a node
        }
    }

    private void initialiseStartNode() {
        if (!this.graph.getNodes().contains(this.startNode)) {
            throw new IllegalArgumentException("The starting node must be in the data.");
        }
    
        this.indexStartNode = this.graph.getNodes().indexOf(this.startNode);
    }
    

    private void initialiseArcs() {
        // Loop through the data to add arcs. Start from the index of the start node.
        for (int i = this.indexStartNode; i < this.data.size(); i++) {
            Movie currentNode = this.data.get(i);
            int currentStartTime = currentNode.getStartTime();
            int currentDuration = currentNode.getDuration();
            
            // FIXME: Is it perhaps possible to jump to the next movie that can be watched after the current movie? Instead of looping through all movies. 
            // e.g. starttime + duration is minimum time of next movie via binary search.
            // Loop through subsequent movies to add arcs
            for (int j = i + 1; j < this.data.size(); j++) {
                Movie nextNode = this.data.get(j);
                int nextStartTime = nextNode.getStartTime();
                double nextRating = nextNode.getRating();
    
                // Ensure next movie can be watched after the current movie
                if (currentStartTime + currentDuration <= nextStartTime) {
                    // Only add arc if both nodes are already in the graph
                    if (this.graph.getNodes().contains(currentNode) && this.graph.getNodes().contains(nextNode)) {
                        // Add the arc between current and next node
                        this.graph.addArc(currentNode, nextNode, nextRating);
                    } else {
                        System.out.println("Inarcs or outarcs do not contain the nodes: current-" + currentNode + ", next-" + nextNode);
                    }
                }
            }
        }
    }
    
}
