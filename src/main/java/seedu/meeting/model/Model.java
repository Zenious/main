package seedu.meeting.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;

import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.exceptions.GroupHasNoMeetingException;
import seedu.meeting.model.group.exceptions.GroupNotFoundException;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.person.util.PersonPropertyComparator;
import seedu.meeting.model.shared.Title;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true for persons
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true for groups
     */
    Predicate<Group> PREDICATE_SHOW_ALL_GROUPS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true for meetings
     */
    Predicate<Meeting> PREDICATE_SHOW_ALL_MEETINGS = unused -> true;

    /**
     * Clears existing backing model and replaces with the provided new data.
     */
    void resetData(ReadOnlyMeetingBook newData);

    /**
     * Returns the MeetingBook
     */
    ReadOnlyMeetingBook getMeetingBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the MeetingBook.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the MeetingBook.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the MeetingBook.
     */
    void addPerson(Person person);

    // @@author Derek-Hardy
    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the MeetingBook.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the MeetingBook.
     */
    void updatePerson(Person target, Person editedPerson);

    /**
     * Replace the given group {@code target} in the list with {@code editedGroup}.
     * {@code target} must exist in the MeetingBook.
     * The group identity of {@code editedGroup} must not be the same as another existing group in the MeetingBook.
     */
    void updateGroup(Group target, Group editedGroup) throws GroupNotFoundException;
    // @@author
    // @@author Derek-Hardy
    /**
     * Add a group object {@code group} to the MeetingBook.
     * The group must not already exist in the MeetingBook.
     *
     * @param group The new group to be added into MeetingBook
     */
    void addGroup(Group group);

    /**
     * Remove a group {@code group} from the MeetingBook.
     * Report error message if the given group does not exist inside the MeetingBook.
     *
     * @param group The group to be removed from MeetingBook
     */
    void removeGroup(Group group);

    /**
     * Join a {@code person} into a {@code group}.
     * Both person and group must exist in the {@code MeetingBook}.
     *
     */
    void joinGroup(Person person, Group group);


    /**
     * Remove a {@code person} from a {@code group}.
     * The person must exist in the group.
     */
    void leaveGroup(Person person, Group group);

    // @@author


    // @@author NyxF4ll
    /**
     * Returns true if a group with the same identity as {@code group} exists in the MeetingBook.
     */
    boolean hasGroup(Group group);

    /** Returns an unmodifiable view of the group list */
    ObservableList<Group> getGroupList();
    // @@author

    /** Returns an existing group that matches the {@code title} */
    Group getGroupByTitle(Title title);

    /** Returns an existing person that matches the {@code name} */
    Person getPersonByName(Name name);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);


    /** Returns an unmodifiable view of the filtered group list */
    ObservableList<Group> getFilteredGroupList();

    /**
     * Updates the filter of the filtered group list to filter by the given {@code predicate}
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredGroupList(Predicate<Group> predicate);

    /**
     * @return An unmodifiable view of the sorted person list
     */
    ObservableList<Person> getSortedPersonList();

    /**
     * Sets meeting field of {@code group} in the group list to {@code meeting}.
     */
    public void setMeeting(Group group, Meeting meeting) throws GroupNotFoundException;

    /**
     * Resets meeting field of {@code group} in the group list to an empty optional.
     */
    public void cancelMeeting(Group group) throws GroupNotFoundException, GroupHasNoMeetingException;

    /**
     * Updates the filter of the filtered meeting list to filter by the given {@code predicate}
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredMeetingList(Predicate<Meeting> predicate);

    /**
     * @return An unmodifiable view of the filtered meeting list
     */
    ObservableList<Meeting> getFilteredMeetingList();

    /**
     * Updates the sorting of the sorted person list to sort by the given {@code comparator}.
     * @param personPropertyComparator The comparator to sort the list by.
     */
    void updateSortedPersonList(PersonPropertyComparator personPropertyComparator);

    /**
     * Returns true if the model has previous MeetingBook states to restore.
     */
    boolean canUndoMeetingBook();

    /**
     * Returns true if the model has undone MeetingBook states to restore.
     */
    boolean canRedoMeetingBook();

    /**
     * Restores the model's MeetingBook to its previous state.
     */
    void undoMeetingBook();

    /**
     * Restores the model's MeetingBook to its previously undone state.
     */
    void redoMeetingBook();

    /**
     * Saves the current MeetingBook state for undo/redo.
     */
    void commitMeetingBook();

    /**
     * Export the current MeetingBook.
     */
    void exportMeetingBook(Path filepath);

    /**
     * Import the current MeetingBook.
     */
    void importMeetingBook(ReadOnlyMeetingBook importMeetingBook, boolean overwrite);

    /**
     * Change User Preferences.
     */
    void changeUserPrefs(Path filepath);

    /**
     * Get Current Address Book File Path.
     */
    Path getMeetingBookFilePath();
}
