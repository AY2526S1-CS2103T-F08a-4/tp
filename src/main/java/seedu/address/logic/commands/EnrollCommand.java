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
 * Enrolls a person into a subject
 */
public class EnrollCommand extends Command {

    public static final String COMMAND_WORD = "enroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrolls all children at the specified indexes "
            + "into the listed subjects; \nor enrolls all children currently listed if the 'all' keyword is used\n"
            + "Parameters: INDEXES (must be positive integers) or 'ALL', s/SUBJECT...\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 s/math\n"
            + "or: " + COMMAND_WORD + " all s/math s/science";

    public static final String MESSAGE_ENROLL_PERSON_SUCCESS = "Enrolled Child: %1$s in Subject: %2$s\n";
    public static final String MESSAGE_SKIPPED_PERSON = "Child: %1$s is already enrolled in Subject %2$s, "
            + "skipping...\n";
    public static final String MESSAGE_NO_PERSON_ENROLLED = "All selected children are already enrolled!";
    public static final String MESSAGE_DONE = "Finished command execution.";

    private final Index[] indexes;
    private final boolean enrollAll;
    private final List<Subject> subjectList;

    /**
     * Enrolls an index array of children into one or more subjects
     * The enrollAll flag can be set to true to enroll all shown children
     * @param indexes the index array
     * @param enrollAll if true, enroll all currently shown children
     * @param subjectList a set of subjects to be enrolled in
     */
    public EnrollCommand(Index[] indexes, boolean enrollAll, List<Subject> subjectList) {
        this.indexes = indexes;
        this.enrollAll = enrollAll;
        this.subjectList = subjectList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        StringBuilder sb = new StringBuilder();
        List<Person> personsToProcess;

        int enrollCount = 0;

        if (enrollAll) {
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

        for (Person person : personsToProcess) {
            for (Subject subject : subjectList) {
                if (!subject.getStudents().contains(person)) {
                    subject.enrollPerson(person);
                    sb.append(String.format(MESSAGE_ENROLL_PERSON_SUCCESS,
                            Messages.formatShort(person), subject));
                    enrollCount++;
                } else {
                    sb.append(String.format(MESSAGE_SKIPPED_PERSON,
                            Messages.formatShort(person), subject));
                }
            }
        }

        sb.append(MESSAGE_DONE);

        String enrolledPersonsResult = sb.toString();

        return new CommandResult(enrollCount == 0 ? MESSAGE_NO_PERSON_ENROLLED
                : enrolledPersonsResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EnrollCommand)) {
            return false;
        }

        EnrollCommand otherCommand = (EnrollCommand) other;
        return Arrays.equals(indexes, otherCommand.indexes)
                && enrollAll == otherCommand.enrollAll
                && subjectList.equals(otherCommand.subjectList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", Arrays.toString(indexes))
                .add("enrolAll", enrollAll)
                .add("subjects", subjectList)
                .toString();
    }
}
