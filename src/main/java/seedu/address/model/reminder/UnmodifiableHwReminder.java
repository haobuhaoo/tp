package seedu.address.model.reminder;

import seedu.address.model.homework.Homework;
import seedu.address.model.person.Person;

/**
 * Represents an unmodifiable homework reminder in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UnmodifiableHwReminder extends UnmodifiableReminder {
    public static final String TWO_LETTER_PREFIX = "HW";
    public static final String MESSAGE_HOMEWORK_UNDONE =
            "%1$s has yet to complete the homework \"%2$s\" due on %3$s.";
    public static final String MESSAGE_TO_MODIFY =
            "To modify homework reminders, please update the homework status of the respective person.";

    /**
     * Constructs a {@code UnmodifiableHwReminder} with the given due date and description.
     *
     * @param dueDate     The due date of the reminder.
     * @param description The description of the reminder.
     */
    private UnmodifiableHwReminder(DueDate dueDate, Description description) {
        super(dueDate, description);
    }

    /**
     * Creates an unmodifiable reminder for a person's undone homework.
     *
     * @param person   The person who has yet to complete the homework.
     * @param homework The homework that is yet to be completed.
     * @return An UnmodifiableHwReminder instance.
     */
    public static UnmodifiableHwReminder of(Person person, Homework homework) {
        DueDate dueDate = new DueDate(homework.getDeadline().format(DueDate.VALID_INPUT_DATE_FORMAT));
        Description description = parseDescription(person, homework);
        return new UnmodifiableHwReminder(dueDate, description);
    }

    /**
     * Creates an unmodifiable reminder with the specified due date and description.
     */
    public static UnmodifiableHwReminder of(DueDate dueDate, Description description) {
        return new UnmodifiableHwReminder(dueDate, description);
    }

    /**
     * Parses the description indicating that the person has yet to complete the homework.
     */
    private static Description parseDescription(Person person, Homework homework) {
        String desc = String.format(MESSAGE_HOMEWORK_UNDONE, person.getName(),
                homework.getDescription(), homework.getDeadline().format(DueDate.VALID_OUTPUT_DATE_FORMAT));
        return new Description(desc);
    }

    @Override
    public boolean isPaymentReminder() {
        return false;
    }

    @Override
    public String getModifyMessage() {
        return MESSAGE_TO_MODIFY;
    }
}
