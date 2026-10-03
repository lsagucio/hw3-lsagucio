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

package edu.virginia.sde.hw3.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static edu.virginia.sde.hw3.io.CSVInputHeadings.STATE_NAME_COLUMN_LABEL;
import static edu.virginia.sde.hw3.io.CSVInputHeadings.STATE_POPULATION_COLUMN_LABEL;
import static org.junit.jupiter.api.Assertions.*;

public class CSVInputHeadingsTest {
    @Test
    @DisplayName("getHeadings(\"State,Population\")")
    public void getHeadings_twoColumns_correctLabels() {
        String headerRow = "State,Population";

        CSVInputHeadings headings = CSVInputHeadings.getHeadings(headerRow);

        assertEquals(2, headings.getNumberOfColumns());
        assertEquals(0, headings.getStateNameColumnIndex());
        assertEquals(1, headings.getStatePopulationColumnIndex());
    }

    @Test
    @DisplayName("getHeadings(\"State,Population\")")
    public void getHeadings_extraColumns_correctLabels_leadingSpaces() {
        String headerRow = "ID, State, Capital City, Year Ratified, Population, Nickname";

        CSVInputHeadings headings = CSVInputHeadings.getHeadings(headerRow);

        assertEquals(6, headings.getNumberOfColumns());
        assertEquals(1, headings.getStateNameColumnIndex());
        assertEquals(4, headings.getStatePopulationColumnIndex());
    }

    @Test
    @DisplayName("getHeadings(\"State,Population\")")
    public void getHeadings_missingPopulation() {
        String headerRow = "ID, State, Capital City, Year Ratified, Nickname";

        MissingColumnHeadersException e = assertThrows(MissingColumnHeadersException.class, () ->
                CSVInputHeadings.getHeadings(headerRow));

        List<String> missingHeaders = e.getMissingHeaders();
        assertTrue(missingHeaders.contains(STATE_POPULATION_COLUMN_LABEL),
                "Missing headers incorrectly does not contain " + STATE_POPULATION_COLUMN_LABEL);
        assertFalse(missingHeaders.contains(STATE_NAME_COLUMN_LABEL),
                "Missing headers incorrectly contains " + STATE_NAME_COLUMN_LABEL);
    }


}
