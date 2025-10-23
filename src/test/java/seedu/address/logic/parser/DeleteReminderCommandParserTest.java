package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.model.reminder.ReminderFieldsContainsKeywordsPredicate;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the DeleteReminderCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteReminderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteReminderCommandParserTest {
    private DeleteReminderCommandParser parser = new DeleteReminderCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteReminderCommand() {
        assertParseSuccess(parser, " " + PREFIX_INDEX + "1",
                new DeleteReminderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgsAfterIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(parser, " x/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_zeroIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_INDEX + "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_negativeIndex_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_INDEX + "-5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceHandling_validArgs() {
        assertParseSuccess(parser, "   " + PREFIX_INDEX + "  1  ",
                new DeleteReminderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_validKeyword_returnsDeleteReminderCommand() {
        ReminderFieldsContainsKeywordsPredicate predicate =
                new ReminderFieldsContainsKeywordsPredicate(Arrays.asList("Oct"));
        assertParseSuccess(parser, " " + PREFIX_KEYWORD + "Oct", new DeleteReminderCommand(predicate));
    }

    @Test
    public void parse_multipleWhiteSpace_returnsDeleteReminderCommand() {
        ReminderFieldsContainsKeywordsPredicate predicate =
                new ReminderFieldsContainsKeywordsPredicate(Arrays.asList("21", "Oct"));
        assertParseSuccess(parser, "   " + PREFIX_KEYWORD + "   21   Oct   ",
                new DeleteReminderCommand(predicate));
    }

    @Test
    public void parse_emptyKeyword_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_KEYWORD + "   ",
                "Invalid search keyword.");
    }

    @Test
    public void parse_bothIndexAndKeywordPresent_throwsParseException() {
        assertParseFailure(parser,
                " " + PREFIX_INDEX + "1 " + PREFIX_KEYWORD + "21 Oct",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateKeywordPrefixes_throwsParseException() {
        assertParseFailure(parser,
                " " + PREFIX_KEYWORD + "21 " + PREFIX_KEYWORD + "Oct",
                Messages.MESSAGE_DUPLICATE_FIELDS + "k/");
    }

    @Test
    public void parse_neitherIndexNorKeywordPresent_throwsParseException() {
        assertParseFailure(parser,
                " delete-reminder",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE));
    }
}
