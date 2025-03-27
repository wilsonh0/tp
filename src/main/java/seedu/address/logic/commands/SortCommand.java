package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.comparators.AddressComparator;
import seedu.address.model.person.comparators.EmailComparator;
import seedu.address.model.person.comparators.HireComparator;
import seedu.address.model.person.comparators.NameComparator;
import seedu.address.model.person.comparators.NricComparator;
import seedu.address.model.person.comparators.PhoneComparator;

/**
 * Sorts all persons in the address book by a specified attribute in either ascending or descending order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Sorts all persons by the specified attribute in the given direction.\n"
        + "Parameters: [ATTRIBUTE] [DIRECTION]\n"
        + "ATTRIBUTE must be one of: name, nric, phone, address, email, hire\n"
        + "DIRECTION must be one of: asc (ascending), desc (descending)\n"
        + "Example: " + COMMAND_WORD + " name asc";

    public static final String MESSAGE_INVALID_ATTRIBUTE = "Invalid attribute specified.\n";

    public static final String MESSAGE_INVALID_DIRECTION = "Invalid direction specified.\n";

    public static final String MESSAGE_SUCCESS = "Sorted all persons by %1$s in %2$s order.";

    private final String attribute;
    private final String direction;

    /**
     * Creates a SortCommand to sort persons by the specified attribute and direction.
     *
     * @param attribute The attribute to sort by (must be one of the valid attributes)
     * @param direction The sort direction (must be either "asc" or "desc")
     * @throws NullPointerException if either attribute or direction is null
     */
    public SortCommand(String attribute, String direction) {
        requireNonNull(attribute);
        requireNonNull(direction);
        this.attribute = attribute;
        this.direction = direction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        List<Person> originalPersons = model.getAddressBook().getPersonList();

        ArrayList<Person> sortedPersons = new ArrayList<>(originalPersons);

        if (!direction.equals("asc") && !direction.equals("desc")) {
            throw new CommandException(MESSAGE_INVALID_DIRECTION + MESSAGE_USAGE);
        }

        switch (attribute) {
        case "name":
            sortedPersons.sort(direction.equals("desc") ? new NameComparator().reversed() : new NameComparator());
            break;
        case "nric":
            sortedPersons.sort(direction.equals("desc") ? new NricComparator().reversed() : new NricComparator());
            break;
        case "phone":
            sortedPersons.sort(direction.equals("desc") ? new PhoneComparator().reversed() : new PhoneComparator());
            break;
        case "address":
            sortedPersons.sort(direction.equals("desc") ? new AddressComparator().reversed() : new AddressComparator());
            break;
        case "email":
            sortedPersons.sort(direction.equals("desc") ? new EmailComparator().reversed() : new EmailComparator());
            break;
        case "hire":
            sortedPersons.sort(direction.equals("desc") ? new HireComparator().reversed() : new HireComparator());
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_ATTRIBUTE + MESSAGE_USAGE);
        }

        AddressBook newAddressBook = new AddressBook();
        newAddressBook.setPersons(sortedPersons);
        model.setAddressBook(newAddressBook);

        return new CommandResult(String.format(MESSAGE_SUCCESS, attribute,
                direction.equals("desc") ? "descending" : "ascending"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return attribute.equals(otherCommand.attribute) && direction.equals(otherCommand.direction);
    }
}
