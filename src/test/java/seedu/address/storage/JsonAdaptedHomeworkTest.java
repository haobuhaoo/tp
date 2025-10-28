package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.homework.Homework;

/**
 * Unit tests for {@link JsonAdaptedHomework}.
 */
public class JsonAdaptedHomeworkTest {

    @Test
    public void toModelType_validHomework_success() {
        JsonAdaptedHomework jsonHw = new JsonAdaptedHomework(
                "Math Worksheet", "2025-10-23", false);
        Homework hw = jsonHw.toModelType();

        assertEquals("Math Worksheet", hw.getDescription());
        assertEquals(LocalDate.parse("2025-10-23"), hw.getDeadline());
        assertFalse(hw.isDone());
    }

    @Test
    public void toModelType_markedDone_success() {
        JsonAdaptedHomework jsonHw = new JsonAdaptedHomework(
                "Science WS", "2025-12-01", true);
        Homework hw = jsonHw.toModelType();

        assertEquals("Science WS", hw.getDescription());
        assertEquals(LocalDate.parse("2025-12-01"), hw.getDeadline());
        assertTrue(hw.isDone(), "Homework should be marked done when isDone = true");
    }

    @Test
    public void fromModel_roundTrip_success() {
        Homework original = new Homework("History Essay", LocalDate.parse("2025-11-10"));
        original.markDone();

        JsonAdaptedHomework adapted = new JsonAdaptedHomework(original);
        Homework convertedBack = adapted.toModelType();

        assertEquals(original.getDescription(), convertedBack.getDescription());
        assertEquals(original.getDeadline(), convertedBack.getDeadline());
        assertEquals(original.isDone(), convertedBack.isDone());
    }

}

