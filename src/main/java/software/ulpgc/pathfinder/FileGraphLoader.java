package software.ulpgc.pathfinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class FileGraphLoader implements GraphLoader {
	private final File file;
	private final Graph<String, DefaultEdge> graph;

	public FileGraphLoader(File file) {
		this.file = file;
		this.graph = new SimpleWeightedGraph<>(DefaultEdge.class);
	}

	@Override
	public GraphContainer load() throws IOException {
		deserialize(Files.readAllLines(file.toPath()));
		return new GraphContainer(graph);
	}

	private void deserialize(List<String> lines) throws IOException {
		for (int i = 0; i < lines.size(); i++)
			try {
				add(lines.get(i).split(","));
			}
			catch (ParseException e) {
				System.err.printf("Invalid line at %d %s%n", i, lines.get(i));
			}

	}

	private void add(String[] parts) throws ParseException {
		if (parts.length != 3) throw new ParseException();
		add(parts[0], parts[1], toDouble(parts[2]));
	}

	private void add(String source, String target, double weight) {
		graph.addVertex(source);
		graph.addVertex(target);
		graph.addEdge(source, target);
		graph.setEdgeWeight(source, target, weight);

	}

	private double toDouble(String value) throws ParseException {
		try {
			return Double.parseDouble(value);
		}
		catch (NumberFormatException e) {
			throw new ParseException();
		}
	}

	public static class ParseException extends Exception {

	}


}

