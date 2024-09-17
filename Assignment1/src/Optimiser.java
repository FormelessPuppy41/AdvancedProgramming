import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

/**
 * Optimiser class is responsible for finding the optimal path through a DirectedGraph of Movies.
 * The optimisation can be for the shortest or longest path based on movie ratings.
 * 
 * @author Berend Quist 611968bq
 */
public class Optimiser {
    protected final DirectedGraph<Movie> graph;
    protected final DirectedGraphArc<Movie> arcs;
    
    protected Double[] shortestPath;
    protected Double[] longestPath;

    protected List<Movie> previousNode;
    protected List<Movie> nextNode;

    protected final Movie startNode;
    protected int indexStartNode;

    protected final List<Movie> data;
    protected boolean dataSorted = false;

    /**
     * Constructor for the Optimiser class.
     * 
     * @param data The list of movies (nodes) in the graph.
     * @param graph The directed graph of movies.
     * @param arcs The arcs representing relationships (edges) between movies.
     * @param startingNode The starting movie node for optimisation.
     */
    public Optimiser(List<Movie> data, DirectedGraph<Movie> graph, DirectedGraphArc<Movie> arcs, Movie startingNode) {
        this.data = data;
        this.graph = graph;
        this.arcs = arcs;
        this.startNode = startingNode;
        
        this.previousNode = new ArrayList<>(this.graph.getNodes().size());
        this.nextNode = new ArrayList<>(this.graph.getNodes().size());

        this.indexStartNode = this.graph.getNodes().indexOf(this.startNode);
        
        initialiseSequencialNodes();
        initialisePaths();
    }

    /**
     * Initializes the previousNode and nextNode lists with null values.
     */
    private void initialiseSequencialNodes() {
        for (int i = 0; i < this.graph.getNodes().size(); i++) {
            this.previousNode.add(i, null);
            this.nextNode.add(i, null);
        }
    }

    /**
     * Initializes the shortestPath and longestPath arrays with default values.
     * Sets the path values for the starting node.
     */
    private void initialisePaths() {
        this.shortestPath = new Double[this.graph.nodes.size()];
        this.longestPath = new Double[this.graph.nodes.size()];

        Arrays.fill(this.shortestPath, Double.MAX_VALUE);
        Arrays.fill(this.longestPath, Double.MIN_VALUE);

        Double rating = this.data.get(indexStartNode).getRating();

        this.shortestPath[this.indexStartNode] = rating;
        this.longestPath[this.indexStartNode] = rating;
    }

    /**
     * Finds either the shortest or longest path from the starting node.
     * 
     * For the mathematical source: https://en.wikipedia.org/wiki/Topological_sorting#Application_to_shortest_path_finding
     * 
     * @param shortest If true, finds the shortest path, otherwise finds the longest path.
     * @return The array representing the shortest or longest path distances.
     */
    public Double[] findPath(boolean shortest) {
        for (Movie movie : this.graph.nodes) {
            int i = this.graph.nodes.indexOf(movie);
            
            // Skip the nodes before the starting node
            if (i < this.indexStartNode) {
                continue;
            }

            // Loop through the arcs from the current node
            for (DirectedGraphArc<Movie> arc : this.graph.getOutArcs(movie)) {
                int j = this.graph.nodes.indexOf(arc.getTo());
                
                // Skip the nodes before the starting node
                if (j < this.indexStartNode || !isArcValid(arc)) {
                    continue;
                }
                
                // Skip the nodes that are before the end time of the starting node
                if (this.startNode.getStartTime() + this.startNode.getDuration() >= arc.getTo().getStartTime()) {
                    continue;
                }
                
                if (shortest) {
                    // Shortest path: Change the shortest path to j, to the path to i + arc(i, j) if the current path to j is greater  then the path to i + arc(i, j).
                    // That is, minimize total length.
                    updateShortestPath(i,j,arc);
                } else {
                    // Longest path: Change the longest path to j, to the path to i + arc(i, j) if the current path to j is less than the path to i + arc(i, j).
                    // That is, maximize total length.
                    updateLongestPath(i,j,arc);
                }
            }
        }

        // Return the shortest or longest path
        return shortest ? this.shortestPath : this.longestPath;
    }

    /**
     * Checks if an arc is valid by comparing start and end times of movies to the starting node.
     * 
     * That is, StartNode.starttime + StartNode.duration < Arc.to.starttime.
     * 
     * @param arc The directed graph arc to be checked.
     * @return true if the arc is valid, false otherwise.
     */
    private boolean isArcValid(DirectedGraphArc<Movie> arc) {
        return this.startNode.getStartTime() + this.startNode.getDuration() < arc.getTo().getStartTime();
    }

    /**
     * Updates the shortest path between two nodes.
     * 
     * @param i The index of the starting node.
     * @param j The index of the target node.
     * @param arc The directed graph arc between nodes.
     */
    private void updateShortestPath(int i, int j, DirectedGraphArc<Movie> arc) {
        // Update the shortest path to j, to the path to i + arc(i, j) if the current path to j is greater than the path to i + arc(i, j).
        // That is, minimize total length.
        if (this.shortestPath[j] > this.shortestPath[i] + arc.getWeight()) {
            this.shortestPath[j] = this.shortestPath[i] + arc.getWeight();
            this.previousNode.set(j, this.graph.nodes.get(i));
        }
    }

