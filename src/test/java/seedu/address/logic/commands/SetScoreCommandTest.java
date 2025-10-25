package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SetScoreCommand.MESSAGE_DONE;
import static seedu.address.logic.commands.SetScoreCommand.MESSAGE_SET_SCORE_SUCCESS;
import static seedu.address.logic.commands.SetScoreCommand.MESSAGE_SKIPPED_STUDENT;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.subject.Subject;

public class SetScoreCommandTest {
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
    public void setOnePersonSuccess() {
        setupTypical();

        Index[] indexes = new Index[] { Index.fromOneBased(1) };
        Subject subject = Subject.MATH;
        int score = 60;
        SetScoreCommand command = new SetScoreCommand(indexes, false, subject, score);

        String expectedMessage = String.format(MESSAGE_SET_SCORE_SUCCESS, Messages.formatShort(ALICE), subject, score);
        expectedMessage += MESSAGE_DONE;

        assertCommandSuccess(command, model, expectedMessage, model);
        assertEquals(60, Subject.MATH.getScore(ALICE));
    }

    @Test
    public void setMultiplePersonSuccess() {
        setupTypical();

        Index[] indexes = new Index[] {
                Index.fromOneBased(1),
                Index.fromOneBased(2)
        };
        Subject subject = Subject.MATH;
        int score = 60;
        SetScoreCommand command = new SetScoreCommand(indexes, false, subject, score);

        String expectedMessage = "";
        expectedMessage += String.format(MESSAGE_SET_SCORE_SUCCESS, Messages.formatShort(ALICE), subject, score);
        expectedMessage += String.format(MESSAGE_SET_SCORE_SUCCESS, Messages.formatShort(BENSON), subject, score);

        expectedMessage += MESSAGE_DONE;

        assertCommandSuccess(command, model, expectedMessage, model);
        assertEquals(60, Subject.MATH.getScore(ALICE));
        assertEquals(60, Subject.MATH.getScore(BENSON));
    }

    @Test
    public void setUnenrolledPersonScore() {
        Index[] indexes = new Index[] {
                Index.fromOneBased(1)
        };
        Subject subject = Subject.MATH;
        int score = 60;
        SetScoreCommand command = new SetScoreCommand(indexes, false, subject, score);

        String expectedMessage = String.format(MESSAGE_SKIPPED_STUDENT, Messages.formatShort(ALICE), subject);

        expectedMessage += MESSAGE_DONE;

        assertCommandSuccess(command, model, expectedMessage, model);
        assertFalse(Subject.MATH.getStudents().contains(ALICE));
    }
}
