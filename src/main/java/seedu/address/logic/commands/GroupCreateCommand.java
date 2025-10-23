package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.GroupName;

/**
 * Creates a new group.
 * Format: group-create g/GROUP
 */
public class GroupCreateCommand extends Command {

    public static final String COMMAND_WORD = "group-create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a group.\n"
            + "Parameters: g/GROUP\n"
            + "Example: " + COMMAND_WORD + " g/Group A";

    public static final String MESSAGE_SUCCESS = "New group created: %1$s";
    public static final String MESSAGE_DUPLICATE = "Group \"%1$s\" already exists.";

    private final GroupName groupName;

    public GroupCreateCommand(GroupName groupName) {
        this.groupName = requireNonNull(groupName);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGroup(groupName)) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE, groupName));
        }

        model.createGroup(groupName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof GroupCreateCommand
                && groupName.equals(((GroupCreateCommand) other).groupName));
    }
}
