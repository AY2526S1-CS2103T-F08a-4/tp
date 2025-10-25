package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.UnenrollCommand.MESSAGE_DONE;
import static seedu.address.logic.commands.UnenrollCommand.MESSAGE_NO_PERSON_UNENROLLED;
import static seedu.address.logic.commands.UnenrollCommand.MESSAGE_SKIPPED_PERSON;
import static seedu.address.logic.commands.UnenrollCommand.MESSAGE_UNENROLL_PERSON_SUCCESS;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;

public class UnenrollCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @AfterEach
    public void cleanUp() {
        for (Subject subject : Subject.values()) {
            subject.getStudents().forEach(person -> subject.unenrollPerson(person));
        }
    }

    public void setupTypical() {
        cleanUp();
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(ALICE);
        Subject.MATH.setScore(BENSON, 100);
        Subject.MATH.setScore(ALICE, 75);
        Subject.SCIENCE.setScore(ALICE, 80);
    }

    @Test
    public void unenrolOneStudentAndOneSubjectSuccess() {
        setupTypical();
        Index targetIndex = Index.fromOneBased(1);
        Index[] indexes = new Index[]{targetIndex};

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        UnenrollCommand command = new UnenrollCommand(indexes, false, subjectList);

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        String expectedMessage = "";
        expectedMessage += String.format(MESSAGE_UNENROLL_PERSON_SUCCESS,
                Messages.formatShort(person), Subject.MATH);
        expectedMessage += String.format(MESSAGE_DONE);

        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertFalse(Subject.MATH.getStudents().contains(person));
    }

    @Test
    public void unenrolMultipleStudentAndOneSubjectSuccess() {
        setupTypical();
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        UnenrollCommand command = new UnenrollCommand(indexes, false, subjectList);

        // Expected message for all unenrolled students
        StringBuilder expectedMessageBuilder = new StringBuilder();
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            expectedMessageBuilder.append(String.format(
                    MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(person), Subject.MATH));
        }
        expectedMessageBuilder.append(MESSAGE_DONE);

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        // Verify that each student is unenrolled
        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            assertFalse(Subject.MATH.getStudents().contains(person));
        }
    }

    @Test
    public void unenrolAllStudentAndOneSubjectSuccess() {
        setupTypical();
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        UnenrollCommand command = new UnenrollCommand(indexes, true, subjectList);

        List<Person> personList = model.getFilteredPersonList();

        // Expected message for all unenrolled students
        StringBuilder expectedMessageBuilder = new StringBuilder();

        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(ALICE), Subject.MATH));
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(BENSON), Subject.MATH));
        for (Person p : model.getFilteredPersonList()) {
            if (p == ALICE || p == BENSON) {
                continue;
            }

            expectedMessageBuilder.append(String.format(MESSAGE_SKIPPED_PERSON,
                    Messages.formatShort(p), Subject.MATH));
        }

        expectedMessageBuilder.append(MESSAGE_DONE);

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        for (Person person : personList) {
            assertFalse(Subject.MATH.getStudents().contains(person));
        }
    }

    @Test
    public void unenrolOneStudentAndMultipleSubjectSuccess() {
        setupTypical();
        Index targetIndex = Index.fromOneBased(1);
        Index[] indexes = new Index[] {targetIndex};

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        subjectList.add(Subject.SCIENCE);

        UnenrollCommand command = new UnenrollCommand(indexes, false, subjectList);

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());

        String expectedMessage = "";
        expectedMessage += String.format(MESSAGE_UNENROLL_PERSON_SUCCESS,
                Messages.formatShort(person), Subject.MATH);
        expectedMessage += String.format(MESSAGE_UNENROLL_PERSON_SUCCESS,
                Messages.formatShort(person), Subject.SCIENCE);
        expectedMessage += MESSAGE_DONE;

        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertFalse(Subject.MATH.getStudents().contains(person));
        assertFalse(Subject.SCIENCE.getStudents().contains(person));
    }

    @Test
    public void unenrolMultipleStudentAndMultipleSubjectSuccess() {
        setupTypical();
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        subjectList.add(Subject.SCIENCE);

        UnenrollCommand command = new UnenrollCommand(indexes, false, subjectList);

        StringBuilder expectedMessageBuilder = new StringBuilder();
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(ALICE), Subject.MATH));
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(ALICE), Subject.SCIENCE));
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(BENSON), Subject.MATH));
        expectedMessageBuilder.append(String.format(
                MESSAGE_SKIPPED_PERSON, Messages.formatShort(BENSON), Subject.SCIENCE));

        expectedMessageBuilder.append(MESSAGE_DONE);

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        for (Index index : indexes) {
            Person person = model.getFilteredPersonList().get(index.getZeroBased());
            assertFalse(Subject.MATH.getStudents().contains(person));
            assertFalse(Subject.SCIENCE.getStudents().contains(person));
        }
    }

    @Test
    public void unenrolAllStudentAndMultipleSubjectSuccess() {
        setupTypical();
        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2),
        };

        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        subjectList.add(Subject.SCIENCE);

        UnenrollCommand command = new UnenrollCommand(indexes, true, subjectList);

        List<Person> personList = model.getFilteredPersonList();

        StringBuilder expectedMessageBuilder = new StringBuilder();
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(ALICE), Subject.MATH));
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(ALICE), Subject.SCIENCE));
        expectedMessageBuilder.append(String.format(
                MESSAGE_UNENROLL_PERSON_SUCCESS, Messages.formatShort(BENSON), Subject.MATH));
        expectedMessageBuilder.append(String.format(
                MESSAGE_SKIPPED_PERSON, Messages.formatShort(BENSON), Subject.SCIENCE));

        for (Person p : model.getFilteredPersonList()) {
            if (p == ALICE || p == BENSON) {
                continue;
            }

            expectedMessageBuilder.append(String.format(
                    MESSAGE_SKIPPED_PERSON, Messages.formatShort(p), Subject.MATH));
            expectedMessageBuilder.append(String.format(
                    MESSAGE_SKIPPED_PERSON, Messages.formatShort(p), Subject.SCIENCE));
        }

        expectedMessageBuilder.append(MESSAGE_DONE);

        String expectedMessage = expectedMessageBuilder.toString();
        Model expectedModel = model;

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        for (Person person : personList) {
            assertFalse(Subject.MATH.getStudents().contains(person));
            assertFalse(Subject.SCIENCE.getStudents().contains(person));
        }
    }

    @Test
    public void invalidArgumentsOutOfRangeIndex() {
        setupTypical();
        Index outOfRangeIndex = Index.fromOneBased(100);
        Index[] indexes = new Index[] {outOfRangeIndex};
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);
        UnenrollCommand command = new UnenrollCommand(indexes, false, subjectList);

        String expectedMessage = MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void notEnrolledStudent() {
        setupTypical();
        Subject.MATH.unenrollPerson(ALICE); // make sure ALICE is not in MATH
        Index[] indexes = new Index[] {
                Index.fromOneBased(1)
        };
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(Subject.MATH);

        UnenrollCommand command = new UnenrollCommand(indexes, false, subjectList);

        String expectedMessage = MESSAGE_NO_PERSON_UNENROLLED; // no one actually unenrolled
        assertCommandSuccess(command, model, expectedMessage, model);
        assertFalse(Subject.MATH.getStudents().contains(ALICE));
    }
}
