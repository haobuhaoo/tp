package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.homework.Homework;


/**
 * Adapter class for {@link Homework}.
 * <p>
 * This class is used to convert between {@code Homework} model objects
 * and a JSON-friendly representation for storage.
 */
public class JsonAdaptedHomework {
    private final String description;
    private final String deadline;
    private final boolean isDone;

    /**
     * Constructs a {@code JsonAdaptedHomework} with the given details.
     *
     * @param d    The homework description.
     * @param by   The homework deadline as a string (ISO-8601 format).
     * @param done Whether the homework has been marked as done.
     */
    @JsonCreator
    public JsonAdaptedHomework(@JsonProperty("description") String d,
                               @JsonProperty("deadline") String by,
                               @JsonProperty("done") boolean done) {
        this.description = d;
        this.deadline = by;
        this.isDone = done;
    }

    /**
     * Converts a given {@code Homework} into this class
     *
     * @param src The source {@code Homework} object to adapt.
     */
    public JsonAdaptedHomework(Homework src) {
        this.description = src.getDescription();
        this.deadline = src.getDeadline().toString();
        this.isDone = src.isDone();
    }

    /**
     * Converts this JSON-friendly adapted homework object back into the model's {@code Homework} object.
     *
     * @return A {@code Homework} object equivalent to this adapted version.
     */
    public Homework toModelType() {
        Homework hw = new Homework(description, LocalDate.parse(deadline));
        if (isDone) {
            hw.markDone();
        }
        return hw;
    }

}
