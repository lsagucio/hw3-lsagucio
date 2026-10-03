package edu.virginia.sde.hw3.algorithms;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;
import edu.virginia.sde.hw3.States;

import java.util.Comparator;

/**
 * Represents the apportionment method proposed by Edward Huntington and Joseph Hill, also called the
 * <a href="https://en.wikipedia.org/wiki/Huntington%E2%80%93Hill_method">method of equal proportions</a>.
 * This is the method currently used by the US House of Representatives.
 */
public class HuntingtonHillMethod implements ApportionmentMethod {

    /**
     * Performs an apportionment using the Huntington-Hill method. Every state is first given one seat. Each
     * remaining seat is then allocated, one at a time, to the state with the highest priority, where
     * priority = population / sqrt(n * (n + 1)) and n is the number of seats the state currently holds.
     * @param states the group of {@link States} to allocate representatives to
     * @param seats the number of seats in Congress to allocate
     * @return {@link Representation} containing the allocation of representative to each state
     * @throws IllegalArgumentException if there are no states, the total population is zero, or there are fewer
     * seats than states (since every state must receive at least one seat)
     */
    @Override
    public Representation getRepresentation(States states, int seats) {
        if (states.isEmpty() || states.getTotalPopulation() == 0) {
            throw new IllegalArgumentException("No states provided! Cannot generate Representation map");
        }
        if (seats <= 0) {
            throw new IllegalArgumentException("Number of representatives must be greater than zero!");
        }
        if (seats < states.size()) {
            throw new UnsolvableApportionmentException(
                    "Huntington-Hill requires at least one seat per state: " + states.size() + " states but only "
                            + seats + " seats");
        }

        Representation representation = new Representation();
        states.forEach(state -> representation.setSeats(state, 1));

        int remainingSeats = seats - representation.getAllocatedSeats();
        for (int seat = 0; seat < remainingSeats; seat++) {
            State nextState = states.getStates().stream()
                    .max(Comparator.comparingDouble((State state) -> getPriority(state, representation)))
                    .orElseThrow();
            representation.addSeats(nextState, 1);
        }
        return representation;
    }

    private static double getPriority(State state, Representation representation) {
        int currentSeats = representation.getSeats(state);
        return state.population() / Math.sqrt((double) currentSeats * (currentSeats + 1));
    }
}
