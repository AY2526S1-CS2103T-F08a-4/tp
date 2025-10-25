package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCORE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetScoreCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.subject.Subject;

/**
 * Parses command for setscore
 */
public class SetScoreCommandParser implements Parser<SetScoreCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SetScoreCommand
     * and returns a SetScoreCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public seedu.address.logic.commands.SetScoreCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_SUBJECT, PREFIX_SCORE);

        if (!arePrefixesPresent(argMultimap, PREFIX_SUBJECT, PREFIX_SCORE)
                || argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetScoreCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SUBJECT, PREFIX_SCORE);

        Index[] indices;
        Subject subject;
        int score;
        boolean setAll = false;

        String preamble = argMultimap.getPreamble();
        if (ParserUtil.checkIsAll(preamble)) {
            setAll = true;
            indices = new Index[0];
        } else {
            indices = ParserUtil.parseIndexArray(preamble);
        }
        subject = ParserUtil.parseSubject(argMultimap.getValue(PREFIX_SUBJECT).get());
        score = ParserUtil.parseScore(argMultimap.getValue(PREFIX_SCORE).get());

        return new SetScoreCommand(indices, setAll, subject, score);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
