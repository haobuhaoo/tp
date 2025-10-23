package seedu.address.testutil;

import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {
    public static final String DEFAULT_DUEDATE = "2025-11-25 1330";
    public static final String DEFAULT_DESCRIPTION = "Payment due soon.";

    private DueDate dueDate;
    private Description description;

    /**
     * Creates a {@code ReminderBuilder} with the default details.
     */
    public ReminderBuilder() {
        dueDate = new DueDate(DEFAULT_DUEDATE);
        description = new Description(DEFAULT_DESCRIPTION);
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(Reminder reminderToCopy) {
        dueDate = reminderToCopy.getDueDate();
        description = reminderToCopy.getDescription();
    }

    /**
     * Sets the {@code DueDate} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDueDate(String dueDate) {
        this.dueDate = new DueDate(dueDate);
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDescription(String description) {
        this.description = new Description(description);
        return this;
    }

    public Reminder build() {
        return new Reminder(dueDate, description);
    }
}
