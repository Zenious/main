package seedu.meeting.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.meeting.testutil.TypicalGroups.GROUP_2101;
import static seedu.meeting.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.meeting.testutil.TypicalPersons.ALICE;
import static seedu.meeting.testutil.TypicalPersons.BOB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.meeting.model.group.Group;
import seedu.meeting.testutil.GroupBuilder;
import seedu.meeting.testutil.PersonBuilder;

public class PersonTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        person.getTags().remove(0);
    }

    @Test
    public void asObservableGroupList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        person.getGroups().remove(0);
    }

    // @@author Derek-Hardy
    @Test
    public void hasGroup_personInGroup_returnsTrue() {
        Person person = new PersonBuilder().withGroup(PROJECT_2103T).build();
        assertTrue(person.hasGroup(PROJECT_2103T));
    }

    @Test
    public void hasGroup_personRemovedFromGroup_returnsFalse() {
        Person person = new PersonBuilder().build();
        person.addGroup(GROUP_2101);
        person.removeGroup(GROUP_2101);
        assertFalse(person.hasGroup(GROUP_2101));
    }

    @Test
    public void hasGroup_personAddedInGroup_returnsFalse() {
        Person person = new PersonBuilder().build();
        person.addGroup(GROUP_2101);
        assertTrue(person.hasGroup(GROUP_2101));
    }

    @Test
    public void clearMembership_personNotInGroup_returnsFalse() {
        Person person = new PersonBuilder().withGroup(GROUP_2101).withGroup(PROJECT_2103T).build();
        person.clearMembership();
        assertFalse(person.hasGroup(GROUP_2101));
    }

    @Test
    public void setUpMembership_personInGroup_returnsTrue() {
        Group group = new GroupBuilder().build();
        // since bidirectional relation is not possible in GroupBuilder
        // manual addition is needed here
        Person person = new PersonBuilder().withGroup(group).build();
        group.addMember(person);

        group.removeMemberHelper(person);
        person.setUpMembership();
        assertTrue(group.hasMember(person));
    }

    @Test
    public void copy_isSamePerson_returnsTrue() {
        Person person = new PersonBuilder().withName("Derek").build();
        Person personCopy = person.copy();
        assertTrue(personCopy.isSamePerson(person));
    }

    @Test
    public void copy_equals_returnsTrue() {
        Person person = new PersonBuilder().withName("Derek").build();
        Person personCopy = person.copy();
        assertTrue(personCopy.equals(person));
    }

    // @@author
    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // different phone and email -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
