package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a child's allergies information.
 * Guarantees: immutable; all elements are valid as declared in {@link Allergy#isValidAllergy(String)}.
 */
public class AllergyList {

    public static final String MESSAGE_CONSTRAINTS =
            "Allergy list should not contain null or invalid entries.";

    private final Set<Allergy> allergyList;

    /**
     * Constructs an {@code Allergies} object.
     *
     * @param allergyList A valid set of {@code Allergy} objects.
     */
    public AllergyList(Set<Allergy> allergyList) {
        requireNonNull(allergyList);
        requireAllNonNull(allergyList);
        this.allergyList = Set.copyOf(allergyList); // defensive copy, immutable
    }

    /**
     * Returns an unmodifiable view of the allergy list.
     */
    public Set<Allergy> getAllergyList() {
        return Collections.unmodifiableSet(allergyList);
    }

    /**
     * Returns true if there are no allergies.
     */
    public boolean isEmpty() {
        return allergyList.isEmpty();
    }

    /**
     * Returns true if the given allergy is contained in this list.
     */
    public boolean contains(Allergy allergy) {
        requireNonNull(allergy);
        return allergyList.contains(allergy);
    }

    @Override
    public String toString() {
        if (allergyList.isEmpty()) {
            return "No known allergies";
        }

        return allergyList.stream()
                .map(Allergy::toString)
                .sorted() // ensure deterministic order
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllergyList)) {
            return false;
        }

        AllergyList otherAllergies = (AllergyList) other;
        return allergyList.equals(otherAllergies.allergyList);
    }

    @Override
    public int hashCode() {
        return allergyList.hashCode();
    }
}
