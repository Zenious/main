package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.meeting.model.Model.PREDICATE_SHOW_ALL_MEETINGS;
import static seedu.meeting.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.model.Model;

// @@author jeffreyooi
/**
 * Lists all persons in the MeetingBook to the user.
 */
public class ListCommand extends Command {

    /**
     * Available list command types
     */
    public enum ListCommandType {
        GROUP, PERSON, MEETING, ALL
    }

    public static final String COMMAND_WORD = "list";

    public static final String COMMAND_PARAM_GROUP = "group";
    public static final String COMMAND_PARAM_GROUP_SHORT = "g";
    public static final String COMMAND_PARAM_PERSON = "person";
    public static final String COMMAND_PARAM_PERSON_SHORT = "p";
    public static final String COMMAND_PARAM_MEETING = "meeting";
    public static final String COMMAND_PARAM_MEETING_SHORT = "m";
    public static final String COMMAND_PARAM_ALL = "all";
    public static final String COMMAND_PARAM_ALL_SHORT = "a";

    public static final String MESSAGE_SUCCESS_PERSON = "Listed all persons.";
    public static final String MESSAGE_SUCCESS_GROUP = "Listed all groups.";
    public static final String MESSAGE_SUCCESS_MEETING = "Listed all meetings.";
    public static final String MESSAGE_SUCCESS_ALL = "Listed all persons, groups and meetings.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Lists all groups / meetings / persons in MeetingBook. \n"
        + "Parameters: "
        + "[" + COMMAND_PARAM_GROUP + "|" + COMMAND_PARAM_GROUP_SHORT
        + "|" + COMMAND_PARAM_MEETING + "|" + COMMAND_PARAM_MEETING_SHORT
        + "|" + COMMAND_PARAM_PERSON + "|" + COMMAND_PARAM_PERSON_SHORT
        + "|" + COMMAND_PARAM_ALL + "|" + COMMAND_PARAM_ALL_SHORT
        + "]\n"
        + "Example: " + COMMAND_WORD + " " + COMMAND_PARAM_GROUP;

    private ListCommandType listCommandType;

    public ListCommand(ListCommandType listCommandType) {
        this.listCommandType = listCommandType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        switch (listCommandType) {
        case PERSON:
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_PERSON);
        case GROUP:
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            return new CommandResult(MESSAGE_SUCCESS_GROUP);
        case MEETING:
            model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
            return new CommandResult(MESSAGE_SUCCESS_MEETING);
        default:
            model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
            model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(MESSAGE_SUCCESS_ALL);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ListCommand // instanceof handles nulls
            && listCommandType.equals(((ListCommand) other).listCommandType)); // state check
    }
    //@@author
}
