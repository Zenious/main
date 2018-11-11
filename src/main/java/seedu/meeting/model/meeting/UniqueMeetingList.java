package seedu.meeting.model.meeting;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// @@author jeffreyooi
/**
 * A list of meetings that does not allow nulls and is paired with groups that holds it.
 * A meeting is considered duplicate if there exist another meeting that belongs to the same owner and both have same
 * identity fields. The identities are compared by {@code Meeting#isSameMeeting(Meeting)}.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueMeetingList implements Iterable<Meeting> {

    private final ObservableList<Meeting> internalList = FXCollections.observableArrayList();

    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(Meeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMeeting);
    }

    /**
     * Replaces the list with new list.
     */
    public void setMeetings(List<Meeting> toAdd) {
        requireNonNull(toAdd);
        internalList.setAll(toAdd);

        Collections.sort(internalList);
    }

    /**
     * Creates a list and return as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Meeting> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Meeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueMeetingList // instanceof handles nulls
                && internalList.equals(((UniqueMeetingList) other).internalList));
    }
}
