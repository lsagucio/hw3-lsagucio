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

import java.util.Comparator;

/**
 * Display states sorted by population.
 */
public class PopulationFormat implements RepresentationFormat{
    private final SortedTableFormat sortedTableFormat = new SortedTableFormat();

    /**
     * Determines whether states are sorted by population in either {@link DisplayOrder#ASCENDING} or
     * {@link DisplayOrder#DESCENDING} order
     */
    private final DisplayOrder displayOrder;

    /**
     * Sets the display order, either {@link DisplayOrder#ASCENDING} or {@link DisplayOrder#DESCENDING}
     * @param displayOrder {@link DisplayOrder}
     */
    public PopulationFormat(DisplayOrder displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Gets the display order
     * @return either {@link DisplayOrder#ASCENDING} or {@link DisplayOrder#DESCENDING}
     */
    public DisplayOrder getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Returns a string of states sorted by population in either descending or descending order, depending on
     * the object's configuration.
     * @param representation {@link Representation} - the results of an apportionment
     * @return a formatted {@link String}
     * @see SortedTableFormat#getSortedTableString(Representation, Comparator)
     */
    @Override
    public String getFormattedString(Representation representation) {
        Comparator<State> stateComparator = Comparator.comparing(state -> state.population());
        if (displayOrder == DisplayOrder.DESCENDING) {
            stateComparator = stateComparator.reversed();
        }

        return sortedTableFormat.getSortedTableString(representation, stateComparator);
    }
}
