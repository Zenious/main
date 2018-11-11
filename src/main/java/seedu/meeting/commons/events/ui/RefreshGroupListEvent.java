package seedu.meeting.commons.events.ui;

import seedu.meeting.commons.events.BaseEvent;

// @@author jeffreyooi
/**
 * Indicates a request to refresh the group list.
 * This is used when certain changes are not observed by the {@code ListView} of JavaFX.
 */
public class RefreshGroupListEvent extends BaseEvent {
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
