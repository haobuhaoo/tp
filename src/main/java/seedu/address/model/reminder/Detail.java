package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Reminder's detail in the reminder list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDetail(String)}
 */
public class Detail {
    private static final String MESSAGE_CONSTRAINTS = "Details should not be empty";

    private final String details;

    /**
     * Constructs a {@code Detail}.
     *
     * @param details A valid detail string.
     */
    public Detail(String details) {
        requireNonNull(details);
        checkArgument(isValidDetail(details), MESSAGE_CONSTRAINTS);
        this.details = details;
    }

    /**
     * Returns true if a given string is a valid detail.
     */
    public static boolean isValidDetail(String test) {
        return test != null && !test.trim().isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Detail)) {
            return false;
        }

        Detail otherDetail = (Detail) other;
        return otherDetail.details.equalsIgnoreCase(details);
    }

    @Override
    public int hashCode() {
        return details.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "Details: " + details;
    }
}
