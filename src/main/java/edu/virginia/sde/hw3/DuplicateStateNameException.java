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

/**
 * Thrown when a state with a duplicate name is added to a {@link States} collection
 */
public class DuplicateStateNameException extends RuntimeException {
    /** The state added which caused the exception */
    private final State addedState;

    /** The set of states when the exception was thrown */
    private final States states;

    /**
     * Constructs a DuplicateStateNameException with the specified state and states collection.
     *
     * @param addedState the state that caused the exception due to its duplicate name
     * @param states the collection of states that already contains a state with the same name
     */
    public DuplicateStateNameException(State addedState, States states) {
        super(generateErrorMessage(states, addedState));
        this.addedState = addedState;
        this.states = states;
    }

    private static String generateErrorMessage(States states, State addedState) {
        return String.format("Attempted to add duplicate state name \"%s\" to States set of size %d", addedState.name(), states.size());
    }

    /**
     * Get the state that was added whose name caused the exception
     * @return the {@link State} whose name was already present in the States collection
     */
    public State getAddedState() {
        return addedState;
    }

    /**
     * Get the current collection of states that was added to
     * @return the {@link States} collection
     */
    public States getStates() {
        return states;
    }
}
