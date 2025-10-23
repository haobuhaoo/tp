package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTH;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Marks a student as paid for a specific month.
 */
public class MarkPaidCommand extends Command {
    public static final String COMMAND_WORD = "mark-paid";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks a student as paid for a month.\n"
            + "Parameters: " + PREFIX_INDEX + "INDEX " + PREFIX_MONTH + "MONTH\n"
            + "MONTH must be a number from 1 to 12 (January to December)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_INDEX + "1 " + PREFIX_MONTH + "9";

    public static final String MESSAGE_MARK_PAID_SUCCESS = "Marked student as paid: %1$s\nMonth: %2$s\nPayment Status: %3$s";
    public static final String MESSAGE_ALREADY_PAID = "Student %1$s is already marked as paid for %2$s.";
    public static final String MESSAGE_INVALID_MONTH = "Invalid month. Month must be between 1 and 12.";

    private final Index index;
    private final int month;

    public MarkPaidCommand(Index index, int month) {
        requireNonNull(index);
        this.index = index;
        this.month = month;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (month < 1 || month > 12) {
            throw new CommandException(MESSAGE_INVALID_MONTH);
        }

        var lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid student index provided.");
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());

        if (personToUpdate.isPaidForMonth(month)) {
            throw new CommandException(
                    String.format(MESSAGE_ALREADY_PAID, personToUpdate.getName(), getMonthName(month)));
        }

        Person updatedPerson = personToUpdate.withPaymentStatus(month, true);
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String monthName = getMonthName(month);
        String statusDisplay = updatedPerson.getPaymentStatusDisplay();

        return new CommandResult(
                String.format(MESSAGE_MARK_PAID_SUCCESS, updatedPerson.getName(), monthName, statusDisplay));
    }

    private String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return monthNames[month - 1];
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MarkPaidCommand)) {
            return false;
        }
        MarkPaidCommand otherCommand = (MarkPaidCommand) other;
        return index.equals(otherCommand.index) && month == otherCommand.month;
    }
}