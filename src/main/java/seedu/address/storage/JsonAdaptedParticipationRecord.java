package seedu.address.storage;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.ParticipationRecord;

class JsonAdaptedParticipationRecord {
    private final String date; // "YYYY-MM-DD"
    private final Integer score; // 0..5

    @JsonCreator
    JsonAdaptedParticipationRecord(@JsonProperty("date") String date,
                                   @JsonProperty("score") Integer score) {
        this.date = date;
        this.score = score;
    }

    JsonAdaptedParticipationRecord(ParticipationRecord src) {
        this.date = src.getDate().toString();
        this.score = src.getScore();
    }

    ParticipationRecord toModelType() {
        return new ParticipationRecord(LocalDate.parse(date), score);
    }
}
