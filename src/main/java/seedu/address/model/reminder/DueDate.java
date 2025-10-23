package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a Reminder's due date in the reminder list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDueDate(String)}
 */
public class DueDate implements Comparable<DueDate> {
    public static final String MESSAGE_CONSTRAINTS = """
            Due date should be in either these formats:
            1. YYYY-MM-DD (for date only)
            2. YYYY-MM-DD HHMM (for date and time in 24h format)""";

    // Date only format
    public static final DateTimeFormatter VALID_INPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    public static final DateTimeFormatter VALID_OUTPUT_DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH);
    public static final String VALIDATION_DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}";

    // DateTime format
    public static final DateTimeFormatter VALID_INPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm", Locale.ENGLISH);
    public static final DateTimeFormatter VALID_OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm a", Locale.ENGLISH);
    public static final String VALIDATION_DATETIME_REGEX = "\\d{4}-\\d{2}-\\d{2} \\d{4}";

    private final LocalDate date;
    private final LocalDateTime dateTime;
    private final boolean isDateOnly;

    /**
     * Constructs a {@code DueDate}.
     *
     * @param dueDate A valid due date string. Can be either date-only or date-time.
     */
    public DueDate(String dueDate) {
        requireNonNull(dueDate);
        checkArgument(isValidDueDate(dueDate), MESSAGE_CONSTRAINTS);

        if (dueDate.matches(VALIDATION_DATE_REGEX)) {
            this.date = LocalDate.parse(dueDate, VALID_INPUT_DATE_FORMAT);
            this.dateTime = null;
            isDateOnly = true;
        } else {
            this.date = null;
            this.dateTime = LocalDateTime.parse(dueDate, VALID_INPUT_DATETIME_FORMAT);
            isDateOnly = false;
        }
    }

    /**
     * Returns true if a given string is a valid due date.
     */
    public static boolean isValidDueDate(String test) {
        try {
            if (test.matches(VALIDATION_DATETIME_REGEX)) {
                LocalDateTime.parse(test, VALID_INPUT_DATETIME_FORMAT);
            } else if (test.matches(VALIDATION_DATE_REGEX)) {
                LocalDate.parse(test, VALID_INPUT_DATE_FORMAT);
            } else {
                return false;
            }
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Converts to LocalDateTime.
     * If the due date is date-only, the time is set to 00:00.
     */
    public LocalDateTime toDateTime() {
        return isDateOnly ? date.atStartOfDay() : dateTime;
    }

    /**
     * Converts dueDate into the Input string format for storing in JSON.
     */
    public String toInputString() {
        return isDateOnly
                ? date.format(VALID_INPUT_DATE_FORMAT)
                : dateTime.format(VALID_INPUT_DATETIME_FORMAT);
    }

    /**
     * Returns true if the date time is before {@code now}.
     */
    public boolean isBeforeDate(LocalDateTime now) {
        return toDateTime().isBefore(now);
    }

    /**
     * @return -1 if this is earlier than other; 1 if this is later than other; 0 if both are equal.
     */
    @Override
    public int compareTo(DueDate other) {
        if (this.toDateTime().isBefore(other.toDateTime())) {
            return -1;
        } else if (this.toDateTime().isAfter(other.toDateTime())) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DueDate)) {
            return false;
        }

        DueDate otherDueDate = (DueDate) other;
        return otherDueDate.toDateTime().equals(this.toDateTime());
    }

    @Override
    public int hashCode() {
        return toDateTime().hashCode();
    }

    @Override
    public String toString() {
        return isDateOnly
                ? date.format(VALID_OUTPUT_DATE_FORMAT)
                : dateTime.format(VALID_OUTPUT_DATETIME_FORMAT)
                .replace("AM", "am").replace("PM", "pm");
    }
}
