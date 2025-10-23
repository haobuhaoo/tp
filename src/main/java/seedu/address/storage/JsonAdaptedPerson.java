package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.homework.Homework;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;


/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Student's %s field is missing!";

    private final String name;
    private final String phone;
    private final String lessonTime;
    private final List<JsonAdaptedHomework> homework = new ArrayList<>();
    private final List<JsonAdaptedLessonTime> lessonTime = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
public JsonAdaptedPerson(@JsonProperty("name") String name,
                         @JsonProperty("phone") String phone,
                         @JsonProperty("lessonTime") String lessonTime,
                         @JsonProperty("homeworks") List<JsonAdaptedHomework> homeworks) {
    this.name = name;
    this.phone = phone;
    this.lessonTime = lessonTime;
    if (homeworks != null) {
        this.homework.addAll(homeworks);
    }
}


    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
lessonTime = source.getLessonTime().toInputString();
// copy homework
source.getHomeworkList().forEach(hw -> homeworks.add(new JsonAdaptedHomework(hw)));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (lessonTime.isEmpty()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LessonTime.class.getSimpleName()));
        }
        final List<LessonTime> modelLessonTimeList = new ArrayList<>();
        for (JsonAdaptedLessonTime lt : lessonTime) {
            modelLessonTimeList.add(lt.toModelType());
        }
        final Set<LessonTime> modelLessonTime = new HashSet<>(modelLessonTimeList);

        final Person person = new Person(modelName, modelPhone, modelLessonTime);

        List<Homework> hwList = new ArrayList<>();
        for (JsonAdaptedHomework jhw : homework) {
            hwList.add(jhw.toModelType());
        }
        person.setHomeworkList(hwList);

        return person;
    }


}
