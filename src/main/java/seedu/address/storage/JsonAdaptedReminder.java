package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;

class JsonAdaptedReminder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String dueDate;
    private final String description;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("dueDate") String dueDate,
                               @JsonProperty("description") String description) {
        this.dueDate = dueDate;
        this.description = description;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        dueDate = source.getDueDate().toInputString();
        description = source.getDescription().toString();
    }

    /**
     * Converts this Jackson-friendly adapted reminder object into the model's {@code Reminder} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reminder.
     */
    public Reminder toModelType() throws IllegalValueException {
        if (dueDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DueDate.class.getSimpleName()));
        }
        if (!DueDate.isValidDueDate(dueDate)) {
            throw new IllegalValueException(DueDate.MESSAGE_CONSTRAINTS);
        }
        final DueDate modelDueDate = new DueDate(dueDate);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDetail = new Description(description);

        return new Reminder(modelDueDate, modelDetail);
    }
}
