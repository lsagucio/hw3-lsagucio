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

package edu.virginia.sde.hw3.formats;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Generates the text for outputting the result of an apportionment ({@link Representation}) to a CSV file
 * sorted alphabetically.
 */
public class CSVOutputFormat implements RepresentationFormat{

    /**
     * Generates a formatted CSV string from the given Representation for writing to a file.
     *
     * @param representation the Representation containing the states and their apportioned number of seats
     * @return a formatted CSV string with states and their corresponding seat allocation
     */
    @Override
    public String getFormattedString(Representation representation) {
        List<State> sortedStates = new ArrayList<>(representation.getStates());
        sortedStates.sort(Comparator.comparing(state -> state.name()));

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("State,Representatives\n");
        for (State state: sortedStates) {
            stringBuilder.append(
                    String.format("%s,%d\n", state.name(), representation.getSeats(state))
            );
        }
        return stringBuilder.toString();
    }
}
