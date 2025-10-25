package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a child's record in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name childName;
    private final Name parentName;
    private final Phone parentPhone;
    private final Email parentEmail;
    private final AllergyList allergies;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Person(Name childName, Name parentName, Phone parentPhone, Email parentEmail, AllergyList allergies,
                  Address address, Set<Tag> tags) {
        requireAllNonNull(childName, parentName, parentPhone, parentEmail, allergies, address, tags);
        this.childName = childName;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.allergies = allergies;
        this.address = address;
        this.tags.addAll(tags);
    }

    public Name getChildName() {
        return childName;
    }

    public Name getParentName() {
        return parentName;
    }

    public Phone getParentPhone() {
        return parentPhone;
    }

    public Email getParentEmail() {
        return parentEmail;
    }

    public Address getAddress() {
        return address;
    }

    public AllergyList getAllergies() {
        return allergies;
    }

    public Set<Allergy> getAllergyList() {
        return allergies.getAllergyList();
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getChildName().equals(getChildName())
                && otherPerson.getParentName().equals(getParentName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return childName.equals(otherPerson.childName)
                && parentName.equals(otherPerson.parentName)
                && parentPhone.equals(otherPerson.parentPhone)
                && parentEmail.equals(otherPerson.parentEmail)
                && allergies.equals(otherPerson.allergies)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(childName, parentName, parentPhone, parentEmail, allergies, address, tags);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("child", childName)
                .add("parent", parentName)
                .add("parentPhone", parentPhone)
                .add("parentEmail", parentEmail)
                .add("allergies", allergies)
                .add("address", address)
                .add("tags", tags)
                .toString();
    }

}
