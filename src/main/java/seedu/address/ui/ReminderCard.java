package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.reminder.Reminder;

/**
 * A UI component that displays information of a {@code Reminder}.
 */
public class ReminderCard extends UiPart<Region> {
    private static final String FXML = "ReminderListCard.fxml";
    private static final String DUE_SOON_MESSAGE = " (Due Soon!)";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Reminder reminder;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label dueDate;
    @FXML
    private Label isDueSoon;
    @FXML
    private Label description;

    /**
     * Creates a {@code ReminderCode} with the given {@code Reminder} and index to display.
     */
    public ReminderCard(Reminder reminder, int displayedIndex) {
        super(FXML);
        this.reminder = reminder;
        id.setText(displayedIndex + ". ");
        dueDate.setText(reminder.getDueDate().toString());
        isDueSoon.setText("");
        description.setText("Description: " + reminder.getDescription().toString());
        applyColourBasedOnDueDate();
    }

    /**
     * Applies colour style to the card based on how soon the reminder is due.
     */
    public void applyColourBasedOnDueDate() {
        long daysUntilDue = reminder.daysUntilDueDate();
        if (daysUntilDue >= 0 && daysUntilDue <= 3) {
            isDueSoon.setText(DUE_SOON_MESSAGE);
            isDueSoon.getStyleClass().add("due-soon-text");
        } else if (daysUntilDue < 0) {
            id.getStyleClass().add("overdue");
            dueDate.getStyleClass().add("overdue");
            description.getStyleClass().add("overdue");
            isDueSoon.getStyleClass().add("overdue");
        }
    }
}
