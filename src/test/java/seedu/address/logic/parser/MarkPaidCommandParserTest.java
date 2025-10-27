package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.MarkPaidCommand;

public class MarkPaidCommandParserTest {

    private MarkPaidCommandParser parser = new MarkPaidCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        assertParseSuccess(parser, " i/1 m/5", new MarkPaidCommand(INDEX_FIRST_PERSON, 5));

        assertParseSuccess(parser, " i/1 m/12", new MarkPaidCommand(INDEX_FIRST_PERSON, 12));

        assertParseSuccess(parser, " i/1 m/3", new MarkPaidCommand(INDEX_FIRST_PERSON, 3));
    }

    @Test
    public void parse_missingParts_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE);

        // no index specified
        assertParseFailure(parser, " m/5", expectedMessage);

        // no month specified
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

        // invalid month
        assertParseFailure(parser, " i/1 m/-1", ParserUtil.MESSAGE_INVALID_MONTH);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE);

        // duplicate index prefix
        assertParseFailure(parser, " i/1 i/2 m/5",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_INDEX));

        // duplicate month prefix
        assertParseFailure(parser, " i/1 m/5 m/6",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_MONTH));
    }

    @Test
    public void parse_preamblePresent_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkPaidCommand.MESSAGE_USAGE);

        // preamble present
        assertParseFailure(parser, "some text i/1 m/5", expectedMessage);
    }
}
