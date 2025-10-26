package seedu.address.logic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The child index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d children listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    public static final String MESSAGE_EMPTY_PARAMETER =
                "A value must be provided after each included prefix";

    /**
     * Returns an error message indicating the duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String format(Person person) {
        final StringBuilder builder = new StringBuilder();

        builder.append(person.getChildName())
                .append("; Parent: ")
                .append(person.getParentName())
                .append("; Phone: ")
                .append(person.getParentPhone())
                .append("; Email: ")
                .append(person.getParentEmail())
                .append("; Address: ")
                .append(person.getAddress())
                .append("; Allergies: ");
        builder.append(
                person.getAllergyList().isEmpty()
                        ? "[None]"
                        : person.getAllergyList().stream()
                        .map(al -> "[" + al + "]")
                        .collect(Collectors.joining())
        );

        builder.append("; Tags: ");
        builder.append(
                person.getTags().isEmpty()
                        ? "[No Tags]"
                        : person.getTags().stream()
                        .map(tag -> "[" + tag + "]")
                        .collect(Collectors.joining())
        );

        return builder.toString();
    }

    /**
     * Formats a person into a relatively shorter string containing only child and parent name
     * @param person the person object to format
     * @return
     */
    public static String formatShort(Person person) {
        return person.getChildName().toString();
    }

}
