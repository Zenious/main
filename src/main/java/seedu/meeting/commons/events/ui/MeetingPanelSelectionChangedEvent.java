package seedu.meeting.commons.events.ui;

import seedu.meeting.commons.events.BaseEvent;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * Represents a selection change in the {@code MeetingListPanel}
 */
public class MeetingPanelSelectionChangedEvent extends BaseEvent {

    private final Meeting newSelection;

    public MeetingPanelSelectionChangedEvent(Meeting newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Meeting getNewSelection() {
        return newSelection;
    }
}
