package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_2_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_DESC;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EnrollCommand;
import seedu.address.model.subject.Subject;

public class EnrollCommandParserTest {
    private EnrollCommandParser parser = new EnrollCommandParser();

    @Test
    public void parseOneSubjectOneStudentReturnsEnrollCommand() {
        String userInput = "1 " + VALID_SUBJECT_DESC;
        List<Subject> expectedSet = new ArrayList<Subject>();
        expectedSet.add(Subject.MATH);
        assertParseSuccess(parser, userInput, new EnrollCommand(new Index[] {INDEX_FIRST_PERSON}, false, expectedSet));
    }

    @Test
    public void parseOneSubjectMultipleStudentReturnsEnrollCommand() {
        String userInput = "1 2 3 " + VALID_SUBJECT_DESC;
        List<Subject> expectedSet = new ArrayList<>();
        expectedSet.add(Subject.MATH);
        Index[] expectedIndexes = new Index[] {
                Index.fromOneBased(3),
                Index.fromOneBased(2),
                Index.fromOneBased(1),
        };

        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, false, expectedSet));
    }

    @Test
    public void parseOneSubjectAllStudentReturnsEnrollCommand() {
        String userInput = "all " + VALID_SUBJECT_DESC;
        List<Subject> expectedSet = new ArrayList<>();
        expectedSet.add(Subject.MATH);
        Index[] expectedIndexes = new Index[0];

        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, true, expectedSet));
    }

    @Test
    public void parseMultipleSubjectOneStudentReturnsEnrollCommand() {
        String userInput = "1 " + VALID_SUBJECT_DESC + VALID_SUBJECT_2_DESC;
        List<Subject> expectedList = new ArrayList<>();
        expectedList.add(Subject.MATH);
        expectedList.add(Subject.SCIENCE);
        Index[] expectedIndexes = new Index[]{
                Index.fromOneBased(1)
        };

        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, false, expectedList));
    }

    @Test
    public void parseMultipleSubjectMultipleStudentReturnsEnrollCommand() {
        String userInput = "1 2 3 " + VALID_SUBJECT_DESC + VALID_SUBJECT_2_DESC;
        List<Subject> expectedList = new ArrayList<>();
        expectedList.add(Subject.MATH);
        expectedList.add(Subject.SCIENCE);

        Index[] expectedIndexes = new Index[] {
                Index.fromOneBased(3),
                Index.fromOneBased(2),
                Index.fromOneBased(1)
        };

        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, false, expectedList));
    }

    @Test
    public void parseMultipleSubjectMultipleStudentWithWhitespaceReturnsEnrollCommand() {
        String userInput = PREAMBLE_WHITESPACE + "1 2 3 " + VALID_SUBJECT_DESC + VALID_SUBJECT_2_DESC;
        List<Subject> expectedList = new ArrayList<>();
        expectedList.add(Subject.MATH);
        expectedList.add(Subject.SCIENCE);
        Index[] expectedIndexes = new Index[] {
                Index.fromOneBased(3),
                Index.fromOneBased(2),
                Index.fromOneBased(1)
        };

        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, false, expectedList));
    }

    @Test
    public void parseMultipleSubjectAllStudentReturnsEnrollCommand() {
        String userInput = "all " + VALID_SUBJECT_DESC + VALID_SUBJECT_2_DESC;
        List<Subject> expectedList = new ArrayList<>();
        expectedList.add(Subject.MATH);
        expectedList.add(Subject.SCIENCE);
        Index[] expectedIndexes = new Index[0];

        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, true, expectedList));
    }

    @Test
    public void parseDuplicateSubject() {
        String userInput = "all " + VALID_SUBJECT_DESC + VALID_SUBJECT_DESC;
        List<Subject> expectedList = new ArrayList<>();
        expectedList.add(Subject.MATH);
        Index[] expectedIndexes = new Index[0];
        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, true, expectedList));
    }

    @Test
    public void parseDuplicateIndexes() {
        String userInput = "1 1 " + VALID_SUBJECT_DESC;
        List<Subject> expectedList = new ArrayList<>();
        expectedList.add(Subject.MATH);
        Index[] expectedIndexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(1)
        };
        assertParseSuccess(parser, userInput, new EnrollCommand(expectedIndexes, false, expectedList));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        // no arguments
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EnrollCommand.MESSAGE_USAGE));

        // just whitespace (missing arguments)
        assertParseFailure(parser, PREAMBLE_WHITESPACE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EnrollCommand.MESSAGE_USAGE));

        // invalid subject
        assertParseFailure(parser, "1 " + INVALID_SUBJECT_DESC, String.format(Subject.MESSAGE_CONSTRAINTS));

        // invalid indexes
        assertParseFailure(parser, "asas " + VALID_SUBJECT_DESC, MESSAGE_INVALID_INDEX);

        // missing preamble
        assertParseFailure(parser, VALID_SUBJECT_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EnrollCommand.MESSAGE_USAGE));

        // missing subject
        assertParseFailure(parser, "1 2 3 ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EnrollCommand.MESSAGE_USAGE));
    }
}
