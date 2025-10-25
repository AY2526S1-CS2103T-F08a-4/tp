package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        // TODO: For Edit command person to fix
        descriptor = new EditPersonDescriptor();
        descriptor.setChildName(person.getChildName());
        descriptor.setParentName(person.getParentName());
        descriptor.setParentPhone(person.getParentPhone());
        descriptor.setParentEmail(person.getParentEmail());
        descriptor.setAllergies(person.getAllergies());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code childName} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withChildName(String childName) {
        descriptor.setChildName(new Name(childName));
        return this;
    }

    /**
     * Sets the {@code parentName} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentName(String parentName) {
        descriptor.setParentName(new Name(parentName));
        return this;
    }

    /**
     * Sets the {@code parentPhone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentPhone(String parentPhone) {
        descriptor.setParentPhone(new Phone(parentPhone));
        return this;
    }

    /**
     * Sets the {@code parentEmail} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withParentEmail(String parentEmail) {
        descriptor.setParentEmail(new Email(parentEmail));
        return this;
    }

    /**
     * Parses the {@code allergies} into a {@code List<Allergy>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withAllergies(String... allergies) {
        Set<Allergy> allergyList = Stream.of(allergies).map(Allergy::new).collect(Collectors.toSet());
        descriptor.setAllergies(new AllergyList(allergyList));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
