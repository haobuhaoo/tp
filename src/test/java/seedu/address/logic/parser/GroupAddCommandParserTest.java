package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GroupAddCommandParserTest {

    private final GroupAddCommandParser parser = new GroupAddCommandParser();

    @Test
    public void parse_success_multipleIndices() throws Exception {
        GroupAddCommand cmd = parser.parse(" g/Group A i/1 i/3 i/5 ");
        org.junit.jupiter.api.Assertions.assertNotNull(cmd);
    }

    @Test
    public void parse_missingGroup_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" i/1 "));
    }

    @Test
    public void parse_missingIndices_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" g/Group A "));
    }

    @Test
    public void parse_duplicateGroupPrefix_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" g/A g/B i/1 "));
    }

    @Test
    public void parse_preambleNotEmpty_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" nonsense g/A i/1 "));
    }
}
