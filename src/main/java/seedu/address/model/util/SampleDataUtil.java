package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(new Name("Alex Yeoh"), new Phone("87438807"),
                    getLessonTimeSet("1000 Sun")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"),
                    getLessonTimeSet("1400 Sat", "1600 Sun")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    getLessonTimeSet("1200 Mon")),
            new Person(new Name("David Li"), new Phone("91031282"),
                    getLessonTimeSet("1300 Wed", "1500 Fri", "1700 Sun")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"),
                    getLessonTimeSet("0900 Tue")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"),
                    getLessonTimeSet("1100 Thu", "1800 Sat"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a lesson time set containing the list of strings given.
     */
    public static Set<LessonTime> getLessonTimeSet(String... strings) {
        return Arrays.stream(strings)
                .map(LessonTime::new)
                .collect(Collectors.toSet());
    }
}
