package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_PARAMETER;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PREFIX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALLERGY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CHILDNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARENTNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CHILDNAME, PREFIX_PARENTNAME, PREFIX_ALLERGY, PREFIX_TAG);

        String invalidPrefixes =
                ParserUtil.detectInvalidPrefixes(
                        args, PREFIX_CHILDNAME, PREFIX_PARENTNAME, PREFIX_ALLERGY, PREFIX_TAG);
        if (!invalidPrefixes.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_PREFIX, invalidPrefixes, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_CHILDNAME, PREFIX_PARENTNAME, PREFIX_ALLERGY, PREFIX_TAG);

        List<String> childNameKeywords = argMultimap.getValue(PREFIX_CHILDNAME)
                .map(s -> Arrays.asList(s.trim().split("\\s+")))
                .orElse(Collections.emptyList());

        List<String> parentNameKeywords = argMultimap.getValue(PREFIX_PARENTNAME)
                .map(s -> Arrays.asList(s.trim().split("\\s+")))
                .orElse(Collections.emptyList());

        List<String> allergyKeywords = argMultimap.getValue(PREFIX_ALLERGY)
                .map(s -> Arrays.asList(s.trim().split("\\s+")))
                .orElse(Collections.emptyList());

        List<String> tagKeywords = argMultimap.getValue(PREFIX_TAG)
                .map(s -> Arrays.asList(s.trim().split("\\s+")))
                .orElse(Collections.emptyList());

        boolean hasEmptyList = Stream.of(childNameKeywords, parentNameKeywords, allergyKeywords, tagKeywords)
                .anyMatch(list -> list.size() == 1 && list.get(0).isEmpty());

        if (hasEmptyList) {
            throw new ParseException(
                    String.format(MESSAGE_EMPTY_PARAMETER));
        }

        if (childNameKeywords.isEmpty()
                && parentNameKeywords.isEmpty()
                && allergyKeywords.isEmpty()
                && tagKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        return new FindCommand(new PersonContainsKeywordsPredicate(childNameKeywords,
                                                                    parentNameKeywords,
                                                                    allergyKeywords,
                                                                    tagKeywords));
    }

}
