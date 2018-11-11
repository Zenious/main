package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_MEETING;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Arrays;
import java.util.List;

import seedu.meeting.commons.core.EventsCenter;
import seedu.meeting.commons.core.Messages;
import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.meeting.commons.events.ui.JumpToListRequestEvent;
import seedu.meeting.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.util.GroupContainsMeetingPredicate;
import seedu.meeting.model.group.util.GroupContainsPersonPredicate;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Person;

// @@author jeffreyooi
/**
 * Selects a person identified using it's displayed index from the MeetingBook.
 */
public class SelectCommand extends Command {

    /**
     * Available select command types
     */
    public enum SelectCommandType {
        GROUP, PERSON, MEETING
    }

    public static final String COMMAND_WORD = "select";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the displayed person list.\n"
            + "Parameters: [" + PREFIX_GROUP + " or " + PREFIX_MEETING + " or "
                            + PREFIX_PERSON + "]INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " g/1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";
    public static final String MESSAGE_SELECT_MEETING_SUCCESS = "Selected Meeting: %1$s";

    private final Index targetIndex;
    private final SelectCommandType selectType;

    public SelectCommand(Index targetIndex, SelectCommandType selectType) {
        this.targetIndex = targetIndex;
        this.selectType = selectType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (selectType == SelectCommandType.GROUP) {
            List<Group> filteredGroupList = model.getFilteredGroupList();

            if (targetIndex.getZeroBased() >= filteredGroupList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }

            EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
            Group group = filteredGroupList.get(targetIndex.getZeroBased());
            model.updateFilteredPersonList(new GroupContainsPersonPredicate(Arrays.asList(group)));
            model.updateFilteredMeetingList(new GroupContainsMeetingPredicate(Arrays.asList(group)));
            return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));
        } else if (selectType == SelectCommandType.PERSON) {
            List<Person> filteredPersonList = model.getFilteredPersonList();

            if (targetIndex.getZeroBased() >= filteredPersonList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
        } else {
            List<Meeting> filteredMeetingList = model.getFilteredMeetingList();

            if (targetIndex.getZeroBased() >= filteredMeetingList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
            }

            EventsCenter.getInstance().post(new JumpToMeetingListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_MEETING_SUCCESS, targetIndex.getOneBased()));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
