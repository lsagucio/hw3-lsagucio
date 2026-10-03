package edu.virginia.sde.hw3.io;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExcelOutputFileTest {
    @TempDir
    Path tempDir;

    @Test
    void constructor_nonXlsxFilename_throws() {
        assertThrows(IllegalArgumentException.class, () -> new ExcelOutputFile("output.csv"));
    }

    @Test
    void constructor_null_throws() {
        assertThrows(IllegalArgumentException.class, () -> new ExcelOutputFile(null));
    }

    @Test
    void writeToOutput() throws IOException {
        Representation representation = new Representation(Map.of(
                new State("Virginia", 8631393), 7,
                new State("Alabama", 5030053), 7,
                new State("Maryland", 6177224), 5,
                new State("Delaware", 989948), 1
        ));
        File file = tempDir.resolve("output.xlsx").toFile();
        ExcelOutputFile excelOutputFile = new ExcelOutputFile(file.getPath());
        assertEquals(file.getPath(), excelOutputFile.getOutputFilename());

        excelOutputFile.writeToOutput(representation);

        try (FileInputStream inputStream = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            assertEquals(1, workbook.getNumberOfSheets());
            Sheet sheet = workbook.getSheetAt(0);
            assertEquals(ExcelOutputFile.SHEET_NAME, sheet.getSheetName());

            assertEquals("State", sheet.getRow(0).getCell(0).getStringCellValue());
            assertEquals("Representatives", sheet.getRow(0).getCell(1).getStringCellValue());

            String[] expectedNames = {"Alabama", "Delaware", "Maryland", "Virginia"};
            int[] expectedSeats = {7, 1, 5, 7};
            assertEquals(expectedNames.length, sheet.getLastRowNum());
            for (int index = 0; index < expectedNames.length; index++) {
                assertEquals(expectedNames[index], sheet.getRow(index + 1).getCell(0).getStringCellValue());
                assertEquals(expectedSeats[index], (int) sheet.getRow(index + 1).getCell(1).getNumericCellValue());
            }
        }
    }
}
