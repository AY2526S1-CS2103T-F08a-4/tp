package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[]{
            new Person(
                new Name("Alex Jr"),
                new Name("Alex Sr"),
                new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new AllergyList(
                    Set.of(new Allergy("Peanuts"), new Allergy("Milk"))
                ),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("adhd")
                ),
            new Person(
                new Name("Bernice Yu"),
                new Name("Bernices Mom"),
                new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new AllergyList(
                    Set.of(new Allergy("Shellfish"))
                ),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("adhd", "only child")
                ),
            new Person(
                new Name("Charlotte Oliveir"),
                new Name("Charlottes Mom"),
                new Phone("93210283"),
                new Email("charlotte@example.com"),
                new AllergyList(
                    Set.of(new Allergy("Dust"))
                ),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("vegetarian")
                ),
            new Person(
                new Name("David Li"),
                new Name("Davids Dad"),
                new Phone("91031282"),
                new Email("lidavid@example.com"),
                new AllergyList(
                    Set.of() // No known allergies
                ),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("diabetic")
                ),
            new Person(
                new Name("Irfan Ibrahim"),
                new Name("Irfans Dad"),
                new Phone("92492021"),
                new Email("irfan@example.com"),
                new AllergyList(
                    Set.of(new Allergy("Eggs"), new Allergy("Soy"))
                ),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("deaf", "blind")
                ),
            new Person(
                new Name("Roy Balakrishnan"),
                new Name("Roys Dad"),
                new Phone("92624417"),
                new Email("royb@example.com"),
                new AllergyList(
                    Set.of(new Allergy("Gluten"))
                ),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("hyperhidrosis", "only child")
                )
        };
    }


    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
