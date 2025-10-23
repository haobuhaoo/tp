package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable (except participation history).
 */
public class Person {
    // Identity fields
    private final Name name;
    private final Phone phone;

    // Data fields
    private final LessonTime lessonTime;

    // Participation (mutable history of last 5 records)
    private final ParticipationHistory participation = new ParticipationHistory();

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, LessonTime lessonTime) {
        requireAllNonNull(name, phone, lessonTime);
        this.name = name;
        this.phone = phone;
        this.lessonTime = lessonTime;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public LessonTime getLessonTime() {
        return lessonTime;
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
