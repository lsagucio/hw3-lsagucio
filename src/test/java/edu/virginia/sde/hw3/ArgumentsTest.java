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
import edu.virginia.sde.hw3.algorithms.HuntingtonHillMethod;
import edu.virginia.sde.hw3.algorithms.JeffersonMethod;
import edu.virginia.sde.hw3.formats.AlphabeticalFormat;
import edu.virginia.sde.hw3.formats.BenefitFormat;
import edu.virginia.sde.hw3.formats.DisplayOrder;
import edu.virginia.sde.hw3.formats.PopulationFormat;
import edu.virginia.sde.hw3.formats.RepresentationFormat;
import edu.virginia.sde.hw3.io.CSVOutputFile;
import edu.virginia.sde.hw3.io.CSVInputFile;
import edu.virginia.sde.hw3.io.ExcelInputFile;
import edu.virginia.sde.hw3.io.ExcelOutputFile;
import edu.virginia.sde.hw3.io.OutputSource;
import edu.virginia.sde.hw3.io.StateSource;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class ArgumentsTest {
    public static final String TEST_CSV_LOCATION = "csv_test_files/states.csv";

    /** The test CSV location using the platform's file separator, for comparing against {@link File#getPath()} */
    private static final String TEST_CSV_PATH = TEST_CSV_LOCATION.replace('/', File.separatorChar);

    @Test
    public void argumentsTest_inputFile_defaultReps_noOptions() {
        String resourceFilename = getResource(TEST_CSV_LOCATION);
        String[] args = {resourceFilename};
        Arguments arguments = new Arguments(args);

        assertEquals(Arguments.DEFAULT_REPRESENTATIVES, arguments.getTargetRepresentatives());

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(CSVInputFile.class, stateSource);
        String filename = ((CSVInputFile) stateSource).getCsvFile().getPath();
        assertTrue(filename.endsWith(TEST_CSV_PATH),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(HuntingtonHillMethod.class, method);

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
        assertTrue(filename.endsWith(TEST_CSV_PATH),
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
        assertTrue(filename.endsWith(TEST_CSV_PATH),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(HuntingtonHillMethod.class, method);

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
        assertTrue(filename.endsWith(TEST_CSV_PATH),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(HuntingtonHillMethod.class, method);

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
        assertTrue(filename.endsWith(TEST_CSV_PATH),
                "Bad filename: " + filename);

        ApportionmentMethod method = arguments.getApportionmentMethod();
        assertInstanceOf(HuntingtonHillMethod.class, method);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(PopulationFormat.class, format);
        PopulationFormat populationFormat = (PopulationFormat) format;
        assertEquals(DisplayOrder.ASCENDING, populationFormat.getDisplayOrder());

        assertTrue(arguments.getOutputFile().isEmpty());
    }

    @Test
    public void argumentsTest_jeffersonFlag() {
        String[] args = {getResource(TEST_CSV_LOCATION), "--jefferson"};
        Arguments arguments = new Arguments(args);

        assertInstanceOf(JeffersonMethod.class, arguments.getApportionmentMethod());
        assertInstanceOf(AlphabeticalFormat.class, arguments.getRepresentationFormat());
    }

    @Test
    public void argumentsTest_benefitFormat_descendingDefault() {
        String[] args = {getResource(TEST_CSV_LOCATION), "--benefit"};
        Arguments arguments = new Arguments(args);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(BenefitFormat.class, format);
        assertEquals(DisplayOrder.DESCENDING, ((BenefitFormat) format).getDisplayOrder());
    }

    @Test
    public void argumentsTest_benefitFormat_ascendingSpecified() {
        String[] args = {getResource(TEST_CSV_LOCATION), "--benefit", "--ascending"};
        Arguments arguments = new Arguments(args);

        RepresentationFormat format = arguments.getRepresentationFormat();
        assertInstanceOf(BenefitFormat.class, format);
        assertEquals(DisplayOrder.ASCENDING, ((BenefitFormat) format).getDisplayOrder());
    }

    @Test
    public void argumentsTest_excelInput_excelOutput(@TempDir Path tempDir) throws IOException {
        File inputFile = tempDir.resolve("states.xlsx").toFile();
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(inputFile)) {
            workbook.createSheet();
            workbook.write(outputStream);
        }

        String[] args = {inputFile.getPath(), "--out", "output.xlsx"};
        Arguments arguments = new Arguments(args);

        StateSource stateSource = arguments.getStateSupplier();
        assertInstanceOf(ExcelInputFile.class, stateSource);
        assertEquals(inputFile.getPath(), ((ExcelInputFile) stateSource).getExcelFile().getPath());

        assertTrue(arguments.getOutputFile().isPresent());
        OutputSource outputFile = arguments.getOutputFile().get();
        assertInstanceOf(ExcelOutputFile.class, outputFile);
        assertEquals("output.xlsx", ((ExcelOutputFile) outputFile).getOutputFilename());
    }

    @Test
    public void argumentsTest_unsupportedOutputFile() {
        String[] args = {getResource(TEST_CSV_LOCATION), "--out", "output.txt"};
        Arguments arguments = new Arguments(args);

        assertThrows(IllegalArgumentException.class, arguments::getOutputFile);
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
