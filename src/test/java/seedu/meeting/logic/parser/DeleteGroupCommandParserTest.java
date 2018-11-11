package seedu.meeting.logic.parser;

import static seedu.meeting.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.meeting.logic.commands.CommandTestUtil.INVALID_GROUP_TITLE_DESC;
import static seedu.meeting.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.meeting.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_DESC_GROUP_0;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_DESC_GROUP_2101;
import static seedu.meeting.logic.commands.CommandTestUtil.VALID_GROUP_TITLE_GROUP_0;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.meeting.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.meeting.testutil.TypicalGroups.GROUP_0;

import org.junit.Test;

import seedu.meeting.logic.commands.DeleteGroupCommand;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.shared.Title;
import seedu.meeting.testutil.GroupBuilder;

// @@author Derek-Hardy
public class DeleteGroupCommandParserTest {

    private DeleteGroupCommandParser parser = new DeleteGroupCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        Group expectedGroup = new GroupBuilder().withTitle(GROUP_0.getTitle().fullTitle).build();

        assertParseSuccess(parser,
                VALID_GROUP_TITLE_DESC_GROUP_0, new DeleteGroupCommand(expectedGroup));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "@",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Group expectedGroup = new GroupBuilder().withTitle(GROUP_0.getTitle().fullTitle).build();
        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + VALID_GROUP_TITLE_DESC_GROUP_0, new DeleteGroupCommand(expectedGroup));

        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_GROUP_TITLE_DESC_GROUP_2101
                + VALID_GROUP_TITLE_DESC_GROUP_0, new DeleteGroupCommand(expectedGroup));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE);
        // missing name prefix
        assertParseFailure(parser, VALID_GROUP_TITLE_GROUP_0, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_GROUP_TITLE_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_GROUP_TITLE_GROUP_0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGroupCommand.MESSAGE_USAGE));
    }
}
