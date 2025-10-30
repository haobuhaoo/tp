package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardRenderTest {

    @BeforeAll
    static void initJavaFx() {
        try {
            // Initialize JavaFX toolkit once (no Swing/JFXPanel dependency)
            Platform.startup(() -> { /* no-op */ });
        } catch (IllegalStateException alreadyInitialized) {
            // Toolkit already started by another test — safe to ignore
        }
    }

    @Test
    public void renderParticipation_chronologicalFiveSlotsAndScores() throws Exception {
        // Build a person and pre-populate participation BEFORE constructing the card
        Person p = new PersonBuilder().withName("Alex Yeoh").build();
        p.getParticipation().add(LocalDate.parse("2025-09-12"), 2);
        p.getParticipation().add(LocalDate.parse("2025-09-10"), 5); // out of order on purpose
        p.getParticipation().add(LocalDate.parse("2025-09-14"), 3);
        // Upsert: same date replaces and should be reflected
        p.getParticipation().add(LocalDate.parse("2025-09-12"), 4);

        CountDownLatch latch = new CountDownLatch(1);
        final PersonCard[] holder = new PersonCard[1];

        Platform.runLater(() -> {
            holder[0] = new PersonCard(p, 1);
            latch.countDown();
        });
        if (!latch.await(10, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX render timed out");
        }

        PersonCard card = holder[0];
        HBox dateRow = (HBox) getPrivateField(card, "dateRow");
        HBox boxes = (HBox) getPrivateField(card, "boxes");

        // Always 5 slots: first two empty (padding), then 09-10, 09-12, 09-14 (chronological)
        assertEquals(5, dateRow.getChildren().size());
        assertEquals(5, boxes.getChildren().size());

        String d2 = ((Label) dateRow.getChildren().get(2)).getText();
        String d3 = ((Label) dateRow.getChildren().get(3)).getText();
        String d4 = ((Label) dateRow.getChildren().get(4)).getText();
        assertEquals("09-10", d2);
        assertEquals("09-12", d3);
        assertEquals("09-14", d4);

        // Scores aligned with dates: "", "", 5, 4, 3
        assertEquals("", readScoreText(boxes.getChildren().get(0)));
        assertEquals("", readScoreText(boxes.getChildren().get(1)));
        assertEquals("5", readScoreText(boxes.getChildren().get(2)));
        assertEquals("4", readScoreText(boxes.getChildren().get(3)));
        assertEquals("3", readScoreText(boxes.getChildren().get(4)));
    }

    private static Object getPrivateField(Object instance, String name) {
        try {
            Field f = instance.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(instance);
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(e);
        }
    }

    private static String readScoreText(Node cellNode) {
        StackPane cell = (StackPane) cellNode;
        for (Node n : cell.getChildren()) {
            if (n instanceof Text) {
                return ((Text) n).getText();
            }
        }
        return null;
    }
}
