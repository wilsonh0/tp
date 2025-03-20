package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";

    private Person person;

    @FXML
    private VBox detailsPane;

    private PersonCard personCard;

    @FXML
    private Label leaveRecordsHeader;

    /**
     * Creates a {@code PersonDetailsPanel} with an empty state.
     */
    public PersonDetailsPanel() {
        super(FXML);
        personCard = null; // Start with no person displayed
    }

    /**
     * Initializes the panel by setting up the leave records header.
     * This method is called automatically after the FXML components are injected.
     */
    @FXML
    private void initialize() {
        // Initialize Leave Records Header
        leaveRecordsHeader.setText("Leave Records");
        leaveRecordsHeader.setStyle("-fx-font-weight: bold;");
        leaveRecordsHeader.setAlignment(Pos.CENTER_LEFT);
        leaveRecordsHeader.setVisible(false); // Initially hidden
    }

    /**
     * Updates the panel to display the details of the given {@code person}.
     */
    public void setPerson(Person person) {
        if (person == null) {
            detailsPane.getChildren().clear();
            leaveRecordsHeader.setVisible(false);
            return;
        }

        this.person = person;

        // Clear previous details and show the new person's details
        detailsPane.getChildren().clear();

        // Create a PersonCard for the selected person
        personCard = new PersonCard(person); // Index is 0 because it's a standalone card
        detailsPane.getChildren().add(personCard.getRoot());

        // Show the leave records
        if (!person.getLeaves().isEmpty()) {
            leaveRecordsHeader.setVisible(true);
            for (Leave leave : person.getLeaves()) {
                Label leaveLabel = new Label(formatLeave(leave));
                detailsPane.getChildren().add(leaveLabel);
            }
        } else {
            leaveRecordsHeader.setVisible(false);
        }
    }

    /**
     * Formats a LocalDate to "18th March 2025" style.
     */
    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        int day = date.getDayOfMonth();
        String suffix = getDaySuffix(day);
        return day + suffix + " " + date.format(formatter);
    }

    /**
     * Formats a Leave object to a string: "From 18th March 2025 to 20th March 2025: Sick Leave"
     */
    private String formatLeave(Leave leave) {
        String startDate = formatDate(leave.getStartDate());
        String endDate = formatDate(leave.getEndDate());
        String reason = leave.getReason();
        return "From " + startDate + " to " + endDate + ": " + reason;
    }

    /**
     * Returns the appropriate suffix for a given day.
     */
    private String getDaySuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
        case 1: return "st";
        case 2: return "nd";
        case 3: return "rd";
        default: return "th";
        }
    }
}
