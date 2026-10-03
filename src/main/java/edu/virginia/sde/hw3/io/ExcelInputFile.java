package edu.virginia.sde.hw3.io;

import edu.virginia.sde.hw3.State;
import edu.virginia.sde.hw3.States;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static edu.virginia.sde.hw3.io.BadCSVLineFormatException.BadFormatReason;

/**
 * Represents an input Excel (.xlsx) file for states. Only the first sheet of the workbook is read. The first row of
 * that sheet must be a header row containing the column headers:
 * <ul>
 *     <li>"State" - the column containing the names of the states</li>
 *     <li>"Population" - the column containing the populations of each state</li>
 * </ul>
 */
public class ExcelInputFile implements StateSource {
    /** Excel file name */
    private final String filename;

    /** The Excel file */
    private final File excelFile;

    /** Used to read cell contents as the text Excel would display */
    private final DataFormatter dataFormatter = new DataFormatter();

    /**
     * Creates an ExcelInputFile object
     * @param filename the name of the .xlsx file to open
     * @throws IllegalArgumentException if the filename does not end in .xlsx, or the file doesn't exist or is a
     * directory
     */
    public ExcelInputFile(String filename) {
        if (!filename.endsWith(".xlsx")) {
            throw new IllegalArgumentException(
                    "ExcelInputFile can only read .xlsx files. Illegal filename given: " + filename
            );
        }
        this.filename = filename;
        excelFile = new File(filename);
        if (!excelFile.exists() || !excelFile.isFile()) {
            throw new IllegalArgumentException(String.format("File not found: %s does not exist.", filename));
        }
    }

    /**
     * Returns the Excel file object
     * @return {@link File}
     */
    public File getExcelFile() {
        return excelFile;
    }

    /**
     * Retrieves state data from the first sheet of the Excel file. Rows that are incorrectly formatted are skipped
     * with a printed warning.
     * @return the group of {@link States} read from the file
     * @throws IOException if the file cannot be read
     * @throws MissingColumnHeadersException if the "State" or "Population" column headers are missing
     */
    @Override
    public States getStates() throws IOException {
        try (FileInputStream inputStream = new FileInputStream(excelFile);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            CSVInputHeadings headings = CSVInputHeadings.getHeadings(getHeaderRowAsCSV(sheet.getRow(0)));

            States states = new States();
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                Optional<State> state = getStateFromRow(row, headings);
                state.ifPresent(states::add);
            }
            return states;
        }
    }

    private String getHeaderRowAsCSV(Row headerRow) {
        if (headerRow == null) {
            throw new MissingColumnHeadersException(
                    CSVInputHeadings.STATE_NAME_COLUMN_LABEL, CSVInputHeadings.STATE_POPULATION_COLUMN_LABEL);
        }
        return IntStream.range(0, headerRow.getLastCellNum())
                .mapToObj(headerRow::getCell)
                .map(this::getCellText)
                .collect(Collectors.joining(","));
    }

    private Optional<State> getStateFromRow(Row row, CSVInputHeadings headings) {
        try {
            return Optional.of(getState(row, headings));
        } catch (BadCSVLineFormatException e) {
            printBadRowFormatWarning(e, row.getRowNum() + 1);
            return Optional.empty();
        }
    }

    private State getState(Row row, CSVInputHeadings headings) throws BadCSVLineFormatException {
        String rowText = getRowText(row);
        Cell nameCell = row.getCell(headings.getStateNameColumnIndex());
        Cell populationCell = row.getCell(headings.getStatePopulationColumnIndex());
        if (nameCell == null || populationCell == null) {
            throw new BadCSVLineFormatException(rowText, BadFormatReason.MISSING_COLUMNS);
        }

        String name = getCellText(nameCell).strip();
        if (name.isEmpty()) {
            throw new BadCSVLineFormatException(rowText, BadFormatReason.EMPTY_STATE_NAME);
        }

        int population = getPopulation(populationCell, rowText);
        if (population < 0) {
            throw new BadCSVLineFormatException(rowText, BadFormatReason.BAD_POPULATION);
        }
        return new State(name, population);
    }

    private int getPopulation(Cell populationCell, String rowText) throws BadCSVLineFormatException {
        if (populationCell.getCellType() == CellType.NUMERIC) {
            double value = populationCell.getNumericCellValue();
            if (value != Math.rint(value) || value > Integer.MAX_VALUE) {
                throw new BadCSVLineFormatException(rowText, BadFormatReason.BAD_POPULATION);
            }
            return (int) value;
        }
        try {
            return Integer.parseInt(getCellText(populationCell).strip());
        } catch (NumberFormatException e) {
            throw new BadCSVLineFormatException(rowText, BadFormatReason.BAD_POPULATION);
        }
    }

    private String getCellText(Cell cell) {
        return cell == null ? "" : dataFormatter.formatCellValue(cell);
    }

    private String getRowText(Row row) {
        List<String> cellTexts = new ArrayList<>();
        for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
            cellTexts.add(getCellText(row.getCell(cellIndex)));
        }
        return String.join(",", cellTexts);
    }

    private void printBadRowFormatWarning(BadCSVLineFormatException exception, int rowNumber) {
        System.out.printf("""
                Warning: %s row %d - %s
                """, filename, rowNumber, exception.getMessage());
    }
}
