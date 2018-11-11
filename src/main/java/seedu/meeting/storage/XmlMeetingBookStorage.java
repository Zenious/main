package seedu.meeting.storage;

import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.commons.exceptions.IllegalValueException;
import seedu.meeting.commons.util.FileUtil;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.model.group.exceptions.GroupNotFoundException;
import seedu.meeting.model.meeting.exceptions.MeetingNotFoundException;
import seedu.meeting.model.person.exceptions.PersonNotFoundException;

/**
 * A class to access MeetingBook data stored as an xml file on the hard disk.
 */
public class XmlMeetingBookStorage implements MeetingBookStorage {

    private static final Logger logger = LogsCenter.getLogger(XmlMeetingBookStorage.class);

    private Path filePath;

    public XmlMeetingBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMeetingBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyMeetingBook> readMeetingBook() throws DataConversionException, IOException {
        return readMeetingBook(filePath);
    }

    /**
     * Similar to {@link #readMeetingBook()}
     * @param filePath location of the data. Cannot be null
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyMeetingBook> readMeetingBook(Path filePath) throws DataConversionException,
                                                                                 FileNotFoundException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("MeetingBook file " + filePath + " not found");
            return Optional.empty();
        }

        XmlSerializableMeetingBook xmlMeetingBook = XmlFileStorage.loadDataFromSaveFile(filePath);
        try {
            return Optional.of(xmlMeetingBook.toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (PersonNotFoundException pnfe) {
            logger.info("Incorrectly Formatted Person Found in XML File");
            throw new DataConversionException(pnfe);
        } catch (GroupNotFoundException gnfe) {
            logger.info("Incorrectly Formatted Group Found in XML File");
            throw new DataConversionException(gnfe);
        } catch (MeetingNotFoundException mnfe) {
            logger.info("Incorrectly Formatted Meeting Found in XML File");
            throw new DataConversionException(mnfe);
        }
    }

    @Override
    public void saveMeetingBook(ReadOnlyMeetingBook meetingBook) throws IOException {
        saveMeetingBook(meetingBook, filePath);
    }

    /**
     * Similar to {@link #saveMeetingBook(ReadOnlyMeetingBook)}
     * @param filePath location of the data. Cannot be null
     */
    public void saveMeetingBook(ReadOnlyMeetingBook meetingBook, Path filePath) throws IOException {
        requireNonNull(meetingBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        XmlFileStorage.saveDataToFile(filePath, new XmlSerializableMeetingBook(meetingBook));
    }

    @Override
    public void deleteMeetingBook(Path filePath) throws IOException {
        FileUtil.deleteFile(filePath);
    }

}
