package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_KEYWORD;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteReminderCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.reminder.ReminderFieldsContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new DeleteCommand object
 * <p>
 * This parser expects an index to be provided as an argument in the form:
 * {@code delete i/INDEX or k/KEYWORD}, where {@code INDEX} refers to the position of the reminder
 * in the displayed list that should be deleted, and {@code KEYWORD} refers to the string present
 * in the reminder's due date or description.
 */
public class DeleteReminderCommandParser implements Parser<DeleteReminderCommand> {
    private static final Pattern VALID_KEYWORDS = Pattern.compile("[A-Za-z0-9 ':]+");

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteReminderCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_KEYWORD);

        boolean hasIndex = argMultimap.getValue(PREFIX_INDEX).isPresent();
        boolean hasKeyword = argMultimap.getValue(PREFIX_KEYWORD).isPresent();

        if (hasIndex == hasKeyword) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteReminderCommand.MESSAGE_USAGE));
        }

        if (hasIndex) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX);
            try {
                Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
                return new DeleteReminderCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteReminderCommand.MESSAGE_USAGE), pe);
            }
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_KEYWORD);
        String raw = argMultimap.getValue(PREFIX_KEYWORD).get().trim().replaceAll("\\s+", " ");
        if (raw.isEmpty() || !VALID_KEYWORDS.matcher(raw).matches()) {
            throw new ParseException("Invalid search keyword.");
        }

        List<String> tokens = Arrays.stream(raw.split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        return new DeleteReminderCommand(new ReminderFieldsContainsKeywordsPredicate(tokens));
    }
}
