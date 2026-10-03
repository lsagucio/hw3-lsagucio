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

/**
 * This exception is thrown when a line in a {@link CSVInputFile} is incorrectly formatted.
 */
public class BadCSVLineFormatException extends Exception {
    public enum BadFormatReason {
        /** This line has fewer columns than expected */
        MISSING_COLUMNS,

        /** The state name is an empty string */
        EMPTY_STATE_NAME,

        /** The population of a state is either non-integer or negative */
        BAD_POPULATION;

        /**
         * Generates an appropriate error message for a reason and the line that triggered the exception
         * @param line the CSV file line that triggered the exception
         * @return an error message explaining the source of the error.
         */
        public String getReasonMessage(String line) {
            return switch (this) {
                case MISSING_COLUMNS -> "A line doesn't have enough columns.";
                case EMPTY_STATE_NAME -> "The state name is empty.";
                case BAD_POPULATION -> "The population is not a non-negative integer.";
            } + "\"" + line + "\"";
        }
    }

    /** The reason for the exception */
    private final BadFormatReason reason;

    /** The line that triggered the exception */
    private final String line;

    /**
     * Constructs a new BadCSVLineFormatException with the specified line and reason.
     *
     * @param line the CSV file line that caused the exception
     * @param reason the {@link BadFormatReason} indicating why the line is incorrectly formatted
     */
    public BadCSVLineFormatException(String line, BadFormatReason reason) {
        super(reason.getReasonMessage(line));
        this.reason = reason;
        this.line = line;
    }

    /**
     * Retrieves the reason for the exception.
     *
     * @return the {@link BadFormatReason} indicating why the CSV line is incorrectly formatted
     */
    public BadFormatReason getReason() {
        return reason;
    }

    /**
     * Retrieves the CSV file line that caused the exception.
     *
     * @return the CSV line that triggered the exception.
     */
    public String getLine() {
        return line;
    }
}
