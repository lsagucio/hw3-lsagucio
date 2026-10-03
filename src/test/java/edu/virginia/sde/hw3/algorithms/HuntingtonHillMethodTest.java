package edu.virginia.sde.hw3.algorithms;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;
import edu.virginia.sde.hw3.States;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class HuntingtonHillMethodTest {
    static final State DE = new State("Delaware", 989948);
    static final State MD = new State("Maryland", 6177224);
    static final State PA = new State("Pennsylvania", 13002700);
    static final State VA = new State("Virginia", 8631393);
    static final State WV = new State("West Virginia", 1793716);

    @Test
    void getRepresentation() {
        States states = new States(Set.of(DE, MD, PA, VA, WV));
        HuntingtonHillMethod huntingtonHillMethod = new HuntingtonHillMethod();
        Representation representation = huntingtonHillMethod.getRepresentation(states, 25);

        assertEquals(25, representation.getAllocatedSeats());
        assertEquals(5, representation.size());

        assertEquals(1, representation.getSeats(DE));
        assertEquals(5, representation.getSeats(MD));
        assertEquals(10, representation.getSeats(PA));
        assertEquals(7, representation.getSeats(VA));
        assertEquals(2, representation.getSeats(WV));
    }

    @Test
    void getRepresentation_everyStateGetsOneSeat() {
        States states = new States(Set.of(DE, MD, PA, VA, WV));
        Representation representation = new HuntingtonHillMethod().getRepresentation(states, 5);

        assertEquals(5, representation.getAllocatedSeats());
        for (State state : states) {
            assertEquals(1, representation.getSeats(state));
        }
    }

    @Test
    void getRepresentation_firstBonusSeatGoesToLargestState() {
        States states = new States(Set.of(DE, MD, PA, VA, WV));
        Representation representation = new HuntingtonHillMethod().getRepresentation(states, 6);

        assertEquals(2, representation.getSeats(PA));
        assertEquals(1, representation.getSeats(VA));
    }

    @Test
    void getRepresentation_fewerSeatsThanStates_throws() {
        States states = new States(Set.of(DE, MD, PA, VA, WV));
        HuntingtonHillMethod huntingtonHillMethod = new HuntingtonHillMethod();

        assertThrows(IllegalArgumentException.class, () -> huntingtonHillMethod.getRepresentation(states, 4));
    }

    @Test
    void getRepresentation_emptyStates_throws() {
        HuntingtonHillMethod huntingtonHillMethod = new HuntingtonHillMethod();

        assertThrows(IllegalArgumentException.class, () -> huntingtonHillMethod.getRepresentation(new States(), 25));
    }

    @Test
    void getRepresentation_nonPositiveSeats_throws() {
        States states = new States(Set.of(DE, MD));
        HuntingtonHillMethod huntingtonHillMethod = new HuntingtonHillMethod();

        assertThrows(IllegalArgumentException.class, () -> huntingtonHillMethod.getRepresentation(states, 0));
    }
}
