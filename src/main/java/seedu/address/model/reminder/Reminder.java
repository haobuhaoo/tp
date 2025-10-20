package seedu.address.model.reminder;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Reminder in the reminder list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Reminder {
    private final DueDate dueDate;
    private final Description description;

    /**
     * Every field must be present and not null.
     */
    public Reminder(DueDate dueDate, Description description) {
        requireAllNonNull(dueDate, description);
        this.dueDate = dueDate;
        this.description = description;
    }

    public DueDate getDuedate() {
        return dueDate;
    }

    public Description getDescription() {
        return description;
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
                && otherReminder.getDescription().equals(getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("dueDate", dueDate)
                .add("description", description)
                .toString();
    }
}
