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
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PopulationFormatTest {
    static final State OH = new State("Ohio", 300);
    static final State VA = new State("Virginia", 150);
    static final State DE = new State("Delaware", 50);

    @Test
    void getFormattedString_descendingPopulation() {
        Representation representation = new Representation(Map.of(
                OH, 6,
                VA, 3,
                DE, 1
        ));

        RepresentationFormat representationFormat = new PopulationFormat(DisplayOrder.DESCENDING);
        String formattedString = representationFormat.getFormattedString(representation);

        String[] lines = formattedString.split("\n");
        assertTrue(lines[0].startsWith("State"), "First line of formatted String isn't header row");
        assertTrue(lines[1].startsWith(OH.name()), "First line of data isn't for " + OH.name());
        assertTrue(lines[2].startsWith(VA.name()), "Second line of data isn't for " + VA.name());
        assertTrue(lines[3].startsWith(DE.name()), "Third line of data isn't for " + DE.name());
    }

    @Test
    void getFormattedString_ascendingPopulation() {
        Representation representation = new Representation(Map.of(
                OH, 6,
                VA, 3,
                DE, 1
        ));

        RepresentationFormat representationFormat = new PopulationFormat(DisplayOrder.ASCENDING);
        String formattedString = representationFormat.getFormattedString(representation);

        String[] lines = formattedString.split("\n");
        assertTrue(lines[0].startsWith("State"), "First line of formatted String isn't header row");
        assertTrue(lines[1].startsWith(DE.name()), "First line of data isn't for " + DE.name());
        assertTrue(lines[2].startsWith(VA.name()), "Second line of data isn't for " + VA.name());
        assertTrue(lines[3].startsWith(OH.name()), "Third line of data isn't for " + OH.name());
    }
}
