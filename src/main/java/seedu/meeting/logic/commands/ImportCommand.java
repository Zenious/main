package seedu.meeting.logic.commands;

import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.exceptions.DataConversionException;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.Model;
import seedu.meeting.model.ReadOnlyMeetingBook;
import seedu.meeting.storage.XmlMeetingBookStorage;

/**
 * Import MeetingBook XML Files into MeetingBook
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String OPTION_OVERWRITE = "--force";
    public static final String MESSAGE_SUCCESS = "%s have been imported into MeetingBook.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Import XML File to MeetingBook. Default behaviour "
            + "is to ignore any conflicting Person/Group entries unless "
            + OPTION_OVERWRITE + " is provided.\n"
            + "Parameters: [Options] "
            + PREFIX_PATH + "FilePath\n"
            + "Options: "
            + OPTION_OVERWRITE + " overwrite any conflicting Person/Group \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_PATH + "backup\n"
            + COMMAND_WORD + " " + OPTION_OVERWRITE + " " + PREFIX_PATH + "backup";
    public static final String MESSAGE_FAIL_DATA = "Data file provided is not in the correct format. ";
    public static final String MESSAGE_FAIL_NOFILE = "File does not exists.";

    private static final Logger logger = LogsCenter.getLogger(XmlMeetingBookStorage.class);

    private boolean overwrite;

    private Path importPath;

    public ImportCommand(Path filepath) {
        importPath = filepath;
        this.overwrite = false;
    }

    public ImportCommand(Path filepath, boolean overwrite) {
        importPath = filepath;
        this.overwrite = overwrite;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {

        XmlMeetingBookStorage importedXmlAddressStorage = new XmlMeetingBookStorage(importPath);
        Optional<ReadOnlyMeetingBook> importedMeetingBook;
        try {
            importedMeetingBook = importedXmlAddressStorage.readMeetingBook();
            if (!importedMeetingBook.isPresent()) {
                return new CommandResult(MESSAGE_FAIL_NOFILE);
            }
        } catch (DataConversionException e) {
            logger.warning(MESSAGE_FAIL_DATA);
            return new CommandResult(MESSAGE_FAIL_DATA);
        } catch (IOException e) {
            logger.warning(MESSAGE_FAIL_NOFILE);
            return new CommandResult(MESSAGE_FAIL_NOFILE);
        }

        model.importMeetingBook(importedMeetingBook.get(), overwrite);
        model.commitMeetingBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, importPath.toString()));
    }

}
