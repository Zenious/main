package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.meeting.logic.commands.ListCommand;

// @@author jeffreyooi
/**
 * Test scope: similar to {@code DeleteCommandParserTest}
 */
public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_validArgs_returnsListPersonCommand() {
        assertParseSuccess(parser, " person", new ListCommand(ListCommand.ListCommandType.PERSON));
        assertParseSuccess(parser, " p", new ListCommand(ListCommand.ListCommandType.PERSON));
    }

    @Test
    public void parse_validArgs_returnsListGroupCommand() {
        assertParseSuccess(parser, " group", new ListCommand(ListCommand.ListCommandType.GROUP));
        assertParseSuccess(parser, " g", new ListCommand(ListCommand.ListCommandType.GROUP));
    }

    @Test
    public void parse_validArgs_returnsListMeetingCommand() {
        assertParseSuccess(parser, " meeting", new ListCommand(ListCommand.ListCommandType.MEETING));
        assertParseSuccess(parser, " m", new ListCommand(ListCommand.ListCommandType.MEETING));
    }

    @Test
    public void parse_validArgs_returnsListAllCommand() {
        assertParseSuccess(parser, "", new ListCommand(ListCommand.ListCommandType.ALL));
        assertParseSuccess(parser, "    ", new ListCommand(ListCommand.ListCommandType.ALL));
        assertParseSuccess(parser, " a", new ListCommand(ListCommand.ListCommandType.ALL));
        assertParseSuccess(parser, " all", new ListCommand(ListCommand.ListCommandType.ALL));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " b", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsWithWhitespaces_returnsListCommand() {
        assertParseSuccess(parser, "        g", new ListCommand(ListCommand.ListCommandType.GROUP));
        assertParseSuccess(parser, "     g   ", new ListCommand(ListCommand.ListCommandType.GROUP));
        assertParseSuccess(parser, "g        ", new ListCommand(ListCommand.ListCommandType.GROUP));
    }
}
