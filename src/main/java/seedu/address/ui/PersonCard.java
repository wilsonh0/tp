package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.person.Tag;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    private static final String CARD_STYLE = "-fx-background-color: #f5f5f5; -fx-background-radius: 10; "
        + "-fx-border-radius: 10; -fx-padding: 10;"
        + "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 1);";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label nric;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label hire;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCard} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        initialiseCard(displayedIndex);
    }

    /**
     * Creates a {@code PersonCard} with the given {@code Person} to display
     * made for showing a PersonCard in the right pane
     */
    public PersonCard(Person person) {
        super(FXML);
        this.person = person;
        initialiseCard(-1); // -1 indicates no index should be shown
    }

    /**
     * Initializes the card with the given {@code Person} and index to display.
     * @param displayedIndex The index of the person to display.
     */
    private void initialiseCard(int displayedIndex) {
        // Apply card styling
        cardPane.setStyle(CARD_STYLE);

        // Set content
        if (displayedIndex >= 0) {
            id.setText(displayedIndex + ". ");
        } else {
            id.setText(""); // Hide index for standalone cards
        }

        name.setText(person.getName().fullName);
        nric.setText(person.getNric().nric);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        hire.setText(person.getHire().hire);

        String[] tagColors = {
            "-fx-background-color: #FFCDD2; -fx-text-fill: #D32F2F;", // Red
            "-fx-background-color: #F8BBD0; -fx-text-fill: #C2185B;", // Pink
            "-fx-background-color: #D1C4E9; -fx-text-fill: #512DA8;", // Purple
            "-fx-background-color: #C5CAE9; -fx-text-fill: #303F9F;", // Indigo
            "-fx-background-color: #BBDEFB; -fx-text-fill: #1976D2;", // Blue
            "-fx-background-color: #B2EBF2; -fx-text-fill: #0097A7;", // Cyan
            "-fx-background-color: #B2DFDB; -fx-text-fill: #00796B;", // Teal
            "-fx-background-color: #C8E6C9; -fx-text-fill: #388E3C;"  // Green
        };

        // Create and style tags
        int colorIndex = 0;
        for (Tag tag : person.getTags().getTags().stream()
            .sorted(Comparator.comparing(t -> t.tagName))
            .toList()) {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle(tagColors[colorIndex % tagColors.length] +
                "-fx-text-fill: black; " +  // All text in black
                "-fx-background-radius: 10; " +
                "-fx-padding: 2 8 2 8; " +
                "-fx-font-size: 11px;");
            tagLabel.setMaxHeight(20);
            tags.getChildren().add(tagLabel);
            colorIndex++;
        }
    }
}
