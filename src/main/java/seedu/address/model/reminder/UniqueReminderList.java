package seedu.address.model.reminder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;

/**
 * A list of reminders that enforces uniqueness between its elements and does not allow nulls.
 * A reminder is considered unique by comparing using {@code Reminder#equals(Reminder)}.
 * As such, adding and updating of reminders uses Reminder#equals(Reminder) for equality
 * to ensure that the reminder being added or updated is unique in terms of identity in the
 * UniqueReminderList. However, the removal of a reminder uses Reminder#equals(Object) to ensure
 * that the reminder with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Reminder#equals(Object)
 */
public class UniqueReminderList implements Iterable<Reminder> {
    private static final Logger logger = LogsCenter.getLogger(UniqueReminderList.class);
    private final ObservableList<Reminder> internalList = FXCollections.observableArrayList();
    private final ObservableList<Reminder> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent reminder as the given argument.
     */
    public boolean contains(Reminder toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Sorts the reminder list based on upcoming due date
     */
    public void sort() {
        internalList.sort(Reminder::compareTo);
    }

    /**
     * Adds a reminder to the list.
     * The reminder must not already exist in the list.
     */
    public void add(Reminder toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateReminderException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the reminder {@code target} in the list with {@code editedReminder}.
     * {@code target} must exist in the list.
     * The {@code editedReminder} must not be the same as another existing reminder in the list.
     */
    public void setReminder(Reminder target, Reminder editedReminder) {
        requireAllNonNull(target, editedReminder);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ReminderNotFoundException();
        }

        if (!target.equals(editedReminder) && contains(editedReminder)) {
            throw new DuplicateReminderException();
        }

        internalList.set(index, editedReminder);
    }

    /**
     * Removes the equivalent reminder from the list.
     * The reminder must exist in the list.
     */
    public void remove(Reminder toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ReminderNotFoundException();
        }
    }

    /**
     * Removes all reminders that satisfy the given {@code predicate}.
     */
    public void removeIf(Predicate<Reminder> predicate) {
        requireNonNull(predicate);
        internalList.removeIf(predicate);
    }

    public void setReminders(UniqueReminderList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code reminders}.
     * {@code reminders} must not contain duplicate reminders.
     */
    public void setReminders(List<Reminder> reminders) {
        requireAllNonNull(reminders);
        if (!remindersAreUnique(reminders)) {
            throw new DuplicateReminderException();
        }

        internalList.setAll(reminders);
    }

    /**
     * Returns the sorted backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Reminder> asUnmodifiableObservableList() {
        logger.info("--- Sorting reminder list ---");
        this.sort();
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Reminder> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueReminderList)) {
            return false;
        }

        UniqueReminderList otherUniqueReminderList = (UniqueReminderList) other;
        return internalList.equals(otherUniqueReminderList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code reminders} contains only unique reminders.
     */
    private boolean remindersAreUnique(List<Reminder> reminders) {
        for (int i = 0; i < reminders.size() - 1; i++) {
            for (int j = i + 1; j < reminders.size(); j++) {
                if (reminders.get(i).equals(reminders.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Creates homework reminders for the given person for all undone homework that is due in 1 day.
     *
     * @return A list of UnmodifiableHwReminder instances for undone homework.
     */
    public static List<UnmodifiableHwReminder> createHomeworkReminder(Person person) {
        return person.getHomeworkList().stream()
                .filter(homework -> !homework.isDone())
                .map(homework -> UnmodifiableHwReminder.of(person, homework))
                .filter(homework
                        -> homework.daysUntilDueDate() <= 1 && homework.daysUntilDueDate() >= 0)
                .toList();
    }
}
