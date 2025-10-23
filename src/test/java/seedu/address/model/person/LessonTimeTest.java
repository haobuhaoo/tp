package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;

import org.junit.jupiter.api.Test;

public class LessonTimeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonTime(null));
    }

    @Test
    public void constructor_invalidLessonTime_throwsIllegalArgumentException() {
        String invalidLessonTime = "";
        assertThrows(IllegalArgumentException.class, () -> new LessonTime(invalidLessonTime));
    }

    @Test
    public void isValidLessonTime() {
        // null lesson time
        assertThrows(NullPointerException.class, () -> LessonTime.isValidLessonTime(null));

        // invalid lesson time
        assertFalse(LessonTime.isValidLessonTime("")); // empty string
        assertFalse(LessonTime.isValidLessonTime(" ")); // spaces only
        assertFalse(LessonTime.isValidLessonTime("2560")); // invalid timing
        assertFalse(LessonTime.isValidLessonTime("time")); // non-numeric
        assertFalse(LessonTime.isValidLessonTime("123")); // less than 4 digits
        assertFalse(LessonTime.isValidLessonTime("12345")); // more than 4 digits
        assertFalse(LessonTime.isValidLessonTime("12:30")); // with colon
        assertFalse(LessonTime.isValidLessonTime("1000")); // missing day

        // pass validation regex but invalid day
        assertFalse(LessonTime.isValidLessonTime("1200 Abc")); // invalid day abbreviation
        assertFalse(LessonTime.isValidLessonTime("1200 Mond")); // valid day but wrong length

        // valid lesson time
        assertTrue(LessonTime.isValidLessonTime("0000 Fri")); // midnight
        assertTrue(LessonTime.isValidLessonTime("2359 Tue")); // one minute to midnight
        assertTrue(LessonTime.isValidLessonTime("1330 Mon"));
    }

    @Test
    public void parseDayOfWeek_invalidDay_throwsIllegalArgumentException() {
        // invalid day abbreviation
        try {
            LessonTime.parseDayOfWeek("Abc");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid day of the week: Abc", e.getMessage());
        }

        // valid day but wrong length
        try {
            LessonTime.parseDayOfWeek("Thurs");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid day of the week: Thurs", e.getMessage());
        }
    }

    @Test
    public void parseDayOfWeek_validDay_returnsCorrectDayOfWeek() {
        // all lowercase
        assertEquals(DayOfWeek.MONDAY, LessonTime.parseDayOfWeek("mon"));
        assertEquals(DayOfWeek.TUESDAY, LessonTime.parseDayOfWeek("tue"));

        // all uppercase
        assertEquals(DayOfWeek.WEDNESDAY, LessonTime.parseDayOfWeek("WED"));
        assertEquals(DayOfWeek.THURSDAY, LessonTime.parseDayOfWeek("THU"));

        // mixed case
        assertEquals(DayOfWeek.FRIDAY, LessonTime.parseDayOfWeek("Fri"));
        assertEquals(DayOfWeek.SATURDAY, LessonTime.parseDayOfWeek("sAt"));
        assertEquals(DayOfWeek.SUNDAY, LessonTime.parseDayOfWeek("suN"));
    }

    @Test
    public void equals() {
        LessonTime lessonTime = new LessonTime("1330 Wed");

        // same values -> returns true
        assertTrue(lessonTime.equals(new LessonTime("1330 Wed")));

        // same object -> returns true
        assertTrue(lessonTime.equals(lessonTime));

        // null -> returns false
        assertFalse(lessonTime.equals(null));

        // different types -> returns false
        assertFalse(lessonTime.equals(5));

        // different values -> returns false
        assertFalse(lessonTime.equals(new LessonTime("1400 Fri")));

        // different time same day -> returns false
        assertFalse(lessonTime.equals(new LessonTime("1400 Wed")));

        // same time different day -> returns false
        assertFalse(lessonTime.equals(new LessonTime("1330 Fri")));
    }

    @Test
    public void hashCodeMethod() {
        LessonTime lessonTime = new LessonTime("1330 Wed");
        assertEquals(lessonTime.hashCode(), new LessonTime("1330 Wed").hashCode());
    }

    @Test
    public void toStringMethod() {
        LessonTime lessonTime = new LessonTime("1330 Wed");
        assertEquals("01:30 pm Wed", lessonTime.toString());
    }

    @Test
    public void toInputStringMethod() {
        LessonTime lessonTime = new LessonTime("1300 Wed");
        assertEquals("1300 Wed", lessonTime.toInputString());
    }
}
