package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Student in the student list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {
    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final Set<LessonTime> lessonTime = new HashSet<>();

    // Participation (mutable history of last 5 records)
    private final ParticipationHistory participation = new ParticipationHistory();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Set<LessonTime> lessonTime) {
        requireAllNonNull(name, phone, lessonTime);
        this.name = name;
        this.phone = phone;
        this.lessonTime.addAll(lessonTime);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    /**
     * Returns an immutable lesson time set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<LessonTime> getLessonTime() {
        return Collections.unmodifiableSet(lessonTime);
    }

    public ParticipationHistory getParticipation() {
        return participation;
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && lessonTime.equals(otherPerson.lessonTime);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, lessonTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("lesson time", lessonTime)
                .toString();
    }
}
