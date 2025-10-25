package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.subject.SubjectRegistry;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label parent;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane allergies;
    @FXML
    private FlowPane subjects;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        // TODO: For GUI person to fix
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getChildName().fullName);
        parent.setText("Parent: " + person.getParentName().fullName);
        phone.setText(person.getParentPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getParentEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        person.getAllergyList().stream()
                .sorted(Comparator.comparing(al -> al.toString()))
                .forEach(al -> allergies.getChildren().add(new Label(al.toString())));
        SubjectRegistry.getSubjectsOf(person).stream()
                .sorted(Comparator.comparing(subject -> subject.toString()))
                .forEach(subject ->
                        subjects.getChildren().add(new Label(subject + " | "
                                + SubjectRegistry.getScoresOf(person).get(subject))));
    }
}
