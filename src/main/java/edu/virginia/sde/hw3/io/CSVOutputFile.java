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

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.formats.CSVOutputFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Represents an output in CSV format. For example: <br>
 * <i>
 *     State,Representatives<br>
 *     Alabama,7<br>
 *     Arizona,10<br>
 *     Alaska,1<br>
 *     etc.
 * </i>
 */
public class CSVOutputFile implements OutputSource {
    /** the location of the file to write the output to */
    private final String outputFilename;

    /**
     * Creates a new CSVOutputFile object
     * @param outputFilename the filename of the file to be written to
     */
    public CSVOutputFile(String outputFilename) {
        if (outputFilename == null) {
            throw new IllegalArgumentException("CSVOutputFile filename cannot be null");
        }
        if (!outputFilename.endsWith(".csv")) {
            throw new IllegalArgumentException("Cannot write to a non-csv file: " + outputFilename);
        }
        this.outputFilename = outputFilename;
    }

    /**
     * Gets the filename of the output file
     * @return filename
     */
    public String getOutputFilename() {
        return outputFilename;
    }

    /**
     * Writes the apportionment results to the output csv file showing the state name and the allocated representatives
     * @param representation {@link Representation} the results of an apportionment algorithm
     * @throws IOException if there are any failures when writing to the file
     */
    @Override
    public void writeToOutput(Representation representation) throws IOException {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename))) {
            String fileContents = representation.getFormattedString(new CSVOutputFormat());
            writer.write(fileContents);
        }
    }
}


