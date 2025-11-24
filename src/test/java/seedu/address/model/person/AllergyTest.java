package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
// @@author uylulu-reused
// TESTS WRITTEN WITH THE HELP OF COPILOT WITH MINOR MODIFICATIONS
public class AllergyTest {

    // Valid examples
    private static final String VALID_ALLERGY_1 = "Peanuts";
    private static final String VALID_ALLERGY_2 = "Milk Protein";
    private static final String VALID_ALLERGY_3 = "Dust Mite";
    private static final String VALID_ALLERGY_4 = "Shellfish";

    // Invalid examples
    private static final String INVALID_ALLERGY_EMPTY = "";
    private static final String INVALID_ALLERGY_SPACES = "   ";
    private static final String INVALID_ALLERGY_SYMBOLS = "Egg@123";
    private static final String INVALID_ALLERGY_STARTS_WITH_SPACE = " Dust";

    // --- isValidAllergy() tests ---

    @Test
    public void isValidAllergy_validInputs_returnTrue() {
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_1));
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_2));
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_3));
        assertTrue(Allergy.isValidAllergy(VALID_ALLERGY_4));
    }

    @Test
    public void isValidAllergy_invalidInputs_returnFalse() {
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_EMPTY));
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_SPACES));
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_SYMBOLS));
        assertFalse(Allergy.isValidAllergy(INVALID_ALLERGY_STARTS_WITH_SPACE));
    }
// @@author

    // --- Length boundary tests ---

    @Test
    public void isValidAllergy_maxLength_returnsTrue() {
        int max = Allergy.MAX_ALLERGY_LENGTH;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < max; i++) {
            sb.append('A');
        }
        String maxLength = sb.toString();
        // should be valid by length (also matches regex since it starts with alnum)
        assertTrue(Allergy.isValidAllergy(maxLength));
        Allergy a = new Allergy(maxLength);
        assertEquals(maxLength, a.toString());
    }

    @Test
    public void isValidAllergy_overMaxLength_returnsFalseAndConstructorThrows() {
        int over = Allergy.MAX_ALLERGY_LENGTH + 1;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < over; i++) {
            sb.append('A');
        }
        String overLength = sb.toString();
        assertFalse(Allergy.isValidAllergy(overLength));
        assertThrows(IllegalArgumentException.class, () -> new Allergy(overLength));
    }

    // --- Constructor tests ---
// @@author uylulu-reused
// TESTS WRITTEN WITH THE HELP OF COPILOT WITH MINOR MODIFICATIONS
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Allergy(null));
    }

    @Test
    public void constructor_invalidAllergy_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Allergy(INVALID_ALLERGY_EMPTY));
        assertThrows(IllegalArgumentException.class, () -> new Allergy(INVALID_ALLERGY_SPACES));
        assertThrows(IllegalArgumentException.class, () -> new Allergy(INVALID_ALLERGY_SYMBOLS));
    }

    @Test
    public void constructor_validAllergy_createsSuccessfully() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertEquals("Peanuts", allergy.toString());
    }

    // --- Equality tests ---

    @Test
    public void equals_sameObject_returnsTrue() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertTrue(allergy.equals(allergy));
    }

    @Test
    public void equals_null_returnsFalse() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertFalse(allergy.equals(null));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        Allergy allergy = new Allergy(VALID_ALLERGY_1);
        assertFalse(allergy.equals("Peanuts"));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Allergy allergy1 = new Allergy(VALID_ALLERGY_1);
        Allergy allergy2 = new Allergy(VALID_ALLERGY_1);
        assertTrue(allergy1.equals(allergy2));
        assertEquals(allergy1.hashCode(), allergy2.hashCode());
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Allergy allergy1 = new Allergy(VALID_ALLERGY_1);
        Allergy allergy2 = new Allergy(VALID_ALLERGY_2);
        assertFalse(allergy1.equals(allergy2));
        assertNotEquals(allergy1.hashCode(), allergy2.hashCode());
    }

    // --- toString() tests ---

    @Test
    public void toString_returnsCorrectValue() {
        Allergy allergy = new Allergy("Shellfish");
        assertEquals("Shellfish", allergy.toString());
    }
}
// @@author
