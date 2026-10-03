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

/**
 * Thrown when a combination of state populations and target number of representatives makes it impossible
 * to use a particular Apportionment Algorithm.
 */
public class UnsolvableApportionmentException extends IllegalArgumentException {
    /**
     * Constructs a new UnsolvableApportionmentException with the specified detail message.
     *
     * @param errorMessage the detail message explaining the cause of the exception
     */
    public UnsolvableApportionmentException(String errorMessage) {
        super(errorMessage);
    }
}
