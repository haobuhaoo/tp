package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;


/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String lessonTime} into a {@code LessonTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code lessonTime} is invalid.
     */
    public static LessonTime parseLessonTime(String lessonTime) throws ParseException {
        requireNonNull(lessonTime);
        String trimmedLessonTime = lessonTime.trim();
        if (!LessonTime.isValidLessonTime(trimmedLessonTime)) {
            throw new ParseException(LessonTime.MESSAGE_CONSTRAINTS);
        }
        return new LessonTime(trimmedLessonTime);
    }

    /**
     * Parses a {@code String description} into a valid description.
     *
     * @param description The string to be parsed.
     * @return A trimmed non-empty description string.
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static String parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (trimmedDescription.isEmpty()) {
            throw new ParseException("Description cannot be empty.");
        }
        return trimmedDescription;
    }
    /**
     * Parses a {@code String Date} into an {@code LocalDate}.
     *
     * @param date The date string to be parsed.
     * @return A {@code LocalDate} representing the parsed date.
     * @throws ParseException if the given {@code LocalDate} is invalid.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        try {
            return LocalDate.parse(date.trim());
        } catch (DateTimeParseException e) {
            throw new ParseException("Invalid date format!Please use yyyy-MM-dd (e.g. 2025-10-25).");
        }
     * Parses a {@code Collection<String> lessonTime} into an {@code Set<LessonTime>}.
     */
    public static Set<LessonTime> parseLessonTimeSet(Collection<String> lessonTime) throws ParseException {
        requireNonNull(lessonTime);
        final Set<LessonTime> lessonTimeSet = new HashSet<>();
        for (String lessonTimeString : lessonTime) {
            lessonTimeSet.add(parseLessonTime(lessonTimeString));
        }
        return lessonTimeSet;
    }
}
