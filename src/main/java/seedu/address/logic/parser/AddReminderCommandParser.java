package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;

/**
 * Parses input arguments and creates a new ReminderCommand object
 * <p>
 * This parser expects 2 prefixes to be provided in the argument in the form:
 * {@code add-reminder d/DATETIME r/DESCRIPTION}, where {@code DATETIME} refers to the date and time, and
 * {@code DESCRIPTION} refers to the description of the reminder to be added to the list.
 */
public class AddReminderCommandParser implements Parser<AddReminderCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ReminderCommand
     * and returns an ReminderCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATE, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_DATE, PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_DATE, PREFIX_DESC);
        DueDate dueDate = ParserUtil.parseDueDate(argMultimap.getValue(PREFIX_DATE).get());
        Description description = ParserUtil.parseReminderDescription(
                argMultimap.getValue(PREFIX_DESC).get());

        Reminder reminder = new Reminder(dueDate, description);

        return new AddReminderCommand(reminder);
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
