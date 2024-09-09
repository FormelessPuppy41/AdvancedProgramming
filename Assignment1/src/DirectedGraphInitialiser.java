import java.util.List;


public class DirectedGraphInitialiser {
    protected final Sorter sorter;
    protected List<String[]> data;

    protected DirectedGraph<String> graph = new DirectedGraph<>();
    protected DirectedGraphArc<String> arcs;

    protected final String startNode;
    protected int indexStartNode;

    /*
     * Constructor for the DirectedGraphInitialiser class.
     * 
     * 
     */
    public DirectedGraphInitialiser(List<String[]> data, String startingNode, Sorter sorter) {
        this.data = data;

        this.startNode = startingNode;

        this.sorter = sorter;
        
        initialiseGraph();
    }

    public DirectedGraph<String> getGraph() {
        return this.graph;
    }

    public DirectedGraphArc<String> getArcs() {
        return this.arcs;
    }
    
    private void initialiseGraph() {
        // Check if the data is sorted
        //if (!this.sorter.isDataSorted(this.data)) {throw new IllegalStateException("Data must be sorted before initialising the graph.");}
        initialiseNodes();
        initialiseStartNode();
        initialiseArcs();
    }

    private void initialiseNodes() {
        for (int i = 1; i < this.data.size(); i++) {
            String nodeName = this.data.get(i)[0];
            //System.out.println(nodeName);
            this.graph.addNode(nodeName); // Add the movie name as a node

            //FIXME: Perhaps make the nodes unique by adding the starting time and duration to the node name? This results in non duplicates like 'Home - 7.9 ...' and 'Home - 4.6 ...'
        }
    }

    private void initialiseStartNode() {
        if (this.graph.getNodes().indexOf(this.startNode) == -1) {
            throw new IllegalArgumentException("The starting node must be in the data.");
        }
        this.indexStartNode = this.graph.getNodes().indexOf(this.startNode);
    }

    private void initialiseArcs() {
        // Loop through the data to add the arcs. Skip the movies that started before the starting movie.
        for (int i = this.indexStartNode; i < this.data.size(); i++) {
            // get the current node values
            String[] currentNodeRow = this.data.get(i);
            int currentStartTime = Integer.parseInt(currentNodeRow[3]);
            int currentDuration = Integer.parseInt(currentNodeRow[2]);


            // Loop through movies that started after the current movie (i+1).
            // FIXME: Is it not possible to search for the value that is first after "starting time + duration" of current movie?

            for (int j = i + 1; j < this.data.size(); j++) {
                // Get the next node values
                String[] nextNodeRow = this.data.get(j);
                int nextStartTime = Integer.parseInt(nextNodeRow[3]);
                float nextRating = Float.parseFloat(nextNodeRow[1]);
                
                // Check if the next movie can be watched after the current movie
                if (currentStartTime + currentDuration <= nextStartTime) {
                    this.graph.addArc(currentNodeRow[0], nextNodeRow[0], nextRating); // Add the arc
                }
            }
        }
    }
    
}
