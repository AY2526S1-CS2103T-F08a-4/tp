package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;

public class ScoreDictTest {

    private ScoreDict scoreDict;

    @BeforeEach
    public void setUp() {
        scoreDict = new ScoreDict();
    }

    @Test
    public void setScore_newPerson_success() {
        scoreDict.setScore(ALICE, 85);

        assertTrue(scoreDict.contains(ALICE));
        assertEquals(85, scoreDict.getScore(ALICE).orElse(-1));
    }

    @Test
    public void setScore_updateExisting_success() {
        scoreDict.setScore(BENSON, 70);
        scoreDict.setScore(BENSON, 95);

        assertEquals(95, scoreDict.getScore(BENSON).orElse(-1));
    }

    @Test
    public void setScore_multiplePersons_success() {
        scoreDict.setScore(ALICE, 85);
        scoreDict.setScore(BENSON, 90);
        scoreDict.setScore(CARL, 75);

        assertEquals(85, scoreDict.getScore(ALICE).orElse(-100));
        assertEquals(90, scoreDict.getScore(BENSON).orElse(-100));
        assertEquals(75, scoreDict.getScore(CARL).orElse(-100));
    }

    @Test
    public void setScore_zeroScore_success() {
        scoreDict.setScore(ALICE, 0);

        assertTrue(scoreDict.contains(ALICE));
        assertEquals(0, scoreDict.getScore(ALICE).orElse(-1));
    }

    @Test
    public void setScore_negativeScore_success() {
        // ScoreDict itself doesn't validate score values
        // validation should be done at a higher level
        scoreDict.setScore(ALICE, -10);

        assertEquals(-10, scoreDict.getScore(ALICE).orElse(999));
    }

    @Test
    public void setScore_highScore_success() {
        scoreDict.setScore(ALICE, 100);

        assertEquals(100, scoreDict.getScore(ALICE).orElse(-1));
    }

    @Test
    public void removePerson_existingPerson_success() {
        scoreDict.setScore(ALICE, 85);
        scoreDict.setScore(BENSON, 90);

        scoreDict.removePerson(ALICE);

        assertFalse(scoreDict.contains(ALICE));
        assertTrue(scoreDict.contains(BENSON));
        assertEquals(Optional.empty(), scoreDict.getScore(ALICE));
    }

    @Test
    public void removePerson_nonExistentPerson_noEffect() {
        scoreDict.setScore(ALICE, 85);

        scoreDict.removePerson(BENSON);

        assertTrue(scoreDict.contains(ALICE));
        assertFalse(scoreDict.contains(BENSON));
    }

    @Test
    public void removePerson_emptyDict_noEffect() {
        scoreDict.removePerson(ALICE);

        assertFalse(scoreDict.contains(ALICE));
    }

    @Test
    public void getScore_existingPerson_returnsScore() {
        scoreDict.setScore(ALICE, 88);

        Optional<Integer> score = scoreDict.getScore(ALICE);

        assertTrue(score.isPresent());
        assertEquals(88, score.get());
    }

    @Test
    public void getScore_nonExistentPerson_returnsEmpty() {
        Optional<Integer> score = scoreDict.getScore(ALICE);

        assertFalse(score.isPresent());
        assertEquals(Optional.empty(), score);
    }

    @Test
    public void contains_existingPerson_returnsTrue() {
        scoreDict.setScore(ALICE, 85);

        assertTrue(scoreDict.contains(ALICE));
    }

    @Test
    public void contains_nonExistentPerson_returnsFalse() {
        assertFalse(scoreDict.contains(ALICE));
    }

    @Test
    public void contains_afterRemoval_returnsFalse() {
        scoreDict.setScore(ALICE, 85);
        scoreDict.removePerson(ALICE);

        assertFalse(scoreDict.contains(ALICE));
    }

    @Test
    public void getAllScores_emptyDict_returnsEmptyMap() {
        Map<Person, Integer> scores = scoreDict.getAllScores();

        assertTrue(scores.isEmpty());
    }

    @Test
    public void getAllScores_singlePerson_returnsCorrectMap() {
        scoreDict.setScore(ALICE, 85);

        Map<Person, Integer> scores = scoreDict.getAllScores();

        assertEquals(1, scores.size());
        assertEquals(85, scores.get(ALICE));
    }

    @Test
    public void getAllScores_multiplePersons_returnsAllScores() {
        scoreDict.setScore(ALICE, 85);
        scoreDict.setScore(BENSON, 90);
        scoreDict.setScore(CARL, 75);
        scoreDict.setScore(DANIEL, 80);

        Map<Person, Integer> scores = scoreDict.getAllScores();

        assertEquals(4, scores.size());
        assertEquals(85, scores.get(ALICE));
        assertEquals(90, scores.get(BENSON));
        assertEquals(75, scores.get(CARL));
        assertEquals(80, scores.get(DANIEL));
    }

    @Test
    public void getAllScores_returnsUnmodifiableMap() {
        scoreDict.setScore(ALICE, 85);

        Map<Person, Integer> scores = scoreDict.getAllScores();

        // Attempting to modify the returned map should throw an exception
        try {
            scores.put(BENSON, 90);
            assertTrue(false, "Should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected behavior
            assertTrue(true);
        }
    }

    @Test
    public void getAllScores_afterModification_reflectsChanges() {
        scoreDict.setScore(ALICE, 85);
        Map<Person, Integer> scores1 = scoreDict.getAllScores();
        assertEquals(1, scores1.size());

        scoreDict.setScore(BENSON, 90);
        Map<Person, Integer> scores2 = scoreDict.getAllScores();

        // The new map should reflect the updated state
        assertEquals(2, scores2.size());
        assertEquals(85, scores2.get(ALICE));
        assertEquals(90, scores2.get(BENSON));
    }

    @Test
    public void integration_addUpdateRemove_success() {
        scoreDict.setScore(ALICE, 85);
        scoreDict.setScore(BENSON, 90);
        scoreDict.setScore(CARL, 75);

        assertEquals(3, scoreDict.getAllScores().size());

        scoreDict.setScore(ALICE, 95);
        assertEquals(95, scoreDict.getScore(ALICE).orElse(-1));

        scoreDict.removePerson(CARL);
        assertEquals(2, scoreDict.getAllScores().size());
        assertFalse(scoreDict.contains(CARL));

        Map<Person, Integer> scores = scoreDict.getAllScores();
        assertEquals(95, scores.get(ALICE));
        assertEquals(90, scores.get(BENSON));
    }

    @Test
    public void integration_removeAndReAdd_success() {
        scoreDict.setScore(ALICE, 85);
        scoreDict.removePerson(ALICE);

        assertFalse(scoreDict.contains(ALICE));

        scoreDict.setScore(ALICE, 95);

        assertTrue(scoreDict.contains(ALICE));
        assertEquals(95, scoreDict.getScore(ALICE).orElse(-1));
    }

}
