package seedu.meeting.model.group;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.NUS_BASKETBALL;
import static seedu.meeting.testutil.TypicalGroups.NUS_COMPUTING;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.meeting.model.group.util.GroupContainsMeetingPredicate;

// @@author jeffreyooi
/**
 * Tests that a {@code Group}'s {@code Title} matches any of the keywords given.
 */
public class GroupContainsMeetingPredicateTest {
    @Test
    public void equals() {
        List<Group> firstPredicateGroupList = Collections.singletonList(GROUP_2101);
        List<Group> secondPredicateGroupList = Arrays.asList(PROJECT_2103T, NUS_COMPUTING);

        GroupContainsMeetingPredicate firstPredicate = new GroupContainsMeetingPredicate(firstPredicateGroupList);
        GroupContainsMeetingPredicate secondPredicate = new GroupContainsMeetingPredicate(secondPredicateGroupList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GroupContainsMeetingPredicate firstPredicateCopy = new GroupContainsMeetingPredicate(firstPredicateGroupList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(0));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different object -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_groupContainsMeeting_returnsTrue() {
        // Matching group
        GroupContainsMeetingPredicate predicate =
            new GroupContainsMeetingPredicate(Collections.singletonList(NUS_COMPUTING));
        assertTrue(predicate.test(NUS_COMPUTING.getMeeting()));
    }

    @Test
    public void tset_groupDoesNotContainMeeting_returnsFalse() {
        // No groups
        GroupContainsMeetingPredicate predicate =
            new GroupContainsMeetingPredicate(Collections.emptyList());
        assertFalse(predicate.test(NUS_COMPUTING.getMeeting()));

        // Different groups
        predicate = new GroupContainsMeetingPredicate(Collections.singletonList(PROJECT_2103T));
        assertFalse(predicate.test(NUS_COMPUTING.getMeeting()));
        assertFalse(predicate.test(NUS_BASKETBALL.getMeeting()));
    }
}
