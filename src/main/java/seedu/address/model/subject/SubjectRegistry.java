package seedu.address.model.subject;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;

/**
 * Provides utility methods for querying Subjects and Scores.
 */
public final class SubjectRegistry {

    private SubjectRegistry() {}

    /**
     * Returns all subjects that the given person is enrolled in.
     *
     * @param person The person whose enrolled subjects are to be retrieved.
     * @return An unmodifiable set of subjects the person is enrolled in.
     */
    public static Set<Subject> getSubjectsOf(Person person) {
        EnumSet<Subject> subjects = EnumSet.noneOf(Subject.class);
        for (Subject subject : Subject.values()) {
            if (subject.getStudents().contains(person)) {
                subjects.add(subject);
            }
        }
        return subjects;
    }

    /**
     * Returns a map of subjects to scores for the given person.
     *
     * @param person The person whose scores are to be retrieved.
     * @return A map where each key is a Subject and the corresponding value is the person's score in that subject.
     */
    public static Map<Subject, Integer> getScoresOf(Person person) {
        Map<Subject, Integer> scores = new LinkedHashMap<>();
        for (Subject subject : Subject.values()) {
            if (subject.getStudents().contains(person)) {
                scores.put(subject, subject.getScore(person));
            }
        }
        return scores;
    }

    /**
     * Returns the ScoreDict associated with the given subject.
     *
     * @param subject The subject whose ScoreDict is to be retrieved.
     * @return The ScoreDict for the given subject.
     */
    public static ScoreDict getScoreDict(Subject subject) {
        return subject.getScoreDict();
    }

    /**
     * Returns a read-only view of all scores for the given subject.
     * The returned map is unmodifiable and maps each enrolled {@link Person} to their score.
     *
     * @param subject The subject whose scores are to be retrieved.
     * @return An unmodifiable map from {@link Person} to their score in the subject.
     */
    public static Map<Person, Integer> getViewOnlyScores(Subject subject) {
        return subject.getScoreDict().getAllScores();
    }

    public static ObservableMap<Person, Integer> getObservableScore(Subject subject) {
        return subject.getScoreDict().getObservableScores();
    }
}
