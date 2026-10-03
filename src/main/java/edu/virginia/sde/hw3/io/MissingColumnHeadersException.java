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

import java.util.List;

/**
 * This exception is thrown when an input tabular file is missing required columns
 */
public class MissingColumnHeadersException extends IllegalArgumentException {
    private final List<String> missingHeaders;

    /**
     * Constructs a new MissingColumnHeadersException with a list of missing column headers.
     *
     * @param values the names of the missing columns
     */
    public MissingColumnHeadersException(String... values) {
        super(getErrorMessage(values));
        this.missingHeaders = List.of(values);
    }

    private static String getErrorMessage(String... values){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Input file is missing required column headers.\nMissing column labels:\n");
        for (String value : values) {
            stringBuilder.append(" - ")
                    .append(value)
                    .append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Returns the list of missing headers from an incorrectly formatted tabular file
     * @return a {@link List} of {@link String}s that contain the missing column labels.
     */
    public List<String> getMissingHeaders() {
        return missingHeaders;
    }
}
