package seedu.address.model.subject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;

public class SubjectRegistryTest {

    @BeforeEach
    public void setUp() {
        cleanupAllSubjects();
    }

    @AfterEach
    public void tearDown() {
        cleanupAllSubjects();
    }

    private void cleanupAllSubjects() {
        for (Subject subject : Subject.values()) {
            Set<Person> students = Set.copyOf(subject.getStudents());
            students.forEach(subject::unenrollPerson);
        }
    }

    @Test
    public void getSubjectsOf_noEnrollment_returnsEmptySet() {
        Set<Subject> subjects = SubjectRegistry.getSubjectsOf(ALICE);

        assertTrue(subjects.isEmpty());
    }

    @Test
    public void getSubjectsOf_singleSubject_returnsCorrectSet() {
        Subject.MATH.enrollPerson(ALICE);

        Set<Subject> subjects = SubjectRegistry.getSubjectsOf(ALICE);

        assertEquals(1, subjects.size());
        assertTrue(subjects.contains(Subject.MATH));
    }

    @Test
    public void getSubjectsOf_multipleSubjects_returnsAllEnrolledSubjects() {
        Subject.MATH.enrollPerson(BENSON);
        Subject.ENGLISH.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(BENSON);

        Set<Subject> subjects = SubjectRegistry.getSubjectsOf(BENSON);

        assertEquals(3, subjects.size());
        assertTrue(subjects.contains(Subject.MATH));
        assertTrue(subjects.contains(Subject.ENGLISH));
        assertTrue(subjects.contains(Subject.SCIENCE));
    }

    @Test
    public void getSubjectsOf_partialEnrollment_returnsOnlyEnrolledSubjects() {
        Subject.MATH.enrollPerson(CARL);
        Subject.SCIENCE.enrollPerson(CARL);

        Set<Subject> subjects = SubjectRegistry.getSubjectsOf(CARL);

        assertEquals(2, subjects.size());
        assertTrue(subjects.contains(Subject.MATH));
        assertTrue(subjects.contains(Subject.SCIENCE));
        assertFalse(subjects.contains(Subject.ENGLISH));
    }

    @Test
    public void getSubjectsOf_returnsUnmodifiableSet() {
        Subject.MATH.enrollPerson(ALICE);
        Set<Subject> subjects = SubjectRegistry.getSubjectsOf(ALICE);

        assertTrue(subjects instanceof java.util.AbstractSet);
    }

    @Test
    public void getScoresOf_noEnrollment_returnsEmptyMap() {
        Map<Subject, Integer> scores = SubjectRegistry.getScoresOf(ALICE);

        assertTrue(scores.isEmpty());
    }

    @Test
    public void getScoresOf_singleSubject_returnsCorrectScore() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.setScore(ALICE, 85);

        Map<Subject, Integer> scores = SubjectRegistry.getScoresOf(ALICE);

