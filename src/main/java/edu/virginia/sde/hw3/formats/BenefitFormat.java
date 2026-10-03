package edu.virginia.sde.hw3.formats;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Displays states sorted by how much each state benefited (or was hurt) by the apportionment. A state's benefit is
 * the number of seats it was allocated minus its quota, where the quota is the state's population divided by
 * (total population / total allocated seats). For example:
 * <pre>
 * State           | Reps| Benefit
 * Pennsylvania    |   12|  +1.375
 * Maryland        |    5|  -0.048
 * Virginia        |    7|  -0.053
 * West Virginia   |    1|  -0.466
 * Delaware        |    0|  -0.809
 * </pre>
 */
public class BenefitFormat implements RepresentationFormat {
    /**
     * Determines whether states are sorted by benefit in either {@link DisplayOrder#ASCENDING} or
     * {@link DisplayOrder#DESCENDING} order
     */
    private final DisplayOrder displayOrder;

    /**
     * Creates a BenefitFormat that displays states by benefit in {@link DisplayOrder#DESCENDING} order
     */
    public BenefitFormat() {
        this(DisplayOrder.DESCENDING);
    }

    /**
     * Sets the display order, either {@link DisplayOrder#ASCENDING} or {@link DisplayOrder#DESCENDING}
     * @param displayOrder {@link DisplayOrder}
     */
    public BenefitFormat(DisplayOrder displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Gets the display order
     * @return either {@link DisplayOrder#ASCENDING} or {@link DisplayOrder#DESCENDING}
     */
    public DisplayOrder getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Returns a table of states sorted by benefit in either ascending or descending order, depending on the
     * object's configuration.
     * @param representation {@link Representation} - the results of an apportionment
     * @return a formatted {@link String}
     */
    @Override
    public String getFormattedString(Representation representation) {
        double divisor = getDivisor(representation);

        Comparator<State> stateComparator = Comparator.comparingDouble(
                (State state) -> getBenefit(state, representation, divisor));
        if (displayOrder == DisplayOrder.DESCENDING) {
            stateComparator = stateComparator.reversed();
        }
        stateComparator = stateComparator.thenComparing(state -> state.name().toUpperCase());

        String headerRow = "%-16s|%5s|%8s\n".formatted("State", "Reps", "Benefit");
        return headerRow + representation.getStates().stream()
                .sorted(stateComparator)
                .map(state -> "%-16s|%5d|%+8.3f\n".formatted(
                        state.name(),
                        representation.getSeats(state),
                        getBenefit(state, representation, divisor)))
                .collect(Collectors.joining());
    }

    /**
     * Computes the benefit of a state: the seats it was allocated minus the seats its quota says it should receive.
     * @param state the {@link State} to compute the benefit for
     * @param representation the results of the apportionment
     * @return the benefit, positive if the state received more seats than its quota, negative if fewer
     */
    public double getBenefit(State state, Representation representation) {
        return getBenefit(state, representation, getDivisor(representation));
    }

    private static double getBenefit(State state, Representation representation, double divisor) {
        return representation.getSeats(state) - state.population() / divisor;
    }

    private static double getDivisor(Representation representation) {
        long totalPopulation = representation.getStates().stream()
                .mapToLong(State::population)
                .sum();
        return (double) totalPopulation / representation.getAllocatedSeats();
    }
}
