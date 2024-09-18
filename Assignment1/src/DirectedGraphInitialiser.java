import java.util.List;

/**
 * A class responsible for initializing a directed graph of movies, where each movie is represented
 * as a node and arcs (edges) are created based on movie start times, duration, and rating.
 * 
 * The directed graph begins from a specified start node, and arcs are created between movies 
 * that can be watched in sequence with a maximum wait time of 15 minutes.
 * 
 * @author 611968bq - Berend Quist
 */
public class DirectedGraphInitialiser {
    protected final Sorter sorter;
    protected List<Movie> data;

    protected DirectedGraph<Movie> graph = new DirectedGraph<>();
    protected DirectedGraphArc<Movie> arcs;

    protected final Movie startNode;
    protected int indexStartNode;

    /**
     * Constructs a DirectedGraphInitialiser with a list of movies, a starting movie node, and a sorter.
     * It initializes the directed graph with nodes and arcs based on the movie schedule.
     * 
     * @param data        The list of movies to be added to the graph.
     * @param startingNode The movie node from which to start the graph.
     * @param sorter      The sorter used for sorting movies.
     */
    public DirectedGraphInitialiser(List<Movie> data, Movie startingNode, Sorter sorter) {
        this.data = data;
        this.startNode = startingNode;
        this.sorter = sorter;
        
        initialiseGraph();
    }

    /**
     * Returns the initialized directed graph containing movie nodes.
     * 
     * @return The directed graph of movies.
     */
    public DirectedGraph<Movie> getGraph() {
        return this.graph;
    }

    /**
     * Returns the arcs (edges) in the directed graph, which represent valid transitions between movies.
     * 
     * @return The arcs in the directed graph.
     */
    public DirectedGraphArc<Movie> getArcs() {
        return this.arcs;
    }
    
    /**
     * Initializes the directed graph by setting up nodes, defining the start node,
     * and creating arcs based on movie schedule constraints.
     */
    private void initialiseGraph() {
        System.out.println("-----------------");
        System.out.println("Initialising graph...");
        initialiseNodes();
        initialiseStartNode();
        initialiseArcs();
        System.out.println("Graph initialised.");
    }

    /**
     * Adds all movies from the data list to the directed graph as nodes.
     */
    private void initialiseNodes() {
        for (Movie movie : this.data) {
            this.graph.addNode(movie);
        }
    }

    /**
     * Sets the start node for the directed graph and validates that the starting movie exists in the graph.
     * 
     * @throws IllegalArgumentException if the starting movie node is not part of the movie data.
     */
    private void initialiseStartNode() {
        if (!this.graph.getNodes().contains(this.startNode)) {
            throw new IllegalArgumentException("The starting node must be in the data.");
        }
    
        this.indexStartNode = this.graph.getNodes().indexOf(this.startNode);
    }

    /**
     * Initializes arcs between the movie nodes in the graph by checking valid transitions.
     */
    private void initialiseArcs() {
        for (int i = this.indexStartNode; i < this.data.size(); i++) {
            Movie currentNode = this.data.get(i);
            createArcsForMovie(currentNode, i);
        }
    }

    /**
     * Creates arcs for a given movie by checking subsequent movies for valid transitions.
     * 
     * @param currentNode The movie for which to create arcs.
     * @param currentIndex The index of the current movie in the data list.
     */
    private void createArcsForMovie(Movie currentNode, int currentIndex) {
        for (int j = currentIndex + 1; j < this.data.size(); j++) {
            Movie nextNode = this.data.get(j);
            if (canWatchNext(currentNode, nextNode)) {
                addArcBetweenMovies(currentNode, nextNode);
            }
        }
    }

    /**
     * Checks if the next movie can be watched after the current movie, considering the movie's duration
     * and start time, and ensuring the gap between movies is at most 15 minutes.
     * 
     * @param currentNode The current movie node.
     * @param nextNode The next movie node to check.
     * @return True if the next movie can be watched after the current movie, false otherwise.
     */
    private boolean canWatchNext(Movie currentNode, Movie nextNode) {
        int currentEndTime = currentNode.getStartTime() + currentNode.getDuration();
        int nextStartTime = nextNode.getStartTime();
        return currentEndTime <= nextStartTime && currentEndTime + 15 >= nextStartTime;
    }

    /**
     * Adds an arc between the current and next movie nodes in the graph if they exist in the graph.
     * 
     * @param currentNode The current movie node.
     * @param nextNode The next movie node to which the arc should be added.
     */
    private void addArcBetweenMovies(Movie currentNode, Movie nextNode) {
        if (this.graph.getNodes().contains(currentNode) && this.graph.getNodes().contains(nextNode)) {
            this.graph.addArc(currentNode, nextNode, nextNode.getRating());
        } else {
            System.out.println("Nodes not found in the graph: current-" + currentNode + ", next-" + nextNode);
        }
    }
}

    /**
     * TODO: Improvements:
     * You can optimize the arc creation by using a more efficient way to find the next movie to watch,
     * possibly using a binary search to jump to the next valid movie based on its start time.
     * This would avoid checking all subsequent movies in a loop.
     */

