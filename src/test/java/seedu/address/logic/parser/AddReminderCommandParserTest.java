package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUEDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderCommandParserTest {
    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Reminder expectedReminder = new ReminderBuilder().withDueDate(VALID_DUEDATE_2)
                .withDescription(VALID_DESCRIPTION_2).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + DUEDATE_DESC_2 + DESCRIPTION_DESC_2,
                new AddReminderCommand(expectedReminder));
    }

    @Test
    public void parse_repeatedValue_failure() {
        String validExpectedReminderString = DUEDATE_DESC_2 + DESCRIPTION_DESC_2;

        // multiple due date
        assertParseFailure(parser, DUEDATE_DESC_1 + validExpectedReminderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple description
        assertParseFailure(parser, DESCRIPTION_DESC_1 + validExpectedReminderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedReminderString + DUEDATE_DESC_1 + DESCRIPTION_DESC_1
                        + validExpectedReminderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE, PREFIX_DESC));

        // invalid value followed by valid value

        // invalid due date
        assertParseFailure(parser, INVALID_DUEDATE_DESC + validExpectedReminderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid description
        assertParseFailure(parser, INVALID_DESCRIPTION_DESC + validExpectedReminderString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));

        // valid value followed by invalid value

        // invalid due date
        assertParseFailure(parser, validExpectedReminderString + INVALID_DUEDATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid description
        assertParseFailure(parser, validExpectedReminderString + INVALID_DESCRIPTION_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing due date prefix
        assertParseFailure(parser, VALID_DUEDATE_1 + DESCRIPTION_DESC_1, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, DUEDATE_DESC_1 + VALID_DESCRIPTION_1, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_DUEDATE_1 + VALID_DESCRIPTION_1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid due date
        assertParseFailure(parser, INVALID_DUEDATE_DESC + DESCRIPTION_DESC_1, DueDate.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, DUEDATE_DESC_1 + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_DUEDATE_DESC + INVALID_DESCRIPTION_DESC,
                DueDate.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + DUEDATE_DESC_1 + DESCRIPTION_DESC_1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
    }
}
