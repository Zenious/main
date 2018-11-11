package seedu.meeting.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.meeting.testutil.TypicalMeetingBook.getTypicalMeetingBook;
import static seedu.meeting.testutil.TypicalMeetings.REHEARSAL;
import static seedu.meeting.testutil.TypicalMeetings.WEEKLY;
import static seedu.meeting.testutil.TypicalPersons.ALICE;
import static seedu.meeting.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.exceptions.GroupNotFoundException;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.person.Name;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.person.exceptions.DuplicatePersonException;
import seedu.meeting.model.person.exceptions.PersonNotFoundException;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.GroupBuilder;
import seedu.meeting.testutil.PersonBuilder;

public class MeetingBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final MeetingBook meetingBook = new MeetingBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), meetingBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        meetingBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyMeetingBook_replacesData() {
        MeetingBook newData = getTypicalMeetingBook();
        meetingBook.resetData(newData);
        assertEquals(newData, meetingBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        // HACK
        // TODO: change to correctly take in groups
        MeetingBookStub newData = new MeetingBookStub(newPersons);

        thrown.expect(DuplicatePersonException.class);
        meetingBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        meetingBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInMeetingBook_returnsFalse() {
        assertFalse(meetingBook.hasPerson(ALICE));
    }

    @Test
    public void hasGroup_groupNotInMeetingBook_returnsFalse() {
        assertFalse(meetingBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void addPerson_personInMeetingBook_returnsTrue() {
        meetingBook.addPerson(ALICE);
        assertTrue(meetingBook.hasPerson(ALICE));
        meetingBook.removePerson(ALICE); //reset
    }

    @Test
    public void addGroup_groupInMeetingBook_returnsTrue() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addPerson(ALICE);
        meetingBook.addGroup(PROJECT_2103T);
        assertTrue(meetingBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void removePerson_personIsRemoved_returnsFalse() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addPerson(BOB);
        meetingBook.removePerson(BOB);
        assertFalse(meetingBook.hasPerson(BOB));
    }

    @Test
    public void removeGroup_groupIsRemoved_returnsFalse() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addGroup(PROJECT_2103T);
        meetingBook.removeGroup(PROJECT_2103T);
        assertFalse(meetingBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void updatePerson_personIsUpdated_returnsTrue() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addPerson(ALICE);
        meetingBook.updatePerson(ALICE, BOB);
        assertTrue(meetingBook.hasPerson(BOB));
    }

    @Test
    public void updatePerson_personIsUpdated_returnsFalse() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addPerson(ALICE);
        meetingBook.updatePerson(ALICE, BOB);
        assertFalse(meetingBook.hasPerson(ALICE));
    }

    @Test
    public void updateGroup_groupIsUpdated_returnsTrue() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addGroup(GROUP_2101);
        meetingBook.updateGroup(GROUP_2101, PROJECT_2103T);
        assertTrue(meetingBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void updateGroup_groupIsUpdated_returnsFalse() {
        MeetingBook meetingBook = new MeetingBook();
        meetingBook.addGroup(GROUP_2101);
        meetingBook.updateGroup(GROUP_2101, PROJECT_2103T);
        assertFalse(meetingBook.hasGroup(GROUP_2101));
    }

    @Test
    public void joinGroup_personNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        MeetingBook meetingBook = new MeetingBook();
        Person outsider = new PersonBuilder().withName("outsider").build();
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addGroup(group);
        meetingBook.joinGroup(outsider, group);
    }

    @Test
    public void joinGroup_groupNotExist_throwsGroupNotFoundException() {
        thrown.expect(GroupNotFoundException.class);
        MeetingBook meetingBook = new MeetingBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group other = new GroupBuilder().withTitle("outlier").build();
        meetingBook.addPerson(person);
        meetingBook.joinGroup(person, other);

    }

    @Test
    public void joinGroup_personInGroup_returnsTrue() {
        MeetingBook meetingBook = new MeetingBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addPerson(person);
        meetingBook.addGroup(group);
        meetingBook.joinGroup(person, group);
        assertTrue(person.hasGroup(group));
    }

    @Test
    public void joinGroup_groupHasPerson_returnsTrue() {
        MeetingBook meetingBook = new MeetingBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addPerson(person);
        meetingBook.addGroup(group);
        meetingBook.joinGroup(person, group);
        assertTrue(group.hasMember(person));
    }

    @Test
    public void leaveGroup_personNotExist_throwsPersonNotFoundException() {
        thrown.expect(PersonNotFoundException.class);
        MeetingBook meetingBook = new MeetingBook();
        Person outsider = new PersonBuilder().withName("outsider").build();
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addGroup(group);
        meetingBook.leaveGroup(outsider, group);
    }


    @Test
    public void leaveGroup_personNotInGroup_returnsFalse() {
        MeetingBook meetingBook = new MeetingBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addPerson(person);
        meetingBook.addGroup(group);
        meetingBook.joinGroup(person, group);
        meetingBook.leaveGroup(person, group);
        assertFalse(person.hasGroup(group));
    }

    @Test
    public void leaveGroup_groupHasNoPerson_returnsFalse() {
        MeetingBook meetingBook = new MeetingBook();
        Person person = new PersonBuilder().withName("Derek").build();
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addPerson(person);
        meetingBook.addGroup(group);
        meetingBook.joinGroup(person, group);
        meetingBook.leaveGroup(person, group);
        assertFalse(group.hasMember(person));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInMeetingBook_returnsTrue() {
        meetingBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(meetingBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonByName_equals_returnsTrue() {
        Person person = new PersonBuilder().withName("Pakorn").build();
        Name name = new Name("Pakorn");

        meetingBook.addPerson(person);
        Person match = meetingBook.getPersonByName(name);

        assertTrue(match.equals(person));
    }

    @Test
    public void getGroupByTitle_equals_returnsTrue() {
        Group group = new GroupBuilder().withTitle("tutorial").build();
        Title title = new Title("tutorial");

        meetingBook.addGroup(group);
        Group match = meetingBook.getGroupByTitle(title);

        assertTrue(match.equals(group));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        meetingBook.getPersonList().remove(0);
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        meetingBook.getGroupList().remove(0);
    }

    @Test
    public void setMeeting_groupWithoutMeeting_returnTrue() {
        List<Meeting> expectedMeetings = new ArrayList();
        expectedMeetings.addAll(meetingBook.getMeetingList());

        List<Group> expectedGroups = new ArrayList();
        expectedGroups.addAll(meetingBook.getGroupList());

        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.addGroup(group);
        meetingBook.setMeeting(group, WEEKLY);

        expectedMeetings.add(WEEKLY);
        expectedGroups.add(group);

        assertEquals(expectedMeetings, meetingBook.getMeetingList());
        assertEquals(expectedGroups, meetingBook.getGroupList());
    }

    @Test
    public void setMeeting_groupWithMeeting_returnTrue() {
        meetingBook.addGroup(GROUP_2101);

        List<Meeting> expectedMeetings = new ArrayList();
        expectedMeetings.addAll(meetingBook.getMeetingList());
        expectedMeetings.remove(REHEARSAL);
        expectedMeetings.add(WEEKLY);

        List<Group> expectedGroups = new ArrayList();
        expectedGroups.addAll(meetingBook.getGroupList());

        meetingBook.setMeeting(GROUP_2101, WEEKLY);

        assertEquals(expectedMeetings, meetingBook.getMeetingList());
        assertEquals(expectedGroups, meetingBook.getGroupList());
    }

    @Test
    public void setMeeting_noGroup_throwsGroupNotFoundExeception() {
        thrown.expect(GroupNotFoundException.class);
        Group group = new GroupBuilder().withTitle("class").build();
        meetingBook.setMeeting(group, WEEKLY);
    }

    /**
     * A stub ReadOnlyMeetingBook whose persons list can violate interface constraints.
     */
    private static class MeetingBookStub implements ReadOnlyMeetingBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        MeetingBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }

        @Override
        public ObservableList<Meeting> getMeetingList() {
            List<Meeting> meetingList = groups.stream().map(Group::getMeeting).collect(Collectors.toList());
            return FXCollections.observableArrayList(meetingList);
        }
    }

}
