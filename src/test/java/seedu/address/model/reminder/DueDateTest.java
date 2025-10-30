package seedu.address.model.reminder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

public class DueDateTest {
    private final DueDate date = new DueDate("2025-10-10");
    private final DueDate dateTime = new DueDate("2025-10-10 1010");

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DueDate(null));
    }

    @Test
    public void constructor_invalidDueDate_throwsIllegalArgumentException() {
        String invalidDueDate = "";
        assertThrows(IllegalArgumentException.class, () -> new DueDate(invalidDueDate));
    }

    @Test
    public void isValidDueDate() {
        // null due date
        assertThrows(NullPointerException.class, () -> DueDate.isValidDueDate(null));

        // invalid due date
        assertFalse(DueDate.isValidDueDate("")); // empty string
        assertFalse(DueDate.isValidDueDate(" ")); // whitespace only
        assertFalse(DueDate.isValidDueDate("20 Nov 2025")); // wrong date format
        assertFalse(DueDate.isValidDueDate("2025-10-10 10:10")); // with colon
        assertFalse(DueDate.isValidDueDate("dateTime")); // non-numeric
        assertFalse(DueDate.isValidDueDate("2025/10/10 1010")); // wrong date time format
        assertFalse(DueDate.isValidDueDate("2025-20-50")); // invalid date
        assertFalse(DueDate.isValidDueDate("2025-06-31")); // invalid date
        assertFalse(DueDate.isValidDueDate("2025-02-29")); // not leap year
        assertFalse(DueDate.isValidDueDate("2025-10-10 2400")); // not between 0000 and 2359

        // valid due date
        assertTrue(DueDate.isValidDueDate("2025-10-10")); // date only
        assertTrue(DueDate.isValidDueDate("2025-10-10 1010")); // date time
        assertTrue(DueDate.isValidDueDate("2024-02-29 1010")); // leap year
    }

    @Test
    public void toDateTime() {
        assertEquals(date.toDateTime(), LocalDateTime.of(2025, 10, 10, 0, 0));
    }

    @Test
    public void isBeforeDate() {
        assertTrue(date.isBeforeDate(LocalDateTime.of(2030, 1, 1, 0, 0)));
    }

    @Test
    public void equals() {
        // same value -> return true
        assertEquals(new DueDate("2025-10-10"), date);

        // same object -> return true
        assertEquals(date, date);

        // null -> return false
        assertNotEquals(null, date);

        // different type -> return false
        assertNotEquals(5.0f, date);

        // different values -> return false
        assertNotEquals(date, dateTime);

        // different date -> return false
        assertNotEquals(new DueDate("2025-12-12"), date);

        // different date time -> return false
        assertNotEquals(new DueDate("2025-12-12 1212"), dateTime);

        // different value but time is at midnight -> return true
        assertEquals(new DueDate("2025-10-10 0000"), date);
    }

    @Test
    public void hashCodeMethod() {
        assertEquals(date.hashCode(), new DueDate("2025-10-10").hashCode());
        assertEquals(dateTime.hashCode(), new DueDate("2025-10-10 1010").hashCode());
        assertEquals(date.hashCode(), new DueDate("2025-10-10 0000").hashCode());
    }

    @Test
    public void toInputStringMethod() {
        assertEquals("2025-10-10", date.toInputString());
        assertEquals("2025-10-10 1010", dateTime.toInputString());
    }

    @Test
    public void toStringMethod() {
        assertEquals("10 Oct 2025", date.toString());
        assertEquals("10 Oct 2025 10:10 am", dateTime.toString());
    }

    @Test
    public void compareToMethod() {
        DueDate now = date;
        DueDate before = new DueDate("2020-01-01 0000");
        DueDate after = new DueDate("2030-12-12 2359");

        assertEquals(1, now.compareTo(before));
        assertEquals(-1, now.compareTo(after));
        assertEquals(0, now.compareTo(now));
    }

    @Test
    public void daysUntilDue() {
        DueDate dueDate = new DueDate("2025-12-31 1200");

        // exact moment
        LocalDateTime now = LocalDateTime.parse("2025-12-31T12:00:00");
        assertEquals(0, dueDate.daysUntilDue(now));

        // before due date
        now = LocalDateTime.parse("2025-12-25T00:00:00");
        assertEquals(6, dueDate.daysUntilDue(now));

        // before by one minute
        now = LocalDateTime.parse("2025-12-31T11:59:00");
        assertEquals(0, dueDate.daysUntilDue(now));

        // after due date
        now = LocalDateTime.parse("2026-01-01T00:00:00");
        assertEquals(-1, dueDate.daysUntilDue(now));

        // after by one minute
        now = LocalDateTime.parse("2025-12-31T12:01:00");
        assertEquals(-1, dueDate.daysUntilDue(now));
    }
}
