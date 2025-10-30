package seedu.address.ui;


import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.homework.Homework;

/**
 * Panel that displays a list of homework for a student.
 * Each homework item is shown with a checkbox (mark as done/undone)
 * and a label containing its description and deadline
 */
public class HomeworkListPanel extends UiPart<Region> {
    private static final String FXML = "HomeworkListPanel.fxml";

    @FXML
    private ListView<Homework> homeworkListView;

    /**
     * Creates a HomeworkListPanel that displays list of homework
     *
     * @param homeworkList list of homework belogning to a student
     */
    public HomeworkListPanel(ObservableList<Homework> homeworkList) {
        super(FXML);
        homeworkListView.setItems(homeworkList);
        homeworkListView.setCellFactory(listView -> new HomeworkListViewCell());
    }

    /**
     *  Cell that shows a checkbox and label for each homework.
     *  Each row contains a checkbox followed by a label (description + deadline)
     */
    private static class HomeworkListViewCell extends ListCell<Homework> {
        private final HBox content = new HBox(10);
        private final CheckBox doneCheckBox = new CheckBox();
        private final Label label = new Label();

        public HomeworkListViewCell() {
            content.getChildren().addAll(doneCheckBox, label);
        }

        @Override
        protected void updateItem(Homework hw, boolean empty) {
            super.updateItem(hw, empty);
            doneCheckBox.setDisable(true);


            if (empty || hw == null) {
                setGraphic(null);
                return;
            }

            //set label to show index + description + deadline
            int displayIndex = getIndex() + 1;
            label.setText(displayIndex + ". " + hw.getDescription() + " (due " + hw.getDeadline() + ")");

            //set checkbox status based on isDone boolean
            doneCheckBox.setSelected(hw.isDone());

            setGraphic(content);
        }
    }
}


