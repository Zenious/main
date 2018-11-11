package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.HashSet;
import java.util.Set;

import seedu.meeting.logic.commands.JoinCommand;
import seedu.meeting.logic.parser.exceptions.ParseException;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.person.Email;
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.person.Phone;
import seedu.meeting.model.shared.Address;
import seedu.meeting.model.shared.Title;
import seedu.meeting.model.tag.Tag;

// @@author Derek-Hardy
/**
 * Parses input arguments and creates a new JoinCommand object
 */
public class JoinCommandParser implements Parser<JoinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the JoinCommand
     * and returns an JoinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public JoinCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_GROUP);

        if (!argMultimap.areAllPrefixesPresent(PREFIX_NAME, PREFIX_GROUP)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, JoinCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_GROUP).get());

        Phone phone = new Phone("0000");
        Email email = new Email("dummy_email@gmail.com");
        Address addr = new Address("dummy_address");
        Set<Tag> tagList = new HashSet<>();

        Person person = new Person(name, phone, email, addr, tagList);
        Group group = new Group(title);

        return new JoinCommand(person, group);
    }
}
