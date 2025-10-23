package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS; // we still reuse PREFIX_STATUS as 's/'

import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AttendanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses arguments for {@link AttendanceCommand}.
 *
 * Format: {@code attendance n/NAME d/YYYY-MM-DD s/1|0}
 * Rules: n/, d/, s/ must each appear exactly once; values are validated in the command.
 */
public class AttendanceCommandParser implements Parser<AttendanceCommand> {
    @Override
    public AttendanceCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_STATUS);

        if (!arePrefixesPresent(map, PREFIX_NAME, PREFIX_DATE, PREFIX_STATUS)
                || !map.getPreamble().isEmpty()
                || hasDuplicatePrefixes(map, PREFIX_NAME, PREFIX_DATE, PREFIX_STATUS)) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, AttendanceCommand.MESSAGE_USAGE));
        }

        String name = map.getValue(PREFIX_NAME).get();
        String date = map.getValue(PREFIX_DATE).get();
        String status = map.getValue(PREFIX_STATUS).get();

        return new AttendanceCommand(name, date, score);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap m, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(p -> m.getValue(p).isPresent());
    }

    /**
     * Disallow multiple occurrences of the same prefix: each of n/, d/, s/ must appear at most once.
     */
    private static boolean hasDuplicatePrefixes(ArgumentMultimap m, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(p -> m.getAllValues(p).size() != 1);
    }
}
