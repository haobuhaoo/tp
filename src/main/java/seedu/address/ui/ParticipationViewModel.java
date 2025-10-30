package seedu.address.ui;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.person.ParticipationHistory;
import seedu.address.model.person.ParticipationRecord;

/**
 * Pure helper to compute 5 participation slots (date label + score text),
 * sorted chronologically and padded at the front with empties.
 */
final class ParticipationViewModel {

    static final DateTimeFormatter MM_DD = DateTimeFormatter.ofPattern("MM-dd");

    private ParticipationViewModel() { }

    static final class Slot {
        final String dateLabel; // "MM-dd" or "" if empty
        final String scoreText; // "0..5" or "" if empty
        Slot(String d, String s) {
            this.dateLabel = d;
            this.scoreText = s;
        }
    }

    /**
     * Returns exactly 5 slots, oldest -> newest, padded at the front.
     * When multiple entries share the same date, keeps the latest (by insertion).
     */
    static List<Slot> computeSlots(ParticipationHistory history) {
        List<ParticipationRecord> list = new ArrayList<>(history.asList());
        // Keep only the last record per date
        java.util.Map<java.time.LocalDate, ParticipationRecord> lastPerDate = new java.util.LinkedHashMap<>();
        for (ParticipationRecord r : list) {
            lastPerDate.put(r.getDate(), r); // overwrites older same-date
        }
        List<ParticipationRecord> dedup = new ArrayList<>(lastPerDate.values());
        // Chronological (by date ascending)
        dedup.sort(Comparator.comparing(ParticipationRecord::getDate));

        // Take at most last 5 (oldest->newest)
        int start = Math.max(0, dedup.size() - 5);
        List<ParticipationRecord> five = dedup.subList(start, dedup.size());

        // Pad with empties at the front
        int missing = 5 - five.size();
        List<Slot> out = new ArrayList<>(5);
        for (int i = 0; i < missing; i++) {
            out.add(new Slot("", ""));
        }
        for (ParticipationRecord r : five) {
            out.add(new Slot(r.getDate().format(MM_DD), Integer.toString(r.getScore())));
        }
        return java.util.Collections.unmodifiableList(out);
    }
}
