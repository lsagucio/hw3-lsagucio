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
import edu.virginia.sde.hw3.States;

/**
 * Describes an algorithm for generating an apportionment, an allocation of seats in the US House of Representatives
 * to the states based on population (represented by {@link Representation}.
 */
public interface ApportionmentMethod {
    /**
     * Gets the results of an apportionment for a group of states and a particular target number of representatives
     * @param states the group of {@link States} to allocate representatives to
     * @param seats the number of seats in Congress to allocate
     * @return {@link Representation} containing the allocation of representative to each state
     */
    Representation getRepresentation(States states, int seats);
}
