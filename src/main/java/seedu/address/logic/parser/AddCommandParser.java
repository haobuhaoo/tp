package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Parses input arguments and creates a new AddCommand object
 * <p>
 * This parser expects 3 prefixes to be provided in the argument in the form:
 * {@code add-student n/NAME p/PHONE_NUMBER t/LESSON_TIME}, where {@code NAME} refers to the name,
 * {@code PHONE_NUMBER} refers to the phone number, and {@code LESSON_TIME} refers to the lesson
 * time of the student to be added to the list.
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_LESSON_TIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_LESSON_TIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_LESSON_TIME);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        LessonTime lessonTime = ParserUtil.parseLessonTime(argMultimap.getValue(PREFIX_LESSON_TIME).get());

        Person person = new Person(name, phone, lessonTime);

        return new AddCommand(person);
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
