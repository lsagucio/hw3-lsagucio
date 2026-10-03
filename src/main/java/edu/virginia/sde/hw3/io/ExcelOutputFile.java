package edu.virginia.sde.hw3.io;

import edu.virginia.sde.hw3.Representation;
import edu.virginia.sde.hw3.State;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

/**
 * Represents an output in Excel (.xlsx) format. The workbook contains a single sheet named "Representation" with
 * the header row "State", "Representatives" followed by one row per state, sorted alphabetically by state name.
 */
public class ExcelOutputFile implements OutputSource {
    /** The name of the single sheet written to the workbook */
    public static final String SHEET_NAME = "Representation";

    /** the location of the file to write the output to */
    private final String outputFilename;

    /**
     * Creates a new ExcelOutputFile object
     * @param outputFilename the filename of the file to be written to
     * @throws IllegalArgumentException if the filename is null or does not end in .xlsx
     */
    public ExcelOutputFile(String outputFilename) {
        if (outputFilename == null) {
            throw new IllegalArgumentException("ExcelOutputFile filename cannot be null");
        }
        if (!outputFilename.endsWith(".xlsx")) {
            throw new IllegalArgumentException("Cannot write to a non-xlsx file: " + outputFilename);
        }
        this.outputFilename = outputFilename;
    }

    /**
     * Gets the filename of the output file
     * @return filename
     */
    public String getOutputFilename() {
        return outputFilename;
    }

    /**
     * Writes the apportionment results to the output Excel file showing the state name and the allocated
     * representatives, one cell at a time.
     * @param representation {@link Representation} the results of an apportionment algorithm
     * @throws IOException if there are any failures when writing to the file
     */
    @Override
    public void writeToOutput(Representation representation) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(SHEET_NAME);
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("State");
            headerRow.createCell(1).setCellValue("Representatives");

            List<State> sortedStates = representation.getStates().stream()
                    .sorted(Comparator.comparing(State::name))
                    .toList();
            for (int index = 0; index < sortedStates.size(); index++) {
                State state = sortedStates.get(index);
                Row row = sheet.createRow(index + 1);
                row.createCell(0).setCellValue(state.name());
                row.createCell(1).setCellValue(representation.getSeats(state));
            }

            try (FileOutputStream outputStream = new FileOutputStream(outputFilename)) {
                workbook.write(outputStream);
            }
        }
    }
}
