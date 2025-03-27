package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";
    private static final String EMPTY_STATE_TEXT = "No employee selected. Select an employee from the list to view details.";

    @FXML private StackPane personCardPlaceholder;
    @FXML private StackPane leaveTablePlaceholder;
    @FXML private Label noLeavesLabel;
    @FXML private VBox leaveSection;
    @FXML private VBox attendanceSection;

    private Person person;
    private PersonCard personCard;
    private TableView<Leave> leaveTable;
    private ScrollPane leaveScrollPane;

    /**
     * Creates a {@code PersonDetailsPanel} with an empty state.
     */
    public PersonDetailsPanel() {
        super(FXML);
        initializeLeaveTable();
    }

    /**
     * Initializes the leave table and adds it to the placeholder.
     */
    private void initializeLeaveTable() {
        leaveTable = new TableView<>();
        leaveTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Leave, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));

        TableColumn<Leave, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("formattedEndDate"));

        TableColumn<Leave, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));

        leaveTable.getColumns().addAll(fromCol, toCol, reasonCol);

        leaveScrollPane = new ScrollPane(leaveTable);
        leaveScrollPane.setFitToWidth(true);
        leaveScrollPane.setFitToHeight(true);
        leaveScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        leaveTablePlaceholder.getChildren().add(leaveScrollPane);
    }

    /**
     * Updates the panel to display the details of the given {@code person}.
     */
    public void setPerson(Person person) {
        if (person == null) {
            showEmptyState();
            return;
        }

        this.person = person;
        clearContent();

        // Set up person card
        personCard = new PersonCard(person);
        personCardPlaceholder.getChildren().add(personCard.getRoot());

        // Update leave records
        if (person.getLeaves().isEmpty()) {
            noLeavesLabel.setVisible(true);
            leaveTablePlaceholder.setVisible(false);
        } else {
            noLeavesLabel.setVisible(false);
            leaveTablePlaceholder.setVisible(true);
            leaveTable.setItems(FXCollections.observableArrayList(person.getLeaves()));
        }

        // Force UI update
        Platform.runLater(() -> leaveTable.requestLayout());
    }

    /**
     * Clears the content of the panel.
     */
    private void clearContent() {
        personCardPlaceholder.getChildren().clear();
        noLeavesLabel.setVisible(false);
    }

    /**
     * Shows the empty state of the panel.
     */
    private void showEmptyState() {
        clearContent();
        Label emptyStateLabel = new Label(EMPTY_STATE_TEXT);
        emptyStateLabel.setStyle("-fx-font-style: italic; -fx-text-fill: derive(-fx-text-background-color, -30%);");
        emptyStateLabel.setAlignment(Pos.CENTER);
        emptyStateLabel.setWrapText(true);
        personCardPlaceholder.getChildren().add(emptyStateLabel);
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
     * Formats a Leave object to a string.
     */
    private String formatLeave(Leave leave) {
        String startDate = formatDate(leave.getStartDate());
        String endDate = formatDate(leave.getEndDate());
        String reason = leave.getReason();
        return String.format("✓ From %s to %s: %s", startDate, endDate, reason);
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
