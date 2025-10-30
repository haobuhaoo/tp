package seedu.address.model.person;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Stores up to 5 most recent participation records (oldest -> newest order).
 */
public class ParticipationHistory {
    private static final int MAX = 5;
    private final Deque<ParticipationRecord> deque = new ArrayDeque<>();

    public ParticipationHistory() {}

    /**
     * Creates a participation history pre-populated with the given records.
     * <p>
     * Records are added in iteration order using {@link #add(ParticipationRecord)},
     * which enforces the maximum capacity (5). If {@code records} contains more
     * than 5 items, only the 5 most-recent (i.e., the last five encountered) are kept.
     *
     * @param records records to seed this history (may be {@code null}); each element must be non-{@code null}
     * @throws NullPointerException if any element in {@code records} is {@code null}
     */
    public ParticipationHistory(List<ParticipationRecord> records) {
        if (records != null) {
            for (ParticipationRecord r : records) {
                if (r != null) { // tolerate legacy/null entries from storage
                    add(r);
                }
            }
        }
    }

    /** Adds a new record, keeping only the 5 most recent by insertion order. */
    public void add(ParticipationRecord record) {
        if (record == null) {
            return; // ignore silently to avoid crashing UI on legacy/null data
        }

        // Remove any existing record for the same date (compare by date only)
        for (var it = deque.iterator(); it.hasNext(); ) {
            ParticipationRecord existing = it.next();
            if (existing != null && existing.getDate().equals(record.getDate())) {
                it.remove();
                break; // keep at most one per date
            }
        }

        // Add as the newest record
        deque.addLast(record);

        // Enforce max size
        while (deque.size() > MAX) {
            deque.removeFirst();
        }
    }

    public void add(LocalDate date, int score) {
        add(new ParticipationRecord(date, score));
    }

    /** Returns an immutable list (oldest -> newest). */
    public List<ParticipationRecord> asList() {
        return Collections.unmodifiableList(new ArrayList<>(deque));
    }

    /** Returns a list padded to 5 entries, with nulls for the missing oldest entries. */
    public List<ParticipationRecord> asListPaddedToFive() {
        List<ParticipationRecord> raw = new ArrayList<>(deque);
        int missing = MAX - raw.size();
        List<ParticipationRecord> padded = new ArrayList<>(missing + raw.size());
        for (int i = 0; i < missing; i++) {
            padded.add(null);
        }
        padded.addAll(raw);
        return Collections.unmodifiableList(padded);
    }

    public int size() {
        return deque.size();
    }

    public ParticipationRecord mostRecent() {
        return deque.peekLast();
    }
}
