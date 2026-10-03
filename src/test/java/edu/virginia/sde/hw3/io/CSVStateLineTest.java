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

import edu.virginia.sde.hw3.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static edu.virginia.sde.hw3.io.BadCSVLineFormatException.BadFormatReason;

public class CSVStateLineTest {
    public static final CSVInputHeadings simpleHeadings = CSVInputHeadings.getHeadings("State,Population");
    public static final CSVInputHeadings extraHeadings = CSVInputHeadings.getHeadings(
            "ID, State, Capital City, Year Ratified, Population, Nickname");

    @Test
    public void getState_simpleHeadings_valid() throws BadCSVLineFormatException {
        String line = "Ohio,500";

        CSVStateLine stateLine = new CSVStateLine(line, simpleHeadings);

        State state = stateLine.getState();

        assertEquals("Ohio", state.name());
        assertEquals(500, state.population());
    }

    @Test
    public void getState_extraHeadings_spaces_valid() throws BadCSVLineFormatException {
        String line = "1, Ohio ,Columbus,1803, 500 ,Buckeye State";

        CSVStateLine stateLine = new CSVStateLine(line, extraHeadings);

        State state = stateLine.getState();

        assertEquals("Ohio", state.name());
        assertEquals(500, state.population());
    }

    /**
     * Even though the relevant state data is there, for defensive programming reasons, we throw exception on
     * every line with *any* missing columns
     */
    @Test
    public void getState_BadCSVLineFormatException_missingColumns()  {
        String line = "1,Ohio,Columbus,1803,500";

        BadCSVLineFormatException exception = assertThrows(BadCSVLineFormatException.class, () -> {
            CSVStateLine csvStateLine = new CSVStateLine(line, extraHeadings);
            csvStateLine.getState();
        });

        assertEquals(BadFormatReason.MISSING_COLUMNS, exception.getReason());
        assertEquals(line, exception.getLine());
    }

    @Test
    public void getState_BadCSVLineFormatException_emptyStateName()  {
        String line = "1,,Columbus,1803,500,Buckeye State";

        BadCSVLineFormatException exception = assertThrows(BadCSVLineFormatException.class, () -> {
            CSVStateLine csvStateLine = new CSVStateLine(line, extraHeadings);
            csvStateLine.getState();
        });

        assertEquals(BadFormatReason.EMPTY_STATE_NAME, exception.getReason());
        assertEquals(line, exception.getLine());
    }

    @Test
    public void getState_BadCSVLineFormatException_populationNonNumber()  {
        String line = "1,Ohio,Columbus,1803,Five Hundred,Buckeye State";

        BadCSVLineFormatException exception = assertThrows(BadCSVLineFormatException.class, () -> {
            CSVStateLine csvStateLine = new CSVStateLine(line, extraHeadings);
            csvStateLine.getState();
        });

        assertEquals(BadFormatReason.BAD_POPULATION, exception.getReason());
        assertEquals(line, exception.getLine());
    }

    @Test
    public void getState_BadCSVLineFormatException_populationNegative()  {
        String line = "1,Ohio,Columbus,1803,-500,Buckeye State";

        BadCSVLineFormatException exception = assertThrows(BadCSVLineFormatException.class, () -> {
            CSVStateLine csvStateLine = new CSVStateLine(line, extraHeadings);
            csvStateLine.getState();
        });

        assertEquals(BadFormatReason.BAD_POPULATION, exception.getReason());
        assertEquals(line, exception.getLine());
    }
}
