package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests whether any of the given keywords is contained in a
 * reminder's due date or description
 */
public class ReminderFieldsContainsKeywordsPredicate implements Predicate<Reminder> {
    private final List<String> keywords;

    /**
     * Creates a predicate that matches if any keyword is contained in a reminder's
     * due date or description
     *
     * @param keywords list of keywords;
     */
    public ReminderFieldsContainsKeywordsPredicate(List<String> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    @Override
    public boolean test(Reminder reminder) {
        requireNonNull(reminder);
        String dueDate = norm(reminder.getDueDate().toString());
        String description = norm(reminder.getDescription().toString());

        for (String kw : keywords) {
            String t = norm(kw);
            if (!t.isEmpty() && (dueDate.contains(t) || description.contains(t))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderFieldsContainsKeywordsPredicate)) {
            return false;
        }

        ReminderFieldsContainsKeywordsPredicate otherReminderContainsKeywordsPredicate =
                (ReminderFieldsContainsKeywordsPredicate) other;
        return keywords.equals(otherReminderContainsKeywordsPredicate.keywords);
    }

    private static String norm(String s) {
        return s == null ? "" : s.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT);
    }

    /**
     * Gets the keywords used in the predicate
     *
     * @return keywords used in the predicate as a list
     */
    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
