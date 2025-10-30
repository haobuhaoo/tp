package seedu.address.logic.parser.homeworkparsers;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.homeworkcommands.MarkUndoneHwCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;


/**
 * Parses input arguments and creates a new {@code MarkUndoneHwCommand} object.
 * <p>
 * This parser expects two prefixes to be provided in the argument in the form:
 * {@code add-homework n/NAME d/DESCRIPTION}, where
 * {@code NAME} refers to the student's name,
 * {@code DESCRIPTION} refers to the homework description,
 */
public class MarkUndoneHwParser {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code MarkUndoneHwCommand}
     * and returns an MarkUndoneHw object for execution.
     *
     * @param args Full user input string containing the command arguments.
     * @return An {@code MarkUndoneHwCommand} object that is ready to be executed.
     * @throws ParseException If the user input does not conform to the expected format,
     *
     */
    public MarkUndoneHwCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_INDEX);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_INDEX)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUndoneHwCommand.MESSAGE_USAGE));
        }

        arePrefixesValid(argMultimap, PREFIX_NAME, PREFIX_INDEX);

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

        return new MarkUndoneHwCommand(name, index);
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

    /**
     * Checks that each given prefix appears exactly once
     * throws a {@link ParseException} if a prefix appears more than once.
     *
     * @param map The argument map containing prefixes and their values.
     * @param prefixes The prefixes to check.
     * @return {@code true} if all prefixes are present exactly once; {@code false} if any are missing.
     * @throws ParseException If any prefix appears more than once.
     */
    private static boolean arePrefixesValid(ArgumentMultimap map, Prefix... prefixes) throws ParseException {
        for (Prefix prefix : prefixes) {
            var values = map.getAllValues(prefix);
            if (values.size() > 1) {
                throw new ParseException("Multiple values specified for prefix: " + prefix.getPrefix());
            }
        }
        return true;
    }
}
