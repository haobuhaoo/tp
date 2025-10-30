package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Objects;

/**
 * Represents a Person's lesson time in the address book. A Person can have more than one lesson time.
 * Guarantees: immutable; is valid as declared in {@link #isValidLessonTime(String)}
 */
public class LessonTime {
    public static final String MESSAGE_CONSTRAINTS =
            "Lesson time should be in 24-hour format (0000 to 2359) followed by a 3-letter day abbreviation";

    /**
     * Day of the week is case-insensitive, but must be a valid 3-letter abbreviation.
     */
    public static final String VALIDATION_REGEX =
            "(?i)(?:[01][0-9]|2[0-3])[0-5][0-9] (mon|tue|wed|thu|fri|sat|sun)$";

    public static final DateTimeFormatter VALID_INPUT_TIME_FORMAT =
            DateTimeFormatter.ofPattern("HHmm", Locale.ENGLISH);

    // Force English locale so "AM/PM" is stable across environments
    public static final DateTimeFormatter VALID_OUTPUT_TIME_FORMAT =
            DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

    public final LocalTime time;
    /**
     * Day is stored in full form.
     */
    public final DayOfWeek day;

    /**
     * Constructs a {@code LessonTime}.
     *
     * @param lessonTime A valid lesson time in 24-hour format with the day of the week.
     */
    public LessonTime(String lessonTime) {
        requireNonNull(lessonTime);
        String collapsedLessonTime = lessonTime.replaceAll("\\s+", " ").trim().toLowerCase();
        checkArgument(isValidLessonTime(collapsedLessonTime), MESSAGE_CONSTRAINTS);
        String[] parts = collapsedLessonTime.split(" ");
        this.time = LocalTime.parse(parts[0], VALID_INPUT_TIME_FORMAT);
        this.day = parseDayOfWeek(parts[1]);
    }

    /**
     * Returns true if a given string is a valid lesson time.
     */
    public static boolean isValidLessonTime(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        try {
            LocalTime.parse(test.split(" ")[0], VALID_INPUT_TIME_FORMAT);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses the day of the week from a 3-letter abbreviation.
     */
    public static DayOfWeek parseDayOfWeek(String dayString) {
        switch (dayString.toLowerCase()) {
        case "mon":
            return DayOfWeek.MONDAY;
        case "tue":
            return DayOfWeek.TUESDAY;
        case "wed":
            return DayOfWeek.WEDNESDAY;
        case "thu":
            return DayOfWeek.THURSDAY;
        case "fri":
            return DayOfWeek.FRIDAY;
        case "sat":
            return DayOfWeek.SATURDAY;
        case "sun":
            return DayOfWeek.SUNDAY;
        default:
            throw new IllegalArgumentException("Invalid day of the week: " + dayString);
        }
    }

    /**
     * Returns the lesson time in input format (HHmm E).
     */
    public String toInputString() {
        return time.format(VALID_INPUT_TIME_FORMAT) + " " + day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    @Override
    public String toString() {
        return time.format(VALID_OUTPUT_TIME_FORMAT)
                .replace("AM", "am").replace("PM", "pm")
                + " " + day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
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
        return time.equals(otherLessonTime.time) && day.equals(otherLessonTime.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }
}
