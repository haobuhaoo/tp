package seedu.address.model.attendance;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory attendance tracker.
 * Maps normalized student name → (date → present?).
 */
public final class AttendanceIndex {
    private final Map<String, Map<LocalDate, Boolean>> data = new HashMap<>();
    private LocalDate currentUiDate = LocalDate.now();

    private static String normalize(String name) {
        return name.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }

    public Optional<Boolean> get(String name, LocalDate date) {
        return Optional.ofNullable(data.getOrDefault(normalize(name), Map.of()).get(date));
    }

    public void put(String name, LocalDate date, boolean present) {
        data.computeIfAbsent(normalize(name), n -> new HashMap<>()).put(date, present);
    }

    public boolean isTaken(String name, LocalDate date) {
        return get(name, date).isPresent();
    }

    public LocalDate getCurrentUiDate() {
        return currentUiDate;
    }

    public void setCurrentUiDate(LocalDate date) {
        currentUiDate = date;
    }

    /**
     * Removes all attendance records and resets the UI date to today.
     */
    public void clear() {
        data.clear();
        currentUiDate = LocalDate.now();
    }

    /**
     * Removes all attendance records associated with the given student name.
     * The same normalization is applied as in {@link #put(String, java.time.LocalDate, boolean)}.
     *
     * @param name the student's name (raw; will be normalized)
     */
    public void removeAllForName(String name) {
        data.remove(normalize(name));
    }

}
