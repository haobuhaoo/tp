package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.model.homework.Homework;

import java.time.LocalDate;

/**
 * Adapter for Homework to allow saving and loading of homework list when app restarts
 */
public class JsonAdaptedHomework {
    private final String description;
    private final String deadline;
    private final boolean isDone;

    @JsonCreator
    public JsonAdaptedHomework(@JsonProperty("description") String d,
                               @JsonProperty("deadline") String by,
                               @JsonProperty("done") boolean done) {
        this.description = d; this.deadline = by; this.isDone = done;
    }

    public JsonAdaptedHomework(Homework src) {
        this.description = src.getDescription();
        this.deadline = src.getDeadline().toString();
        this.isDone = src.isDone();
    }

    public Homework toModelType() {
        Homework hw = new Homework(description, LocalDate.parse(deadline));
        if (isDone) hw.markDone();
        return hw;
    }





}
