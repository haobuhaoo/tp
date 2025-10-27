package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.DUEDATE_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DUEDATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DUEDATE_2;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_REMINDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_REMINDER;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.testutil.EditReminderDescriptorBuilder;

public class EditReminderCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE);

    private EditReminderCommandParser parser = new EditReminderCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_DUEDATE_1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " " + PREFIX_INDEX + "1", EditReminderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, " " + PREFIX_INDEX + "-5" + DUEDATE_DESC_2, ParserUtil.MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, " " + PREFIX_INDEX + "0" + DUEDATE_DESC_2, ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as index
        assertParseFailure(parser, " " + PREFIX_INDEX + "1 some random string", ParserUtil.MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as index
        assertParseFailure(parser, "1 e/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid due date
        assertParseFailure(parser, " " + PREFIX_INDEX + "1" + INVALID_DUEDATE_DESC,
                DueDate.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, " " + PREFIX_INDEX + "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_CONSTRAINTS);

        // invalid description followed by valid due date
        assertParseFailure(parser, " " + PREFIX_INDEX + "1" + INVALID_DESCRIPTION_DESC + DUEDATE_DESC_2,
                Description.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, " " + PREFIX_INDEX + "1" + INVALID_DUEDATE_DESC
                + INVALID_DESCRIPTION_DESC, DueDate.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_REMINDER;
        String userInput = " " + PREFIX_INDEX + targetIndex.getOneBased() + DUEDATE_DESC_2 + DESCRIPTION_DESC_1;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withDueDate(VALID_DUEDATE_2)
                .withDescription(VALID_DESCRIPTION_1).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_REMINDER;
        String userInput = " " + PREFIX_INDEX + targetIndex.getOneBased() + DESCRIPTION_DESC_2;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_2).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_REMINDER;
        String userInput = " " + PREFIX_INDEX + targetIndex.getOneBased() + DUEDATE_DESC_2 + INVALID_DUEDATE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid followed by valid
        userInput = " " + PREFIX_INDEX + targetIndex.getOneBased() + DESCRIPTION_DESC_2
                + INVALID_DESCRIPTION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));

        // multiple valid fields repeated
        userInput = " " + PREFIX_INDEX + targetIndex.getOneBased() + DUEDATE_DESC_2 + DESCRIPTION_DESC_1
                + DUEDATE_DESC_1 + DESCRIPTION_DESC_2;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE, PREFIX_DESC));

        // multiple invalid values
        userInput = " " + PREFIX_INDEX + targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + INVALID_DUEDATE_DESC
                + INVALID_DESCRIPTION_DESC + INVALID_DUEDATE_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE, PREFIX_DESC));
    }
}
