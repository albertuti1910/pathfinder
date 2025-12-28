package software.ulpgc.pathfinder;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

public class GraphContainer {
	private final Graph<String, DefaultEdge> graph;

	public GraphContainer(Graph<String, DefaultEdge> graph) {
		this.graph = graph;
	}

	public List<String> shortestPathBetween(String source, String target) {
		if (notExists(source) || notExists(target))
			throw new IllegalArgumentException("Source or target vertex does not exist in the graph");
		return serialize(new DijkstraShortestPath<>(graph).getPath(source, target));
	}

	public double pathWeightBetween(String source, String target) {
		if (notExists(source) || notExists(target))
			throw new IllegalArgumentException("Source or target vertex does not exist in the graph");
		return calculate(new DijkstraShortestPath<>(graph).getPath(source, target));
	}

	private static List<String> serialize(GraphPath<String, DefaultEdge> path) {
		if (path == null)
			throw new IllegalArgumentException("No path exists between source and target");
		return path.getVertexList();
	}

	private static double calculate(GraphPath<String, DefaultEdge> path) {
		if (path == null)
			throw new IllegalArgumentException("No path exists between source and target");
		return path.getWeight();
	}

	private boolean notExists(String source) {
		return !graph.containsVertex(source);
	}
}