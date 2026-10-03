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

package edu.virginia.sde.hw3;

import edu.virginia.sde.hw3.algorithms.ApportionmentMethod;
import edu.virginia.sde.hw3.algorithms.HamiltonMethod;
import edu.virginia.sde.hw3.algorithms.JeffersonMethod;
import edu.virginia.sde.hw3.formats.AlphabeticalFormat;
import edu.virginia.sde.hw3.formats.DisplayOrder;
import edu.virginia.sde.hw3.formats.PopulationFormat;
import edu.virginia.sde.hw3.formats.RepresentationFormat;
import edu.virginia.sde.hw3.io.CSVOutputFile;
import edu.virginia.sde.hw3.io.CSVInputFile;
import edu.virginia.sde.hw3.io.OutputSource;
import edu.virginia.sde.hw3.io.StateSource;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentsTest {
    public static final String TEST_CSV_LOCATION = "csv_test_files\\states.csv";

    @Test
    public void argumentsTest_inputFile_defaultReps_noOptions() {
        String resourceFilename = getResource(TEST_CSV_LOCATION);
        String[] args = {resourceFilename};
        Arguments arguments = new Arguments(args);

        assertEquals(Arguments.DEFAULT_REPRESENTATIVES, arguments.getTargetRepresentatives());

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(CSVInputFile.class, stateSource);
        String filename = ((CSVInputFile) stateSource).getCsvFile().getPath();
        assertTrue(filename.endsWith(TEST_CSV_LOCATION),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(JeffersonMethod.class, method);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(AlphabeticalFormat.class, format);

        assertTrue(arguments.getOutputFile().isEmpty());
    }

    @Test
    public void argumentsTest_inputFile_defaultReps_Hamilton() {
        String resourceFilename = getResource(TEST_CSV_LOCATION);
        String[] args = {resourceFilename, "--hamilton"};
        Arguments arguments = new Arguments(args);

        assertEquals(Arguments.DEFAULT_REPRESENTATIVES, arguments.getTargetRepresentatives());

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(CSVInputFile.class, stateSource);
        String filename = ((CSVInputFile) stateSource).getCsvFile().getPath();
        assertTrue(filename.endsWith(TEST_CSV_LOCATION),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(HamiltonMethod.class, method);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(AlphabeticalFormat.class, format);

        assertTrue(arguments.getOutputFile().isEmpty());
    }

    @Test
    public void argumentsTest_inputFile_repsSpecified_outPutFilePresent() {
        String resourceFilename = getResource(TEST_CSV_LOCATION);
        String[] args = {resourceFilename, "100", "--out", "output.csv"};
        Arguments arguments = new Arguments(args);

        assertEquals(100, arguments.getTargetRepresentatives());

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(CSVInputFile.class, stateSource);
        String filename = ((CSVInputFile) stateSource).getCsvFile().getPath();
        assertTrue(filename.endsWith(TEST_CSV_LOCATION),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(JeffersonMethod.class, method);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(AlphabeticalFormat.class, format);

        assertTrue(arguments.getOutputFile().isPresent());
        OutputSource outputFile = arguments.getOutputFile().get();
        assertInstanceOf(CSVOutputFile.class, outputFile);
        CSVOutputFile csvOutputFile = (CSVOutputFile) outputFile;
        assertEquals("output.csv", csvOutputFile.getOutputFilename());
    }

    @Test
    public void argumentsTest_inputFile_repsSpecified_populationFormat_descendingDefault() {
        String resourceFilename = getResource(TEST_CSV_LOCATION);
        String[] args = {resourceFilename, "100", "--population"};
        Arguments arguments = new Arguments(args);

        assertEquals(100, arguments.getTargetRepresentatives());

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(CSVInputFile.class, stateSource);
        String filename = ((CSVInputFile) stateSource).getCsvFile().getPath();
        assertTrue(filename.endsWith(TEST_CSV_LOCATION),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(JeffersonMethod.class, method);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(PopulationFormat.class, format);
        PopulationFormat populationFormat = (PopulationFormat) format;
        assertEquals(DisplayOrder.DESCENDING, populationFormat.getDisplayOrder());

        assertTrue(arguments.getOutputFile().isEmpty());
    }

    @Test
    public void argumentsTest_inputFile_defaultReps_populationFormat_ascendingSpecified() {
        String resourceFilename = getResource(TEST_CSV_LOCATION);
        String[] args = {resourceFilename, "--population", "--ascending"};
        Arguments arguments = new Arguments(args);

        assertEquals(Arguments.DEFAULT_REPRESENTATIVES, arguments.getTargetRepresentatives());

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(CSVInputFile.class, stateSource);
        String filename = ((CSVInputFile) stateSource).getCsvFile().getPath();
        assertTrue(filename.endsWith(TEST_CSV_LOCATION),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(JeffersonMethod.class, method);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(PopulationFormat.class, format);
        PopulationFormat populationFormat = (PopulationFormat) format;
        assertEquals(DisplayOrder.ASCENDING, populationFormat.getDisplayOrder());

        assertTrue(arguments.getOutputFile().isEmpty());
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
