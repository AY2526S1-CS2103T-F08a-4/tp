package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_PARAMETER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.PersonContainsKeywordsPredicate;


public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        assertParseFailure(
                parser,
                " a/Bukit Timah p/12345678",
                String.format(MESSAGE_INVALID_PREFIX, "a/, p/", FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyParameter_throwsParseException() {
        assertParseFailure(parser, " c/", String.format(MESSAGE_EMPTY_PARAMETER));
        assertParseFailure(parser, " r/", String.format(MESSAGE_EMPTY_PARAMETER));
        assertParseFailure(parser, " c/childName b/", String.format(MESSAGE_EMPTY_PARAMETER));
        assertParseFailure(parser, " c/childName t/ r/", String.format(MESSAGE_EMPTY_PARAMETER));
    }

    @Test
    public void parse_parseAllValidArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        Arrays.asList("Charlie", "Dave"),
                        Collections.singletonList("Dust"),
                        Collections.singletonList("ADHD")));

        assertParseSuccess(parser, " c/Alice Bob b/Charlie Dave r/Dust t/ADHD", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " c/\n Alice \n\t Bob b/Charlie Dave r/Dust t/ADHD\t", expectedFindCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        FindCommand expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        Arrays.asList("Charlie", "Dave"),
                        Collections.singletonList("Dust"),
                        Collections.emptyList()));

        assertParseSuccess(parser, " c/Alice Bob b/Charlie Dave r/Dust", expectedFindCommand);

        expectedFindCommand =
                new FindCommand(new PersonContainsKeywordsPredicate(
                        Arrays.asList("Alice", "Bob"),
                        Collections.emptyList(),
                        Collections.emptyList(),
                        Collections.singletonList("ADHD")));

        assertParseSuccess(parser, " c/Alice Bob t/ADHD", expectedFindCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_failure() {

        assertParseFailure(
                parser,
                " c/Alice Bob c/Charlie Dave",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CHILDNAME)
        );

        assertParseFailure(
                parser,
                " b/Alice Bob b/Charlie Dave",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PARENTNAME)
        );

        assertParseFailure(
                parser,
                " r/Dust r/Peanut",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ALLERGY)
        );

        assertParseFailure(
                parser,
                " t/ADHD t/single parent",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TAG)
        );
    }

}
