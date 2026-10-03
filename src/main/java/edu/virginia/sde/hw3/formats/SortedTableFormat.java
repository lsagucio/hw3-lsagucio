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
 * Represent a Default format for displaying an apportionment result to console. This format will display as:<br>
 * <pre>
 * State          |          Population| Reps <br>
 * Alabama        |             5030053|    7 <br>
 * Alaska         |              736081|    1 <br>
 * Arizona        |             7158923|    9 <br>
 * </pre>
 *
 * Sorted by a specific {@link Comparator}
 */
public class SortedTableFormat {
    /**
     * Generates a table-link string for displaying the results an apportionment console. <br>
     *  <pre>
     *  State          |          Population| Reps <br>
     *  Alabama        |             5030053|    7 <br>
     *  Alaska         |              736081|    1 <br>
     *  Arizona        |             7158923|    9 <br>
     *  </pre>
     * @param representation the results of an apportionment to display
     * @param stateComparator how the states are sorted in the table
     * @return a formatted {@link String}
     */
    String getSortedTableString(Representation representation, Comparator<State> stateComparator) {
        List<State> states = new ArrayList<>(representation.getStates());

        String headerRow = "%-15s|%19s |%5s\n".formatted("State", "Population", "Reps");
        // TODO - replace the rest of the code in this method with a stream
        // As a hint, you no longer need to use a StringBuilder
        // You can use the Collectors.joining method to concatenate strings
        // return headerRow + states.stream()
        states.sort(stateComparator);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(headerRow);
        for (State state : states) {
            stringBuilder.append(String.format("%-15s|%19s |%5s\n",
                    state.name(),
                    state.population(),
                    representation.getSeats(state)));
        }
        return stringBuilder.toString();
    }
}