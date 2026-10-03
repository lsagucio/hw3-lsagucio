package edu.virginia.sde.hw3.formats;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BenefitFormatTest {
    static final State DE = new State("Delaware", 989948);
    static final State MD = new State("Maryland", 6177224);
    static final State PA = new State("Pennsylvania", 13002700);
    static final State VA = new State("Virginia", 8631393);
    static final State WV = new State("West Virginia", 1793716);

    /** The result of a Jefferson apportionment of 25 seats to the states above */
    static Representation getJeffersonRepresentation() {
        return new Representation(Map.of(
                DE, 0,
                MD, 5,
                PA, 12,
                VA, 7,
                WV, 1
        ));
    }

    @Test
    void defaultConstructor_isDescending() {
        assertEquals(DisplayOrder.DESCENDING, new BenefitFormat().getDisplayOrder());
    }

    @Test
    void oneArgumentConstructor_setsDisplayOrder() {
        assertEquals(DisplayOrder.ASCENDING, new BenefitFormat(DisplayOrder.ASCENDING).getDisplayOrder());
        assertEquals(DisplayOrder.DESCENDING, new BenefitFormat(DisplayOrder.DESCENDING).getDisplayOrder());
    }

    @Test
    void getBenefit() {
        Representation representation = getJeffersonRepresentation();
        BenefitFormat format = new BenefitFormat();

        assertEquals(-0.808914, format.getBenefit(DE, representation), 0.000001);
        assertEquals(-0.047580, format.getBenefit(MD, representation), 0.000001);
        assertEquals(1.375136, format.getBenefit(PA, representation), 0.000001);
        assertEquals(-0.052948, format.getBenefit(VA, representation), 0.000001);
        assertEquals(-0.465695, format.getBenefit(WV, representation), 0.000001);
    }

    @Test
    void getFormattedString_descending() {
        String formattedString = new BenefitFormat().getFormattedString(getJeffersonRepresentation());

        String[] lines = formattedString.split("\n");
        assertEquals(6, lines.length);
        assertEquals("State           | Reps| Benefit", lines[0]);
        assertEquals("Pennsylvania    |   12|  +1.375", lines[1]);
        assertEquals("Maryland        |    5|  -0.048", lines[2]);
        assertEquals("Virginia        |    7|  -0.053", lines[3]);
        assertEquals("West Virginia   |    1|  -0.466", lines[4]);
        assertEquals("Delaware        |    0|  -0.809", lines[5]);
    }

    @Test
    void getFormattedString_ascending() {
        String formattedString = new BenefitFormat(DisplayOrder.ASCENDING)
                .getFormattedString(getJeffersonRepresentation());

        String[] lines = formattedString.split("\n");
        assertEquals(6, lines.length);
        assertTrue(lines[0].startsWith("State"), "First line of formatted String isn't header row");
        assertTrue(lines[1].startsWith(DE.name()), "First line of data isn't for " + DE.name());
        assertTrue(lines[2].startsWith(WV.name()), "Second line of data isn't for " + WV.name());
        assertTrue(lines[3].startsWith(VA.name()), "Third line of data isn't for " + VA.name());
        assertTrue(lines[4].startsWith(MD.name()), "Fourth line of data isn't for " + MD.name());
        assertTrue(lines[5].startsWith(PA.name()), "Fifth line of data isn't for " + PA.name());
    }

    @Test
    void getFormattedString_columnWidths() {
        String[] lines = new BenefitFormat().getFormattedString(getJeffersonRepresentation()).split("\n");

        for (String line : lines) {
            String[] columns = line.split("\\|");
            assertEquals(3, columns.length, "Line should have three columns: " + line);
            assertEquals(16, columns[0].length(), "State column should be 16 wide: " + line);
            assertEquals(5, columns[1].length(), "Reps column should be 5 wide: " + line);
            assertEquals(8, columns[2].length(), "Benefit column should be 8 wide: " + line);
        }
    }
}
