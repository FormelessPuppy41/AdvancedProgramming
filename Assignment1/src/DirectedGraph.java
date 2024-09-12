

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple class that can be used to model directed graphs, where arbitrary types
 * of data are associated with the nodes of the graph. Note that this
 * implementation of an arc allows multiple copies of the same arc. As such, it
 * can not be assumed that graphs stored by instances of this class are simple;
 * they can be multigraphs.
 * 
 * It is assumed that the data type associated with the nodes has a consistent
 * implementation of hashCode() and equals().
 * 
 * @author Bart van Rossum
 * 
 * @param <V> the type of data associated with nodes in this graph
 */

public class DirectedGraph<V>
{
	protected final List<V> nodes;
	protected final List<DirectedGraphArc<V>> arcs;
	protected final Map<V, List<DirectedGraphArc<V>>> outArcs;
	protected final Map<V, List<DirectedGraphArc<V>>> inArcs;

	/**
	 * Creates an empty graph with no nodes or arcs.
	 */
	public DirectedGraph()
	{
		this.nodes = new ArrayList<>();
		this.arcs = new ArrayList<>();
		this.outArcs = new LinkedHashMap<>();
		this.inArcs = new LinkedHashMap<>();
	}

	/**
	 * Add a new node to this graph
	 * 
	 * @param node the data associated with the node that is added
	 * @throws IllegalArgumentException if the node is already in the graph or is
	 *                                  null
	 */
	public void addNode(V node) throws IllegalArgumentException
	{
		if (node == null)
		{
			throw new IllegalArgumentException("Unable to add null to the graph");
		}
		else if (inArcs.containsKey(node))
		{	
			throw new IllegalArgumentException("Unable to add the same node twice to the same graph");
		}
		else
		{
			nodes.add(node);
			inArcs.put(node, new ArrayList<>());
			outArcs.put(node, new ArrayList<>());
		}
	}

	/**
	 * Adds an arc to this graph.
	 * 
	 * @param from   the origin node of the arc to be added
	 * @param to     the destination of the arc to be added
	 * @param weight the weight of the arc
	 * @throws IllegalArgumentException if one of the end points is not in the graph
	 */
	public void addArc(V from, V to, double weight) throws IllegalArgumentException
	{
		if (!inArcs.containsKey(from) || !outArcs.containsKey(to))
		{
			throw new IllegalArgumentException("Unable to add arcs between nodes not in the graph");
		}
		DirectedGraphArc<V> a = new DirectedGraphArc<>(from, to, weight);
		outArcs.get(from).add(a);
		inArcs.get(to).add(a);
		arcs.add(a);
	}

	public void removeNode(V node) throws IllegalArgumentException
	{
		if (node == null)
		{
			throw new IllegalArgumentException("Unable to remove null from the graph");
		}
		else if (!nodes.contains(node))
		{
			throw new IllegalArgumentException("Unable to remove node that is not in the graph");
		}
		else
		{
			nodes.remove(node);
			for (DirectedGraphArc<V> arc : inArcs.get(node))
			{
				arcs.remove(arc);
				outArcs.get(arc.getFrom()).remove(arc);
			}
			inArcs.remove(node);
			for (DirectedGraphArc<V> arc : outArcs.get(node))
			{
				arcs.remove(arc);
				inArcs.get(arc.getTo()).remove(arc);
			}
			outArcs.remove(node);
		}
	}

	public void removeArc(DirectedGraphArc<V> arc) throws IllegalArgumentException
	{
		if (arc == null)
		{
			throw new IllegalArgumentException("Unable to remove null from the graph");
		}
		else if (!arcs.contains(arc))
		{
			throw new IllegalArgumentException("Unable to remove arc that is not in the graph");
		}
		else
		{
			arcs.remove(arc);
			outArcs.get(arc.getFrom()).remove(arc);
			inArcs.get(arc.getTo()).remove(arc);
		}
	}

	/**
	 * Gives a list of all nodes currently in the graph
	 * 
	 * @return the nodes in the graph
	 */
	public List<V> getNodes()
	{
		return Collections.unmodifiableList(nodes);
	}

	/**
	 * Gives a list of all arcs currently in the graph
	 * 
	 * @return the arcs in the graph
	 */
	public List<DirectedGraphArc<V>> getArcs()
	{
		return Collections.unmodifiableList(arcs);
	}

	/**
	 * Gives all the arcs that leave a particular node in the graph. Note that this
	 * list may be empty if no arcs leave this node.
	 * 
	 * @param node the node for which we want the leaving arcs
	 * @return a list of arcs leaving the node
	 * @throws IllegalArgumentException if the node is not in the graph
	 */
	public List<DirectedGraphArc<V>> getOutArcs(V node) throws IllegalArgumentException
	{
		if (!outArcs.containsKey(node))
		{
			throw new IllegalArgumentException("Unable to provide out-arcs for a node that is not in the graph");
		}
		return Collections.unmodifiableList(outArcs.get(node));
	}

	/**
	 * Gives all the arcs that enter a particular node in the graph. Note that this
	 * list may be empty if no arcs enter this node.
	 * 
	 * @param node the node for which we want the entering arcs
	 * @return a list of arcs entering the node
	 * @throws IllegalArgumentException if the node is not in the graph
	 */
	public List<DirectedGraphArc<V>> getInArcs(V node) throws IllegalArgumentException
	{
		if (!inArcs.containsKey(node))
		{
			throw new IllegalArgumentException("Unable to provide in-arcs for a node that is not in the graph");
		}
		return Collections.unmodifiableList(inArcs.get(node));
	}

	/**
	 * The total number of nodes in this graph
	 * 
	 * @return the number of nodes in the graph
	 */
	public int getNumberOfNodes()
	{
		return nodes.size();
	}

	/**
	 * The total number of arcs in this graph
	 * 
	 * @return the number of arcs in the graph
	 */
	public int getNumberOfArcs()
	{
		return arcs.size();
	}

	/**
	 * Gives the in-degree of a node in the graph.
	 * 
	 * @param node the node for which we want the in-degree
	 * @return the in-degree of the node
	 * @throws IllegalArgumentException if the node is not in the graph
	 */
	public int getInDegree(V node) throws IllegalArgumentException
	{
		return getInArcs(node).size();
	}

	/**
	 * Gives the out-degree of a node in the graph
	 * 
	 * @param node the node for which we want the out-degree
	 * @return the out-degree of the node
	 * @throws IllegalArgumentException if the node is not in the graph
	 */
	public int getOutDegree(V node) throws IllegalArgumentException
	{
		return getOutArcs(node).size();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 17;
		result = prime * result + ((arcs == null) ? 0 : arcs.hashCode());
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectedGraph<?> other = (DirectedGraph<?>) obj;
		if (arcs == null)
		{
			if (other.arcs != null)
				return false;
		}
		else if (!arcs.equals(other.arcs))
			return false;
		if (nodes == null)
		{
			if (other.nodes != null)
				return false;
		}
		else if (!nodes.equals(other.nodes))
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "DirectedGraph [nodes=" + nodes + ", arcs=" + arcs + "]";
	}
}
