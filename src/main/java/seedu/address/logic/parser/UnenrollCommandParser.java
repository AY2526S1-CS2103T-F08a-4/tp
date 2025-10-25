package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnenrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.subject.Subject;

/**
 * Parser for the unenroll command
 */
public class UnenrollCommandParser implements Parser<UnenrollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UnenrollCommand
     * and returns a UnenrollCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnenrollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnenrollCommand.MESSAGE_USAGE));
        }

        Index[] indices;
        boolean unenrollAll = false;
        List<Subject> subjects;

        String preamble = argMultimap.getPreamble();
        if (ParserUtil.checkIsAll(preamble)) {
            unenrollAll = true;
            indices = new Index[0];
        } else {
            indices = ParserUtil.parseIndexArray(preamble);
        }
        subjects = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

        return new UnenrollCommand(indices, unenrollAll, subjects);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
