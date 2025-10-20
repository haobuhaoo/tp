package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Reminder in the reminder list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {
    private final DueDate dueDate;
    private final Detail message;

    /**
     * Every field must be present and not null.
     */
    public Reminder(DueDate dueDate, Detail message) {
        requireAllNonNull(dueDate, message);
        this.dueDate = dueDate;
        this.message = message;
    }

    public DueDate getDuedate() {
        return dueDate;
    }

    public Detail getDetail() {
        return message;
    }

    /**
     * Returns true if both reminders have the same due date and detail.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Reminder)) {
            return false;
        }

        Reminder otherReminder = (Reminder) other;
        return otherReminder.getDuedate().equals(getDuedate())
                && otherReminder.getDetail().equals(getDetail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, message);
    }

    @Override
    public String toString() {
        return "[" + dueDate + "] " + message;
    }
}
