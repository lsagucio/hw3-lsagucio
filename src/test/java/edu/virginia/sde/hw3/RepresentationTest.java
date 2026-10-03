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

package edu.virginia.sde.hw3;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RepresentationTest {

    static final State ohio = new State("Ohio", 1234567890);
    static final State virginia = new State("Virginia", 987654321);

    @Test
    void getRepresentatives_notPresent() {
        Representation rep = new Representation();
        assertEquals(0, rep.getSeats(ohio));
    }

    @Test
    void getRepresentatives_present() {
        Map<State, Integer> initialMap = new HashMap<>(
                Map.of(ohio, 5)
        );
        Representation rep = new Representation(initialMap);
        assertEquals(5, rep.getSeats(ohio));
    }

    @Test
    void setSeats_notPresent() {
        Representation rep = new Representation();
        rep.setSeats(ohio, 5);

        assertEquals(5, rep.getSeats(ohio));
    }

    @Test
    void setSeats_present() {
        Map<State, Integer> initialMap = new HashMap<>(
                Map.of(ohio, 10)
        );
        Representation rep = new Representation(initialMap);
        rep.setSeats(ohio, 5);

        assertEquals(5, rep.getSeats(ohio));
    }

    @Test
    void addRepresentatives_notPresent() {
        Representation rep = new Representation();
        rep.addSeats(ohio, 5);

        assertEquals(5, rep.getSeats(ohio));
    }

    @Test
    void addRepresentatives_present() {
        Map<State, Integer> initialMap = new HashMap<>(
                Map.of(ohio, 10)
        );
        Representation rep = new Representation(initialMap);
        rep.addSeats(ohio, 5);

        assertEquals(15, rep.getSeats(ohio));
    }



    @Test
    void getAllocatedSeats_notEmpty() {
        Map<State, Integer> initialMap = new HashMap<>(
                Map.of(
                        ohio, 10,
                        virginia, 5
                )
        );
        Representation rep = new Representation(initialMap);
        assertEquals(15, rep.getAllocatedSeats());
    }

    @Test
    void getAllocatedSeats_Empty() {
        Representation rep = new Representation();
        assertEquals(0, rep.getAllocatedSeats());
    }
}