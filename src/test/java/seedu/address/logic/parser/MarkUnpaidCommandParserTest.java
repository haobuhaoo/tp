package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkUnpaidCommand;

public class MarkUnpaidCommandParserTest {

    private MarkUnpaidCommandParser parser = new MarkUnpaidCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Valid input with index and month
        assertParseSuccess(parser, " i/1 m/5", new MarkUnpaidCommand(INDEX_FIRST_PERSON, 5));

        // Valid input with different month
        assertParseSuccess(parser, " i/1 m/12", new MarkUnpaidCommand(INDEX_FIRST_PERSON, 12));

        // Valid input with different index
        assertParseSuccess(parser, " i/1 m/3", new MarkUnpaidCommand(INDEX_FIRST_PERSON, 3));
    }

    @Test
    public void parse_missingParts_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnpaidCommand.MESSAGE_USAGE);

        // no index
        assertParseFailure(parser, " m/5", expectedMessage);

        // no month
        assertParseFailure(parser, " i/1", expectedMessage);

        // no prefixes
        assertParseFailure(parser, "1 5", expectedMessage);

        // empty string
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid index (not a number)
        assertParseFailure(parser, " i/a m/5", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid month (not a number)
        assertParseFailure(parser, " i/1 m/abc", ParserUtil.MESSAGE_INVALID_MONTH);

        // invalid index (zero)
        assertParseFailure(parser, " i/0 m/5", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid month (out of range)
        assertParseFailure(parser, " i/1 m/13", ParserUtil.MESSAGE_INVALID_MONTH);

        // invalid month (negative)
        assertParseFailure(parser, " i/1 m/-1", ParserUtil.MESSAGE_INVALID_MONTH);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnpaidCommand.MESSAGE_USAGE);

        // duplicate index
        assertParseFailure(parser, " i/1 i/2 m/5",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_INDEX));

        // duplicate month
        assertParseFailure(parser, " i/1 m/5 m/6",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_MONTH));
    }

    @Test
    public void parse_preamblePresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnpaidCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "some text i/1 m/5", expectedMessage);
    }
}
