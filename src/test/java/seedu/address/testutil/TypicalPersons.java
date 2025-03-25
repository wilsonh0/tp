package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HIRE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HIRE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.attendance.Attendance;
import seedu.address.model.leave.Leave;
import seedu.address.model.person.Person;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withName("Alice Pauline")
            .withNric("S1234567A")
            .withPhone("94351253")
            .withEmail("alice@example.com")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withHire("2019-10-10")
            .withTags("friends")
            .withLeaves(new Leave("2021-10-10", "2021-10-12", "sick"))
            .withAttendance(new Attendance())
            .build();

    public static final Person BENSON = new PersonBuilder()
            .withName("Benson Meier")
            .withNric("T2132398K")
            .withPhone("98765432")
            .withEmail("johnd@example.com")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withHire("2022-10-10")
            .withTags("owesMoney", "friends")
            .withLeaves(new Leave("2022-10-10", "2022-11-10", "paternity"))
        .withAttendance(new Attendance())
            .build();

    public static final Person CARL = new PersonBuilder()
            .withName("Carl Kurz")
            .withNric("S1234572F")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withHire("2020-10-10")
            .withLeaves(new Leave("2021-10-11", "2021-10-12", "Annual"))
            .withTags("friends")
            .build();

    public static final Person DANIEL = new PersonBuilder()
            .withName("Daniel Meier")
            .withNric("S1234573G")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withHire("2023-04-01")
            .withTags("friends")
            .build();

    public static final Person ELLE = new PersonBuilder()
            .withName("Elle Meyer")
            .withNric("T0000002A")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withHire("2024-02-10")
            .withTags("rivals")
            .build();

    public static final Person FIONA = new PersonBuilder()
            .withName("Fiona Kunz")
            .withNric("T0000003A")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withHire("2024-02-10")
            .build();

    public static final Person GEORGE = new PersonBuilder()
            .withName("George Best")
            .withNric("T0000003A")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withHire("2024-02-10")
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withName("Hoon Meier")
            .withNric("S1234568B")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withHire("2023-08-01")
            .build();

    public static final Person IDA = new PersonBuilder()
            .withName("Ida Mueller")
            .withNric("S1234569C")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withHire("2023-09-01")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withNric(VALID_NRIC_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withHire(VALID_HIRE_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();

    public static final Person BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withNric(VALID_NRIC_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withHire(VALID_HIRE_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
