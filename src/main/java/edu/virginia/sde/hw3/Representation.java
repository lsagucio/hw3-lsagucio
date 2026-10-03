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

import edu.virginia.sde.hw3.formats.RepresentationFormat;

import java.util.*;

/**
 * Represents the results of an apportionment, or an allocation of seats in the US House of Representatives.
 */
public class Representation implements Iterable<State>{
    /**
     * A mapping of {@link State}s to the number of allocated seats in congress
     */
    private final Map<State, Integer> representation;

    /**
     * Allows direct injection of initial representation Map
     * @param representation a {@link Map} of {@link State}s to integers, representing an apportionment
     */
    public Representation(Map<State, Integer> representation) {
        this.representation = representation;
    }

    /**
     * Creates an initially empty representation, with no states
     */
    public Representation() {
        this(new HashMap<>());
    }

    /**
     * Gets the number of states in the representation
     * @return the number of states, <b>not</b> the number of seats!
     */
    public int size() {
        return representation.size();
    }

    /**
     * Sets the number of seats for a particular {@link State}, overwriting any previous value if present
     * @param state the state to set the number of seats for
     * @param seats the number of seats to allocate to the state
     */
    public void setSeats(State state, int seats) {
        representation.put(state, seats);
    }

    /**
     * Adds the number of seats to a particular state. Note that this behaves the same as
     * {@link Representation#setSeats(State, int)} when the state is not present in the representation
     * @param state the {@link State} to add seats to
     * @param seats the number of seats to add to that state
     */
    public void addSeats(State state, int seats) {
        int totalRepresentatives = seats + representation.getOrDefault(state, 0);
        representation.put(state, totalRepresentatives);
    }

    /**
     * Get the number of seats for a particular state. Returns zero if the state isn't present.
     * @param state the {@link State} to get the seats for.
     * @return the number of seats assigned to the state, or zero if the state is not present.
     */
    public int getSeats(State state) {
        return representation.getOrDefault(state, 0);
    }

    /**
     * Gets the total number of seats allocated to every state
     * @return the sum of seats allocated to all states combined
     */
    public int getAllocatedSeats() {
        // TODO - replace the code in this method with a stream
        // return representation.entrySet().stream()  // OR
        // return representation.keySet().stream() // OR
        // return representation.values().stream()
        int totalSeats = 0;
        for (int numberOfSeats : representation.values()) {
            totalSeats += numberOfSeats;
        }
        return totalSeats;
    }

    /**
     * Return all states in the representation as a {@link Set}
     * @return an immutable {@link Set} of {@link State}s in the representation
     */
    public Set<State> getStates() {
        return Collections.unmodifiableSet(representation.keySet());
    }

    /**
     * Gets a string representation of the underlying data.
     * @return {@link String}
     */
    public String toString() {
        return representation.toString();
    }

    /**
     * Returns a formatted {@link String} to give a textual representation of the Representation object
     * @param format {@link RepresentationFormat}
     * @return a formatted {@link String}
     */
    public String getFormattedString(RepresentationFormat format) {
        return format.getFormattedString(this);
    }

    /**
     * Returns an iterator over the set of {@link State}s in the Representation
     * @return an {@link Iterator}
     */
    @Override
    public Iterator<State> iterator() {
        return representation.keySet().iterator();
    }
}
