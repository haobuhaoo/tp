package seedu.address.logic.commands.homeworkcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.homework.Homework;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.UnmodifiableHwReminder;


/**
 * Marks homework task as done
 */
public class MarkUndoneHwCommand extends Command {
    public static final String COMMAND_WORD = "mark-undone";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks homework as undone for student.\n"
            + "Command format: " + COMMAND_WORD + " "
            + PREFIX_NAME + "NAME "
            + PREFIX_INDEX + "INDEX\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Marcus "
            + PREFIX_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "Marked homework as undone for %1$s: %2$s";
    public static final String MESSAGE_NO_PERSON_FOUND = "No student with given name";
    public static final String MESSAGE_INVALID_HW_INDEX = "Invalid homework index: %d (valid range: 1 to %d)";

    private final Name studentName;
    private final Index index;

    /**
     * Creates a MarkunDoneHW to mark a homework as undone in homework list
     */
    public MarkUndoneHwCommand(Name studentName, Index index) {
        requireNonNull(studentName);
        requireNonNull(index);
        this.studentName = studentName;
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

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

        ObservableList<Homework> homeworkList = target.getHomeworkList();
        int size = homeworkList.size();
        int zeroBased = index.getZeroBased();

        if (size == 0) {
            throw new CommandException(String.format(MESSAGE_INVALID_HW_INDEX, index.getOneBased(), 0));
        }
        if (zeroBased < 0 || zeroBased >= size) {
            throw new CommandException(String.format(MESSAGE_INVALID_HW_INDEX, index.getOneBased(), size));
        }

        Homework toUnmark = homeworkList.get(zeroBased);

        if (toUnmark.isDone()) {
            toUnmark.markUndone();
            UnmodifiableHwReminder undoneReminder = UnmodifiableHwReminder.of(target, toUnmark);
            model.addReminder(undoneReminder);
        }


        return new CommandResult(String.format(
                MESSAGE_SUCCESS,
                target.getName().fullName,
                toUnmark.getDescription()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkUndoneHwCommand)) {
            return false;
        }
        MarkUndoneHwCommand otherCommand = (MarkUndoneHwCommand) other;
        return studentName.equals(otherCommand.studentName)
                && index.equals(otherCommand.index);
    }
}
