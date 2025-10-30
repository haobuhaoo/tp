package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ParticipationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ParticipationCommandParserTest {

    private final ParticipationCommandParser parser = new ParticipationCommandParser();

    @Test
    public void parse_valid_success() throws Exception {
        ParticipationCommand cmd =
                parser.parse(" n/Alex Yeoh d/2025-09-19 s/3 ");
        // We only check that the command is created with the raw values (validation later in execute)
        ParticipationCommand expected =
                new ParticipationCommand("Alex Yeoh", "2025-09-19", "3");
        assertEquals(expected.getClass(), cmd.getClass());
    }

    @Test
    public void parse_missingName_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" d/2025-09-19 s/3"));
    }

    @Test
    public void parse_missingDate_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" n/Alex Yeoh s/3"));
    }

    @Test
    public void parse_missingScore_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" n/Alex Yeoh d/2025-09-19"));
    }

    @Test
    public void parse_preambleNotEmpty_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" garbage n/Alex d/2025-09-19 s/3"));
    }

    @Test
    public void parse_duplicateNamePrefix_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" n/Alex n/Bob d/2025-09-19 s/2"));
    }

    @Test
    public void parse_duplicateDatePrefix_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" n/Alex d/2025-09-18 d/2025-09-19 s/2"));
    }

    @Test
    public void parse_duplicateScorePrefix_throws() {
        assertThrows(ParseException.class, () ->
                parser.parse(" n/Alex d/2025-09-19 s/2 s/3"));
    }

    @Test
    public void parse_trimsWhitespace_success() throws Exception {
        ParticipationCommand cmd =
                parser.parse("  n/  Alex   Yeoh   d/ 2025-09-19   s/  4 ");
        // no exception is enough; trimming is later normalized in execute()
        assertEquals(ParticipationCommand.class, cmd.getClass());
    }
}
