package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
// @@author uylulu-reused
// TESTS WRITTEN WITH THE HELP OF COPILOT WITH MINOR MODIFICATIONS
public class AllergyListTest {

    private static final Allergy PEANUTS = new Allergy("Peanuts");
    private static final Allergy MILK = new Allergy("Milk");
    private static final Allergy DUST = new Allergy("Dust");

    // --- Constructor Tests ---

    @Test
    public void constructor_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AllergyList(null));
    }

    @Test
    public void constructor_setContainsNull_throwsNullPointerException() {
        Set<Allergy> setWithNull = new HashSet<>(Arrays.asList(PEANUTS, null, MILK));
        assertThrows(NullPointerException.class, () -> new AllergyList(setWithNull));
    }

    @Test
    public void constructor_validSet_createsImmutableCopy() {
        Set<Allergy> mutableSet = new HashSet<>(Arrays.asList(PEANUTS, MILK));
        AllergyList allergies = new AllergyList(mutableSet);

        // Modify the original list; Allergies object should remain unaffected
        mutableSet.add(DUST);
        assertEquals(2, allergies.getAllergyList().size());
    }

    // --- getAllergyList() Tests ---

    @Test
    public void getAllergyList_unmodifiable_throwsUnsupportedOperationException() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertThrows(UnsupportedOperationException.class, () -> allergies.getAllergyList().add(DUST));
    }

    // --- isEmpty() Tests ---

    @Test
    public void isEmpty_emptySet_returnsTrue() {
        AllergyList allergies = new AllergyList(Collections.emptySet());
        assertTrue(allergies.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptySet_returnsFalse() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS));
        assertFalse(allergies.isEmpty());
    }

    // --- contains() Tests ---

    @Test
    public void contains_nullAllergy_throwsNullPointerException() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertThrows(NullPointerException.class, () -> allergies.contains(null));
    }

    @Test
    public void contains_existingAllergy_returnsTrue() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertTrue(allergies.contains(new Allergy("Peanuts")));
    }

    @Test
    public void contains_nonExistingAllergy_returnsFalse() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertFalse(allergies.contains(new Allergy("Dust")));
    }

    // --- toString() Tests ---

    @Test
    public void toString_emptyList_returnsNoKnownAllergies() {
        AllergyList allergies = new AllergyList(Collections.emptySet());
        assertEquals("No known allergies", allergies.toString());
    }

    @Test
    public void toString_nonEmptyList_returnsCommaSeparatedList() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK, DUST));
        assertEquals("Dust, Milk, Peanuts", allergies.toString());
    }

    // --- equals() and hashCode() Tests ---

    @Test
    public void equals_sameObject_returnsTrue() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertTrue(allergies.equals(allergies));
    }

    @Test
    public void equals_null_returnsFalse() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertFalse(allergies.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        AllergyList allergies = new AllergyList(Set.of(PEANUTS, MILK));
        assertFalse(allergies.equals("Peanuts"));
    }

    @Test
    public void equals_sameList_returnsTrue() {
        AllergyList allergies1 = new AllergyList(Set.of(PEANUTS, MILK));
        AllergyList allergies2 = new AllergyList(Set.of(new Allergy("Peanuts"), new Allergy("Milk")));
        assertTrue(allergies1.equals(allergies2));
        assertEquals(allergies1.hashCode(), allergies2.hashCode());
    }

    @Test
    public void equals_differentList_returnsFalse() {
        AllergyList allergies1 = new AllergyList(Set.of(PEANUTS, MILK));
        AllergyList allergies2 = new AllergyList(Set.of(PEANUTS, DUST));
        assertFalse(allergies1.equals(allergies2));
        assertNotEquals(allergies1.hashCode(), allergies2.hashCode());
    }
}
// @@author
