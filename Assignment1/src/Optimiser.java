import java.util.List;
import java.util.ArrayList;

public class Optimiser {
    protected final DirectedGraph<Movie> graph;
    protected final DirectedGraphArc<Movie> arcs;
    
    protected Double[] shortestPath;
    protected Double[] longestPath;

    protected List<Movie> previousNode;

    protected final Movie startNode;
    protected int indexStartNode;

    protected final List<Movie> data;
    protected boolean dataSorted = false;

    public Optimiser(List<Movie> data, DirectedGraph<Movie> graph, DirectedGraphArc<Movie> arcs, Movie startingNode) {
        this.data = data;
        
        this.graph = graph;
        this.arcs = arcs;
        this.startNode = startingNode;
        
        this.shortestPath = new Double[this.graph.nodes.size()];
        initialiseShortestPath(startingNode.getTitle());
        
        this.previousNode = new ArrayList<>(this.graph.getNodes().size());
        initialisePreviousNode();
    }

    private void initialiseShortestPath(String startNode) {
        for (int i = 0; i < this.graph.getNodes().size(); i++) {
            if (i == this.indexStartNode) {
                this.shortestPath[i] = this.data.get(indexStartNode).getRating();
            } else {
                this.shortestPath[i] = Double.MAX_VALUE;
            }
        }
    }

    private void initialisePreviousNode() {
        for (int i = 0; i < this.graph.getNodes().size(); i++) {
            this.previousNode.add(null);
        }
    }

    private Double[] shortestPath() {
        // https://en.wikipedia.org/wiki/Topological_sorting#Application_to_shortest_path_finding

        for (int i = this.indexStartNode; i < this.graph.getNodes().size(); i++) {
            for (DirectedGraphArc<Movie> arc : this.graph.getOutArcs(this.graph.getNodes().get(i))) {
                int j = this.graph.getNodes().indexOf(arc.getTo());

                if (this.shortestPath[j] > this.shortestPath[i] + arc.getWeight()) {
                    this.shortestPath[j] = this.shortestPath[i] + arc.getWeight();
                    this.previousNode.set(j, this.graph.getNodes().get(i));
                } 
            }
                
        }
        return this.shortestPath;
    }

    private Double[] longestPath() {
        // https://en.wikipedia.org/wiki/Topological_sorting#Application_to_shortest_path_finding

        for (int i = this.indexStartNode; i < this.graph.getNodes().size(); i++) {
            for (DirectedGraphArc<Movie> arc : this.graph.getOutArcs(this.graph.getNodes().get(i))) {
                int j = this.graph.getNodes().indexOf(arc.getTo());

                if (this.longestPath[j] < this.longestPath[i] + arc.getWeight()) {
                    this.longestPath[j] = this.longestPath[i] + arc.getWeight();
                    this.previousNode.set(j, this.graph.getNodes().get(i));
                } 
            }
                
        }
        return this.longestPath;
    }

    public Double[] optimise(boolean shortestPath) {
        // https://en.wikipedia.org/wiki/Topological_sorting#Application_to_shortest_path_finding

        if (shortestPath) {
            return shortestPath();
        } else {
            return longestPath();
        }
    }

    public List<Movie> getOptimalMovieSequence() {
        if (this.previousNode == null) {
            throw new IllegalArgumentException("The previous node list is empty.");
        } else {
        List<Movie> optimalSequence = new ArrayList<>();
        int i = this.graph.getNodes().indexOf(this.startNode);

        while (this.previousNode.get(i) != null) {
            optimalSequence.add(this.graph.getNodes().get(i));
            i = this.graph.getNodes().indexOf(this.previousNode.get(i));
        }
        
        return optimalSequence;
        }
    }
}
