package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.model.person.StudentFieldsContainsKeywordsPredicate;

public class FindCommandParserTest {

    private SearchCommandParser parser = new SearchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // No k/ prefix
        assertParseFailure(parser, "     ", "Invalid search keyword.");

        // k/ but empty after normalization
        assertParseFailure(parser, "k/    ", "Invalid search keyword.");
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        SearchCommand expectedFindCommand =
                new SearchCommand(new StudentFieldsContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "k/Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n k/ Alice \n \t Bob  \t", expectedFindCommand);
    }

}
