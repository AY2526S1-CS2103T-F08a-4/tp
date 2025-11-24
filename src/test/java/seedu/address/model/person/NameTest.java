package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid names
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains invalid special character
// @@author uylulu-reused
// Test added with chatgpt
        assertFalse(Name.isValidName("12345")); // numbers only
        assertFalse(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertFalse(Name.isValidName("John@Doe")); // contains @
        assertFalse(Name.isValidName("Jane_Lee")); // contains underscore
        assertFalse(Name.isValidName("Mary#Kate")); // contains #
        assertFalse(Name.isValidName("李华")); // non-English characters
        assertFalse(Name.isValidName("François-Marie")); // accented characters
        assertFalse(Name.isValidName("Peter$")); // contains $
// @@author

        // valid names — English-only, with allowed symbols
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr II")); // long names
// @@author uylulu-reused
// Test added with chatgpt
        // new valid cases (expanded rule coverage)
        assertTrue(Name.isValidName("Mary-Kate O'Neill")); // hyphen and apostrophe
        assertTrue(Name.isValidName("Jean-Luc Picard")); // hyphen
        assertTrue(Name.isValidName("O'Connor")); // apostrophe only
        assertTrue(Name.isValidName("Ali (Senior)")); // parentheses
        assertTrue(Name.isValidName("Tan, Wei Ling")); // comma
        assertTrue(Name.isValidName("John R. R. Tolkien")); // period and initials
        assertTrue(Name.isValidName("Alice Pauline d/o Paulines Mom")); // slash
        assertTrue(Name.isValidName("Anita Devi w/o Rajesh Singh")); // slash
        assertTrue(Name.isValidName("Henry VIII")); // numeric suffix
// @@author
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");
        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));
        // same object -> returns true
        assertTrue(name.equals(name));
        // different case -> returns true
        assertTrue(name.equals(new Name("vALID nAME")));
        // same name with extra spaces -> returns true
        assertTrue(name.equals(new Name("Valid  Name"))); // two spaces
        // null -> returns false
        assertFalse(name.equals(null));
        // different types -> returns false
        assertFalse(name.equals(5.0f));
        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
