package seedu.meeting.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.logic.Logic;

// @@author jeffreyooi
/**
 * The UI component that displays information of groups, meetings and persons in 3 horizontal panes.
 */
public class InformationDisplay extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(InformationDisplay.class);
    private static final String FXML = "InformationDisplay.fxml";

    private final Logic logic;

    private GroupListPanel groupListPanel;
    private PersonListPanel personListPanel;
    private MeetingListPanel meetingListPanel;

    @FXML
    private GridPane gridPane;

    @FXML
    private StackPane groupListPanelPlaceholder;

    @FXML
    private StackPane meetingListPanelPlaceholder;

    @FXML
    private StackPane personListPanelPlaceholder;

    public InformationDisplay(Logic logic) {
        super(FXML);
        this.logic = logic;
        fillInnerParts();
        registerAsAnEventHandler(this);
    }

    /**
     * Fills up all the placeholder of this Ui component
     */
    private void fillInnerParts() {
        groupListPanel = new GroupListPanel(logic.getFilteredGroupList());
        groupListPanelPlaceholder.getChildren().add(groupListPanel.getRoot());

        personListPanel = new PersonListPanel(logic.getSortedPersonList());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        meetingListPanel = new MeetingListPanel(logic.getFilteredMeetingList());
        meetingListPanelPlaceholder.getChildren().add(meetingListPanel.getRoot());
    }
}
