package seedu.address.model.reminder;

import java.util.Objects;

/**
 * Represents an unmodifiable reminder in the reminder list.
 * An unmodifiable reminder cannot be changed or deleted once created.
 */
public abstract class UnmodifiableReminder extends Reminder {
    /**
     * Constructs a {@code UnmodifiableReminder} with the given due date and description.
     *
     * @param dueDate     The due date of the reminder.
     * @param description The description of the reminder.
     */
    protected UnmodifiableReminder(DueDate dueDate, Description description) {
        super(dueDate, description);
    }

    /**
     * Returns false always for unmodifiable reminder.
     */
    @Override
    public boolean isModifiable() {
        return false;
    }

    /**
     * Returns true if this unmodifiable reminder is a payment reminder; false if it is a homework reminder.
     */
    public abstract boolean isPaymentReminder();

    /**
     * Returns the appropriate modify message for this unmodifiable reminder.
     */
    public abstract String getModifyMessage();

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnmodifiableReminder)) {
            return false;
        }

        UnmodifiableReminder otherReminder = (UnmodifiableReminder) other;
        return otherReminder.getDueDate().equals(this.getDueDate())
                && otherReminder.getDescription().equals(this.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getDueDate(), super.getDescription());
    }
}
