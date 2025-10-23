package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GroupDeleteCommandParserTest {

    private final GroupDeleteCommandParser parser = new GroupDeleteCommandParser();

    @Test
    public void parse_success() throws Exception {
        GroupDeleteCommand cmd = parser.parse(" g/Group A ");
        org.junit.jupiter.api.Assertions.assertNotNull(cmd);
    }

    @Test
    public void parse_missingPrefix_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" Group A "));
    }

    @Test
    public void parse_duplicatePrefix_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" g/A g/B "));
    }
}
