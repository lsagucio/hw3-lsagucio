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

import static org.junit.jupiter.api.Assertions.*;

class JeffersonMethodTest {

    @Test
    void getRepresentation() {
        State DE = new State("Delaware", 989948);
        State MD = new State("Maryland", 6177224);
        State PA = new State("Pennsylvania", 13002700);
        State VA = new State("Virginia", 8631393);
        State WV = new State("West Virginia", 1793716);

        States states = new States(Set.of(DE, MD, PA, VA, WV));
        JeffersonMethod jeffersonMethod = new JeffersonMethod();
        Representation representation = jeffersonMethod.getRepresentation(states, 25);

        assertEquals(25, representation.getAllocatedSeats());
        assertEquals(5, representation.size());

        assertEquals(0, representation.getSeats(DE));
        assertEquals(5, representation.getSeats(MD));
        assertEquals(12, representation.getSeats(PA));
        assertEquals(7, representation.getSeats(VA));
        assertEquals(1, representation.getSeats(WV));
    }
}