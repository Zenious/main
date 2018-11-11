package seedu.meeting.model.meeting;

import static seedu.meeting.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.meeting.model.meeting.DateUtil.isDateConsistent;
import static seedu.meeting.model.meeting.DateUtil.isHourWithinBounds;
import static seedu.meeting.model.meeting.DateUtil.isMinuteWithinBounds;
import static seedu.meeting.model.meeting.DateUtil.isYearWithinBounds;

import java.time.Month;
import java.time.Year;

// @@author NyxF4ll
/**
 * TimeStamp represents a specific timing on the calendar.
 */
public class TimeStamp implements Comparable<TimeStamp> {
    public static final String DATE_SPLIT_REGEX = "[:@-]";

    public static final int SPLITTED_YEAR_INDEX = 2;
    public static final int SPLITTED_MONTH_INDEX = 1;
    public static final int SPLITTED_DAY_INDEX = 0;
    public static final int SPLITTED_HOUR_INDEX = 3;
    public static final int SPLITTED_MINUTE_INDEX = 4;
    public static final int EXPECTED_SPLIITTED_LENGTH = 5;

    public static final String MESSAGE_TIMESTAMP_CONSTRAINT = "TimeStamp must follow the following constraints:\n"
            + "1. TimeStamp String must be of the format DD-MM-YYYY@HH:MM\n"
            + "2. Year must be between 0001 and 9999\n"
            + "3. Date must be consistent with the month's length\n"
            + "4. Time of day must be between 0 hour 0 minute to 23 hours 59 minutes\n"
            + "5. Month must be between 1 and 12";

    private final Year year;
    private final Month month;
    private final Integer date;
    private final Integer hour;
    private final Integer minute;

    public TimeStamp(Year year, Month month, Integer date, Integer hour, Integer minute) {
        requireAllNonNull(year, month, date, hour, minute);
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns true if the specified arguments could be combined together to create a valid {@code TimeStamp}.
     */
    public static boolean isValidArgument(Year year, Month month, Integer date, Integer hour, Integer minute) {
        return isDateConsistent(date, month, year)
                && isYearWithinBounds(year)
                && isHourWithinBounds(hour)
                && isMinuteWithinBounds(minute);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || ((other instanceof TimeStamp)
                && (this.compareTo((TimeStamp) other) == 0));
    }

    @Override
    public int compareTo(TimeStamp other) {
        if (year.getValue() - other.year.getValue() != 0) {
            return year.getValue() - other.year.getValue();
        }

        if (month.getValue() - other.month.getValue() != 0) {
            return month.getValue() - other.month.getValue();
        }

        if (date - other.date != 0) {
            return date - other.date;
        }

        if (hour - other.hour != 0) {
            return hour - other.hour;
        }

        return minute - other.minute;
    }

    @Override
    public String toString() {
        return String.format("%d-%d-%d@%02d:%02d", date, month.getValue(), year.getValue(), hour, minute);
    }
}
