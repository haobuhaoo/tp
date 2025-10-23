package seedu.address.logic.commands.HomeworkCommands;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.homework.Homework;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

/**
 * Deletes a homework item from a student's homework list.
 */
public class DeleteHomeworkCommand extends Command {
    public static final String COMMAND_WORD = "delete-homework";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a homework item for a student. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DESC + "DESCRIPTION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Marcus "
            + PREFIX_DESC + "Math Worksheet 1";

    public static final String MESSAGE_SUCCESS = "Deleted homework for %1$s: %2$s";
    public static final String MESSAGE_NO_PERSON_FOUND = "No student with given name";
    public static final String MESSAGE_NO_HW_FOUND = "No such homework in list";

    private final Name studentName;
    private final String description;

    /**
     * Creates a DeleteHomeworkCommand to remove a homework from a student's list.
     */
    public DeleteHomeworkCommand(Name studentName, String description) {
        requireNonNull(studentName);
        requireNonNull(description);
        this.studentName = studentName;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        //Able to delete homework after search command
        List<Person> lastShownList = model.getFilteredPersonList();
        Person target = null;
        for (Person p : lastShownList) {
            if (p.getName().equals(studentName)) {
                target = p;
                break;
            }
        }

        if (target == null) {
            throw new CommandException(MESSAGE_NO_PERSON_FOUND);
        }

        Homework matched = null;
        for (Homework hw : target.getHomeworkList()) {
            if (hw.getDescription().equalsIgnoreCase(description)) {
                matched = hw;
                break;
            }
        }

        if (matched == null) {
            throw new CommandException(MESSAGE_NO_HW_FOUND);
        }

        target.removeHomework(matched);

        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                target.getName().fullName,
                matched.getDescription()
        ));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteHomeworkCommand)) {
            return false;
        }

        DeleteHomeworkCommand otherCommand = (DeleteHomeworkCommand) other;
        return studentName.equals(otherCommand.studentName)
                && description.equals(otherCommand.description);
    }
}