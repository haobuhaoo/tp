package seedu.address.logic.parser.homeworkparsertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.homeworkcommands.DeleteHomeworkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.homeworkparsers.DeleteHomeworkCommandParser;
import seedu.address.model.person.Name;

public class DeleteHomeworkParserTest {

    private DeleteHomeworkCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new DeleteHomeworkCommandParser();
    }

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String input = " n/Alex Yeoh i/1";
        DeleteHomeworkCommand expected = new DeleteHomeworkCommand(
                new Name("Alex Yeoh"), Index.fromOneBased(1));

        DeleteHomeworkCommand actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_orderIrrelevant_success() throws Exception {
        String input = " i/1 n/Bernice Yu";
        DeleteHomeworkCommand expected = new DeleteHomeworkCommand(
                new Name("Bernice Yu"), Index.fromOneBased(1));

        assertEquals(expected, parser.parse(input));
    }


    @Test
    public void parse_duplicateNamePrefix_throwsParseException() {
        String input = " n/Alex Yeoh i/1 n/Bernice Yu";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_duplicateIndexPrefix_throwsParseException() {
        String input = " n/Alex Yeoh i/1 i/1";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }


    @Test
    public void parse_missingNamePrefix_failure() {
        assertInvalidFormat(" i/1");
    }

    @Test
    public void parse_missingIndexPrefix_failure() {
        assertInvalidFormat(" n/Alex Yeoh");
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertInvalidFormat(" randomText n/Alex Yeoh i/1");
    }

    @Test
    public void parse_indexNonNumeric_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/Alex Yeoh i/abc"));
    }

    @Test
    public void parse_indexDecimal_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/Alex Yeoh i/1.5"));
    }

    @Test
    public void parse_indexMissingValue_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/Alex Yeoh i/"));
    }

    @Test
    public void parse_indexWhitespaceOnly_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/Alex Yeoh i/ "));
    }

    @Test
    public void parse_indexZero_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" n/Alex Yeoh i/0"));
    }


    private void assertInvalidFormat(String input) {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteHomeworkCommand.MESSAGE_USAGE),
                ex.getMessage());
    }
}
