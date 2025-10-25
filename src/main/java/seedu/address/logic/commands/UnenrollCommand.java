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
 * Unenrolls children from a subject
 */
public class UnenrollCommand extends Command {
    public static final String COMMAND_WORD = "unenroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unenrolls all children at the specified indexes"
            + "into the listed subjects;\n or unenrolls all children currently listed if the 'all' keyword is used\n"
            + "Parameters: INDEXES (must be positive integers) or 'ALL', s/SUBJECT...\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 s/math\n"
            + "or: " + COMMAND_WORD + " all s/math s/science\n";

    public static final String MESSAGE_UNENROLL_PERSON_SUCCESS = "Unenrolled Child: %1$s in Subject: %2$s\n";
    public static final String MESSAGE_SKIPPED_PERSON = "Child: %1$s not enrolled in Subject %2$s, skipping...\n";
    public static final String MESSAGE_NO_PERSON_UNENROLLED = "All selected children are already unenrolled";
    public static final String MESSAGE_DONE = "Finished command execution";

    private final Index[] indexes;
    private final boolean unenrollAll;
    private final List<Subject> subjectList;

    /**
     * Unenrolls an index array of children into one or more subjects
     * The unenrollAll flag can be set to true to unenroll all shown children
     * @param indexes the index array
     * @param unenrollAll if true, unenroll all currently shown children
     * @param subjectList a set of subjects to be unenrolled from
     */
    public UnenrollCommand(Index[] indexes, boolean unenrollAll, List<Subject> subjectList) {
        this.indexes = indexes;
        this.unenrollAll = unenrollAll;
        this.subjectList = subjectList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        StringBuilder sb = new StringBuilder();

        List<Person> personsToProcess;
        int unenrollCount = 0;

        if (unenrollAll) {
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
                if (subject.getStudents().contains(person)) {
                    unenrollCount++;
                    subject.unenrollPerson(person);
                    sb.append(String.format(MESSAGE_UNENROLL_PERSON_SUCCESS,
                            Messages.formatShort(person), subject));
                } else {
                    sb.append(String.format(MESSAGE_SKIPPED_PERSON, Messages.formatShort(person), subject));
                }
            }
        }

        sb.append(MESSAGE_DONE);

        String unenrolledPersonsResult = sb.toString();

        return new CommandResult(unenrollCount == 0 ? MESSAGE_NO_PERSON_UNENROLLED
                : unenrolledPersonsResult);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UnenrollCommand)) {
            return false;
        }

        UnenrollCommand otherCommand = (UnenrollCommand) other;
        return Arrays.equals(indexes, otherCommand.indexes)
                && unenrollAll == otherCommand.unenrollAll
                && subjectList.equals(otherCommand.subjectList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("indexes", indexes)
                .add("enrolAll", unenrollAll)
                .add("subjects", subjectList)
                .toString();
    }
}
