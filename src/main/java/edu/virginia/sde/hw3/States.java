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

import java.util.*;
import java.util.stream.Collectors;

/**
 * This represents a group of States. This class contains several mathematical functions useful for apportionment
 * on this group of States.
 */
public class States implements Iterable<State> {

    /** The collection of states */
    private final Set<State> states;

    /** A set of the state names already in the collection */
    private final Set<String> stateNames;

    /**
     * Create a group of states initially population with
     * @param states the initial collection of States
     */
    public States(Set<State> states) {
        this.states = states;
        this.stateNames = new HashSet<>();
        for (State state : states) {
            stateNames.add(state.name());
        }
    }

    /**
     * Create an empty group of states
     */
    public States() {
        this.states = new HashSet<>();
        stateNames = new HashSet<>();
    }

    /**
     * Add a new state to the group of states
     * @param state the new {@link State} to add
     */
    public void add(State state) {
        if (stateNames.contains(state.name())) {
            throw new DuplicateStateNameException(state, this);
        }
        states.add(state);
        stateNames.add(state.name());
    }

    /**
     * Returns whether the states collection is empty
     * @return true if the states collection has no states
     */
    public boolean isEmpty() {
        return states.isEmpty();
    }

    /**
     * Computes the number of states in the collection.
     * @return the number of states
     */
    public int size() {
        return states.size();
    }

    /**
     * Returns an unmodifiable view of the set of states.
     * @return an unmodifiable set containing all the states.
     */
    public Set<State> getStates() {
        return Collections.unmodifiableSet(states);
    }

    /**
     * Returns a set of state names in the collection.
     * @return an unmodifiable set containing the names of all states
     */
    public Set<String> getNames() {
        return Collections.unmodifiableSet(stateNames);
    }

    /**
     * Returns the total population of the list of states
     * @return the combined population of every state
     */
    public int getTotalPopulation() {
        return states.stream()
                .mapToInt(State::population)
                .sum();
    }

    /**
     * Returns the average population per seat in US House of Representatives for the group of states
     * @param seats the total number of seats in the House of Representatives
     * @return the average number of residents per seat for all states combined
     */
    public double getAverageRepresentation(int seats) {
        return (double) getTotalPopulation() / seats;
    }

    /**
     * Get the quota for each state, or the expected number of seats allocated to the state based on
     * a particular divisor.
     * @param divisor the population per seat
     * @return A mapping of states to their floating-point quotas
     */
    public Map<State, Double> getQuotas(double divisor) {
        return states.stream()
                .collect(Collectors.toMap(state -> state, state -> state.population() / divisor));
    }

    /**
     * Does the same thing as {@link States#getQuotas(double)} but rounds down the result to an integer
     * @param divisor divisor the population per seat
     * @return A mapping of states to their floating-point quotas always rounded *down*. So a quota of 4.99 would be
     * set equal to 4
     */
    public Map<State, Integer> getRoundedDownQuotas(double divisor) {
        Map<State, Double> quotas = getQuotas(divisor);
        return quotas.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (int) Math.floor(entry.getValue())));
    }

    /**
     * Get the remainders of the quotes from {@link States#getQuotas(double)}
     * @param divisor divisor the population per seat
     * @return A mapping of states to their floating point remainders. For example, if a state has s quota of 4.73, then
     * that state maps to 0.73 in this output.
     */
    public Map<State, Double> getRemainders(double divisor) {
        Map<State, Double> quotas = getQuotas(divisor);
        return quotas.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue() - Math.floor(entry.getValue())));
    }

    @Override
    public Iterator<State> iterator() {
        return states.iterator();
    }
}
