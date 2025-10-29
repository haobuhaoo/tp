package seedu.address.model.reminder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;

import seedu.address.model.person.Person;

/**
 * Represents an unmodifiable payment reminder in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class UnmodifiablePaymentReminder extends UnmodifiableReminder {
    public static final String TWO_LETTER_PREFIX = "PM";
    public static final String MESSAGE_YET_TO_PAY = "%1$s has yet to pay for the month of %2$s.";
    public static final String MESSAGE_TO_MODIFY =
            "To modify payment reminders, please update the payment status of the respective person.";

    /**
     * Constructs a {@code UnmodifiablePaymentReminder} with the given due date and description.
     *
     * @param dueDate     The due date of the reminder.
     * @param description The description of the reminder.
     */
    private UnmodifiablePaymentReminder(DueDate dueDate, Description description) {
        super(dueDate, description);
    }

    /**
     * Creates an unmodifiable reminder for a person's unpaid month.
     *
     * @param month          The month number (1-12).
     * @param personToUpdate The person who has yet to pay.
     * @param monthName      The name of the month.
     * @return An UnmodifiablePaymentReminder instance.
     */
    public static UnmodifiablePaymentReminder of(int month, Person personToUpdate, String monthName) {
        DueDate dueDate = parseDueDate(month);
        Description description = parseDescription(personToUpdate, monthName);
        return new UnmodifiablePaymentReminder(dueDate, description);
    }

    /**
     * Creates an unmodifiable reminder with the specified due date and description.
     */
    public static UnmodifiablePaymentReminder of(DueDate dueDate, Description description) {
        return new UnmodifiablePaymentReminder(dueDate, description);
    }

    /**
     * Parses the due date for the end of the specified month in the current year.
     *
     * @param month The month number (1-12).
     * @return A DueDate instance representing the end of the month.
     */
    private static DueDate parseDueDate(int month) {
        int currentYear = LocalDate.now().getYear();
        LocalDateTime endOfMonth = YearMonth.of(currentYear, month).atEndOfMonth().atTime(23, 59);
        String endOfMonthString = endOfMonth.format(DueDate.VALID_INPUT_DATETIME_FORMAT);
        return new DueDate(endOfMonthString);
    }

    /**
     * Parses the description indicating that the person has yet to pay for the specified month.
     *
     * @param person    The person who has yet to pay.
     * @param monthName The name of the month.
     * @return A Description instance with the payment status message.
     */
    private static Description parseDescription(Person person, String monthName) {
        String desc = String.format(MESSAGE_YET_TO_PAY, person.getName(), monthName);
        return new Description(desc);
    }

    @Override
    public boolean isPaymentReminder() {
        return true;
    }

    @Override
    public String getModifyMessage() {
        return MESSAGE_TO_MODIFY;
    }
}
