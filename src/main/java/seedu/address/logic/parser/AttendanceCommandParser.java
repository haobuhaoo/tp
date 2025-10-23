package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS; // we still reuse PREFIX_STATUS as 's/'

import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/** Parses input arguments and creates a new AttendanceCommand object. */
public class AttendanceCommandParser implements Parser<AttendanceCommand> {

    @Override
    public AttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_STATUS);

        if (!arePrefixesPresent(map, PREFIX_NAME, PREFIX_DATE, PREFIX_STATUS) || !map.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AttendanceCommand.MESSAGE_USAGE));
        }

        String name = map.getValue(PREFIX_NAME).get(); // raw; validated in command
        String date = map.getValue(PREFIX_DATE).get(); // raw; validated in command
        String score = map.getValue(PREFIX_STATUS).get(); // raw; will be parsed as int 0..5 in command

        return new AttendanceCommand(name, date, score);
    }

    /** Returns true if all prefixes are present in the given ArgumentMultimap. */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
