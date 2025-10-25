package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.subject.Subject;

/**
 * Set scores of children
 */
public class SetScoreCommand extends Command {
    public static final String COMMAND_WORD = "setscore";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Set score of children at the specified index.\n"
            + "Can only specify one grade and one subject per command. Multiple children can be selected by\n"
            + "using a space-separated list of indexes or the ALL keyword.\n"
            + "Parameters: INDEXES (must be positive integers) or 'ALL', s/SUBJECT g/score\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 s/math g/100\n"
            + "or: " + COMMAND_WORD + " all s/math g/85";

    public static final String MESSAGE_SET_SCORE_SUCCESS = "Grade of %1$s in Subject: %2$s set to %3$s\n";
    public static final String MESSAGE_SKIPPED_STUDENT = "%1$s not enrolled in Subject: %2$s, skipping...\n";
    public static final String MESSAGE_DONE = "Finished command execution";

    private final Index[] indexes;
    private final boolean setAll;
    private final Subject subject;
    private final int score;

    /**
     * Creates new set score command. Only one subject and one score to prevent ambiguity
     * @param indexes list of indexes in the current list
     * @param setAll when true, set score for all listed children
     * @param subject the subject to set
     * @param score the score to set
     */
    public SetScoreCommand(Index[] indexes, boolean setAll, Subject subject, int score) {
        this.indexes = indexes;
        this.setAll = setAll;
        this.subject = subject;
        this.score = score;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        StringBuilder sb = new StringBuilder();

        List<Person> personsToProcess;

        if (setAll) {
            personsToProcess = lastShownList;
        } else {
            // Validate indexes and map to persons
            personsToProcess = new ArrayList<>();
            for (Index index : indexes) {
                int i = index.getZeroBased();
                if (i >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                personsToProcess.add(lastShownList.get(i));
            }
        }

        // Process each person
        for (Person person : personsToProcess) {
            if (!subject.getStudents().contains(person)) {
                sb.append(String.format(MESSAGE_SKIPPED_STUDENT, Messages.formatShort(person), subject));
            } else {
                subject.setScore(person, score);
                sb.append(String.format(MESSAGE_SET_SCORE_SUCCESS, Messages.formatShort(person), subject, score));
            }
        }

        sb.append(MESSAGE_DONE);

        String setScoreResult = sb.toString();

        return new CommandResult(setScoreResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetScoreCommand)) {
            return false;
        }

        SetScoreCommand otherCommand = (SetScoreCommand) other;
        return Arrays.equals(indexes, otherCommand.indexes)
                && setAll == otherCommand.setAll
                && subject.equals(otherCommand.subject)
                && score == otherCommand.score;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", indexes)
                .add("setAll", setAll)
                .add("subject", subject)
                .add("score", score)
                .toString();
    }
}