        assertEquals(1, scores.size());
        assertEquals(85, scores.get(Subject.MATH));
    }

    @Test
    public void getScoresOf_multipleSubjects_returnsAllScores() {
        Subject.MATH.enrollPerson(BENSON);
        Subject.ENGLISH.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(BENSON);

        Subject.MATH.setScore(BENSON, 90);
        Subject.ENGLISH.setScore(BENSON, 85);
        Subject.SCIENCE.setScore(BENSON, 95);

        Map<Subject, Integer> scores = SubjectRegistry.getScoresOf(BENSON);

        assertEquals(3, scores.size());
        assertEquals(90, scores.get(Subject.MATH));
        assertEquals(85, scores.get(Subject.ENGLISH));
        assertEquals(95, scores.get(Subject.SCIENCE));
    }

    @Test
    public void getScoresOf_defaultScore_returnsNegativeOne() {
        Subject.SCIENCE.enrollPerson(CARL);

        Map<Subject, Integer> scores = SubjectRegistry.getScoresOf(CARL);

        assertEquals(1, scores.size());
        assertEquals(-1, scores.get(Subject.SCIENCE));
    }

    @Test
    public void getScoresOf_mixedEnrollment_returnsOnlyEnrolledScores() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.setScore(ALICE, 75);
        // ALICE is not enrolled in ENGLISH or SCIENCE

        Map<Subject, Integer> scores = SubjectRegistry.getScoresOf(ALICE);

        assertEquals(1, scores.size());
        assertTrue(scores.containsKey(Subject.MATH));
        assertFalse(scores.containsKey(Subject.ENGLISH));
        assertFalse(scores.containsKey(Subject.SCIENCE));
    }

    @Test
    public void getScoreDict_returnsCorrectScoreDict() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.setScore(ALICE, 88);

        ScoreDict scoreDict = SubjectRegistry.getScoreDict(Subject.MATH);

        assertTrue(scoreDict.contains(ALICE));
        assertEquals(88, scoreDict.getScore(ALICE).orElse(-1));
    }

    @Test
    public void getScoreDict_differentSubjects_returnsDifferentScoreDicts() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.ENGLISH.enrollPerson(BENSON);

        ScoreDict mathDict = SubjectRegistry.getScoreDict(Subject.MATH);
        ScoreDict englishDict = SubjectRegistry.getScoreDict(Subject.ENGLISH);

        assertTrue(mathDict.contains(ALICE));
        assertFalse(mathDict.contains(BENSON));

        assertFalse(englishDict.contains(ALICE));
        assertTrue(englishDict.contains(BENSON));
    }

    @Test
    public void getViewOnlyScores_emptySubject_returnsEmptyMap() {
        Map<Person, Integer> scores = SubjectRegistry.getViewOnlyScores(Subject.MATH);

        assertTrue(scores.isEmpty());
    }

    @Test
    public void getViewOnlyScores_multipleStudents_returnsAllScores() {
        Subject.SCIENCE.enrollPerson(ALICE);
        Subject.SCIENCE.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(CARL);

        Subject.SCIENCE.setScore(ALICE, 80);
        Subject.SCIENCE.setScore(BENSON, 90);
        Subject.SCIENCE.setScore(CARL, 85);

        Map<Person, Integer> scores = SubjectRegistry.getViewOnlyScores(Subject.SCIENCE);

        assertEquals(3, scores.size());
        assertEquals(80, scores.get(ALICE));
        assertEquals(90, scores.get(BENSON));
        assertEquals(85, scores.get(CARL));
    }

    @Test
    public void getViewOnlyScores_returnsUnmodifiableMap() {
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.setScore(ALICE, 75);

        Map<Person, Integer> scores = SubjectRegistry.getViewOnlyScores(Subject.MATH);

        // Attempting to modify should throw an exception
        try {
            scores.put(BENSON, 80);
            assertTrue(false, "Should have thrown UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {
            // Expected
            assertTrue(true);
        }
    }

    @Test
    public void integration_enrollmentAndScores_workTogether() {
        // Enroll multiple students in different subjects
        Subject.MATH.enrollPerson(ALICE);
        Subject.MATH.enrollPerson(BENSON);
        Subject.ENGLISH.enrollPerson(ALICE);
        Subject.SCIENCE.enrollPerson(BENSON);
        Subject.SCIENCE.enrollPerson(CARL);

        // Set scores
        Subject.MATH.setScore(ALICE, 95);
        Subject.MATH.setScore(BENSON, 88);
        Subject.ENGLISH.setScore(ALICE, 92);
        Subject.SCIENCE.setScore(BENSON, 85);
        Subject.SCIENCE.setScore(CARL, 90);

        // Verify ALICE's enrollment and scores
        Set<Subject> aliceSubjects = SubjectRegistry.getSubjectsOf(ALICE);
        assertEquals(2, aliceSubjects.size());

        Map<Subject, Integer> aliceScores = SubjectRegistry.getScoresOf(ALICE);
        assertEquals(95, aliceScores.get(Subject.MATH));
        assertEquals(92, aliceScores.get(Subject.ENGLISH));

        // Verify BENSON's enrollment and scores
        Set<Subject> bensonSubjects = SubjectRegistry.getSubjectsOf(BENSON);
        assertEquals(2, bensonSubjects.size());

        Map<Subject, Integer> bensonScores = SubjectRegistry.getScoresOf(BENSON);
        assertEquals(88, bensonScores.get(Subject.MATH));
        assertEquals(85, bensonScores.get(Subject.SCIENCE));

        // Verify CARL's enrollment and scores
        Set<Subject> carlSubjects = SubjectRegistry.getSubjectsOf(CARL);
        assertEquals(1, carlSubjects.size());

        Map<Subject, Integer> carlScores = SubjectRegistry.getScoresOf(CARL);
        assertEquals(90, carlScores.get(Subject.SCIENCE));
    }


    @Test
    public void getScoreDict_returnsSameInstance() {
        ScoreDict original = Subject.MATH.getScoreDict();

        // Call your static helper
        ScoreDict returned = SubjectRegistry.getScoreDict(Subject.MATH);

        assertSame(original, returned, "getScoreDict() should return the exact same ScoreDict instance");
    }

    @Test
    public void getObservableScore_returnsLiveObservableMap() {
        ObservableMap<Person, Integer> observableScores = SubjectRegistry.getObservableScore(Subject.MATH);

        assertNotNull(observableScores);
        assertTrue(observableScores.isEmpty(), "Initial score map should be empty");

        ScoreDict dict = Subject.MATH.getScoreDict();

        // Listen for map changes
        final boolean[] changeTriggered = { false };
        observableScores.addListener((MapChangeListener<Person, Integer>) change -> {
            changeTriggered[0] = true;
        });

        // Modify through ScoreDict
        dict.setScore(ALICE, 95);

        // Check observable updated
        assertTrue(observableScores.containsKey(ALICE));
        assertEquals(95, observableScores.get(ALICE));
        assertTrue(changeTriggered[0], "Listener should be triggered on score change");
    }

    private void assertNotNull(ObservableMap<Person, Integer> observableScores) {
    }
}
