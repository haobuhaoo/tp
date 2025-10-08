package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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

        // valid lesson time
        assertTrue(LessonTime.isValidLessonTime("0000")); // midnight
        assertTrue(LessonTime.isValidLessonTime("2359")); // one minute to midnight
        assertTrue(LessonTime.isValidLessonTime("1330"));
    }

    @Test
    public void equals() {
        LessonTime lessonTime = new LessonTime("1330");

        // same values -> returns true
        assertTrue(lessonTime.equals(new LessonTime("1330")));

        // same object -> returns true
        assertTrue(lessonTime.equals(lessonTime));

        // null -> returns false
        assertFalse(lessonTime.equals(null));

        // different types -> returns false
        assertFalse(lessonTime.equals(5));

        // different values -> returns false
        assertFalse(lessonTime.equals(new LessonTime("1400")));
    }

    @Test
    public void hashCodeMethod() {
        LessonTime lessonTime = new LessonTime("1330");
        assertEquals(lessonTime.hashCode(), new LessonTime("1330").hashCode());
    }

    @Test
    public void toStringMethod() {
        LessonTime lessonTime = new LessonTime("1330");
        assertEquals("01:30 pm", lessonTime.toString());
    }

    @Test
    public void toInputStringMethod() {
        LessonTime lessonTime = new LessonTime("0900");
        assertEquals("0900", lessonTime.toInputString());
    }
}