    /**
     * Updates the longest path between two nodes.
     * 
     * @param i The index of the starting node.
     * @param j The index of the target node.
     * @param arc The directed graph arc between nodes.
     */
    private void updateLongestPath(int i, int j, DirectedGraphArc<Movie> arc) {
        // Update the longest path to j, to the path to i + arc(i, j) if the current path to j is less than the path to i + arc(i, j).
        // That is, maximize total length.
        if (this.longestPath[j] < this.longestPath[i] + arc.getWeight()) {
            this.longestPath[j] = this.longestPath[i] + arc.getWeight();
            this.previousNode.set(j, this.graph.nodes.get(i));
        }
    }

    /**
     * Optimises the movie sequence by finding either the shortest or longest path.
     * 
     * @param shortestPath If true, finds the shortest path; otherwise, finds the longest path.
     * @return The array representing the shortest or longest path.
     */
    public Double[] optimise(boolean shortestPath) {
        System.out.println("-----------------");
        System.out.println("Optimising movie schedule");

        // Find the shortest or longest path
        Double[] path = findPath(shortestPath);

        // Log the shortest or longest path
        if (shortestPath) {
            double minValue = findMinValue(path);
            System.out.println("The shortest path is: " + minValue + ", at index: " + Arrays.asList(path).indexOf(findMinValue(path)));
        } else {
            double maxValue = findMaxValue(path);
            System.out.println("The longest path is: " + maxValue + ", at index: " + Arrays.asList(path).indexOf(findMaxValue(path)));
        }

        return path;
    }

    /**
     * Retrieves the optimal movie sequence based on the previously calculated paths.
     * 
     * @param previousOrNext If true, uses previousNode list; if false, uses nextNode list.
     * @param pathIndex The index of the starting node for the sequence.
     * @return A list of movies in the optimal sequence.
     */

    public List<Movie> getOptimalMovieSequence(boolean previousOrNext, int pathIndex) {
        System.out.println("-----------------");
        System.out.println("Finding the optimal movie sequence");
        
        // Get the correct sequence depending on the previousOrNext input
        List<Movie> sequence = previousOrNext ? this.previousNode : this.nextNode;

        validateSequence();

        // Find the optimal sequence
        List<Movie> optimalSequence = buildOptimalSequence(sequence, pathIndex);

        // Add the starting node to the sequence
        if (!optimalSequence.contains(this.startNode)) {
            optimalSequence.add(this.startNode);
        }

        // Reverse the sequence -> start from the starting node and go down the path.
        Collections.reverse(optimalSequence);
        System.out.println("The optimal sequence is: " + optimalSequence.toString());

        return optimalSequence;
    }

    /**
     * Validates the sequence list to ensure it's not null or empty.
     * 
     * @param sequence The sequence to validate.
     */
    private void validateSequence() {
        if (this.previousNode == null) {
            throw new IllegalArgumentException("The sequence is empty. (null)");
        } else if (this.previousNode.isEmpty()) {
            throw new IllegalArgumentException("The sequence is empty. (isEmpty = True)");
        }
    }

    /**
     * Builds the optimal movie sequence from the given path.
     * 
     * @param sequence The list of previous or next nodes.
     * @param pathIndex The starting index of the path.
     * @return A list of movies in the optimal sequence.
     */
    private List<Movie> buildOptimalSequence(List<Movie> sequence, int pathIndex) {
        List<Movie> optimalSequence = new ArrayList<>();
        int currentIndex = pathIndex;

        // Add the starting node to the optimal sequence, that is the index with the greatest or smallest value.
        optimalSequence.add(this.graph.getNodes().get(pathIndex));

        // Loop through the sequence to find the optimal path. Starting from the pathIndex.
        while (currentIndex != -1 && currentIndex > this.indexStartNode + 1) {
            // If the sequence does not contain the current index, deflate the index.
            if (sequence.get(currentIndex) == null) {
                currentIndex = currentIndex - 1;
                continue;
            }
            // Add the current node to the optimal sequence and update the currentIndex to the next previous movie.
            Movie currentNode = this.graph.getNodes().get(currentIndex);
            optimalSequence.add(currentNode);
            currentIndex = this.graph.getNodes().indexOf(sequence.get(currentIndex));
        }

        // If the optimal sequence is empty, throw an exception.
        if (optimalSequence.isEmpty()) {
            throw new IllegalArgumentException("No valid path found from the starting node.");
        }

        return optimalSequence;
    }

    /**
     * Finds the minimum value in a Double array.
     * 
     * @param array The array to search.
     * @return The minimum value.
     */
    private static double findMinValue(Double[] array) {
        double minValue = Double.MAX_VALUE;
        for (double value : array) {
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    /**
     * Finds the maximum value in a Double array.
     * 
     * @param array The array to search.
     * @return The maximum value.
     */
    private static double findMaxValue(Double[] array) {
        double maxValue = Double.MIN_VALUE;
        for (double value : array) {
            if (value > maxValue) {
                maxValue = value;
            }
        }
        return maxValue;
    }
}
