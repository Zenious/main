package seedu.meeting.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;

// @@author Derek-Hardy
/**
 * Adds a group to the MeetingBook.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "addGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a group to the MeetingBook. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "CS2103T ";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the MeetingBook.";

    private final Group toAdd;
    private final Title groupName;

    /**
     * Creates an AddGroupCommand to add the specified {@code Group}
     */
    public AddGroupCommand(Group group) {
        requireNonNull(group);
        toAdd = group;
        groupName = toAdd.getTitle();
    }


    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasGroup(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        model.addGroup(toAdd);
        model.commitMeetingBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddGroupCommand // instanceof handles nulls
                && groupName.equals(((AddGroupCommand) other).groupName));
    }
}
