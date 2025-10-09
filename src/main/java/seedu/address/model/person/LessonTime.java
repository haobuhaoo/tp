package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a Person's lesson time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLessonTime(String)}
 */
public class LessonTime {
    public static final String MESSAGE_CONSTRAINTS = "Lesson time should be in 24-hour format (0000 to 2359)";

    public static final DateTimeFormatter VALID_INPUT_TIME_FORMAT =
            DateTimeFormatter.ofPattern("HHmm");

    // Force English locale so "AM/PM" is stable across environments
    public static final DateTimeFormatter VALID_OUTPUT_TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

    public final LocalTime time;

    /**
     * Constructs a {@code LessonTime}.
     *
     * @param lessonTime A valid time in 24-hour format.
     */
    public LessonTime(String lessonTime) {
        requireNonNull(lessonTime);
        checkArgument(isValidLessonTime(lessonTime), MESSAGE_CONSTRAINTS);
        time = LocalTime.parse(lessonTime, VALID_INPUT_TIME_FORMAT);
    }

    /**
     * Returns true if a given string is a valid lesson time.
     */
    public static boolean isValidLessonTime(String test) {
        try {
            LocalTime.parse(test, VALID_INPUT_TIME_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Returns the lesson time in input format (HHmm).
     */
    public String toInputString() {
        return time.format(VALID_INPUT_TIME_FORMAT);
    }

    @Override
    public String toString() {
        // Example: "01:30 pm" (lowercase day-period to match tests)
        return time.format(VALID_OUTPUT_TIME_FORMAT).toLowerCase(Locale.ENGLISH);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonTime)) {
            return false;
        }

        LessonTime otherLessonTime = (LessonTime) other;
        return time.equals(otherLessonTime.time);
    }

    @Override
    public int hashCode() {
        return time.hashCode();
    }
}
