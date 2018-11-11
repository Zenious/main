package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.commons.core.Messages.MESSAGE_GROUP_NOT_FOUND;
import static seedu.meeting.commons.core.Messages.MESSAGE_PERSON_NOT_FOUND;
import static seedu.meeting.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.shared.Title;


// @@author Derek-Hardy
/**
 * Remove a person from a group existed in the MeetingBook.
 */
public class LeaveCommand extends Command {

    public static final String COMMAND_WORD = "leave";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a person from a group in the MeetingBook.\n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_GROUP + "GROUP\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Derek Hardy "
            + PREFIX_GROUP + "GROUP_03";

    public static final String MESSAGE_LEAVE_SUCCESS = "Person: %1$s removed from the group: %2$s";

    private final Name personName;
    private final Title groupName;
    private Person matchedPersonByName;
    private Group matchedGroupByName;

    /**
     * Creates an LeaveCommand to remove the specified {@code person} from {@code group}.
     */
    public LeaveCommand(Person person, Group group) {
        requireAllNonNull(person);
        this.personName = person.getName();
        this.groupName = group.getTitle();
        matchedPersonByName = null;
        matchedGroupByName = null;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        matchedGroupByName = model.getGroupByTitle(groupName);

        matchedPersonByName = model.getPersonByName(personName);

        if (matchedGroupByName == null) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);

        } else if (matchedPersonByName == null) {
            throw new CommandException(MESSAGE_PERSON_NOT_FOUND);
        }

        model.leaveGroup(matchedPersonByName, matchedGroupByName);
        model.commitMeetingBook();
        return new CommandResult(String.format(MESSAGE_LEAVE_SUCCESS, matchedPersonByName.getName().fullName,
            matchedGroupByName.getTitle().fullTitle));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LeaveCommand // instanceof handles nulls
                && personName.equals(((LeaveCommand) other).personName)
                && groupName.equals(((LeaveCommand) other).groupName)); // state check
    }
}
