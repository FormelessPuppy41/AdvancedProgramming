

/**
 * Class that models arcs in the directed arcs. Stores both the end-points, as
 * well as the weight of the arc.
 * 
 * @author Bart van Rossum
 *
 * @param <V> the type of data associated with nodes in the graph
 */
public class DirectedGraphArc<V>
{
	private final V from;
	private final V to;
	private final double weight;

	/**
	 * Construct an arc of the graph
	 * 
	 * @param from   the origin of this arc
	 * @param to     the destination of this arc
	 * @param weight the weight of this arc
	 */
	public DirectedGraphArc(V from, V to, double weight)
	{
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	/**
	 * Used to retrieve the origin of this arc
	 * 
	 * @return the origin of this arc
	 */
	public V getFrom()
	{
		return from;
	}

	/**
	 * Used to retrieve the destination of this arc
	 * 
	 * @return the destination of this arc
	 */
	public V getTo()
	{
		return to;
	}

	/**
	 * Used to retrieve the weight of this arc
	 * 
	 * @return the weight of this arc
	 */
	public double getWeight()
	{
		return weight;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		DirectedGraphArc<?> other = (DirectedGraphArc<?>) obj;
		if (from == null)
		{
			if (other.from != null) return false;
		}
		else if (!from.equals(other.from)) return false;
		if (to == null)
		{
			if (other.to != null) return false;
		}
		else if (!to.equals(other.to)) return false;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "Arc [from=" + from + ", to=" + to + ", weight=" + weight + "]";
	}
}