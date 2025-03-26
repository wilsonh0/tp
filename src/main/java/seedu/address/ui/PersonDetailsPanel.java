package seedu.address.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;

/**
 * A UI component that displays detailed information of a {@code Person}.
 */
public class PersonDetailsPanel extends UiPart<Region> {

    private static final String FXML = "PersonDetailsPanel.fxml";
    private static final String DEFAULT_STYLE = "-fx-padding: 10; -fx-background-color: -fx-background;";
    private static final String SECTION_HEADER_STYLE = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-padding: 5 0 5 0;";
    private static final String DETAIL_LABEL_STYLE = "-fx-font-size: 13px; -fx-padding: 2 0 2 0;";
    private static final String LEAVE_ITEM_STYLE = "-fx-font-size: 12px; -fx-padding: 3 0 3 10;";
    private static final String EMPTY_STATE_TEXT = "No employee selected. Select an employee from the list to view details.";

    private Person person;

    private PersonCard personCard;

    @FXML
    private HBox contentPane;

    // Table components
    private TableView<Leave> leaveTable;
    private Label noLeavesLabel;

    /**
     * Creates a {@code PersonDetailsPanel} with an empty state.
     */
    public PersonDetailsPanel() {
        super(FXML);
        personCard = null;
        showEmptyState();
    }

    /**
     * Initializes the panel by setting up the leave records header.
     * This method is called automatically after the FXML components are injected.
     */
    @FXML
    private void initialize() {
        // Initialize the leave table
        initializeLeaveTable();

        // Initialize the no leaves label
        noLeavesLabel = new Label("No leave records found!");
        noLeavesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10;");

        // Set up the content pane
        contentPane.setSpacing(20);
        contentPane.setPadding(new Insets(10));
    }

    private void initializeLeaveTable() {
        leaveTable = new TableView<>();
        leaveTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // From column
        TableColumn<Leave, String> fromCol = new TableColumn<>("From");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("formattedStartDate"));

        // To column
        TableColumn<Leave, String> toCol = new TableColumn<>("To");
        toCol.setCellValueFactory(new PropertyValueFactory<>("formattedEndDate"));

        // Reason column
        TableColumn<Leave, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));

        leaveTable.getColumns().addAll(fromCol, toCol, reasonCol);
        leaveTable.setPrefWidth(0.7 * contentPane.getWidth()); // 70% width
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
        contentPane.getChildren().clear();

        // Add person card
        personCard = new PersonCard(person);
        contentPane.getChildren().add(personCard.getRoot());

        // Create container for leave records and future attendance (70-30 split)
        HBox recordsContainer = new HBox();
        recordsContainer.setSpacing(20);

        // Leave records (70%)
        VBox leaveSection = new VBox();
        leaveSection.setSpacing(10);
        leaveSection.setPrefWidth(0.7 * contentPane.getWidth());

        Label leaveHeader = new Label("LEAVE RECORDS");
        leaveHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        leaveSection.getChildren().add(leaveHeader);

        if (person.getLeaves().isEmpty()) {
            leaveSection.getChildren().add(noLeavesLabel);
        } else {
            ObservableList<Leave> leaves = FXCollections.observableArrayList(person.getLeaves());
            leaveTable.setItems(leaves);
            leaveSection.getChildren().add(leaveTable);
        }

        // Attendance placeholder (30%)
        VBox attendanceSection = new VBox();
        attendanceSection.setSpacing(10);
        attendanceSection.setPrefWidth(0.3 * contentPane.getWidth());

        Label attendanceHeader = new Label("ATTENDANCE");
        attendanceHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        attendanceSection.getChildren().addAll(attendanceHeader);
        // Add attendance content later

        recordsContainer.getChildren().addAll(leaveSection, attendanceSection);
        contentPane.getChildren().add(recordsContainer);
    }
    /**
     * Shows the empty state of the panel.
     */
    private void showEmptyState() {
        contentPane.getChildren().clear();

        Label emptyStateLabel = new Label(EMPTY_STATE_TEXT);
        emptyStateLabel.setStyle("-fx-font-style: italic; -fx-text-fill: derive(-fx-text-background-color, -30%);");
        emptyStateLabel.setAlignment(Pos.CENTER);
        emptyStateLabel.setWrapText(true);
        contentPane.getChildren().add(emptyStateLabel);
    }

    /**
     * Creates a spacer with the specified height.
     */
    private Region createSpacer(double height) {
        Region spacer = new Region();
        spacer.setPrefHeight(height);
        return spacer;
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