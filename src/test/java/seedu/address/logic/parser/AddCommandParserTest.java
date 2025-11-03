package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_DUST;
import static seedu.address.logic.commands.CommandTestUtil.ALLERGY_DESC_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB_PARENT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SINGLEPARENT;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_SPECIALNEEDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_DUST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALLERGY_PEANUTS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SINGLEPARENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_SPECIALNEEDS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIQUE_ID_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTEMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    private void assertAddCommandParseSuccessIgnoringUniqueId(AddCommandParser parser, String userInput,
                                                    Person expectedPerson) {
        try {
            AddCommand command = parser.parse(userInput);
            Person actualPerson = command.getPersonToAdd();

            assertEquals(expectedPerson.getChildName(), actualPerson.getChildName());
            assertEquals(expectedPerson.getParentName(), actualPerson.getParentName());
            assertEquals(expectedPerson.getParentPhone(), actualPerson.getParentPhone());
            assertEquals(expectedPerson.getParentEmail(), actualPerson.getParentEmail());
            assertEquals(expectedPerson.getAddress(), actualPerson.getAddress());
            assertEquals(expectedPerson.getAllergies(), actualPerson.getAllergies());
            assertEquals(expectedPerson.getTags(), actualPerson.getTags());
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withAllergies(VALID_ALLERGY_DUST)
                        .withTags(VALID_TAG_SINGLEPARENT).build();

        // whitespace only preamble
        assertAddCommandParseSuccessIgnoringUniqueId(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + NAME_DESC_BOB_PARENT
                + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT + ADDRESS_DESC_BOB + TAG_DESC_SINGLEPARENT
                + ALLERGY_DESC_DUST, expectedPerson);

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB)
                .withAllergies(VALID_ALLERGY_DUST, VALID_ALLERGY_PEANUTS)
                .withTags(VALID_TAG_SINGLEPARENT, VALID_TAG_SPECIALNEEDS)
                .build();
        assertAddCommandParseSuccessIgnoringUniqueId(parser,
                NAME_DESC_BOB + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT
                        + ADDRESS_DESC_BOB + ALLERGY_DESC_DUST + ALLERGY_DESC_PEANUTS
                        + TAG_DESC_SPECIALNEEDS + TAG_DESC_SINGLEPARENT,
                expectedPersonMultipleTags);
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT
                + ADDRESS_DESC_BOB + TAG_DESC_SINGLEPARENT;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILDNAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY_PARENT + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTPHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY_PARENT + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTEMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString
                        + PHONE_DESC_AMY_PARENT
                        + EMAIL_DESC_AMY_PARENT
                        + NAME_DESC_AMY
                        + ADDRESS_DESC_AMY,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILDNAME, PREFIX_ADDRESS, PREFIX_PARENTEMAIL,
                        PREFIX_PARENTPHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILDNAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTEMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTPHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILDNAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTEMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTPHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withAllergies().withTags()
                .withUniqueId(VALID_UNIQUE_ID_AMY).build();
        assertAddCommandParseSuccessIgnoringUniqueId(parser,
                NAME_DESC_AMY + NAME_DESC_AMY_PARENT + PHONE_DESC_AMY_PARENT
                        + EMAIL_DESC_AMY_PARENT + ADDRESS_DESC_AMY,
                expectedPerson);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser,
                VALID_NAME_BOB + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB_PARENT + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB_PARENT + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser,
                INVALID_NAME_DESC + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT
                        + ADDRESS_DESC_BOB + TAG_DESC_SPECIALNEEDS + TAG_DESC_SINGLEPARENT,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser,
                NAME_DESC_BOB + NAME_DESC_BOB_PARENT + INVALID_PHONE_DESC + EMAIL_DESC_BOB_PARENT
                        + ADDRESS_DESC_BOB + TAG_DESC_SPECIALNEEDS + TAG_DESC_SINGLEPARENT,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser,
                NAME_DESC_BOB + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_BOB + TAG_DESC_SPECIALNEEDS + TAG_DESC_SINGLEPARENT,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser,
                NAME_DESC_BOB + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT
                        + INVALID_ADDRESS_DESC + TAG_DESC_SPECIALNEEDS + TAG_DESC_SINGLEPARENT,
                Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser,
                NAME_DESC_BOB + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT
                        + ADDRESS_DESC_BOB + INVALID_TAG_DESC + VALID_TAG_SINGLEPARENT,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser,
                INVALID_NAME_DESC + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT + EMAIL_DESC_BOB_PARENT
                        + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_BOB + NAME_DESC_BOB_PARENT + PHONE_DESC_BOB_PARENT
                        + EMAIL_DESC_BOB_PARENT + ADDRESS_DESC_BOB + TAG_DESC_SPECIALNEEDS + TAG_DESC_SINGLEPARENT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
