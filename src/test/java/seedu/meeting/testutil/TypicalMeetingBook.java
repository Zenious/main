package seedu.meeting.testutil;

import static seedu.meeting.testutil.TypicalGroups.getTypicalGroups;
import static seedu.meeting.testutil.TypicalPersons.getTypicalPersons;

import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.person.Person;

// @@author jeffreyooi
/**
 * A utility class that returns a typical {@code MeetingBook} object to be used in tests.
 */
public class TypicalMeetingBook {
    /**
     * Returns an {@code MeetingBook} with all the typical persons and groups.
     */
    public static MeetingBook getTypicalMeetingBook() {
        MeetingBook ab = new MeetingBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        for (Group group : getTypicalGroups()) {
            ab.addGroup(group);
        }
        return ab;
    }
}
