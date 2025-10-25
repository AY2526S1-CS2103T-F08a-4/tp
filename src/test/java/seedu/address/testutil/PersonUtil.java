package seedu.address.testutil;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTEMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        // TODO: For GUI person to fix
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_CHILDNAME + person.getChildName().fullName + " ");
        sb.append(PREFIX_PARENTNAME + person.getParentName().fullName + " ");
        sb.append(PREFIX_PARENTPHONE + person.getParentPhone().value + " ");
        sb.append(PREFIX_PARENTEMAIL + person.getParentEmail().value + " ");
        sb.append(PREFIX_ADDRESS + person.getAddress().value + " ");
        person.getAllergyList().stream().forEach(
                s -> sb.append(PREFIX_ALLERGY + s.toString() + " ")
        );
        person.getTags().stream().forEach(
            s -> sb.append(PREFIX_TAG + s.tagName + " ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getChildName().ifPresent(name -> sb.append(PREFIX_CHILDNAME).append(name.fullName).append(" "));
        descriptor.getParentName().ifPresent(name -> sb.append(PREFIX_PARENTNAME).append(name.fullName).append(" "));
        descriptor.getParentPhone().ifPresent(phone -> sb.append(PREFIX_PARENTPHONE).append(phone.value).append(" "));
        descriptor.getParentEmail().ifPresent(email -> sb.append(PREFIX_PARENTEMAIL).append(email.value).append(" "));
        descriptor.getAllergies().ifPresent(allergyList -> {
            Set<Allergy> allergies = allergyList.getAllergyList();
            if (allergies.isEmpty()) {
                sb.append(PREFIX_ALLERGY + " ");
            } else {
                allergies.forEach(s -> sb.append(PREFIX_ALLERGY).append(s).append(" "));
            }
        });
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
