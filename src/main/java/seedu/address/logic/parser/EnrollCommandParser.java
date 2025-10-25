package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EnrollCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.subject.Subject;

/**
 * Parser class for Enroll command
 */
public class EnrollCommandParser implements Parser<EnrollCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EnrolCommand
     * and returns a EnrolCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EnrollCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EnrollCommand.MESSAGE_USAGE));
        }

        Index[] indices;
        boolean enrolAll = false;
        List<Subject> subjects;

        String preamble = argMultimap.getPreamble();
        if (ParserUtil.checkIsAll(preamble)) {
            enrolAll = true;
            indices = new Index[0];
        } else {
            indices = ParserUtil.parseIndexArray(preamble);
        }
        subjects = ParserUtil.parseSubjects(argMultimap.getAllValues(PREFIX_SUBJECT));

        return new EnrollCommand(indices, enrolAll, subjects);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
