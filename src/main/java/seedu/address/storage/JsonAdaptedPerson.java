package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String childName;
    private final String parentName;
    private final String parentPhone;
    private final String parentEmail;
    private final String address;
    private final List<String> allergies = new ArrayList<>();
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(
            @JsonProperty("childName") String childName,
            @JsonProperty("parentName") String parentName,
            @JsonProperty("parentPhone") String parentPhone,
            @JsonProperty("parentEmail") String parentEmail,
            @JsonProperty("address") String address,
            @JsonProperty("allergies") List<String> allergies,
            @JsonProperty("tags") List<JsonAdaptedTag> tags) {

        this.childName = childName;
        this.parentName = parentName;
        this.parentPhone = parentPhone;
        this.parentEmail = parentEmail;
        this.address = address;

        if (allergies != null) {
            this.allergies.addAll(allergies);
        }
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        childName = source.getChildName().toString();
        parentName = source.getParentName().toString();
        parentPhone = source.getParentPhone().value;
        parentEmail = source.getParentEmail().value;
        address = source.getAddress().value;

        allergies.addAll(source.getAllergies().getAllergyList().stream()
                .map(Allergy::toString)
                .collect(Collectors.toList()));

        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (childName == null || parentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(childName) || !Name.isValidName(parentName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelChildName = new Name(childName);
        final Name modelParentName = new Name(parentName);

        if (parentPhone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(parentPhone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelParentPhone = new Phone(parentPhone);

        if (parentEmail == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(parentEmail)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelParentEmail = new Email(parentEmail);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        // Convert allergy strings into Allergy objects
        final Set<Allergy> modelAllergiesList = new HashSet<>();
        for (String allergyName : allergies) {
            if (!Allergy.isValidAllergy(allergyName)) {
                throw new IllegalValueException(Allergy.MESSAGE_CONSTRAINTS);
            }
            modelAllergiesList.add(new Allergy(allergyName));
        }
        final AllergyList modelAllergies = new AllergyList(modelAllergiesList);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        return new Person(modelChildName, modelParentName, modelParentPhone,
                modelParentEmail, modelAllergies, modelAddress, modelTags);
    }
}
