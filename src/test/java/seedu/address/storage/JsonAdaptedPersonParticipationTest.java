package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedPersonParticipationTest {

    @Test
    public void modelToJsonToModel_participationRoundTrip_ok() throws Exception {
        // Build a valid Person via testutil (ensures lessonTime etc. are valid)
        Person p = new PersonBuilder()
                .withName("Alex Yeoh")
                .withPhone("98765432")
                // (PersonBuilder usually seeds a valid lesson time by default; keep defaults)
                .build();

        // Participation data
        p.getParticipation().add(LocalDate.parse("2025-09-19"), 4);
        p.getParticipation().add(LocalDate.parse("2025-09-21"), 2);

        // Serialize to adapter, then back to model
        JsonAdaptedPerson adapted = new JsonAdaptedPerson(p);
        Person restored = adapted.toModelType();

        // Verify participation survived and order (oldest -> newest) in ParticipationHistory list
        assertEquals(2, restored.getParticipation().size());
        assertEquals(LocalDate.parse("2025-09-19"),
                restored.getParticipation().asList().get(0).getDate());
        assertEquals(4,
                restored.getParticipation().asList().get(0).getScore());
        assertEquals(LocalDate.parse("2025-09-21"),
                restored.getParticipation().asList().get(1).getDate());
        assertEquals(2,
                restored.getParticipation().asList().get(1).getScore());
    }

    @Test
    public void jsonCreator_withParticipationList_ok() throws Exception {
        // Use a valid Person to derive valid JsonAdaptedLessonTime entries (no private access)
        Person base = new PersonBuilder().build();
        List<JsonAdaptedLessonTime> lessonTimes = base.getLessonTime().stream()
                .map(JsonAdaptedLessonTime::new)
                .collect(Collectors.toList());

        var homeworks = new ArrayList<JsonAdaptedHomework>(); // empty is fine

        var partList = new ArrayList<JsonAdaptedParticipationRecord>();
        partList.add(new JsonAdaptedParticipationRecord("2025-09-10", 1));
        partList.add(new JsonAdaptedParticipationRecord("2025-09-11", 3));

        // Use the 6-arg ctor (keeps compatibility with your 5-arg overload too)
        JsonAdaptedPerson adapter = new JsonAdaptedPerson(
                "Alex Yeoh",
                "98765432",
                lessonTimes,
                homeworks,
                "000000000000",
                partList
        );

        Person restored = adapter.toModelType();
        assertEquals(2, restored.getParticipation().size());
        assertEquals(LocalDate.parse("2025-09-10"),
                restored.getParticipation().asList().get(0).getDate());
        assertEquals(LocalDate.parse("2025-09-11"),
                restored.getParticipation().asList().get(1).getDate());
    }
}
