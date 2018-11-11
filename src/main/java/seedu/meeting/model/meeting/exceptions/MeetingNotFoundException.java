package seedu.meeting.model.meeting.exceptions;

// @@author jeffreyooi
/**
 * Signals that the Meeting cannot be found.
 */
public class MeetingNotFoundException extends RuntimeException {
    public MeetingNotFoundException() {
        super("Meeting does not exist");
    }
}
