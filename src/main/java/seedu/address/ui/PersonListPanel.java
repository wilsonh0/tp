package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);
    private static final String LIST_VIEW_STYLE =
        "-fx-control-inner-background: transparent; " +
            "-fx-background-color: transparent; " +
            "-fx-padding: 5; " +  // Padding around the entire list
            "-fx-cell-size: 150;";

    @FXML
    private ListView<Person> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
        personListView.setStyle(LIST_VIEW_STYLE); // Makes list background transparent

        // Apply CSS for spacing between cells
        personListView.getStylesheets().add(getClass().getResource("/view/ListViewStyle.css").toExternalForm());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
                setStyle("-fx-padding: 0; -fx-background-color: transparent;");
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
                setStyle("-fx-padding: 0 0 8 0; -fx-background-color: transparent;");
            }
        }
    }

}
