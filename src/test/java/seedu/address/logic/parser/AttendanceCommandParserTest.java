package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

/** Parser tests for the attendance command. */
public class AttendanceCommandParserTest {

    private final AttendanceCommandParser parser = new AttendanceCommandParser();

    @Test
    public void parse_valid_success() {
        String input = " "
                + PREFIX_NAME + "Alex Yeoh "
                + PREFIX_DATE + "2025-09-19 "
                + PREFIX_STATUS + "1";
        assertDoesNotThrow(() -> parser.parse(input));
    }

    @Test
    public void parse_missingPrefixes_failure() {
        // missing s/
        String input = " "
                + PREFIX_NAME + "Alex Yeoh "
                + PREFIX_DATE + "2025-09-19";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_extraPreamble_failure() {
        // non-empty preamble should fail
        String input = "oops "
                + PREFIX_NAME + "Alex "
                + PREFIX_DATE + "2025-09-19 "
                + PREFIX_STATUS + "1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }
}
