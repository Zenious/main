package seedu.meeting.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * An UI component that displays information of a {@code Meeting}
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";

    public final Meeting meeting;

    @FXML
    private HBox meetingCardPane;

    @FXML
    private Label id;

    @FXML
    private Label meetingTitle;

    @FXML
    private Label meetingDescription;

    @FXML
    private Label meetingTime;

    @FXML
    private Label meetingLocation;

    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        id.setText(displayedIndex + ". ");
        meetingTitle.setText(meeting.getTitle().fullTitle);
        meetingDescription.setText(meeting.getDescription().statement);
        meetingTime.setText(meeting.getTime().toString());
        meetingLocation.setText(meeting.getLocation().value);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MeetingCard)) {
            return false;
        }

        MeetingCard card = (MeetingCard) other;
        return id.getText().equals(card.id.getText())
            && meetingTitle.getText().equals(card.meetingTitle.getText())
            && meetingDescription.getText().equals(card.meetingDescription.getText())
            && meetingTime.getText().equals(card.meetingTime.getText())
            && meetingLocation.getText().equals(card.meetingLocation.getText());
    }
}
