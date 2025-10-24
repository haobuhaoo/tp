package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditReminderCommand;
import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 * <p>
 * This parser expects an index and at least 1 other prefix to be provided in the argument in the form:
 * {@code edit-student i/INDEX [d/DATETIME] [r/DESCRIPTION]}, where {@code INDEX} refers to the
 * position of the reminder in the displayed list that is to be edited, and {@code DATETIME} and
 * {@code DESCRIPTION} refer to the new values of the respective fields to be updated.
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_DATE, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditReminderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_DATE, PREFIX_DESC);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

        EditReminderDescriptor editReminderDescriptor = new EditReminderDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editReminderDescriptor.setDueDate(ParserUtil.parseDueDate(
                    argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editReminderDescriptor.setDescription(ParserUtil.parseReminderDescription(
                    argMultimap.getValue(PREFIX_DESC).get()));
        }

        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }

    /**
     * Returns {@code true} if all specified prefixes are present in the given {@link ArgumentMultimap},
     * meaning each prefix has a non-empty {@code Optional} value.
     *
     * @param argumentMultimap the mapping of prefixes to their arguments, obtained from tokenizing user input.
     * @param prefixes         the prefixes to check for presence.
     * @return {@code true} if all specified prefixes are present and non-empty; {@code false} otherwise.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
