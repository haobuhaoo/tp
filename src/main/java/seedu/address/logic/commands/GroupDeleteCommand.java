package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupName;

/**
 * Deletes an existing group along with its memberships.
 * <p>
 * Format: {@code group-delete g/GROUP}
 */
public class GroupDeleteCommand extends Command {

    /** Command word for deleting a group. */
    public static final String COMMAND_WORD = "group-delete";

    /** Usage message shown on format errors. */
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a group.\n"
            + "Parameters: g/GROUP\n"
            + "Example: " + COMMAND_WORD + " g/Group A";

    /** Success message template. */
    public static final String MESSAGE_SUCCESS = "Group deleted: %1$s";

    /** Error shown when the referenced group does not exist. */
    public static final String MESSAGE_NOT_FOUND = "Group \"%1$s\" not found.";

    private final GroupName groupName;

    /**
     * Constructs a command to delete the group identified by {@code groupName}.
     *
     * @param groupName validated name of the group to delete (non-null).
     */
    public GroupDeleteCommand(GroupName groupName) {
        this.groupName = requireNonNull(groupName);
    }

    /**
     * Executes the command to delete the target group and its memberships.
     *
     * @param model backing model (non-null)
     * @return command result with a summary message
     * @throws CommandException if the group does not exist
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasGroup(groupName)) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, groupName));
        }

        model.deleteGroup(groupName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName));
    }

    /**
     * Returns true if both commands refer to the same group name.
     *
     * @param other other object
     * @return equality as per group name
     */
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GroupDeleteCommand
                && groupName.equals(((GroupDeleteCommand) other).groupName));
    }
}
