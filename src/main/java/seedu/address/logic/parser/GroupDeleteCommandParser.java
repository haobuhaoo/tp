package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;

import java.util.stream.Stream;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.GroupDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/** Parses input for {@link GroupDeleteCommand}. */
public class GroupDeleteCommandParser implements Parser<GroupDeleteCommand> {

    @Override
    public GroupDeleteCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_GROUP);

        if (!arePrefixesPresent(map, PREFIX_GROUP)
                || !map.getPreamble().isEmpty()
                || hasDuplicate(map, PREFIX_GROUP)) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, GroupDeleteCommand.MESSAGE_USAGE));
        }

        String raw = map.getValue(PREFIX_GROUP).get();
        GroupName name;
        try {
            name = GroupName.of(raw);
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }

        return new GroupDeleteCommand(name);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap m, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(p -> m.getValue(p).isPresent());
    }

    private static boolean hasDuplicate(ArgumentMultimap m, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(p -> m.getAllValues(p).size() != 1);
    }
}
