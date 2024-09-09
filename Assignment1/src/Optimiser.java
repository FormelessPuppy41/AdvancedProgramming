import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Optimiser {
    protected final DirectedGraph<String> graph;
    protected final DirectedGraphArc<String> arcs;
    
    protected Double[] shortestPath;
    protected List<String> previousNode;

    protected final String startNode;
    protected int indexStartNode;

    protected final List<String[]> data;
    protected boolean dataSorted = false;

    public Optimiser(List<String[]> data, DirectedGraph<String> graph, DirectedGraphArc<String> arcs, String startingNode) {
        this.data = data;
        
        this.graph = graph;
        this.arcs = arcs;
        this.startNode = startingNode;
        
        this.shortestPath = new Double[this.graph.nodes.size()];
        initialiseShortestPath(startingNode);
        this.previousNode = new ArrayList<>(this.graph.getNodes().size());
        initialisePreviousNode();
    }


    private void initialiseShortestPath(String startNode) {
        for (int i = 0; i < this.graph.getNodes().size(); i++) {
            if (i == this.indexStartNode) {
                this.shortestPath[i] = Double.parseDouble(this.data.get(indexStartNode)[1]);
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

    public void optimise() {
        // https://en.wikipedia.org/wiki/Topological_sorting#Application_to_shortest_path_finding

        for (int i = this.indexStartNode; i < this.graph.getNodes().size(); i++) {
            for (DirectedGraphArc<String> arc : this.graph.getOutArcs(this.graph.getNodes().get(i))) {
                int j = this.graph.getNodes().indexOf(arc.getTo());

                if (this.shortestPath[j] > this.shortestPath[i] + arc.getWeight()) {
                    this.shortestPath[j] = this.shortestPath[i] + arc.getWeight();
                    this.previousNode.set(j, this.graph.getNodes().get(i));
                }
            }
                
        }
    }
}
