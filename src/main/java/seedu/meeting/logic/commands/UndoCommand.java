package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.model.Model.PREDICATE_SHOW_ALL_GROUPS;
import static seedu.meeting.model.Model.PREDICATE_SHOW_ALL_MEETINGS;
import static seedu.meeting.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;

/**
 * Reverts the {@code model}'s MeetingBook to its previous state.
 */
public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";
    public static final String MESSAGE_SUCCESS = "Undo success!";
    public static final String MESSAGE_FAILURE = "No more commands to undo!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (!model.canUndoMeetingBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.undoMeetingBook();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        model.updateFilteredGroupList(PREDICATE_SHOW_ALL_GROUPS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
