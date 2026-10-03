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


import java.util.ArrayList;
import java.util.List;

/**
 * Represents the column headings CSV file for states. Specifically, this file must contain the column headers:
 * <ul>
 *     <li>"State" - the column containing the names of the states</li>
 *     <li>"Population" - the column containing the populations of each state</li>
 * </ul>
 * See sample_inputs/part1_input.csv and sample_inputs/part2_input.csv for examples
 */
public class CSVInputHeadings {
    /** Required Column Header for State Name */
    public final static String STATE_NAME_COLUMN_LABEL = "State";

    /** Required Column Header for State Population */
    public final static String STATE_POPULATION_COLUMN_LABEL = "Population";

    private final int numberOfColumns;

    /** The column index of the "State" column containing state names */
    private final int stateNameColumnIndex;

    /** The column index of the "Population" column containing the state populations */
    private final int statePopulationColumnIndex;

    /** number of columns in the CSV file */
    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    /**
     * Returns the index of the state name column in a CSV file.
     * @return the index of the state name column
     */
    public int getStateNameColumnIndex() {
        return stateNameColumnIndex;
    }

    /**
     * Returns the index of the state population column in a CSV file.
     * @return the index of the state population column
     */
    public int getStatePopulationColumnIndex() {
        return statePopulationColumnIndex;
    }

    /**
     * Constructs an instance of CSVHeadings from the header row of an input CSV File
     * @param headerRow the first row of the input CSV file
     * @return a {@link CSVInputHeadings} object
     */
    public static CSVInputHeadings getHeadings(String headerRow) {
        int numberOfColumns = headerRow.split(",").length;
        int stateNameColumnIndex = -1, statePopulationColumnIndex = -1;
        String[] columnLabels = headerRow.trim().split(",");
        for (int columnIndex = 0; columnIndex < columnLabels.length; columnIndex++) {
            String heading = columnLabels[columnIndex].trim();
            if (heading.equalsIgnoreCase(STATE_NAME_COLUMN_LABEL) && stateNameColumnIndex == -1) {
                stateNameColumnIndex = columnIndex;
            }
            if (heading.equalsIgnoreCase(STATE_POPULATION_COLUMN_LABEL) && statePopulationColumnIndex == -1) {
                statePopulationColumnIndex = columnIndex;
            }
        }

        if (stateNameColumnIndex == -1 || statePopulationColumnIndex == -1) {
            throwMissingColumnHeadersException(stateNameColumnIndex, statePopulationColumnIndex);
        }
        return new CSVInputHeadings(numberOfColumns, stateNameColumnIndex, statePopulationColumnIndex);
    }

    private static void throwMissingColumnHeadersException(int stateNameColumnIndex, int statePopulationColumnIndex) {
        List<String> missingLabels = new ArrayList<>();
        if (stateNameColumnIndex == -1) {
            missingLabels.add(CSVInputHeadings.STATE_NAME_COLUMN_LABEL);
        }

        if (statePopulationColumnIndex == -1) {
            missingLabels.add(CSVInputHeadings.STATE_POPULATION_COLUMN_LABEL);
        }
        throw new MissingColumnHeadersException(missingLabels.toArray(new String[0]));
    }

    private CSVInputHeadings(int numberOfColumns, int stateNameColumnIndex, int statePopulationColumnIndex) {
        this.numberOfColumns = numberOfColumns;
        this.stateNameColumnIndex = stateNameColumnIndex;
        this.statePopulationColumnIndex = statePopulationColumnIndex;
    }
}
