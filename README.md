# Homework 3 - Streams and Incremental Development

It may look like all these files are new files. The reason is that I accidentally cloned the Homework 3 starter repo and did all my word in the clone. I tried to push, but it did not let me, and that was when I realized I had done all my work in the starter repo without copying it. I had to copy the repo to hw3-lsagucio then clone it, then copy all of hw3-starter repo's contents into the new hw3-lsagucio.

## Authors
1) Liam Sagucio, lsagucio, lsagucio

## To Run

Build the runnable jar (requires JDK 17+ for Gradle 9):

```
./gradlew jar          # macOS/Linux
gradlew.bat jar        # Windows
```

This produces `build/libs/Apportionment.jar`. Run it with an input file (`.csv` or `.xlsx`) as the first argument:

```
java -jar build/libs/Apportionment.jar <input file> [representatives] [flags]
```

* `<input file>` - a `.csv` or `.xlsx` file with a header row containing `State` and `Population` columns.
  For `.xlsx` files, only the first sheet is read. See `sample_inputs/` for examples.
* `[representatives]` - optional number of seats to apportion (default 435).
* Apportionment method flags (default is Huntington-Hill):
  * `--jefferson` - use the Jefferson method
  * `--hamilton` - use the Hamilton method
* Console format flags (default is alphabetical):
  * `--population` - sort by population
  * `--benefit` - sort by how much each state benefited from the apportionment (seats minus quota)
  * `--ascending` - with `--population` or `--benefit`, sort ascending instead of descending
* `--out <file>` - also write the results to a `.csv` or `.xlsx` file (sorted alphabetically)

Examples:

```
java -jar build/libs/Apportionment.jar sample_inputs/part1_input.csv 25
java -jar build/libs/Apportionment.jar sample_inputs/part2_input.xlsx --jefferson --benefit
java -jar build/libs/Apportionment.jar sample_inputs/part2_input.csv --population --ascending --out output.xlsx
```

Run the tests with `./gradlew test`.

## Contributions

### Liam Sagucio

* Part 1: replaced loops with streams in `Representation`, `States`, `HamiltonMethod`, and `SortedTableFormat`
* Part 2: implemented `HuntingtonHillMethod` and made it the default method (`--jefferson` flag added)
* Part 3: implemented `ExcelInputFile` and `ExcelOutputFile` using Apache POI; `Arguments` accepts `.xlsx` input/output
* Part 4: implemented `BenefitFormat` with the `--benefit` flag
* Added `junit-platform-launcher` and `poi-ooxml` dependencies to `build.gradle`
* Updated `ArgumentsTest` for the new default method and added tests for all new classes
* Added `sample_inputs/part2_input.xlsx`

## Issues

No known issues at the time of submission.
