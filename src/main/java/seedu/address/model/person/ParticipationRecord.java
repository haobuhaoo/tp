package seedu.address.model.person;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Immutable record of a single class participation.
 * score is an integer in [0,5].
 */
public final class ParticipationRecord {
    private final LocalDate date;
    private final int score;

    /**
     * Constructs an immutable participation record.
     *
     * @param date  the class date; must be non-{@code null}
     * @param score participation score in the inclusive range {@code [0, 5]}
     * @throws NullPointerException     if {@code date} is {@code null}
     * @throws IllegalArgumentException if {@code score} is outside {@code [0, 5]}
     */
    public ParticipationRecord(LocalDate date, int score) {
        if (date == null) {
            throw new NullPointerException("date");
        }
        if (score < 0 || score > 5) {
            throw new IllegalArgumentException("score must be in [0,5]");
        }
        this.date = date;
        this.score = score;
    }

    public LocalDate getDate() {
        return date;
    }
    public int getScore() {
        return score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParticipationRecord)) {
            return false;
        }
        ParticipationRecord that = (ParticipationRecord) o;
        return score == that.score && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, score);
    }

    @Override
    public String toString() {
        return "ParticipationRecord{" + "date=" + date + ", score=" + score + '}';
    }
}
