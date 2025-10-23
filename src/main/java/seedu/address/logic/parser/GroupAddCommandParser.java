package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.GroupAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.group.GroupName;

/** Parses input for {@link GroupAddCommand}. */
public class GroupAddCommandParser implements Parser<GroupAddCommand> {

    @Override
    public GroupAddCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_INDEX);

        // require: exactly one g/, at least one i/, no preamble, no duplicate g/
        if (!arePresent(map, PREFIX_GROUP, PREFIX_INDEX)
                || !map.getPreamble().isEmpty()
                || hasDuplicate(map, PREFIX_GROUP)) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        }

        GroupName groupName;
        try {
            groupName = GroupName.of(map.getValue(PREFIX_GROUP).get());
        } catch (IllegalArgumentException ex) {
            throw new ParseException(ex.getMessage());
        }

        List<String> rawIdx = map.getAllValues(PREFIX_INDEX);
        if (rawIdx.isEmpty()) {
            throw new ParseException(String.format(
                    Messages.MESSAGE_INVALID_COMMAND_FORMAT, GroupAddCommand.MESSAGE_USAGE));
        }

        List<Index> indices = new ArrayList<>(rawIdx.size());
        for (String s : rawIdx) {
            indices.add(ParserUtil.parseIndex(s));
        }

        return new GroupAddCommand(groupName, indices);
    }

    private static boolean arePresent(ArgumentMultimap m, Prefix... ps) {
        return Stream.of(ps).allMatch(p -> m.getValue(p).isPresent() || p == PREFIX_INDEX);
    }

    private static boolean hasDuplicate(ArgumentMultimap m, Prefix... ps) {
        return Stream.of(ps).anyMatch(p -> p == PREFIX_GROUP && m.getAllValues(p).size() != 1);
    }
}
