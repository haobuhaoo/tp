package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_LESSON_TIME = "1000 Sat";

    private Name name;
    private Phone phone;
    private Set<LessonTime> lessonTime = new HashSet<>();

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        lessonTime.add(new LessonTime(DEFAULT_LESSON_TIME));
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        lessonTime = personToCopy.getLessonTime();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code LessonTime} of the {@code Person} that we are building.
     */
    public PersonBuilder withLessonTime(String... lessonTime) {
        this.lessonTime = SampleDataUtil.getLessonTimeSet(lessonTime);
        return this;
    }

    public Person build() {
        return new Person(name, phone, lessonTime);
    }
}
