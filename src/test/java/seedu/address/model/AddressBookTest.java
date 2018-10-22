package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUPTAG_CCA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalGroups.GROUP_2101;
import static seedu.address.testutil.TypicalGroups.PROJECT_2103T;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .withGrouptags(VALID_GROUPTAG_CCA).build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        // HACK
        // TODO: change to correctly take in groups
        AddressBookStub newData = new AddressBookStub(newPersons, editedAlice.getGroupTags());

        thrown.expect(DuplicatePersonException.class);
        addressBook.resetData(newData);
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        addressBook.hasPerson(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasGroup_groupNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        assertTrue(addressBook.hasPerson(ALICE));
        addressBook.removePerson(ALICE); //reset
    }

    @Test
    public void hasGroup_groupInAddressBook_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(PROJECT_2103T);
        assertTrue(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void hasPerson_personIsRemoved_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(BOB);
        addressBook.removePerson(BOB);
        assertFalse(addressBook.hasPerson(BOB));
    }

    @Test
    public void hasGroup_groupIsRemoved_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(PROJECT_2103T);
        addressBook.removeGroup(PROJECT_2103T);
        assertFalse(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void hasPerson_personIsUpdated_returnsTrue() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.updatePerson(ALICE, BOB);
        assertTrue(addressBook.hasPerson(BOB));
    }

    @Test
    public void hasPerson_personIsUpdated_returnsFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(ALICE);
        addressBook.updatePerson(ALICE, BOB);
        assertFalse(addressBook.hasPerson(ALICE));
    }

    @Test
    public void hasGroup_groupIsUpdated_returnTrue() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(GROUP_2101);
        addressBook.updateGroup(GROUP_2101, PROJECT_2103T);
        assertTrue(addressBook.hasGroup(PROJECT_2103T));
    }

    @Test
    public void hasGroup_groupIsUpdated_returnFalse() {
        AddressBook addressBook = new AddressBook();
        addressBook.addGroup(GROUP_2101);
        addressBook.updateGroup(GROUP_2101, PROJECT_2103T);
        assertFalse(addressBook.hasGroup(GROUP_2101));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getPersonList().remove(0);
    }

    @Test
    public void getGroupList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getGroupList().remove(0);
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Group> groups = FXCollections.observableArrayList();

        private final ObservableList<Tag> groupTags = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons, Collection<Tag> groups) {
            this.persons.setAll(persons);
            this.groupTags.setAll(groups);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Group> getGroupList() {
            return groups;
        }
    }

}
