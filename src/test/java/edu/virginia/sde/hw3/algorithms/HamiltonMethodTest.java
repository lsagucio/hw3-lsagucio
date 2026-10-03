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

package edu.virginia.sde.hw3.algorithms;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;
import edu.virginia.sde.hw3.States;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HamiltonMethodTest {



    /**
     * Example from HW1 Part1 Appendix <br>
     * <a href="https://docs.google.com/document/d/1vs9rsE9rbOP590e01oonExrxHlbil4XwrtE7sG1fjM0/edit#heading=h.cankebxhr2i0">
     *     Link to document</a>
     */

    @Test
    void getHamiltonRepresentation() {
        State DE = new State("Delaware", 989948);
        State MD = new State("Maryland", 6177224);
        State PA = new State("Pennsylvania", 13002700);
        State VA = new State("Virginia", 8631393);
        State WV = new State("West Virginia", 1793716);

        States states = new States(Set.of(DE, MD, PA, VA, WV));
        HamiltonMethod hamiltonMethod = new HamiltonMethod();
        Representation representation = hamiltonMethod.getRepresentation(states, 25);

        assertEquals(25, representation.getAllocatedSeats());
        assertEquals(5, representation.size());

        assertEquals(1, representation.getSeats(DE));
        assertEquals(5, representation.getSeats(MD));
        assertEquals(11, representation.getSeats(PA));
        assertEquals(7, representation.getSeats(VA));
        assertEquals(1, representation.getSeats(WV));
    }
}