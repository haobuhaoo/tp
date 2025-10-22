package seedu.address.logic.parser.Homework;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.HomeworkCommands.AddHomeworkCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDate;
import java.util.stream.Stream;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import seedu.address.model.homework.Homework;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new {@code AddHomeworkCommand} object.
 * <p>
 * This parser expects three prefixes to be provided in the argument in the form:
 * {@code add-homework n/NAME d/DESCRIPTION by/DEADLINE}, where
 * {@code NAME} refers to the student's name,
 * {@code DESCRIPTION} refers to the homework description,
 * and {@code DEADLINE} refers to the due date of the homework in {@code yyyy-MM-dd} format.
 */

public class AddHomeworkCommandParser implements Parser<AddHomeworkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code AddHomeworkCommand}
     * and returns an AddHomeworkCommand object for execution.
     *
     * @param args Full user input string containing the command arguments.
     * @return An {@code AddHomeworkCommand} object that is ready to be executed.
     * @throws ParseException If the user input does not conform to the expected format,
     *
     */
    public AddHomeworkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESC, PREFIX_DEADLINE);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DESC, PREFIX_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddHomeworkCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        LocalDate deadline = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DEADLINE).get());


        Homework hw = new Homework(description, deadline);
        return new AddHomeworkCommand(name, hw);
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


