package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Allergy;
import seedu.address.model.person.AllergyList;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.subject.Subject;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }
    /**
     * Parses {@code indices} into an array of {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if any of the specified indices are invalid (not non-zero unsigned integer).
     */
    public static Index[] parseIndexArray(String indices) throws ParseException {
        String[] indexStrings = indices.trim().split("\\s+");
        Index[] indexArray = new Index[indexStrings.length];
        for (int i = 0; i < indexStrings.length; i++) {
            indexArray[i] = parseIndex(indexStrings[i]);
        }
        Arrays.sort(indexArray, (x, y) -> y.getZeroBased() - x.getZeroBased());
        return indexArray;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String allergy} into an {@code Allergy}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code allergy} is invalid.
     */
    public static Allergy parseAllergy(String allergy) throws ParseException {
        requireNonNull(allergy);
        String trimmedAllergy = allergy.trim();
        if (!Allergy.isValidAllergy(trimmedAllergy)) {
            throw new ParseException(Allergy.MESSAGE_CONSTRAINTS);
        }
        return new Allergy(trimmedAllergy);
    }

    /**
     * Parses {@code Collection<String> allergies} into an {@code AllergyList}.
     */
    public static AllergyList parseAllergies(Collection<String> allergies) throws ParseException {
        requireNonNull(allergies);
        final List<Allergy> allergyList = new ArrayList<>();
        for (String allergyName : allergies) {
            allergyList.add(parseAllergy(allergyName));
        }
        return new AllergyList(allergyList);
    }

    /**
     * Parse a single subject string into a {@code Subject}
     * @param subject the string to be parsed
     * @return a subject object
     * @throws ParseException
     */
    public static Subject parseSubject(String subject) throws ParseException {
        requireNonNull(subject);

        String trimmedSubject = subject.trim();

        if (!Subject.contains(subject)) {
            throw new ParseException(Subject.MESSAGE_CONSTRAINTS);
        }

        return Subject.fromString(subject);
    }

    /**
     * Parse a list of subject strings into their corresponding subjects
     * @param subjects the list of subjects
     * @return A set of subject objects
     * @throws ParseException
     */
    public static List<Subject> parseSubjects(List<String> subjects) throws ParseException {
        requireNonNull(subjects);

        final List<Subject> subjectList = new ArrayList<>();
        for (String subject : subjects) {
            subjectList.add(parseSubject(subject));
        }
        return subjectList.stream().distinct().toList();
    }

    /**
     * Check if the text matches the "ALL" keyword
     * @param text
     * @return
     */
    public static boolean checkIsAll(String text) {
        return text.trim().equalsIgnoreCase("all");
    }

    /**
     * Parses a score from user input. Must be between 0 - 100
     * @param scoreString the string to be processed
     * @return an integer representation of the score
     * @throws ParseException
     */
    public static int parseScore(String scoreString) throws ParseException {
        try {
            int score = Integer.parseInt(scoreString);
            if (score < 0 || score > 100) {
                throw new ParseException("Score must be between 0 to 100!");
            }

            return score;
        } catch (NumberFormatException | NullPointerException e) {
            throw new ParseException("Score must be numeric!");
        }
    }

    /**
     * Detects and returns any invalid prefixes found in the given {@code args} string.
     * A prefix is considered invalid if it matches the pattern {@code [a-zA-Z]+/}
     * but is not present in the specified list of {@code validPrefixes}.
     *
     * @param args the full command arguments string to check
     * @param validPrefixes the list of prefixes that are considered valid
     * @return a comma-separated string of invalid prefixes found, or an empty string if none exist
     */
    public static String detectInvalidPrefixes(String args, Prefix... validPrefixes) {
        Set<String> valid = Arrays.stream(validPrefixes)
                .map(Prefix::getPrefix)
                .collect(Collectors.toSet());

        Matcher m = Pattern.compile("([a-zA-Z]+/)").matcher(args);
        List<String> invalids = new ArrayList<>();
        while (m.find()) {
            String prefix = m.group(1);
            if (!valid.contains(prefix)) {
                invalids.add(prefix);
            }
        }

        return String.join(", ", invalids);
    }
}
