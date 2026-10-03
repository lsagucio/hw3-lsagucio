/**
 * Starter code copyright Paul "Will" McBurney, 2024 - starter code visible at:
 * <a href="https://github.com/uva-cs3140-fa24/hw3-starter-repo">https://github.com/uva-cs3140-fa24/hw3-starter-repo</a>
 * <br><br>
 * This code may not be reposted, redistributed, or shared with others without explicit written consent
 * by Paul "Will" McBurney. This code may not be used by students in portfolios or job interviews.
 * <br><br>
 * For students, this code is considered protected by the University of Virginia Academic Honesty policy,
 * and sharing this code with anyone without explicit permission will be treated as an academic honesty
 * violation.
 * <br><br>
 * To other professors, if interested in sharing reusing this assignment, please contact Prof. McBurney via email.
 */

package edu.virginia.sde.hw3.io;

import edu.virginia.sde.hw3.State;
import edu.virginia.sde.hw3.States;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class CSVInputFileTest {
    @Test
    public void getStates() throws IOException {
        final String TEST_CSV_FILE = "csv_test_files/states.csv";
        CSVInputFile stateCSVFile = new CSVInputFile(getResource(TEST_CSV_FILE));

        States states = stateCSVFile.getStates();
        assertIterableEquals(new HashSet<>(Set.of(
                new State("Delaware", 989948),
                new State("Maryland", 6177224),
                new State("Pennsylvania", 13002700),
                new State("Virginia", 8631393),
                new State("West Virginia", 1793716)
        )), states.getStates());
    }

    @SuppressWarnings("SameParameterValue")
    private String getResource(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            return Objects.requireNonNull(classLoader.getResource(resourceName)).toURI().getPath();
        } catch (Exception e) {
            throw new RuntimeException("Error! The resource was unable to be loaded.");
        }
    }
}
