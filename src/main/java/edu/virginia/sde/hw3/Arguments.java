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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * This class handles parsing the program's command line arguments to configure the apportionment, including
 * identifying the input source, selecting the algorithm, number of representatives, and determining how output
 * is generated.
 */
public class Arguments {
    /** The position in the args array of the required input filename argument */
    public static final int INPUT_FILENAME_POSITION = 0;

    /** The position in the args array of the optional representatives argument */
    public static final int REPRESENTATIVES_ARGUMENT_POSITION = 1;

    /** the default number of seats in the US House of representatives */
    public static final int DEFAULT_REPRESENTATIVES = 435;

    /** the command line arguments passed to the program */
    private final List<String> args;

    /**
     * @param args the command line arguments to the program.
     * @throws IllegalArgumentException if arguments are empty
     */
    public Arguments(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Missing command line arguments! You must include at least one argument for the filename");
        }
        this.args = Arrays.asList(args);
    }

    /**
     * Returns the StateSupplier with access to the data source that the state population data is pulled from.
     * @return {@link StateSource}
     * @throws IllegalArgumentException if unsupported file format used
     */
    public StateSource getStateSupplier() {
        String filename = args.get(INPUT_FILENAME_POSITION);
        if (filename.toLowerCase().endsWith(".csv")) {
            return new CSVInputFile(filename);
        }
        if (filename.toLowerCase().endsWith(".xlsx")) {
            return new ExcelInputFile(filename);
        }

        throw new IllegalArgumentException(String.format("""
                Unsupported input file: %s
                    This program currently supports the following input file formats:
                        - .csv files
                        - .xlsx files
                """, filename));
    }

    /**
     * Returns the number of representatives to apportion for the House of Representatives. By default, the House
     * of Representatives has 435 representatives. This is an optional position argument.
     * @return number of representatives to allocate
     * @throws IllegalArgumentException if non-positive number of representatives passed in.
     * @see Arguments#REPRESENTATIVES_ARGUMENT_POSITION
     */
    public int getTargetRepresentatives() {
        if (args.size() < REPRESENTATIVES_ARGUMENT_POSITION + 1) {
            return DEFAULT_REPRESENTATIVES;
        }

        try {
            int targetRepresentatives = Integer.parseInt(args.get(REPRESENTATIVES_ARGUMENT_POSITION));
            if (targetRepresentatives <= 0) {
                throw new IllegalArgumentException(
                        "Invalid representative argument: the target representatives must be a positive integer"
                );
            }
            return targetRepresentatives;
        } catch (NumberFormatException e) {
            return DEFAULT_REPRESENTATIVES;
        }
    }

    /**
     * Gets the apportionment algorithm to use. By default, we use
     * {@link HuntingtonHillMethod the Huntington-Hill Method}, but the {@link HamiltonMethod} can be selected with
     * the --hamilton flag and the {@link JeffersonMethod} with the --jefferson flag in the command line arguments.
     * @return {@link ApportionmentMethod}
     */
    public ApportionmentMethod getApportionmentMethod() {
        if (args.contains("--hamilton")) {
            return new HamiltonMethod();
        }
        if (args.contains("--jefferson")) {
            return new JeffersonMethod();
        }
        return new HuntingtonHillMethod();
    }

    /**
     * Returns how to display the apportionment to the console. By default, states are displayed alphabetically.
     * The --population flag sorts by population, and the --benefit flag sorts by {@link BenefitFormat benefit}.
     * Both default to descending order, which can be changed with the --ascending flag.
     * @return {@link RepresentationFormat}
     */
    public RepresentationFormat getRepresentationFormat() {
        DisplayOrder displayOrder = args.contains("--ascending") ? DisplayOrder.ASCENDING : DisplayOrder.DESCENDING;
        if (args.contains("--population")) {
            return new PopulationFormat(displayOrder);
        }
        if (args.contains("--benefit")) {
            return new BenefitFormat(displayOrder);
        }
        return new AlphabeticalFormat();
    }

    /**
     * Returns an assembled Apportionment object using {@link StateSource}, {@link ApportionmentMethod}, and
     * the target number of representatives
     * @return {@link Apportionment}
     */
    public Apportionment getApportionment() {
        StateSource stateSource = getStateSupplier();
        ApportionmentMethod apportionmentMethod = getApportionmentMethod();
        int targetRepresentatives = getTargetRepresentatives();
        return new Apportionment(stateSource, apportionmentMethod, targetRepresentatives);
    }

    /**
     * Gets the OutputSource if specified by the user for outputting to a file or other resource.
     * @return an {@link Optional} of {@link OutputSource}
     */
    public Optional<OutputSource> getOutputFile() {
        int index = args.indexOf("--out");
        if (index == -1) {
            return Optional.empty();
        }
        if (args.size() <= index + 1) {
            throw new IllegalArgumentException("No file specified after --out argument");
        }

        String outputFilename = args.get(index + 1);
        if (outputFilename.endsWith(".csv")) {
            return Optional.of(new CSVOutputFile(outputFilename));
        }
        if (outputFilename.endsWith(".xlsx")) {
            return Optional.of(new ExcelOutputFile(outputFilename));
        }
        throw new IllegalArgumentException(String.format("""
                Unsupported output file: %s
                    This program currently supports the following output file formats:
                        - .csv files
                        - .xlsx files
                """, outputFilename));
    }
}
