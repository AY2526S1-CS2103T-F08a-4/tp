package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIQUE_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIQUE_ID_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

// @@author uylulu-reused
// Child and parent information was generated with Github Copilot.
/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder()
            .withChildName("Alice Pauline")
            .withParentName("Paulines Mom")
            .withParentPhone("94351253")
            .withParentEmail("alice@example.com")
            .withAllergies("Peanuts")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withUniqueId(0)
            .build();

    public static final Person BENSON = new PersonBuilder()
            .withChildName("Benson Meier")
            .withParentName("Bensons Dad")
            .withParentPhone("98765432")
            .withParentEmail("johnd@example.com")
            .withAllergies("Milk", "Shellfish")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withUniqueId(1)
            .build();

    public static final Person CARL = new PersonBuilder()
            .withChildName("Carl Kurz")
            .withParentName("Carls Mom")
            .withParentPhone("95352563")
            .withParentEmail("heinz@example.com")
            .withAllergies("Dust")
            .withAddress("wall street")
            .withUniqueId(2)
            .build();

    public static final Person DANIEL = new PersonBuilder()
            .withChildName("Daniel Meier")
            .withParentName("Daniels Dad")
            .withParentPhone("87652533")
            .withParentEmail("cornelia@example.com")
            .withAllergies("Eggs")
            .withAddress("10th Street")
            .withUniqueId(3)
            .build();

    public static final Person ELLE = new PersonBuilder()
            .withChildName("Elle Meyer")
            .withParentName("Elles Mom")
            .withParentPhone("94822248")
            .withParentEmail("werner@example.com")
            .withAllergies("Pollen")
            .withAddress("Michegan Ave")
            .withUniqueId(4)
            .build();

    public static final Person FIONA = new PersonBuilder()
            .withChildName("Fiona Kunz")
            .withParentName("Fionas Dad")
            .withParentPhone("94824278")
            .withParentEmail("lydia@example.com")
            .withAllergies("Gluten")
            .withAddress("Little Tokyo")
            .withUniqueId(5)
            .build();

    public static final Person GEORGE = new PersonBuilder()
            .withChildName("George Best")
            .withParentName("Georges Mom")
            .withParentPhone("94824427")
            .withParentEmail("anna@example.com")
            .withAllergies() // no allergies
            .withAddress("4th Street")
            .withUniqueId(6)
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder()
            .withChildName("Hoon heh")
            .withParentName("Elle egh")
            .withParentPhone("84824249")
            .withParentEmail("elle@example.com")
            .withAllergies("Soy")
            .withAddress("Little India")
            .withUniqueId(7)
            .build();

    public static final Person IDA = new PersonBuilder()
            .withChildName("Ida Mueller")
            .withParentName("Idas Dad")
            .withParentPhone("84821313")
            .withParentEmail("hans@example.com")
            .withAllergies("Dust")
            .withAddress("Chicago Ave")
            .withUniqueId(8)
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder()
            .withChildName(VALID_NAME_AMY)
            .withParentName(VALID_NAME_AMY_PARENT)
            .withParentPhone(VALID_PHONE_AMY)
            .withParentEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withUniqueId(VALID_UNIQUE_ID_AMY)
            .build();

    public static final Person BOB = new PersonBuilder()
            .withChildName(VALID_NAME_BOB)
            .withParentName(VALID_NAME_BOB_PARENT)
            .withParentPhone(VALID_PHONE_BOB)
            .withParentEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withUniqueId(VALID_UNIQUE_ID_BOB)
            .build();
//@@author

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
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, AMY, BOB));
    }
}
