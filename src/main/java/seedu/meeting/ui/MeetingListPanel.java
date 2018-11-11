package seedu.meeting.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.meeting.commons.core.LogsCenter;
import seedu.meeting.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.meeting.commons.events.ui.MeetingPanelSelectionChangedEvent;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * Panel containing the list of meetings.
 */
public class MeetingListPanel extends UiPart<Region> {
    private static final String FXML = "MeetingListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MeetingListPanel.class);

    @FXML
    private ListView<Meeting> meetingListView;

    public MeetingListPanel(ObservableList<Meeting> meetingList) {
        super(FXML);
        setConnections(meetingList);
        registerAsAnEventHandler(this);
    }

    private void setConnections(ObservableList<Meeting> meetingList) {
        meetingListView.setItems(meetingList);
        meetingListView.setCellFactory(listView -> new MeetingListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        meetingListView.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in meeting list panel changed to : '" + newValue + "'");
                raise(new MeetingPanelSelectionChangedEvent(newValue));
            }
        }));
    }

    /**
     * Scrolls to the {@code MeetingCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            meetingListView.scrollTo(index);
            meetingListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToMeetingListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Meeting} using a {@code MeetingCard}
     */
    class MeetingListViewCell extends ListCell<Meeting> {
        @Override
        protected void updateItem(Meeting meeting, boolean empty) {
            super.updateItem(meeting, empty);

            if (empty || meeting == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MeetingCard(meeting, getIndex() + 1).getRoot());
            }
        }
    }
}
