package seedu.meeting.model.group.exceptions;

// @@author Derek-Hardy
/**
 * Signals that the operation will result in duplicate Groups
 * (Groups are considered duplicates if they have the same identity).
 *
 * {@author Derek-Hardy}
 */
public class DuplicateGroupException extends RuntimeException {

    public DuplicateGroupException() {
        super("Operation would result in duplicate groups");
    }
}
