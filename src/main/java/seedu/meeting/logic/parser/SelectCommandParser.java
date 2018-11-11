package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.stream.Stream;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.commands.SelectCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;

// @@author jeffreyooi
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class SelectCommandParser implements Parser<SelectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SelectCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_GROUP, PREFIX_PERSON, PREFIX_MEETING);

        if (!isOneOfThePrefixesPresent(argMultimap, PREFIX_GROUP, PREFIX_PERSON, PREFIX_MEETING)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));
        }

        try {
            SelectCommand.SelectCommandType selectOption = parseSelectCommandType(argMultimap);
            Index index = parseSelectIndex(argMultimap, selectOption);
            return new SelectCommand(index, selectOption);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE), pe);
        }
    }

    /**
     * Returns the {@code SelectCommand.SelectCommandType} from user input.
     * @throws ParseException if the prefix does not exist
     */
    private SelectCommand.SelectCommandType parseSelectCommandType(ArgumentMultimap argumentMultimap)
        throws ParseException {

        if (argumentMultimap.getValue(PREFIX_GROUP).isPresent()) {
            return SelectCommand.SelectCommandType.GROUP;
        } else if (argumentMultimap.getValue(PREFIX_MEETING).isPresent()) {
            return SelectCommand.SelectCommandType.MEETING;
        } else if (argumentMultimap.getValue(PREFIX_PERSON).isPresent()) {
            return SelectCommand.SelectCommandType.PERSON;
        } else {
            throw new ParseException("Unknown prefix");
        }
    }

    /**
     * Returns the index from user input based on the {@code selectType}
     * @throws ParseException if the prefix does not exist
     */
    private Index parseSelectIndex(ArgumentMultimap argumentMultimap, SelectCommand.SelectCommandType selectType)
        throws ParseException {
        switch (selectType) {
        case GROUP:
            return ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_GROUP).get());
        case MEETING:
            return ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_MEETING).get());
        case PERSON:
            return ParserUtil.parseIndex(argumentMultimap.getValue(PREFIX_PERSON).get());
        default:
            throw new ParseException("Unknown prefix");
        }
    }

    /**
     * Returns true if one of the prefixes exist in the given {@code ArgumentMultimap}
     */
    private static boolean isOneOfThePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
