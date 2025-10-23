package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.GroupCreateCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class GroupCreateCommandParserTest {

    private final GroupCreateCommandParser parser = new GroupCreateCommandParser();

    @Test
    public void parse_success() throws Exception {
        GroupCreateCommand cmd = parser.parse(" g/  group   a  ");
        org.junit.jupiter.api.Assertions.assertNotNull(cmd);

        GroupCreateCommand cmd2 = parser.parse(" g/Group A ");
        org.junit.jupiter.api.Assertions.assertEquals(cmd, cmd2);
    }

    @Test
    public void parse_missingPrefix_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" Group A "));
    }

    @Test
    public void parse_duplicatePrefix_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" g/A g/B "));
    }

    @Test
    public void parse_invalidName_fail() {
        assertThrows(ParseException.class, () -> parser.parse(" g/ "));
        assertThrows(ParseException.class, () -> parser.parse(" g/" + "a".repeat(31)));
        assertThrows(ParseException.class, () -> parser.parse(" g/Group@A "));
    }
}
