import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Arrays;

/**
 * The Optimiser class is responsible for finding the optimal path through a DirectedGraph of Movies.
 * It can optimize for the shortest or longest path based on movie ratings and total duration constraints.
 * 
 * @author Berend Quist 611968bq
 */
public class Optimiser {
    protected final DirectedGraph<Movie> graph;
    protected final DirectedGraphArc<Movie> arcs;
    
    protected Double[] shortestPath;
    protected Double[] longestPath;

    protected List<Movie> previousNode;
    protected int[] totalTime;

    protected Movie startNode;
    protected int indexStartNode;

    protected final List<Movie> data;
    protected boolean dataSorted = false;
    protected final int numberOfNodes;

    protected int MAX_DURATION = 360;

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
        
        this.indexStartNode = this.graph.getNodes().indexOf(this.startNode);
        this.numberOfNodes = this.graph.getNodes().size();
        
        initialiseSequencialNodes();
        initialisePaths();
        initialiseTimes();
    }

    /**
     * Initializes the previousNode list with null values.
     */
    private void initialiseSequencialNodes() {
        this.previousNode = new ArrayList<>(Collections.nCopies(this.numberOfNodes, null));
    }

    /**
     * Initializes the shortestPath and longestPath arrays with default values.
     * Sets the path values for the starting node.
     */
    private void initialisePaths() {
        this.shortestPath = new Double[this.numberOfNodes];
        this.longestPath = new Double[this.numberOfNodes];

        Arrays.fill(this.shortestPath, Double.MAX_VALUE);
        Arrays.fill(this.longestPath, Double.MIN_VALUE);

        Double startRating = this.startNode.getRating();

        this.shortestPath[this.indexStartNode] = startRating;
        this.longestPath[this.indexStartNode] = startRating;
    }

    /**
     * Initializes the totalTime array with default values.
     */
    private void initialiseTimes() {
        this.totalTime = new int[this.numberOfNodes];
        Arrays.fill(this.totalTime, 0);
        this.totalTime[this.indexStartNode] = this.startNode.getDuration();
    }

    /**
     * Resets the starting node for path calculations and reinitializes paths and times.
     * 
     * @param newStartNode The new starting node.
     */
    public void setStartNode(Movie newStartNode) {
        this.startNode = newStartNode;
        this.indexStartNode = this.graph.getNodes().indexOf(newStartNode);

        initialisePaths();
        initialiseTimes(); 
        initialiseSequencialNodes();
    }

    /**
     * Finds the shortest or longest path from the starting node.
     * 
     * @param shortest If true, finds the shortest path, otherwise finds the longest path.
     * @param timeLimit If true, considers the time constraint (maximum duration).
     * @return An array representing the shortest or longest path distances.
     */
    private Double[] findPaths(boolean shortest, boolean timeLimit) {
        if (!timeLimit) {
            MAX_DURATION = Integer.MAX_VALUE;
        }

        for (Movie movie : this.graph.nodes) {
            int i = this.graph.nodes.indexOf(movie);
            
            if (i < this.indexStartNode) {
                continue;  // Skip nodes before the starting node
            }

            // Iterate over all arcs (edges) of the current node
            for (DirectedGraphArc<Movie> arc : this.graph.getOutArcs(movie)) {
                handleArcForPaths(i, arc, shortest);
            }
        }
        
        return shortest ? this.shortestPath : this.longestPath;
    }

    /**
     * Handles an arc for updating the shortest or longest path.
     * 
     * @param i The index of the current movie.
     * @param arc The arc representing the relationship to the next movie.
     * @param shortest If true, calculates the shortest path; otherwise, calculates the longest path.
     */
    private void handleArcForPaths(int i, DirectedGraphArc<Movie> arc, boolean shortest) {
        int j = this.graph.nodes.indexOf(arc.getTo());
        
        // Skip invalid arcs or nodes before the start node
        if (j < this.indexStartNode || !isArcValid(arc)) {
            return;
        }
        
        int totalDuration = arc.getTo().getStartTime() + arc.getTo().getDuration() - this.startNode.getStartTime();
        if (totalDuration > MAX_DURATION) {
            return;
        }

        if (shortest) {
            updateShortestPath(i, j, arc);
        } else {
            updateLongestPath(i, j, arc);
        }

        this.totalTime[j] = totalDuration;
    }

    /**
     * Checks if an arc is valid by comparing start and end times of movies.
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
        if (this.longestPath[j] < this.longestPath[i] + arc.getWeight()) {
            this.longestPath[j] = this.longestPath[i] + arc.getWeight();
            this.previousNode.set(j, this.graph.nodes.get(i));
        }
    }

    /**
     * Finds the shortest or longest path and returns the calculated path array.
     * 
     * @param shortestPath If true, finds the shortest path; otherwise, finds the longest path.
     * @return The array representing the shortest or longest path.
     */
    public Double[] findPath(boolean shortestPath, boolean timeLimit) {
        System.out.println("-----------------");
        System.out.println("Optimising movie schedule...");

        Double[] path = findPaths(shortestPath, timeLimit);

        System.out.println("-> Movie schedule optimised.");

        if (shortestPath) {
            logPath(shortestPath, path);
        } else {
            logPath(shortestPath, path);
        }

        return path;
    }

    /**
     * Logs the shortest or longest path to the console.
     * 
     * @param pathType The type of path ("shortest" or "longest").
     * @param path The calculated path array.
     */
    private void logPath(boolean shortestPath, Double[] path) {
        double value = shortestPath ? findMinValue(path) : findMaxValue(path);
        String pathType = shortestPath ? "shortest" : "longest";
        System.out.println("-> The " + pathType + " path is: " + value + ", ending at index: " + Arrays.asList(path).indexOf(value));
    }

    /**
     * Finds the optimal movie sequence, disregarding the starting index and testing all possible starting points.
     * The method evaluates both shortest and longest paths and stores the best sequence found.
     * 
     * @param shortestPath If true, finds the shortest path; otherwise, finds the longest path.
     * @param timeLimit If true, applies a time constraint for the maximum duration.
     * @return The list of movies in the optimal sequence.
     */
    public List<Movie> findPathDisregardStartingIndex(boolean shortestPath, boolean timeLimit) {
        System.out.println("-----------------");
        System.out.println("Finding the optimal movie sequence disregarding the starting index. That is, testing all possible starting points and finding the optimal.");
        HashMap<Movie, Double> movieScores = new HashMap<>();
        HashMap<Movie, List<Movie>> movieSequences = new HashMap<>();
    
        evaluateAllStartingNodes(shortestPath, timeLimit, movieScores, movieSequences);

        Movie optimalStartingMovie = getOptimalStartingMovie(movieScores);
        List<Movie> optimalSequence = movieSequences.get(optimalStartingMovie);

        logOptimalPathResult(optimalStartingMovie, optimalSequence, movieScores);

        // Set the optimal starting node and sequence for further usage
        this.previousNode = optimalSequence;
        this.startNode = optimalStartingMovie;

        return getOptimalMovieSequence();
    }

    /**
     * Evaluates the paths for all starting nodes in the graph, storing the best sequence and scores for each node.
     * 
     * @param shortestPath If true, evaluates paths for the shortest path; otherwise, evaluates the longest path.
     * @param timeLimit If true, applies a time constraint for the maximum duration.
     * @param movieScores A map to store the score (min or max path value) for each starting movie.
     * @param movieSequences A map to store the corresponding path sequence for each starting movie.
     */
    private void evaluateAllStartingNodes(
            boolean shortestPath, boolean timeLimit,
            HashMap<Movie, Double> movieScores,
            HashMap<Movie, List<Movie>> movieSequences
        ) {
        for (Movie movie : this.graph.nodes) {
            // Log progress for every 100th movie
            if (this.graph.nodes.indexOf(movie)%100 == 0) {
                System.out.println("---> Checking path for index: " + this.graph.nodes.indexOf(movie) + ", Movie: " + movie);
            }

            setStartNode(movie);  // Reset starting node to the current movie
            Double[] path = findPaths(shortestPath, timeLimit);  // Find the path for the current node

            movieSequences.put(movie, new ArrayList<>(this.previousNode));  // Store sequence
            
            // Store the score (min or max path value) for the current movie
            if (shortestPath) {
                movieScores.put(movie, findMinValue(path));
            } else {
                movieScores.put(movie, findMaxValue(path));
            }
        }
    }

    /**
     * Returns the movie node with the optimal score (min for shortest path, max for longest path).
     * 
     * @param movieScores A map containing the scores for each starting movie.
     * @return The movie node with the best score.
     */
    private Movie getOptimalStartingMovie(HashMap<Movie, Double> movieScores) {
        return Collections.max(movieScores.entrySet(), (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue())).getKey();
    }

    /**
     * Logs the results of the optimal path calculation, displaying the starting movie, sequence, and score.
     * 
     * @param optimalStartingMovie The movie node that was determined to be the best starting point.
     * @param optimalSequence The sequence of movies representing the optimal path.
     * @param movieScores A map containing the scores for each starting movie.
     */
    private void logOptimalPathResult(Movie optimalStartingMovie, List<Movie> optimalSequence, HashMap<Movie, Double> movieScores) {
        System.out.println("-----------------");
        System.out.println("The optimal starting movie is: " + optimalStartingMovie);
        //System.out.println("The optimal sequence of previous nodes is: " + optimalSequence);
        System.out.println("The optimal score is: " + movieScores.get(optimalStartingMovie));
    }

    /**
     * Retrieves the optimal movie sequence based on the previously calculated paths.
     * Ensures the starting node is included in the sequence and reverses the list for correct ordering.
     * 
     * @return A list of movies in the optimal sequence.
     */
    public List<Movie> getOptimalMovieSequence() {
        System.out.println("-----------------");
        System.out.println("Finding the optimal movie sequence");

        List<Movie> optimalSequence = buildOptimalSequence(this.previousNode);

        // Ensure the start node is part of the sequence -> add if absent
        if (!optimalSequence.contains(this.startNode)) {
            optimalSequence.add(this.startNode);
        }

        Collections.reverse(optimalSequence);  // Reverse to get correct order
        System.out.println("-> The optimal sequence is: " + optimalSequence);

        return optimalSequence;
    }

    /**
     * Builds the optimal movie sequence by following the previous nodes list, starting from the last valid node.
     * 
     * @param sequence The list of previous nodes representing the movie path.
     * @return A list of movies in the optimal sequence.
     */
    private List<Movie> buildOptimalSequence(List<Movie> sequence) {
        List<Movie> optimalSequence = new ArrayList<>();

        int index = findFirstNonNullIndex(sequence);
        if (index == -1) {
            return optimalSequence;  // No valid sequence found
        }

        System.out.println("-> First value found at index: " + index + ", Movie: " + this.graph.getNodes().get(index));

        buildSequenceFromIndex(sequence, optimalSequence, index);

        return optimalSequence;
    }

    /**
     * Finds the first non-null index in the previous nodes list, which marks the start of the sequence.
     * 
     * @param sequence The list of previous nodes representing the movie path.
     * @return The index of the first non-null element, or -1 if none is found.
     */
    private int findFirstNonNullIndex(List<Movie> sequence) {
        for (int i = sequence.size() - 1; i >= 0; i--) {
            if (sequence.get(i) != null) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Recursively builds the optimal sequence by following the path of previous nodes.
     * 
     * @param sequence The list of previous nodes.
     * @param optimalSequence The list where the optimal sequence will be stored.
     * @param startIndex The index to start building the sequence from.
     */
    private void buildSequenceFromIndex(List<Movie> sequence, List<Movie> optimalSequence, int startIndex) {
        int index = startIndex;
        while (index >= 0 && sequence.get(index) != null) { // Follow the path until null is reached or all nodes are visited
            Movie currentNode = this.graph.getNodes().get(index);
            optimalSequence.add(currentNode);
            index = this.graph.getNodes().indexOf(sequence.get(index));
        }
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
