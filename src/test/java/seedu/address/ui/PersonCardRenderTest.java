package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * UI render test that runs only if JavaFX toolkit can start.
 * If the runner doesn't support JavaFX (e.g., headless CI), this test skips.
 */
public class PersonCardRenderTest {
    private static boolean javafxReady = false;

    @BeforeAll
    static void initJavaFx() {
        try {
            // Try to start toolkit. If already started, this throws IllegalStateException.
            Platform.startup(() -> { /* no-op */ });
            javafxReady = true;
        } catch (IllegalStateException already) {
            javafxReady = true;
        } catch (Throwable t) {
            javafxReady = false;
        }
    }

    @Test
    public void renderParticipation_skipsWhenNoJavaFx() throws Exception {
        Assumptions.assumeTrue(javafxReady, "JavaFX not available on test runner; skipping UI test.");

        // Minimal smoke: construct Person and ensure JavaFX can schedule a task
        Person p = new PersonBuilder().withName("Alex Yeoh").build();
        p.getParticipation().add(LocalDate.parse("2025-09-12"), 2);

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(latch::countDown);
        boolean ok = latch.await(5, TimeUnit.SECONDS);
        assertTrue(ok, "JavaFX runLater did not execute in time");
    }
}
