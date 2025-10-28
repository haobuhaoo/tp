package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.reminder.Description;
import seedu.address.model.reminder.DueDate;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.UnmodifiableHwReminder;
import seedu.address.model.reminder.UnmodifiablePaymentReminder;
import seedu.address.model.reminder.UnmodifiableReminder;

class JsonAdaptedReminder {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reminder's %s field is missing!";

    private final String dueDate;
    private final String description;
    private final boolean isModifiable;

    /**
     * Constructs a {@code JsonAdaptedReminder} with the given reminder details.
     */
    @JsonCreator
    public JsonAdaptedReminder(@JsonProperty("dueDate") String dueDate,
                               @JsonProperty("description") String description,
                               @JsonProperty("isModifiable") boolean isModifiable) {
        this.dueDate = dueDate;
        this.description = description;
        this.isModifiable = isModifiable;
    }

    /**
     * Converts a given {@code Reminder} into this class for Jackson use.
     */
    public JsonAdaptedReminder(Reminder source) {
        dueDate = source.getDueDate().toInputString();
        isModifiable = source.isModifiable();

        if (!isModifiable) {
            assert source instanceof UnmodifiableReminder
                    : "Non-modifiable reminder should be an instance of UnmodifiableReminder";

            UnmodifiableReminder unmodifiableReminder = (UnmodifiableReminder) source;
            if (unmodifiableReminder.isPaymentReminder()) {
                description = UnmodifiablePaymentReminder.TWO_LETTER_PREFIX + source.getDescription().toString();
            } else {
                description = UnmodifiableHwReminder.TWO_LETTER_PREFIX + source.getDescription().toString();
            }
        } else {
            description = source.getDescription().toString();
        }
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

        if (isModifiable) {
            return new Reminder(modelDueDate, modelDetail);
        }

        assert description.length() >= 2 : "Description of unmodifiable reminder should have at least 2 characters";
        String prefix = description.substring(0, 2);
        String actualDescription = description.substring(2);

        switch (prefix) {
        case UnmodifiablePaymentReminder.TWO_LETTER_PREFIX:
            return UnmodifiablePaymentReminder.of(modelDueDate, new Description(actualDescription));

        case UnmodifiableHwReminder.TWO_LETTER_PREFIX:
            return UnmodifiableHwReminder.of(modelDueDate, new Description(actualDescription));

        default:
            throw new IllegalValueException("Unknown unmodifiable reminder type with prefix: " + prefix);
        }
    }
}
