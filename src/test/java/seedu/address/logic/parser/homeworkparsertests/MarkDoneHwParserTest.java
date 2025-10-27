package seedu.address.logic.parser.homeworkparsertests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.homeworkcommands.MarkDoneHwCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.homeworkparsers.MarkDoneHwParser;
import seedu.address.model.person.Name;

public class MarkDoneHwParserTest {

    private MarkDoneHwParser parser;

    @BeforeEach
    public void setUp() {
        parser = new MarkDoneHwParser();
    }


    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        String input = " n/Alex Yeoh desc/Math Homework";
        MarkDoneHwCommand expected = new MarkDoneHwCommand(
                new Name("Alex Yeoh"), "Math Homework");

        MarkDoneHwCommand actual = parser.parse(input);
        assertEquals(expected, actual);
    }

    @Test
    public void parse_orderIrrelevant_success() throws Exception {
        String input = " desc/Science WS n/Bernice Yu";
        MarkDoneHwCommand expected = new MarkDoneHwCommand(
                new Name("Bernice Yu"), "Science WS");

        assertEquals(expected, parser.parse(input));
    }

    @Test
    public void parse_descriptionWhitespaceCollapsed_success() throws Exception {
        String input = " n/Alex Yeoh desc/  Math\t   Homework   2  ";
        MarkDoneHwCommand expected = new MarkDoneHwCommand(
                new Name("Alex Yeoh"), "Math Homework 2");

        assertEquals(expected, parser.parse(input));
    }



    @Test
    public void parse_duplicateNamePrefix_throwsParseException() {
        String input = " n/Alex Yeoh desc/Math n/Bernice Yu";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

    @Test
    public void parse_duplicateDescPrefix_throwsParseException() {
        String input = " n/Alex Yeoh desc/Math desc/Science";
        assertThrows(ParseException.class, () -> parser.parse(input));
    }


    @Test
    public void parse_missingNamePrefix_failure() {
        assertInvalidFormat(" desc/Math Homework");
    }

    @Test
    public void parse_missingDescPrefix_failure() {
        assertInvalidFormat(" n/Alex Yeoh");
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        assertInvalidFormat(" randomText n/Alex Yeoh desc/Math Homework");
    }


    private void assertInvalidFormat(String input) {
        ParseException ex = assertThrows(ParseException.class, () -> parser.parse(input));
        assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneHwCommand.MESSAGE_USAGE),
                ex.getMessage());
    }
}
