package seedu.address.model.homework;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a piece of homework assigned to a student.
 * Contains a description,deadline, and completion status.
 */
public class Homework {
    private final String description;
    private final LocalDate deadline;
    private boolean isDone;

    public Homework(String description, LocalDate deadline) {
        requireNonNull(description);
        requireNonNull(deadline);
        this.description = description;
        this.deadline = deadline;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markDone() {
        isDone = true;
    }

    public void markUndone() {
        isDone = false;
    }

    //to prevent adding duplicate homework
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Homework)) {
            return false;
        }
        Homework otherHw = (Homework) other;
        return description.equalsIgnoreCase(otherHw.description)
                && deadline.equals(otherHw.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description.toLowerCase(), deadline);
    }

    @Override
    public String toString() {
        return (isDone ? "[✓] " : "[ ] ") + description + " (due " + deadline + ")";
    }

}
