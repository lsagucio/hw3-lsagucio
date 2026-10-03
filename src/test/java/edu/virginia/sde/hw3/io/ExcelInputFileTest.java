package edu.virginia.sde.hw3.io;

import edu.virginia.sde.hw3.State;
import edu.virginia.sde.hw3.States;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExcelInputFileTest {
    @TempDir
    Path tempDir;

    /**
     * Writes a workbook whose first sheet contains the given rows. Each row is an array of cell values; Strings are
     * written as text cells and Numbers as numeric cells. A null row is left empty.
     */
    private File writeWorkbook(String filename, Object[]... rows) throws IOException {
        File file = tempDir.resolve(filename).toFile();
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(file)) {
            Sheet sheet = workbook.createSheet("Some Arbitrary Sheet Name");
            workbook.createSheet("Second sheet that should be ignored").createRow(0).createCell(0)
                    .setCellValue("State");
            for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
                if (rows[rowIndex] == null) {
                    continue;
                }
                Row row = sheet.createRow(rowIndex);
                for (int cellIndex = 0; cellIndex < rows[rowIndex].length; cellIndex++) {
                    Object value = rows[rowIndex][cellIndex];
                    if (value instanceof Number number) {
                        row.createCell(cellIndex).setCellValue(number.doubleValue());
                    } else if (value != null) {
                        row.createCell(cellIndex).setCellValue(value.toString());
                    }
                }
            }
            workbook.write(outputStream);
        }
        return file;
    }

    @Test
    void constructor_nonXlsxFilename_throws() {
        assertThrows(IllegalArgumentException.class, () -> new ExcelInputFile("states.csv"));
    }

    @Test
    void constructor_fileDoesNotExist_throws() {
        String missing = tempDir.resolve("missing.xlsx").toString();
        assertThrows(IllegalArgumentException.class, () -> new ExcelInputFile(missing));
    }

    @Test
    void constructor_directory_throws() throws IOException {
        File directory = tempDir.resolve("folder.xlsx").toFile();
        assertTrue(directory.mkdir());
        assertThrows(IllegalArgumentException.class, () -> new ExcelInputFile(directory.getPath()));
    }

    @Test
    void getStates_columnsInAnyOrder() throws IOException {
        File file = writeWorkbook("states.xlsx",
                new Object[]{"ID", "Population", "Capital", "State"},
                new Object[]{1, 989948, "Dover", "Delaware"},
                new Object[]{2, 6177224, "Annapolis", "Maryland"},
                new Object[]{3, "13002700", "Harrisburg", "Pennsylvania"});

        States states = new ExcelInputFile(file.getPath()).getStates();

        assertEquals(Set.of(
                new State("Delaware", 989948),
                new State("Maryland", 6177224),
                new State("Pennsylvania", 13002700)
        ), states.getStates());
    }

    @Test
    void getStates_badRowsSkippedWithoutCrashing() throws IOException {
        File file = writeWorkbook("states.xlsx",
                new Object[]{"State", "Population"},
                new Object[]{"Delaware", 989948},
                new Object[]{"", 100},                   // empty name
                new Object[]{"Nowhere", "lots"},         // non-numeric population
                new Object[]{"Negative", -5},            // negative population
                new Object[]{"Fraction", 12.5},          // non-integer population
                new Object[]{"Missing"},                 // missing population cell
                null,                                    // completely empty row
                new Object[]{"Maryland", 6177224});

        States states = new ExcelInputFile(file.getPath()).getStates();

        assertEquals(Set.of(
                new State("Delaware", 989948),
                new State("Maryland", 6177224)
        ), states.getStates());
    }

    @Test
    void getStates_missingHeaders_throws() throws IOException {
        File file = writeWorkbook("states.xlsx",
                new Object[]{"Name", "Pop"},
                new Object[]{"Delaware", 989948});
        ExcelInputFile excelInputFile = new ExcelInputFile(file.getPath());

        MissingColumnHeadersException exception =
                assertThrows(MissingColumnHeadersException.class, excelInputFile::getStates);
        assertEquals(2, exception.getMissingHeaders().size());
    }

    @Test
    void getStates_missingPopulationHeader_throws() throws IOException {
        File file = writeWorkbook("states.xlsx",
                new Object[]{"State", "Pop"},
                new Object[]{"Delaware", 989948});
        ExcelInputFile excelInputFile = new ExcelInputFile(file.getPath());

        MissingColumnHeadersException exception =
                assertThrows(MissingColumnHeadersException.class, excelInputFile::getStates);
        assertEquals(1, exception.getMissingHeaders().size());
        assertEquals(CSVInputHeadings.STATE_POPULATION_COLUMN_LABEL, exception.getMissingHeaders().get(0));
    }
}
