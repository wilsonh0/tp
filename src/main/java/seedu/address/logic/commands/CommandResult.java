package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Person;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The person to view for view command (optional). */
    private final Person personToView;

    /** The details panel should be cleared. */
    private final boolean clearDetailsPanel;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit, boolean clearDetailsPanel) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.personToView = null;
        this.clearDetailsPanel = clearDetailsPanel;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and {@code personToView} for the view command.
     */
    public CommandResult(String feedbackToUser, Person personToView, boolean clearDetailsPanel) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.exit = false;
        this.personToView = requireNonNull(personToView);
        this.clearDetailsPanel = clearDetailsPanel;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and {@code personToView} for the view command, with default clearDetailsPanel value.
     */
    public CommandResult(String feedbackToUser, Person personToView) {
        this(feedbackToUser, personToView, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public Person getPersonToView() {
        return personToView;
    }

    public boolean isClearDetailsPanel() {
        return clearDetailsPanel;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && Objects.equals(personToView, otherCommandResult.personToView)
                && clearDetailsPanel == otherCommandResult.clearDetailsPanel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .add("personToView", personToView)
                .add("clearDetailsPanel", clearDetailsPanel)
                .toString();
    }

}
