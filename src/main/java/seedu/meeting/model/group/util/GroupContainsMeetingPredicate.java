package seedu.meeting.model.group.util;

import java.util.List;
import java.util.function.Predicate;

import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * Tests that a {@code Group}'s {@code Meeting} matches any of the groups given.
 */
public class GroupContainsMeetingPredicate implements Predicate<Meeting> {
    private final List<Group> groups;

    public GroupContainsMeetingPredicate(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public boolean test(Meeting meeting) {
        if (meeting == null) {
            return false;
        }
        return groups.stream().filter(group -> group.getMeeting() != null)
            .anyMatch(group -> group.getMeeting().isSameMeeting(meeting));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof GroupContainsMeetingPredicate
            && groups.equals(((GroupContainsMeetingPredicate) other).groups));
    }
}
