package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.ParticipationRecord;

public class JsonAdaptedParticipationRecordTest {

    @Test
    public void toModelType_roundTrip_ok() {
        JsonAdaptedParticipationRecord a = new JsonAdaptedParticipationRecord("2025-09-19", 5);
        ParticipationRecord r = a.toModelType();

        assertEquals(LocalDate.parse("2025-09-19"), r.getDate());
        assertEquals(5, r.getScore());

        JsonAdaptedParticipationRecord back = new JsonAdaptedParticipationRecord(r);
        ParticipationRecord r2 = back.toModelType();
        assertEquals(r.getDate(), r2.getDate());
        assertEquals(r.getScore(), r2.getScore());
    }
}
