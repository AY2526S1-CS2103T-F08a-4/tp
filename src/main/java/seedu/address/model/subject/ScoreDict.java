package seedu.address.model.subject;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import seedu.address.model.person.Person;

/**
 * Represents a mapping from {@link Person} to their scores in a particular subject.
 * Provides controlled access to add, remove, and query scores.
 */
public class ScoreDict {
    private final ObservableMap<Person, Integer> scores = FXCollections.observableHashMap();

    /**
     * Adds or updates the score of a person.
     * Made public so storage/deserialization code can directly populate scores.
     *
     * @param person The person whose score is to be set.
     * @param score  The score to assign to the person.
     */
    public void setScore(Person person, int score) {
        scores.put(person, score);
    }

    /**
     * Removes a person from this score dictionary.
     * Made public so storage/deserialization can remove entries if necessary.
     *
     * @param person The person to remove.
     */
    public void removePerson(Person person) {
        scores.remove(person);
    }

    /**
     * Retrieves the score of the given person.
     *
     * @param person The person whose score is to be retrieved.
     * @return An {@link Optional} containing the score if present, otherwise empty.
     */
    Optional<Integer> getScore(Person person) {
        return Optional.ofNullable(scores.get(person));
    }

    /**
     * Checks if the given person has a score in this dictionary.
     *
     * @param person The person to check.
     * @return {@code true} if the person has a score, {@code false} otherwise.
     */
    boolean contains(Person person) {
        return scores.containsKey(person);
    }

    /**
     * Returns an unmodifiable view of all scores in this dictionary.
     *
     * @return A map from {@link Person} to their scores.
     */
    public Map<Person, Integer> getAllScores() {
        return Collections.unmodifiableMap(scores);
    }

    public ObservableMap<Person, Integer> getObservableScores() {
        return scores;
    }
}
