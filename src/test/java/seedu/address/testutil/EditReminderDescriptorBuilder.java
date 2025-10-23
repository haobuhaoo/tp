package seedu.address.testutil;

import seedu.address.logic.commands.EditReminderCommand.EditReminderDescriptor;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;

/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {
    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditReminderDescriptor} with fields containing {@code reminder}'s details
     */
    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setDueDate(reminder.getDueDate());
        descriptor.setDescription(reminder.getDescription());
    }

    /**
     * Sets the {@code DueDate} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDueDate(String dueDate) {
        descriptor.setDueDate(new DueDate(dueDate));
        return this;
    }

    /**
     * Sets the {@code Description} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(new Description(description));
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}
