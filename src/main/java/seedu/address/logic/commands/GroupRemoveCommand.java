package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupName;
import seedu.address.model.person.Person;

/**
 * Removes one or more students from a group.
 * <p>
 * Format: {@code group-remove g/GROUP i/INDEX [i/INDEX ...]}
 */
public class GroupRemoveCommand extends Command {

    /** Command word for removing members from a group. */
    public static final String COMMAND_WORD = "group-remove";

    /** Usage message shown on format errors. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes students from a group.\n"
            + "Parameters: g/GROUP i/INDEX [i/INDEX ...]\n"
            + "Example: " + COMMAND_WORD + " g/Group A i/2 i/3";

    /** Success message template. */
    public static final String MESSAGE_SUCCESS = "Removed %1$d student(s) from group: %2$s";

    /** Error shown when the referenced group does not exist. */
    public static final String MESSAGE_GROUP_NOT_FOUND = "Group \"%1$s\" not found.";

    private final GroupName groupName;
    private final List<Index> targetIndices;

    /**
     * Constructs a command that removes the given displayed-list {@code targetIndices} from {@code groupName}.
     *
     * @param groupName     validated group name to remove members from (non-null).
     * @param targetIndices one or more 1-based indices from the current filtered person list (non-null).
     */
    public GroupRemoveCommand(GroupName groupName, List<Index> targetIndices) {
        this.groupName = requireNonNull(groupName);
        this.targetIndices = List.copyOf(requireNonNull(targetIndices));
    }

    /**
     * Executes the command to remove the specified displayed-list indices from the target group.
     *
     * @param model backing model (non-null)
     * @return command result with a summary message
     * @throws CommandException if the group does not exist or any index is invalid
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasGroup(groupName)) {
            throw new CommandException(String.format(MESSAGE_GROUP_NOT_FOUND, groupName));
        }

        var shown = model.getFilteredPersonList();
        if (shown.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
        }

        List<Person> members = new ArrayList<>();
        for (Index idx : targetIndices) {
            int zero = idx.getZeroBased();
            if (zero < 0 || zero >= shown.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX);
            }
            members.add(shown.get(zero));
        }

        model.removeFromGroup(groupName, members);
        return new CommandResult(String.format(MESSAGE_SUCCESS, members.size(), groupName));
    }

    /**
     * Returns true if both commands target the same group and indices.
     *
     * @param other other object
     * @return equality as per group name and indices
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GroupRemoveCommand)
                && groupName.equals(((GroupRemoveCommand) other).groupName)
                && targetIndices.equals(((GroupRemoveCommand) other).targetIndices);
    }
}
