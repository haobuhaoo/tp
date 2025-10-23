package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MarkUnpaidCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkUnpaidCommand object
 */
public class MarkUnpaidCommandParser implements Parser<MarkUnpaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkUnpaidCommand
     * @return a MarkUnpaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MarkUnpaidCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_MONTH);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_MONTH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkUnpaidCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_MONTH);

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        int month = ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get());

        return new MarkUnpaidCommand(index, month);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
