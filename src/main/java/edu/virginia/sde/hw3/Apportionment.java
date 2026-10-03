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

import edu.virginia.sde.hw3.algorithms.ApportionmentMethod;
import edu.virginia.sde.hw3.algorithms.UnsolvableApportionmentException;
import edu.virginia.sde.hw3.io.StateSource;

import java.io.IOException;

/**
 * This class processes the Apportionment by getting the list of states from the {@link StateSource} and calling the
 * {@link ApportionmentMethod} to get the {@link Representation}.
 */
public class Apportionment {
    /** The data source for state information */
    private final StateSource stateSource;

    /** The apportionment algorithm to use */
    private final ApportionmentMethod apportionmentMethod;

    /** The number of representatives to allocate */
    private final int representatives;

    /**
     * Creates an Apportionment object
     * @param stateSource the data source for state information
     * @param apportionmentMethod the apportionment algorithm to use
     * @param representatives the number of representatives to allocate
     */
    public Apportionment(
            StateSource stateSource,
            ApportionmentMethod apportionmentMethod,
            int representatives
    ) {
        this.stateSource = stateSource;
        this.apportionmentMethod = apportionmentMethod;
        this.representatives = representatives;
    }

    /**
     * Get the results of the apportionment
     * @return {@link Representation} with the allocation of seats in the House of Representatives to the States
     */
    public Representation getRepresentation() {
        try {
            States states = stateSource.getStates();
            if (states.isEmpty() || states.getTotalPopulation() <= 0) {
                String errorMessage = "Cannot apportion representatives as no states with a positive population were provided";
                throw new UnsolvableApportionmentException(errorMessage);
            }

            return apportionmentMethod.getRepresentation(states, representatives);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
