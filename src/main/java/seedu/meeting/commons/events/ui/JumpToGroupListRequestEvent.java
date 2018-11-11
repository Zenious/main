package seedu.meeting.commons.events.ui;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.commons.events.BaseEvent;

// @@author jeffreyooi
/**
 * Indicates a request to jump to the list of groups
 */
public class JumpToGroupListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToGroupListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
