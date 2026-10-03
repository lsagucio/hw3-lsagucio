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

import java.io.IOException;

/**
 * Describes an output source to send the results of the apportionment to
 */
public interface OutputSource {
    /**
     * Writes the results of the apportionment algorithm to some output source
     * @param representation {@link Representation} the results of an apportionment algorithm
     * @throws IOException if there is any error in writing to the output source
     */
    void writeToOutput(Representation representation) throws IOException;
}
