package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.Test;

import software.ulpgc.pathfinder.FileGraphLoader;
import software.ulpgc.pathfinder.GraphContainer;

class FileGraphLoaderTest {

    @Test
    void loadValidFileCreatesGraph() throws Exception {
        File tempFile = File.createTempFile("graph", ".csv");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("""
                A,B,1
                B,C,2
                A,D,4
                C,D,1
                """);
        }

        FileGraphLoader loader = new FileGraphLoader(tempFile);
        GraphContainer container = loader.load();

        assertEquals(3.0, container.pathWeightBetween("A", "C"));
        assertEquals(3, container.shortestPathBetween("A", "C").size());
    }
    @Test
    void invalidLinesAreIgnored() throws Exception {
        File tempFile = File.createTempFile("graph", ".csv");

        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("""
                A,B,1
                INVALID_LINE
                C,D,X
                """);
        }

        FileGraphLoader loader = new FileGraphLoader(tempFile);
        GraphContainer container = loader.load();

        assertThrows(
                IllegalArgumentException.class,
                () -> container.shortestPathBetween("A", "C")
        );
    }
}

