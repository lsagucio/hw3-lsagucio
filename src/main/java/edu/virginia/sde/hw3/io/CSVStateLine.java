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

import static edu.virginia.sde.hw3.io.BadCSVLineFormatException.BadFormatReason;

public class CSVStateLine {
    /** The headings for the CSV file */
    private final CSVInputHeadings headings;

    /** The raw line contents */
    private final String line;

    /** The line contents split by commas */
    private final String[] lineData;

    /**
     * Constructs a CSVStateLine instance from a given CSV line and headings.
     *
     * @param line the string representation of a line from a CSV file.
     * @param headings the CSVInputHeadings object representing the column headings.
     * @throws BadCSVLineFormatException if the number of columns in the line does not match the number of columns in the headings.
     */
    public CSVStateLine(String line, CSVInputHeadings headings) throws BadCSVLineFormatException {
        this.headings = headings;
        this.line = line;
        this.lineData = line.split(",");
        if (lineData.length != headings.getNumberOfColumns()) {
            throw new BadCSVLineFormatException(line, BadFormatReason.MISSING_COLUMNS);
        }
    }

    /**
     * Parse a line in a CSV file to retrieve a State object.
     * @return the generated {@link State} object
     * @throws BadCSVLineFormatException if the line is incorrectly formatted.
     */
    public State getState() throws BadCSVLineFormatException {
        String name = lineData[headings.getStateNameColumnIndex()].strip();
        if (name.isEmpty()) {
            throw new BadCSVLineFormatException(line, BadFormatReason.EMPTY_STATE_NAME);
        }

        int population;
        try {
            population = Integer.parseInt(lineData[headings.getStatePopulationColumnIndex()].strip());
        } catch (NumberFormatException e) {
            throw new BadCSVLineFormatException(line, BadFormatReason.BAD_POPULATION);
        }
        if (population < 0) {
            throw new BadCSVLineFormatException(line, BadFormatReason.BAD_POPULATION);
        }

        return new State(name, population);
    }
}
