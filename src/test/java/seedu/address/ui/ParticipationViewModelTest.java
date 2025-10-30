package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ParticipationHistory;

public class ParticipationViewModelTest {

    @Test
    public void computeSlots_paddingAndChronological_andDedupSameDate() {
        ParticipationHistory h = new ParticipationHistory();
        // Add out-of-order, with duplicate date (latest should win)
        h.add(LocalDate.parse("2025-09-12"), 2);
        h.add(LocalDate.parse("2025-09-10"), 5);
        h.add(LocalDate.parse("2025-09-14"), 3);
        h.add(LocalDate.parse("2025-09-12"), 4); // replaces 2

        List<ParticipationViewModel.Slot> slots = ParticipationViewModel.computeSlots(h);
        assertEquals(5, slots.size());

        // Expect padding then 09-10, 09-12, 09-14
        assertEquals("", slots.get(0).dateLabel);
        assertEquals("", slots.get(0).scoreText);
        assertEquals("", slots.get(1).dateLabel);
        assertEquals("", slots.get(1).scoreText);

        assertEquals("09-10", slots.get(2).dateLabel);
        assertEquals("5", slots.get(2).scoreText);

        assertEquals("09-12", slots.get(3).dateLabel);
        assertEquals("4", slots.get(3).scoreText);

        assertEquals("09-14", slots.get(4).dateLabel);
        assertEquals("3", slots.get(4).scoreText);
    }

    @Test
    public void computeSlots_capsAtFive() {
        ParticipationHistory h = new ParticipationHistory();
        // six days → only last five chronological survive
        h.add(LocalDate.parse("2025-09-10"), 1);
        h.add(LocalDate.parse("2025-09-11"), 2);
        h.add(LocalDate.parse("2025-09-12"), 3);
        h.add(LocalDate.parse("2025-09-13"), 4);
        h.add(LocalDate.parse("2025-09-14"), 5);
        h.add(LocalDate.parse("2025-09-15"), 1);

        List<ParticipationViewModel.Slot> slots = ParticipationViewModel.computeSlots(h);
        assertEquals(5, slots.size());

        // Oldest kept is 09-11, newest 09-15
        assertEquals("09-11", slots.get(0).dateLabel);
        assertEquals("2", slots.get(0).scoreText);
        assertEquals("09-15", slots.get(4).dateLabel);
        assertEquals("1", slots.get(4).scoreText);
    }
}
