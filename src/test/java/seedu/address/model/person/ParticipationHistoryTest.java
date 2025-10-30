package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ParticipationHistoryTest {

    @Test
    public void addSameDateReplacesScoreIsMostRecent() {
        ParticipationHistory h = new ParticipationHistory();

        h.add(LocalDate.parse("2025-09-18"), 1);
        h.add(LocalDate.parse("2025-09-19"), 3);
        h.add(LocalDate.parse("2025-09-19"), 5); // upsert by date

        // size still 2
        assertEquals(2, h.size());

        // most recent is the updated 2025-09-19 with score 5
        ParticipationRecord last = h.mostRecent();
        assertEquals(LocalDate.parse("2025-09-19"), last.getDate());
        assertEquals(5, last.getScore());
    }

    @Test
    public void addCapsAtFiveOldestDropped() {
        ParticipationHistory h = new ParticipationHistory();
        h.add(LocalDate.parse("2025-09-10"), 1);
        h.add(LocalDate.parse("2025-09-11"), 2);
        h.add(LocalDate.parse("2025-09-12"), 3);
        h.add(LocalDate.parse("2025-09-13"), 4);
        h.add(LocalDate.parse("2025-09-14"), 5);
        h.add(LocalDate.parse("2025-09-15"), 1); // sixth → drop 09-10

        assertEquals(5, h.size());
        List<ParticipationRecord> list = h.asList();
        assertEquals(LocalDate.parse("2025-09-11"), list.get(0).getDate());
        assertEquals(LocalDate.parse("2025-09-15"), list.get(4).getDate());
    }

    @Test
    public void asListPaddedToFivePadsFrontWithNulls() {
        ParticipationHistory h = new ParticipationHistory();
        h.add(LocalDate.parse("2025-09-19"), 2);
        h.add(LocalDate.parse("2025-09-20"), 3);

        List<ParticipationRecord> five = h.asListPaddedToFive();
        assertEquals(5, five.size());

        // first three are null (padding oldest slots)
        assertNull(five.get(0));
        assertNull(five.get(1));
        assertNull(five.get(2));

        // last two are the real ones (oldest -> newest)
        assertEquals(LocalDate.parse("2025-09-19"), five.get(3).getDate());
        assertEquals(LocalDate.parse("2025-09-20"), five.get(4).getDate());
    }

    @Test
    public void constructorIgnoresNullsKeepsAllWhenAtMostFive() {
        ParticipationRecord r1 = new ParticipationRecord(LocalDate.parse("2025-09-10"), 1);
        ParticipationRecord r2 = null; // should be ignored
        ParticipationRecord r3 = new ParticipationRecord(LocalDate.parse("2025-09-11"), 2);
        ParticipationRecord r4 = new ParticipationRecord(LocalDate.parse("2025-09-12"), 3);
        ParticipationRecord r5 = new ParticipationRecord(LocalDate.parse("2025-09-13"), 4);
        ParticipationRecord r6 = new ParticipationRecord(LocalDate.parse("2025-09-14"), 5);
        ParticipationHistory h = new ParticipationHistory(java.util.Arrays.asList(r1, r2, r3, r4, r5, r6));

        // After ignoring null, exactly 5 remain → none dropped.
        assertEquals(5, h.size());
        List<ParticipationRecord> list = h.asList();
        assertEquals(LocalDate.parse("2025-09-10"), list.get(0).getDate());
        assertEquals(LocalDate.parse("2025-09-14"), list.get(4).getDate());
    }

    @Test
    public void constructorOverCapacityDropsOldestAfterIgnoringNulls() {
        ParticipationRecord r1 = new ParticipationRecord(LocalDate.parse("2025-09-09"), 1);
        ParticipationRecord r2 = new ParticipationRecord(LocalDate.parse("2025-09-10"), 2);
        ParticipationRecord r3 = new ParticipationRecord(LocalDate.parse("2025-09-11"), 3);
        ParticipationRecord r4 = null; // ignored
        ParticipationRecord r5 = new ParticipationRecord(LocalDate.parse("2025-09-12"), 4);
        ParticipationRecord r6 = new ParticipationRecord(LocalDate.parse("2025-09-13"), 5);
        ParticipationRecord r7 = new ParticipationRecord(LocalDate.parse("2025-09-14"), 1);

        // Non-null count = 6 → keep only the last 5 (drop 2025-09-09)
        ParticipationHistory h = new ParticipationHistory(
                java.util.Arrays.asList(r1, r2, r3, r4, r5, r6, r7));

        assertEquals(5, h.size());
        List<ParticipationRecord> list = h.asList();
        assertEquals(LocalDate.parse("2025-09-10"), list.get(0).getDate()); // 09-09 dropped
        assertEquals(LocalDate.parse("2025-09-14"), list.get(4).getDate());
    }

}
